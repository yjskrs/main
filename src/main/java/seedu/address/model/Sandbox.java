package seedu.address.model;
import seedu.address.services.GetRequestManager;
import seedu.address.services.JsonParsedModule;

import java.io.IOException;

public class Sandbox {

    public static void main( String[] args ) throws IOException {
        GetRequestManager grm = new GetRequestManager( "https://api.nusmods.com/2018-2019/modules/ACC1006/index.json" );
        String res = grm.makeRequest();
        JsonParsedModule jpm = JsonParsedModule.initJsonParsedModule( res );
        System.out.println(jpm);
    }

}
