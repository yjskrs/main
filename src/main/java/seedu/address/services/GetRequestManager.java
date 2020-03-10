package seedu.address.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetRequestManager extends RequestManager {

    public GetRequestManager( String url ) throws IOException {
        super( url );
        connection.setRequestMethod( "GET" );
    }

    public String makeRequest() throws IOException {

        int status = connection.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader( connection.getInputStream() )
        );

        String inputLine;
        StringBuilder content = new StringBuilder(  );
        while ((inputLine = in.readLine()) != null ){
            content.append(inputLine);
        }

        in.close();

        connection.disconnect();

        return content.toString();

    }

}
