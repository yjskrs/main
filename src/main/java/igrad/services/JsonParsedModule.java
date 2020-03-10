package igrad.services;

/**
 * A buffer for the Module class which additionally handles the parsing of a JSON response string.
 */
public class JsonParsedModule {

    private String title;
    private String moduleCode;
    private String credits;
    private String description;

    public JsonParsedModule(String title, String moduleCode, String credits, String description) {
        this.title = title;
        this.moduleCode = moduleCode;
        this.credits = credits;
        this.description = description;
    }

    /**
     * Parses a JSON response string. This function is rudimentary and can only handle
     * single-level JSON strings with no nesting.
     *
     * @param data A JSON response string
     * @return a JsonParsedModule object with {@code title},
     * {@code moduleCode},
     * {@code credits} and
     * {@code description} attributes
     */
    public static JsonParsedModule initJsonParsedModule(String data) {

        String title = "";
        String moduleCode = "";
        String credits = "";
        String description = "";

        data = data.substring(1, data.length() - 2);
        String[] keyValueStrArray = data.split("\",");
        for (String keyValueStr : keyValueStrArray) {

            String[] keyValueStrSplit = keyValueStr.split(":");

            String key = keyValueStrSplit[0].substring(1, keyValueStrSplit[0].length() - 1);

            StringBuilder value = new StringBuilder();
            int i = 0;

            while (i < keyValueStrSplit.length) {
                if (i > 0) {
                    value.append(keyValueStrSplit[i]);
                }
                i++;
            }

            String valueStr = value.length() > 0 ? value.toString().substring(1) : "";

            System.out.println(key + valueStr);

            switch (key) {
            case "ModuleCode":
                moduleCode = valueStr;
                break;
            case "ModuleTitle":
                title = valueStr;
                break;
            case "ModuleCredit":
                credits = valueStr;
                break;
            case "ModuleDescription":
                description = valueStr;
                break;
            default:
                break;
            }

        }

        return new JsonParsedModule(title, moduleCode, credits, description);
    }

    public String getTitle() {
        return title;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getCredits() {
        return credits;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Module Title: " + title
            + "\nModule Code: " + moduleCode
            + "\nModular Credits: " + credits
            + "\nDescription: " + description;
    }
}
