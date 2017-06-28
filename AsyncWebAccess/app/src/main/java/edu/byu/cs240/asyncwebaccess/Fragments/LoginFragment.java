package edu.byu.cs240.asyncwebaccess.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONObject;

import edu.byu.cs240.asyncwebaccess.AsyncTasks.LoginRegisterRequest;
import edu.byu.cs240.asyncwebaccess.AsyncTasks.SyncData;
import edu.byu.cs240.asyncwebaccess.MainActivity;
import edu.byu.cs240.asyncwebaccess.ModelClasses.AuthToken;
import edu.byu.cs240.asyncwebaccess.ModelClasses.ConnectionObject;
import edu.byu.cs240.asyncwebaccess.ModelClasses.LoginObject;
import edu.byu.cs240.asyncwebaccess.ModelClasses.User;
import edu.byu.cs240.asyncwebaccess.R;
import edu.byu.cs240.asyncwebaccess.ServerProxy;

import static edu.byu.cs240.asyncwebaccess.R.id.editEmail;
import static edu.byu.cs240.asyncwebaccess.R.id.editFirstName;
import static edu.byu.cs240.asyncwebaccess.R.id.editLastName;
import static edu.byu.cs240.asyncwebaccess.R.id.editPassword;
import static edu.byu.cs240.asyncwebaccess.R.id.editUsername;

/**
 * Created by corycooper on 3/26/17.
 */

public class LoginFragment extends android.support.v4.app.Fragment {
    private LoginObject loginObject;
    private User newUser;
    private ConnectionObject connectionObject;
    private EditText serverHostField;
    private EditText serverPortField;
    private EditText editUsernameField;
    private EditText editPasswordField;
    private EditText firstNameField;
    private EditText lastNameField;
    private EditText emailField;
    private RadioGroup radioSex;

    private Button loginButton;
    private Button registerButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginObject = new LoginObject();
        newUser = new User();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment, container, false);

        serverHostField = (EditText)v.findViewById(R.id.serverHost);
        serverPortField = (EditText)v.findViewById(R.id.serverPort);
        editUsernameField = (EditText)v.findViewById(editUsername);
        editPasswordField = (EditText)v.findViewById(editPassword);
        firstNameField = (EditText)v.findViewById(editFirstName);
        lastNameField = (EditText)v.findViewById(editLastName);
        emailField = (EditText)v.findViewById(editEmail);

        radioSex = (RadioGroup)v.findViewById(R.id.radioSex);

        loginButton = (Button)v.findViewById(R.id.buttonLogin);
        registerButton = (Button)v.findViewById(R.id.buttonRegister);


        /**
         * User login button
         */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectionObject = new ConnectionObject(serverHostField.getText().toString(), serverPortField.getText().toString(), "POST");
                if (!connectionObject.canStartConnection()) {
                    Toast.makeText(getContext(), "You need to fill in the connection data", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginObject.setUserName(editUsernameField.getText().toString());
                loginObject.setPassword(editPasswordField.getText().toString());

                if (loginObject.validLogin()) {
                    ServerProxy.SERVER_PROXY.setUpConnection(connectionObject);
                    LoginRegisterRequest loginRegisterRequest = new LoginRegisterRequest(new ServerProxy.CallListener() {
                        @Override
                        public void onComplete(JSONObject json) {
                            AuthToken authToken = new AuthToken(json);
                            Toast.makeText(getContext(), "User logged in: " + authToken.getUsername(), Toast.LENGTH_SHORT).show();
                            SyncData syncData = new SyncData(new ServerProxy.CallListener() {
                                @Override
                                public void onComplete(JSONObject json) {
                                    openMapFragment();
                                }
                            });
                            syncData.execute(authToken);
                        }
                    });
                    loginRegisterRequest.loginUser(getContext(), loginObject);

                } else {
                    Toast.makeText(getContext(), "You need to enter your username and password.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**
         * New user registration button
         */
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectionObject = new ConnectionObject(serverHostField.getText().toString(), serverPortField.getText().toString(), "POST");
                if (!connectionObject.canStartConnection()) {
                    Toast.makeText(getContext(), "You need to fill in the connection data", Toast.LENGTH_SHORT).show();
                    return;
                }

                newUser.setUsername(editUsernameField.getText().toString());
                newUser.setPassword(editPasswordField.getText().toString());
                newUser.setEmail(emailField.getText().toString());
                newUser.setFirstName(firstNameField.getText().toString());
                newUser.setLastName(lastNameField.getText().toString());
                int selectedRadioId = radioSex.getCheckedRadioButtonId();
                if (selectedRadioId == R.id.radioMale) {
                    newUser.setGender("m");
                } else {
                    newUser.setGender("f");
                }

                if (newUser.validRegistration()) {
                    ServerProxy.SERVER_PROXY.setUpConnection(connectionObject);
                    LoginRegisterRequest loginRegisterRequest = new LoginRegisterRequest(new ServerProxy.CallListener() {
                        @Override
                        public void onComplete(JSONObject json) {
                            AuthToken authToken = new AuthToken(json);
                            Toast.makeText(getContext(), "User logged in: " + authToken.getUsername(), Toast.LENGTH_SHORT).show();
                            SyncData syncData = new SyncData(new ServerProxy.CallListener() {
                                @Override
                                public void onComplete(JSONObject json) {
                                    openMapFragment();
                                }
                            });
                            syncData.execute(authToken);
                        }
                    });
                    loginRegisterRequest.registerUser(getContext(), newUser);

                } else {
                    Toast.makeText(getContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    private void openMapFragment() {
        ((MainActivity)getActivity()).makeMapVisible();

    }

}
