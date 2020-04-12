package igrad.model.module;

//@@author dargohzy

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the module preclusions in a list of module codes.
 * Preclusions for a specific module are modules that cannot be taken
 * if the module is already taken
 */
public class ModulePreclusions extends ModulePrerequisitesOrPreclusions {

    public ModulePreclusions(List<ModuleCode> moduleCodes) {
        this.moduleCodes = moduleCodes;
    }

    public ModulePreclusions() {
        this.moduleCodes = new ArrayList<>();
    }

}
