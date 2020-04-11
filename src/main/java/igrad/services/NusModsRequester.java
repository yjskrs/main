package igrad.services;

//@@author waynewee

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import igrad.commons.core.LogsCenter;
import igrad.logic.parser.CliSyntax;

/**
 * The client of NUSMods which makes requests from NUSMods API
 */
public class NusModsRequester {

    private static final Logger logger = LogsCenter.getLogger(NusModsRequester.class);
    private static final String fileNotFoundMsg = "The specified module could not be"
        + " found on NUSMods. Please remove the " + CliSyntax.FLAG_AUTO
        + " flag and add the file manually";

    /**
     * A representation of the {@code Module} object, but all attributes are strings
     *
     * @param moduleCode code of module to be fetched from NUSMods API
     * @return a {@code JsonParsedModule} object
     */
    public static JsonParsedModule getModule(String moduleCode) throws IOException {

        String academicYear = getAcademicYear(true);
        String urlPath = getUrlPath(academicYear, moduleCode);
        GetRequestManager getRequestManager = new GetRequestManager(urlPath);
        String res;
        JsonParsedModule jsonParsedModule;

        try {
            res = getRequestManager.makeRequest();
            jsonParsedModule = JsonParsedModule.initJsonParsedModule(res);
        } catch (IOException e) {
            logger.warning(e.getMessage());
            throw e;
        }

        return jsonParsedModule;

    }

    /**
     * Formulates the URL to retrieve from
     *
     * @param academicYear formatted as YYYY-YYYY
     * @param moduleCode   {@code moduleCode}
     */
    private static String getUrlPath(String academicYear, String moduleCode) {
        return Routes.NUS_MODS_ROOT + "/" + academicYear
            + "/" + Routes.NUS_MODS_MODULES_DIR + "/"
            + moduleCode.toUpperCase() + "/"
            + Routes.NUS_MODS_INDEX_JSON;
    }

    /**
     * Gets the current academic year based on:
     * startOfSem: 9th August
     * If the current date is before the startOfSem, get previous to current year,
     * else, get current to next year.
     */
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
