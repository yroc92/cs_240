package edu.byu.cs240.asyncwebaccess.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import edu.byu.cs240.asyncwebaccess.ModelClasses.LoginObject;
import edu.byu.cs240.asyncwebaccess.ModelClasses.User;
import edu.byu.cs240.asyncwebaccess.ServerProxy;

/**
 * Created by corycooper on 3/27/17.
 */

public class LoginRegisterRequest extends AsyncTask<Object, Void, JSONObject> {

    private String url;
    private Context context;
    private ServerProxy.CallListener listener;

    /**
     * Generic constructor
     */
    public LoginRegisterRequest(ServerProxy.CallListener listener) {
        this.listener = listener;
    }

    /**
     * Handles login requests
     * @param context
     * @param credentials
     */
    public void loginUser(Context context, LoginObject credentials) {
        this.url = "/user/login";
        this.context = context;
        // Execute HTTP request in the background
        this.execute(credentials);
    }

    /**
     * Handles new user registration
     * @param context
     * @param userToRegister
     */
    public void registerUser(Context context, User userToRegister) {
        this.url = "/user/register";
        this.context = context;
        // Execute HTTP request in the background
        this.execute(userToRegister);

    }

    @Override
    protected JSONObject doInBackground(Object... params) {
        return ServerProxy.SERVER_PROXY.sendPost(params[0], this.url);
    }

    @Override
    public void onPostExecute(JSONObject json) {
        if (json != null) {
            listener.onComplete(json);

        } else {
            Toast.makeText(this.context, "Invalid Login or Registration", Toast.LENGTH_SHORT).show();
        }


    }
}