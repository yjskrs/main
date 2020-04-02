package igrad.logic.parser.module;

import java.util.Arrays;

/**
 * Parser for modules' preclusion data from NUSMods.
 */
public class ModulePreclusionParser {

    private String preclusionString;
    private String[] preclusionModules;

    public ModulePreclusionParser(String preclusionString) {
        this.preclusionString = preclusionString;
        this.preclusionModules = splitPreclusionModules(this.preclusionString);
    }

    /**
     * Returns a String array of preclusion module codes in String form from data.
     */
    public String[] splitPreclusionModules(String preclusionModulesString) {
        String[] preclusionModules = preclusionModulesString.split(" ");
        preclusionModules = Arrays.stream(preclusionModules).filter(x -> isModule(x)).toArray(String[]::new);
        preclusionModules = Arrays.stream(preclusionModules)
                .map(x -> removeModulePunctuation(x)).toArray(String[]::new);

        return preclusionModules;
    }

    /**
     * Verifies if String given is a valid module code.
     */
    private boolean isModule(String module) {
        int numberCount = 0;
        char[] chars = module.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isDigit(chars[i])) {
                numberCount++;
            }
        }

        if (numberCount == 4) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes punctuation from the module code String given.
     */
    private String removeModulePunctuation(String module) {
        if (module.contains(",") || module.contains(".")) {
            return module.substring(0, module.length() - 2);
        } else {
            return module;
        }
    }

    public String[] getPreclusionModules() {
        return preclusionModules;
    }
}
