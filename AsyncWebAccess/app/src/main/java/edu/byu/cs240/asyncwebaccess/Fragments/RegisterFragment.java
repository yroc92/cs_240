package edu.byu.cs240.asyncwebaccess.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import edu.byu.cs240.asyncwebaccess.ModelClasses.User;
import edu.byu.cs240.asyncwebaccess.R;
import edu.byu.cs240.asyncwebaccess.ServerProxy;

/**
 * Created by corycooper on 3/26/17.
 */

public class RegisterFragment extends Fragment {

    private User newUser;
    private EditText editUsername;
    private EditText editPassword;
    private EditText editEmail;
    private EditText editFirstName;
    private EditText editLastName;
    private RadioGroup radioSex;
    private Button registerUserButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newUser = new User();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.register_fragment, container, false);

        editUsername = (EditText)v.findViewById(R.id.editUsername);
        editPassword = (EditText)v.findViewById(R.id.editPassword);
        editEmail = (EditText)v.findViewById(R.id.editEmail);
        editFirstName = (EditText)v.findViewById(R.id.editFirstName);
        editLastName = (EditText)v.findViewById(R.id.editLastName);
        radioSex = (RadioGroup)v.findViewById(R.id.radioSex);

        registerUserButton = (Button)v.findViewById(R.id.buttonRegister);
        registerUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newUser.setUsername(editUsername.getText().toString());
                newUser.setPassword(editPassword.getText().toString());
                newUser.setEmail(editEmail.getText().toString());
                newUser.setFirstName(editFirstName.getText().toString());
                newUser.setLastName(editLastName.getText().toString());
                int selectedRadioId = radioSex.getCheckedRadioButtonId();
                if (selectedRadioId == R.id.radioMale) {
                    newUser.setGender("m");
                } else {
                    newUser.setGender("f");
                }
                if (newUser.validRegistration()) {
                    ServerProxy.SERVER_PROXY.registerUser(newUser, getActivity());
//                    String toastString = "First Name: " + newUser.getFirstName() + "\n" + "Last Name: " + newUser.getLastName();
//                    Toast.makeText(getContext(), toastString, Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(getContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    public void registerUser(View view) {
        Toast.makeText(getContext(), "You'd like to register?", Toast.LENGTH_SHORT).show();
    }
}
