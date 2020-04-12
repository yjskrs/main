package igrad.model.module;

//@@author dargohzy

import java.util.List;

/**
 * Checks if the current module is valid in terms of
 * prerequisites and preclusions
 */
public abstract class ModulePrerequisitesOrPreclusions {

    protected List<ModuleCode> moduleCodes;

    public List<ModuleCode> getModuleCodes() {
        return moduleCodes;
    }

    public boolean isEmpty() {
        return moduleCodes.size() == 0;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        for (ModuleCode moduleCode : moduleCodes) {
            string.append(moduleCode.value).append("\n");
        }

        return string.toString();
    }

}
