package igrad.services;

//@@author waynewee

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The Request Manager sets up the connection for its children
 */
public abstract class RequestManager {

    protected URL url;
    protected HttpURLConnection connection;

    /**
     * @param urlName url of the site to retrieve data from
     */
    public RequestManager(String urlName) throws IOException {
        url = new URL(urlName);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setConnectTimeout(1000);
        connection.setReadTimeout(5000);
    }

}
