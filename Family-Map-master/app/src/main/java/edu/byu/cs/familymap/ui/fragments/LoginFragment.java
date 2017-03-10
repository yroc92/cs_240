package edu.byu.cs.familymap.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.byu.cs.familymap.R;
import edu.byu.cs.familymap.model.Login;
import edu.byu.cs.familymap.model.Model;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    private static String userName;

    private static String password;

    private static String serverHost;

    private static int serverPort;

    private Login loginObject;

    private EditText userNameEditText;

    private EditText passwordEditText;

    private EditText serverHostEditText;

    private EditText serverPortEditText;

    private Button loginButton;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param loginObject this is the object that contains the onLogin credentials.
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance(Login loginObject) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(userName, loginObject.getUserName());
        args.putString(password, loginObject.getPassword());
        args.putString(serverHost, loginObject.getServerHost());
        args.putInt(String.valueOf(serverPort), loginObject.getServerPort());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            loginObject = new Login();
            loginObject.setUserName(getArguments().getString(userName));
            loginObject.setPassword(getArguments().getString(password));
            loginObject.setServerHost(getArguments().getString(serverHost));
            loginObject.setServerPort(getArguments().getInt(String.valueOf(serverPort)));
        }
    }

    private void onButtonClicked() {
        Log.i("Log in", "Log in Button pressed.");

        Model.getCurrentPerson().setLogin(getLogInCredentials());

        Model.getServer().login();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        loginButton = (Button) v.findViewById(R.id.logInButton);
        loginButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onButtonClicked();
                    }
                }
        );

        userNameEditText = (EditText) v.findViewById(R.id.userName);
        passwordEditText = (EditText) v.findViewById(R.id.password);
        serverHostEditText = (EditText) v.findViewById(R.id.serverHost);
        serverPortEditText = (EditText) v.findViewById(R.id.serverPort);

        return v;
    }

    public Login getLogInCredentials() {
        loginObject.setUserName(userNameEditText.getText().toString());
        loginObject.setPassword(passwordEditText.getText().toString());
        loginObject.setServerHost(serverHostEditText.getText().toString());
        loginObject.setServerPort(Integer.parseInt(serverPortEditText.getText().toString()));

        return loginObject;
    }

    /**
     * This is a helper method that will log the user in synchronously.
     * @return true if the provided credentials are valid, false if the provided credentials
     * are not valid.
     */
    public void onLogin(boolean loggedIn) {
        Toast loginStatus;
        if(loggedIn) {
            loginStatus = Toast.makeText(
                    this.getContext(),
                    "Authorization has succeeded",
                    Toast.LENGTH_SHORT
            );
            Model.getMainActivity().onLogin();
        }
        else {
            loginStatus = Toast.makeText(
                    this.getContext(),
                    "Authorization has failed",
                    Toast.LENGTH_SHORT
            );
        }
        loginStatus.show();
    }
}
