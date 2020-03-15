package igrad.model.requirement;

import static java.util.Objects.requireNonNull;

import java.util.List;

import igrad.model.module.Module;
import igrad.model.module.UniqueModuleList;
import javafx.collections.ObservableList;

/**
 * Contains data at the requirement level.
 */
public class Requirement implements ReadOnlyRequirement {
    private final UniqueModuleList modules = new UniqueModuleList();
    private Title title;

    public Requirement(Title title) {
        requireNonNull(title);
        this.title = title;
    }

    public Requirement(ReadOnlyRequirement toBeCopied) {
        resetData(toBeCopied);
    }

    // requirement-level operations

    /**
     * Replaces the title of the requirement with {@code newTitle}.
     */
    public void setTitle(Title newTitle) {
        this.title = newTitle;
    }

    /**
     * Replaces the contents of the module list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
    }

    /**
     * Resets the existing data of this {@code Requirement} with {@code newData}.
     */
    public void resetData(ReadOnlyRequirement newData) {
        requireNonNull(newData);

        setTitle(newData.getTitle());
        setModules(newData.getModuleList());
    }

    // module-level operations

    /**
     * Returns true if a module with the same identity as {@code module} exists in the list.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modules.contains(module);
    }

    /**
     * Replaces the title with {@code newTitle}.
     */
    public void modifyTitle(Title newTitle) {
        this.title = newTitle;
    }

    /**
     * Adds a module to the list.
     * The module must not already exist in the list.
     */
    public void addModule(Module module) {
        modules.add(module);
    }

    /**
     * Replaces the given module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the list.
     * The module identity of {@code editedModule} must not be the same as another existing module
     * in the list.
     */
    public void setModule(Module target, Module editedModule) {
        requireNonNull(editedModule);

        modules.setModule(target, editedModule);
    }

    /**
     * Removes {@code module} from this {@code Requirement}.
     * {@code module} must exist in the list.
     */
    public void removeModule(Module module) {
        modules.remove(module);
    }

    // util methods

    @Override
    public String toString() {
        return modules.asUnmodifiableObservableList().size() + " modules";
        // TODO: refine later
    }

    @Override
    public Title getTitle() {
        return title;
    }

    public boolean hasSameTitle(Requirement requirement) {
        return this.title.equals(requirement.title);
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                   || (other instanceof Requirement // instanceof handles nulls
                           && modules.equals(((Requirement) other).modules)
                           && title.equals(((Requirement) other).title));
    }

    @Override
    public int hashCode() {
        return modules.hashCode();
    }
}
