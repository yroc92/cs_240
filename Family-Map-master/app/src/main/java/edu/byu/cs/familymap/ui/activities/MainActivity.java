package edu.byu.cs.familymap.ui.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import edu.byu.cs.familymap.R;
import edu.byu.cs.familymap.model.Login;
import edu.byu.cs.familymap.model.Model;
import edu.byu.cs.familymap.ui.fragments.LoginFragment;
import edu.byu.cs.familymap.ui.fragments.MapFragment;

public class MainActivity extends AppCompatActivity {

    private LoginFragment loginFragment;

    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null) {

        }

        FragmentManager fm = this.getSupportFragmentManager();
        if(Model.getCurrentPerson() == null) {
            Model.init(this);

            loginFragment = (LoginFragment) fm.findFragmentById(R.id.loginFragment);
            if (loginFragment == null) {
                loginFragment = LoginFragment.newInstance(new Login());
                fm.beginTransaction()
                        .add(R.id.loginFragment, loginFragment)
                        .commit();
            }
        }

        mapFragment = (MapFragment) fm.findFragmentById(R.id.mapFragment);

        if(mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            if(Model.getCurrentPerson().getLogin() != null) {
                fm.beginTransaction()
                        .add(R.id.mapFragment, mapFragment)
                        .commit();
            }
        }
    }

    public void onLogin() {
        Model.getServer().getCurrentPerson();
        Model.getServer().getEvents(false);
    }

    public void onEventsLoaded() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.loginFragment, mapFragment, "mapFragment");
        transaction.commit();
    }

    public void onPeopleLoaded() {
        Toast peopleLoaded = Toast.makeText(
                this,
                "Welcome " +
                        Model.getCurrentPerson().getFirstName() +
                        " " + Model.getCurrentPerson().getLastName() +
                        "!",
                Toast.LENGTH_SHORT
        );
        peopleLoaded.show();
        Model.getDataImporter().generateData();
    }

    public LoginFragment getLoginFragment() {
        return loginFragment;
    }
}
