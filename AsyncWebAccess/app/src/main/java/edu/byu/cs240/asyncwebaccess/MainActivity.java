package edu.byu.cs240.asyncwebaccess;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.byu.cs240.asyncwebaccess.Fragments.LoginFragment;

public class MainActivity extends AppCompatActivity {

//    private ProgressBar progressBar;
    private TextView totalSizeTextView;
//    private Button downloadButton;

    private Button loginButton;
    private Button registerButton;
    private RelativeLayout map;
    private RelativeLayout bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        map = (RelativeLayout)findViewById(R.id.mapContainer);
        bottomBar = (RelativeLayout)findViewById(R.id.barBottom);

        // Deal with the fragments
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        LoginFragment loginFragment = new LoginFragment();
        if (fragment == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, loginFragment)
                    .commit();
        }

    }

    public void changeFragment(Fragment newFragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newFragment).commit();

//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragment_container, newFragment);
//        transaction.commit();
//        getSupportFragmentManager().executePendingTransactions();
    }

    public void makeMapVisible() {

    }
}
