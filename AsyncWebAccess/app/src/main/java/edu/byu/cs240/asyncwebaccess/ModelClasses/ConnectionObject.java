package edu.byu.cs240.asyncwebaccess.ModelClasses;

/**
 * Created by corycooper on 4/17/17.
 */

public class ConnectionObject {
    private String serverHost;
    private String serverPort;
    private String requestType;

    public ConnectionObject(String serverHost, String serverPort, String requestType) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.requestType = requestType;
    }

    /**
     * Determines whether the object has all values filled.
     * @return a boolean to determine validity of the object's content.
     */
    public boolean canStartConnection() {
        if (this.serverHost.length() == 0) return false;
        if (this.serverPort.length() == 0) return false;
        if (this.requestType.length() == 0) return false;
        return true;
    }

    /**
     * Similar to canStartConnection(), but doesn't care about the host or port
     * @return
     */
    public boolean isValidRequest() {
        if (this.requestType.length() == 0) return false;
        return true;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

}
