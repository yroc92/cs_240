package edu.byu.cs.familymap.ui.fragments;

import android.graphics.AvoidXfermode;
import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.IconTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import edu.byu.cs.familymap.R;
import edu.byu.cs.familymap.model.Event;
import edu.byu.cs.familymap.model.Filter;
import edu.byu.cs.familymap.model.Model;
import edu.byu.cs.familymap.model.Person;
import edu.byu.cs.familymap.ui.activities.FilterActivity;
import edu.byu.cs.familymap.ui.activities.MainActivity;
import edu.byu.cs.familymap.ui.activities.PersonActivity;
import edu.byu.cs.familymap.ui.activities.SearchActivity;
import edu.byu.cs.familymap.ui.activities.SettingsActivity;

public class MapFragment extends Fragment {
    private LatLng currentLocation;

    private String selectedEventId;

    private GoogleMap googleMap;

    private RelativeLayout personInfoLayout;

    private Polyline lifeStoryLine;

    private Polyline spouseLine;

    private Polyline familyTreeLine;

    public static MapFragment newInstance() {
        MapFragment mapFragment = new MapFragment();
        mapFragment.currentLocation = null;
        return mapFragment;
    }

    public static MapFragment newInstance(Event event) {
        MapFragment mapFragment = new MapFragment();
        mapFragment.currentLocation = event.getPosition();
        mapFragment.selectedEventId = event.getEventId();
        Model.getPeople().get(
                Model.getEvents().get(event.getEventId()).getPersonId()
        ).generateLines(event.getEventId());
        return mapFragment;
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setHasOptionsMenu(true);

        SupportMapFragment supportMapFragment = SupportMapFragment.newInstance();

        supportMapFragment.getMapAsync(
                new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap gMap) {
                        googleMap = gMap;

                        generateMarkers();

                        if (currentLocation != null) {
                            Log.i("MapFragment", "Moving camera to selected event");
                            CameraUpdate center =
                                    CameraUpdateFactory.newLatLng(currentLocation);
                            CameraUpdate zoom =
                                    CameraUpdateFactory.zoomTo(5);
                            googleMap.moveCamera(center);
                            googleMap.animateCamera(zoom);
                            displaySelectedEvent(selectedEventId);
                        }

                        googleMap.setMapType(Model.getSettings().getCurrentMapType());

                        googleMap.setOnMarkerClickListener(
                                new GoogleMap.OnMarkerClickListener() {
                                    @Override
                                    public boolean onMarkerClick(Marker marker) {
                                        String selectedEventId = Model.getEventMarkersToEvents().get(marker);
                                        if (selectedEventId == null) {
                                            Log.d("MarkerClickListener", "selectedEventId is null");
                                        } else {
                                            Model.setFocusedPerson(
                                                    Model.getPeople().get(
                                                            Model.getEvents().get(selectedEventId).getPersonId()
                                                    )
                                            );
                                            assert Model.getFocusedPerson() != null;
                                            Model.getFocusedPerson().generateFamily();
                                            Model.getFocusedPerson().generateEvents();
                                            Model.getFocusedPerson().generateLines(selectedEventId);
                                            displaySelectedEvent(selectedEventId);
                                        }
                                        return false;
                                    }
                                }
                        );
                    }
                }
        );

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.map, supportMapFragment).commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        Filter.applyFilters();
        if(googleMap != null) {
            googleMap.setMapType(Model.getSettings().getCurrentMapType());
        }
        if(lifeStoryLine != null) {
            lifeStoryLine.setVisible(Model.getSettings().isLifeStoryLineOn());
            lifeStoryLine.setColor(Model.getSettings().getCurrentLifeStoryLineColor());
        }
        if(spouseLine != null) {
            spouseLine.setVisible(Model.getSettings().isSpouseLineOn());
            spouseLine.setColor(Model.getSettings().getCurrentSpouseLineColor());
        }
        if(familyTreeLine != null) {
            familyTreeLine.setVisible(Model.getSettings().isFamilyTreeLineOn());
            familyTreeLine.setColor(Model.getSettings().getCurrentFamilyTreeLineColor());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        personInfoLayout = (RelativeLayout) v.findViewById(R.id.mapPersonInfo);
        personInfoLayout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), PersonActivity.class));
                    }
                }
        );
        return v;
    }

    private void generateMarkers() {
        for(Map.Entry<String, Event> entry : Model.getEvents().entrySet()) {
            final int color = getEventColor(entry.getValue());

            IconDrawable icon = new IconDrawable(getActivity(), Iconify.IconValue.fa_map_marker) {
                @Override
                public void draw(Canvas canvas) {
                        TextPaint paint = new TextPaint();
                        paint.setTypeface(Iconify.getTypeface(getContext()));
                        paint.setStyle(Paint.Style.FILL_AND_STROKE);
                        paint.setTextAlign(Paint.Align.CENTER);
                        paint.setUnderlineText(false);

                        paint.setColor(color);

                        paint.setAntiAlias(true);
                        paint.setTextSize(getBounds().height());
                        Rect textBounds = new Rect();
                        String textValue = String.valueOf(Iconify.IconValue.fa_map_marker.character());
                        paint.getTextBounds(textValue, 0, 1, textBounds);
                        float textBottom = (getBounds().height() - textBounds.height()) / 2f + textBounds.height() - textBounds.bottom;
                        canvas.drawText(textValue, getBounds().width() / 2f, textBottom, paint);
                }
            };

            icon.sizeDp(40);
            Canvas canvas = new Canvas();
            Bitmap bitmap = Bitmap.createBitmap(icon.getIntrinsicWidth(), icon.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            canvas.setBitmap(bitmap);
            icon.draw(canvas);
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);

            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(entry.getValue().getPosition())
                    .icon(bitmapDescriptor)
                .alpha(1));
            Model.getEventMarkersToEvents().put(marker, entry.getValue().getEventId());
            Model.getPeopleToEventMarkers().put(entry.getValue().getPersonId(), marker);
        }
        Filter.applyFilters();
    }

    private int getEventColor(Event event) {
        int hashedDescription = event.getDescription().hashCode();

        String color = "#" + Integer.toHexString(Math.abs(hashedDescription & 0xFFFFFF));
        if(color.length() < 7) {
            color = color + "0";
        }

        try {
            return Color.parseColor(color);
        }
        catch (Exception e) {
            Log.e("Color Gen", e.getMessage(), e);
            return Color.parseColor("gray");
        }
    }

    private void displaySelectedEvent(String eventId) {
        assert eventId.length() > 0;
        Event selectedEvent = Model.getEvents().get(eventId);

        assert selectedEvent != null;

        Log.d("MapFragment", "selected event is: " + eventId);

        Person person = Model.getPeople().get(selectedEvent.getPersonId());

        TextView name = (TextView) getActivity().findViewById(R.id.name);
        name.setText(person.getFullName());

        TextView eventInfo = (TextView) getActivity().findViewById(R.id.eventInfo);
        eventInfo.setText(selectedEvent.getDescription() + ": ");
        eventInfo.append(selectedEvent.getCity() + ", ");
        eventInfo.append(selectedEvent.getCountry() + " (");
        eventInfo.append(selectedEvent.getYear() + ")");

        IconTextView image = (IconTextView) getActivity().findViewById(R.id.image);
        if(person.isMale()) {
            image.setText("{fa-male}");
            image.setTextColor(getResources().getColor(R.color.male, getActivity().getTheme()));
        }
        else {
            image.setText("{fa-female}");
            image.setTextColor(getResources().getColor(R.color.female, getActivity().getTheme()));
        }
        clearPolyLines();
        addPolyLines(person);
    }

    private void clearPolyLines() {
        if(lifeStoryLine != null) {
            lifeStoryLine.remove();
        }
        if(familyTreeLine != null) {
            familyTreeLine.remove();
        }
        if(spouseLine != null) {
            spouseLine.remove();
        }
    }

    private void addPolyLines(Person person) {
        if(Model.getSettings().isLifeStoryLineOn()) {
            lifeStoryLine = googleMap.addPolyline(person.getLifeStoryLine());
        }
        if(Model.getSettings().isFamilyTreeLineOn()) {
            familyTreeLine = googleMap.addPolyline(person.getFamilyTreeLine());
        }
        if(Model.getSettings().isSpouseLineOn() && person.getSpouseId() != null) {
            spouseLine = googleMap.addPolyline(person.getSpouseLine());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if(currentLocation == null) {
            inflater.inflate(R.menu.fragment_map_menu, menu);

            MenuItem searchMenuItem = menu.findItem(R.id.search);
            searchMenuItem.setIcon(
                    new IconDrawable(getActivity(), Iconify.IconValue.fa_search)
                            .actionBarSize()
                            .colorRes(R.color.menuItemColor)
            );

            MenuItem filterMenuItem = menu.findItem(R.id.filter);
            filterMenuItem.setIcon(
                    new IconDrawable(getActivity(), Iconify.IconValue.fa_filter)
                            .actionBarSize()
                            .colorRes(R.color.menuItemColor)
            );

            MenuItem settingsMenuItem = menu.findItem(R.id.settings);
            settingsMenuItem.setIcon(
                    new IconDrawable(getActivity(), Iconify.IconValue.fa_gear)
                            .actionBarSize()
                            .colorRes(R.color.menuItemColor)
            );
        }
        else {
            inflater.inflate(R.menu.fragment_go_to_top, menu);

            MenuItem goToTopMenuItem = menu.findItem(R.id.goToTopButton);
            goToTopMenuItem.setIcon(
                    new IconDrawable(getActivity(), Iconify.IconValue.fa_angle_double_up)
                            .actionBarSize()
                            .colorRes(R.color.menuItemColor)
            );
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.filter:
                startActivity(new Intent(getActivity(), FilterActivity.class));
                break;
            case R.id.search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                break;
            case R.id.goToTopButton:
                startActivity(new Intent(getActivity(), MainActivity.class));
                break;
        }
        return true;
    }
}
