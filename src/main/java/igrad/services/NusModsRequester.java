package igrad.services;

import igrad.commons.core.LogsCenter;
import igrad.logic.parser.CliSyntax;
import igrad.services.exceptions.ServiceException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

/**
 * The client of NUSMods which makes requests from NUSMods API
 */
public class NusModsRequester {

    private static final Logger logger = LogsCenter.getLogger(NusModsRequester.class);
    private static final String fileNotFoundMsg = "The specified module could not be"
        + " found on NUS Mods. Please remove the " + CliSyntax.FLAG_AUTO
        + " flag and add the file manually";

    /**
     * A representation of the {@code Module} object, but all attributes are strings
     *
     * @param moduleCode code of module to be fetched from NUSMods API
     * @return a {@code JsonParsedModule} object
     */
    public static JsonParsedModule getModule(String moduleCode) throws IOException, ServiceException {

        String academicYear = getAcademicYear(false);
        GetRequestManager grm = new GetRequestManager(getUrlPath(academicYear, moduleCode));
        String res;
        JsonParsedModule jpm = null;

        try {
            res = grm.makeRequest();
            jpm = JsonParsedModule.initJsonParsedModule(res);
        } catch (FileNotFoundException e1) {
            logger.warning(fileNotFoundMsg);

            academicYear = getAcademicYear(true);
            GetRequestManager grm2 = new GetRequestManager(getUrlPath(academicYear, moduleCode));

            try {
                res = grm2.makeRequest();
                jpm = JsonParsedModule.initJsonParsedModule(res);
            } catch (FileNotFoundException e2) {
                logger.warning(fileNotFoundMsg);
                throw new ServiceException(fileNotFoundMsg, e2);
            }

        } catch (IOException e) {
            logger.warning("Error attempting to read from response stream");
            throw e;
        }

        return jpm;

    }

    private static String getUrlPath(String academicYear, String moduleCode) {
        return Routes.NUS_MODS_ROOT + "/" + academicYear
            + "/" + Routes.NUS_MODS_MODULES_DIR + "/"
            + moduleCode.toUpperCase() + "/"
            + Routes.NUS_MODS_INDEX_JSON;
    }

    private static String getAcademicYear(Boolean getPrevYear) {
        int year = Calendar.getInstance().get(Calendar.YEAR);

        Calendar startOfSem = Calendar.getInstance();
        startOfSem.set(Calendar.YEAR, year);
        startOfSem.set(Calendar.MONTH, Calendar.AUGUST);
        startOfSem.set(Calendar.DAY_OF_MONTH, 9);

        Date startOfSemDate = startOfSem.getTime();

        Calendar now = Calendar.getInstance();
        Date nowDate = now.getTime();

        if (getPrevYear) {
            if (nowDate.before(startOfSemDate)) {
                return (year - 2) + "-" + (year - 1);
            } else {
                return (year - 1) + "-" + (year - 2);
            }
        } else {
            if (nowDate.before(startOfSemDate)) {
                return (year - 1) + "-" + year;
            } else {
                return year + "-" + (year - 1);
            }
        }


    }

}
