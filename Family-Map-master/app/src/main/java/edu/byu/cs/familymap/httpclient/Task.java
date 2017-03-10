package edu.byu.cs.familymap.httpclient;

import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.familymap.model.Login;
import edu.byu.cs.familymap.model.Model;

/**
 * Created by cole on 3/25/16.
 */
public class Task extends AsyncTask<URL, Void, List<String>>{
    public interface TaskListener {
        boolean onFinished(List<String> result);
    }

    private final TaskListener taskListener;

    private String requestMethod;

    public Task(TaskListener taskListener, String requestMethod) {
        this.taskListener = taskListener;
        this.requestMethod = requestMethod;
    }

    @Override
    public List<String> doInBackground(URL... urls) {
        List<String> results = new ArrayList();
        Login login = Model.getCurrentPerson().getLogin();
        for (URL url : urls) {
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(requestMethod);
                connection.setConnectTimeout(5000);
                if(requestMethod.equals("POST")) {
                    connection.setDoOutput(true);
                    connection.connect();

                    OutputStream requestBody = connection.getOutputStream();
                    requestBody.write(login.toJSON().getBytes());
                    requestBody.close();
                }
                else {
                    connection.setRequestProperty("Authorization", login.getAuthToken());
                    connection.connect();
                }

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream responseBody = connection.getInputStream();

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int length;

                    while((length = responseBody.read(buffer)) != -1) {
                        byteArrayOutputStream.write(buffer, 0, length);
                    }

                    results.add(byteArrayOutputStream.toString());
                }
                else {
                    Model.getSettings().resyncFailed();
                    return null;
                }


            }
            catch (Exception e) {
                Log.e("AsyncTask", e.getMessage(), e);
                Model.getSettings().resyncFailed();
            }
        }

        return results;
    }

    @Override
    protected void onPostExecute(List<String> results) {
        super.onPostExecute(results);

        assert taskListener != null;

        if(this.taskListener != null) {
            this.taskListener.onFinished(results);
        }
    }
}
