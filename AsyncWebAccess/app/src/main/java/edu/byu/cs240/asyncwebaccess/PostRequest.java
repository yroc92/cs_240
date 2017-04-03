package edu.byu.cs240.asyncwebaccess;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import java.net.URL;

import edu.byu.cs240.asyncwebaccess.ModelClasses.User;

/**
 * Created by corycooper on 3/27/17.
 */

public class PostRequest extends AsyncTask<Object, Void, User> {

    private String url;
    private FragmentActivity activity;

    public PostRequest(String url, FragmentActivity activity) {
        this.url = url;
        this.activity = activity;
    }

    @Override
    protected User doInBackground(Object... params) {
//        Call to proxy
        Object registeredUser = null;
        registeredUser = ServerProxy.SERVER_PROXY.send(params[0], this.url);
        return (User)registeredUser;
    }
//    @Override
    public void onPostExecute(User loggedInUser) {
        if (loggedInUser != null && this.url.contains("login")) {
            String toastString = "First Name: " + loggedInUser.getFirstName() + "\n" + "Last Name: " + loggedInUser.getLastName();
            Toast.makeText(this.activity, toastString, Toast.LENGTH_SHORT).show();
        } else if (loggedInUser == null && this.url.contains("login")) {
            String toastString = "User not found";
            Toast.makeText(this.activity, toastString, Toast.LENGTH_SHORT).show();
        } else if (loggedInUser == null && this.url.contains("register")){
            String toastString = "User already Exists";
            Toast.makeText(this.activity, toastString, Toast.LENGTH_SHORT).show();
        } else {
            String toastString = "First Name: " + loggedInUser.getFirstName() + "\n" + "Last Name: " + loggedInUser.getLastName();
            Toast.makeText(activity, toastString, Toast.LENGTH_SHORT).show();
        }
    }
}