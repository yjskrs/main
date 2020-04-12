package igrad.model.module;

//@@author dargohzy

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the module prerequisites in a list of module codes.
 * Prerequisites for a specific module are modules that have to be fulfilled
 * before the module is taken
 */
public class ModulePrerequisites extends ModulePrerequisitesOrPreclusions {

    public ModulePrerequisites(List<ModuleCode> moduleCodes) {
        this.moduleCodes = moduleCodes;
    }

    public ModulePrerequisites() {
        this.moduleCodes = new ArrayList<>();
    }

}
