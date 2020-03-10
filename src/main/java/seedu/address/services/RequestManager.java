package seedu.address.services;

import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;

/**
 * The Request Manager sets up the connection for its children
 */
public abstract class RequestManager {

    URL url;
    HttpURLConnection connection;

    /**
     *
     * @param urlName url of the site to retrieve data from
     */
    public RequestManager(String urlName) throws IOException {
        url = new URL(urlName);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
    }

}
