package igrad.model.module;

//@@author teriaiw

import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import igrad.model.module.exceptions.DuplicateModuleException;
import igrad.model.module.exceptions.ModuleNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of modules that enforces uniqueness between its elements and does not allow nulls.
 * A module is considered unique by comparing using {@code Module#isSameModule(Module)}. As such, adding and updating of
 * modules uses Module#isSameModule(Module) for equality so as to ensure that the module being added or updated is
 * unique in terms of identity in the UniqueModuleList. However, the removal of a module uses Module#equals(Object) so
 * as to ensure that the module with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Module#isSameModule(Module)
 */
public class UniqueModuleList implements Iterable<Module> {

    private final ObservableList<Module> internalList = FXCollections.observableArrayList();
    private final ObservableList<Module> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent {@code Module} as the given argument;
     * {@code toCheck}.
     */
    public boolean contains(Module toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameModule);
    }

    /**
     * Returns true if the list contains all equivalent {@code Module}s in the list of
     * {@code Module}s as the given argument; {@code modulesToCheck}
     */
    public boolean contains(List<Module> modulesToCheck) {
        requireNonNull(modulesToCheck);

        return modulesToCheck.stream().allMatch(this::contains);
    }

    /**
     * Returns a module if the list contains an equivalent {@code Module};
     * which has module code; {@code moduleCode}, and returns an {@code Optional.empty} if otherwise.
     */
    public Optional<Module> getByModuleCode(ModuleCode moduleCode) {
        return internalList.stream()
            .filter(module -> module.getModuleCode().equals(moduleCode))
            .findFirst();
    }

    /**
     * Returns a list of modules; {@code List<Module>} of all modules in the internal list
     * whose module code matches the module codes in; {@code moduleCodes}.
     */
    public List<Module> getByModuleCodes(List<ModuleCode> moduleCodes) {
        return internalList.stream()
            .filter(modules -> moduleCodes.stream()
                .anyMatch(moduleCode -> moduleCode.equals(modules.getModuleCode())))
            .collect(Collectors.toList());
    }

    /**
     * Adds a module to the list.
     * The module must not already exist in the list.
     */
    public void add(Module toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateModuleException();
        }
        internalList.add(toAdd);
    }

    /**
     * Adds all modules to the current list.
     * The module must not already exist in the list.
     */
    public void add(List<Module> modulesToAdd) {
        requireNonNull(modulesToAdd);

        modulesToAdd.forEach(this::add);
    }

    /**
     * Replaces the module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the list.
     * The module identity of {@code editedModule} must not be the same as another existing module in the list.
     */
    public void setModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ModuleNotFoundException();
        }

        if (!target.isSameModule(editedModule) && contains(editedModule)) {
            throw new DuplicateModuleException();
        }

        internalList.set(index, editedModule);
    }

    public void setModules(UniqueModuleList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     */
    public void setModules(List<Module> modules) {
        requireAllNonNull(modules);
        if (!modulesAreUnique(modules)) {
            throw new DuplicateModuleException();
        }

        internalList.setAll(modules);
    }

    /**
     * Removes the equivalent module from the list.
     * The module must exist in the list.
     */
    public void remove(Module toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ModuleNotFoundException();
        }
    }

    /**
     * Removes all modules from the current list.
     * The module must already exist in the list.
     */
    public void remove(List<Module> modulesToRemove) {
        requireNonNull(modulesToRemove);

        modulesToRemove.forEach(this::remove);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Module> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Module> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueModuleList // instanceof handles nulls
            && internalList.equals(((UniqueModuleList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code modules} contains only unique modules.
     */
    private boolean modulesAreUnique(List<Module> modules) {
        for (int i = 0; i < modules.size() - 1; i++) {
            for (int j = i + 1; j < modules.size(); j++) {
                if (modules.get(i).isSameModule(modules.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
