package igrad.logic.parser.module;

import java.util.Arrays;

/**
 * Parser for modules' preclusion data from NUSMods.
 */
public class ModulePrerequisiteParser {

    private String prerequisiteString;
    private String[] prerequisiteModules;

    public ModulePrerequisiteParser(String prerequisiteString) {
        this.prerequisiteString = prerequisiteString;
        this.prerequisiteModules = splitPreclusionModules(this.prerequisiteString);
    }

    /**
     * Returns a String array of prerequisite module codes in String form from data.
     */
    public String[] splitPreclusionModules(String prerequisiteModulesString) {
        String[] prerequisiteModules = prerequisiteModulesString.split(" ");
        prerequisiteModules = Arrays.stream(prerequisiteModules).filter(x -> isModule(x)).toArray(String[]::new);
        prerequisiteModules = Arrays.stream(prerequisiteModules)
                .map(x -> removeModulePunctuation(x)).toArray(String[]::new);

        return prerequisiteModules;
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

    public String[] getPrerequisiteModules() {
        return prerequisiteModules;
    }
}
