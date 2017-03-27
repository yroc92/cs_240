package edu.byu.cs240.asyncwebaccess;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;

import edu.byu.cs240.asyncwebaccess.Fragments.LoginFragment;
import edu.byu.cs240.asyncwebaccess.Fragments.RegisterFragment;

public class MainActivity extends FragmentActivity {

//    private ProgressBar progressBar;
    private TextView totalSizeTextView;
//    private Button downloadButton;

    private Button loginButton;
    private Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Deal with the fragments
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, new LoginFragment())
                    .commit();
        }

        loginButton = (Button)findViewById((R.id.button_login_fragment));
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new LoginFragment(), fragmentManager);
            }
        });
        registerButton = (Button)findViewById((R.id.button_register_fragment));
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new RegisterFragment(), fragmentManager);
            }
        });

    }

    private void changeFragment(Fragment newFragment, FragmentManager fm) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.commit();
        fm.executePendingTransactions();
    }

    public class DownloadTask extends AsyncTask<URL, Integer, Long> {

        protected Long doInBackground(URL... urls) {

            HttpClient httpClient = new HttpClient();

            long totalSize = 0;

            for (int i = 0; i < urls.length; i++) {

                String urlContent = httpClient.getUrl(urls[i]);
                if (urlContent != null) {
                    totalSize += urlContent.length();
                }

                int progress = 0;
                if (i == urls.length - 1) {
                    progress = 100;
                }
                else {
                    float cur = i + 1;
                    float total = urls.length;
                    progress = (int)((cur / total) * 100);
            }
                publishProgress(progress);
            }

            return totalSize;
        }

        protected void onPostExecute(Long result) {
            totalSizeTextView.setText("Total Size: " + result);
        }
    }
}
