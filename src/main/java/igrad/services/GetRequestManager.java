package igrad.services;

//@@author waynewee

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The handler for all get requests
 */
public class GetRequestManager extends RequestManager {

    public GetRequestManager(String url) throws IOException {
        super(url);
        connection.setRequestMethod("GET");
    }

    /**
     * Makes a request to the web-page specified by {@code url}
     *
     * @return the response converted to a string
     */
    public String makeRequest() throws IOException {

        connection.getResponseCode();

        BufferedReader in = new BufferedReader(
            new InputStreamReader(connection.getInputStream())
        );

        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();

        connection.disconnect();

        return content.toString();

    }

}
