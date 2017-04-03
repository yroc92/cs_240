package edu.byu.cs240.asyncwebaccess.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.byu.cs240.asyncwebaccess.ModelClasses.LoginObject;
import edu.byu.cs240.asyncwebaccess.ModelClasses.User;
import edu.byu.cs240.asyncwebaccess.R;
import edu.byu.cs240.asyncwebaccess.ServerProxy;

/**
 * Created by corycooper on 3/26/17.
 */

public class LoginFragment extends android.support.v4.app.Fragment{
    private LoginObject loginObject;
    private EditText editUsernameField;
    private EditText editPasswordField;
    private Button loginButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginObject = new LoginObject();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment, container, false);

        loginButton = (Button)v.findViewById(R.id.buttonLogin);
        editUsernameField = (EditText)v.findViewById(R.id.editUsername);
        editPasswordField = (EditText)v.findViewById(R.id.editPassword);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginObject.setUserName(editUsernameField.getText().toString());
                loginObject.setPassword(editPasswordField.getText().toString());
                if (loginObject.validLogin()) {
                    ServerProxy.SERVER_PROXY.loginUser(loginObject, getActivity());
//                    String toastString = "First Name: " + loggedInUser.getFirstName() + "\n" + "Last Name: " + loggedInUser.getLastName();
//                    Toast.makeText(getContext(), toastString, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getContext(), "This is a valid log in", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "You need to enter your username and password.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

}
