package igrad.model.requirement;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import igrad.model.requirement.exceptions.DuplicateRequirementException;
import igrad.model.requirement.exceptions.RequirementNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of requirements that enforces uniqueness between its elements and does not allow nulls.
 * A requirement is considered unique by comparing using {@code Requirement#hasSameName(Requirement)}.
 * As such, adding and updating of requirements uses Requirement#hasSameName(Requirement) for equality
 * so as to ensure that the requirement being added or updated is unique in terms of name in this class.
 *
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Requirement#hasSameTitle(Requirement) (Requirement)
 */
public class UniqueRequirementList implements Iterable<Requirement> {

    private final ObservableList<Requirement> internalList = FXCollections.observableArrayList();
    private final ObservableList<Requirement> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent requirement to {@code toCheck}.
     */
    public boolean contains(Requirement toCheck) {
        requireNonNull(toCheck);

        return internalList.stream().anyMatch(toCheck::hasSameTitle);
    }

    /**
     * Adds a {@code requirement} to the list.
     * The requirement must not already exist in the list.
     */
    public void add(Requirement toAdd) throws DuplicateRequirementException {
        requireNonNull(toAdd);

        if (contains(toAdd)) {
            throw new DuplicateRequirementException();
        }

        internalList.add(toAdd);
    }

    /**
     * Replaces all requirements with {@code replacement}.
     */
    public void setRequirements(UniqueRequirementList replacement) {
        requireNonNull(replacement);

        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with the list {@code requirements}.
     * The {@code requirements} list must not contain duplicate requirements.
     */
    public void setRequirements(List<Requirement> requirements) throws DuplicateRequirementException {
        requireAllNonNull(requirements);

        if (!requirementsAreUnique(requirements)) {
            throw new DuplicateRequirementException();
        }

        internalList.setAll(requirements);
    }

    /**
     * Replaces the requirement {@code target} in the list with {@code editedRequirement}.
     * {@code target} must exist in the list.
     */
    public void setRequirement(Requirement target, Requirement editedRequirement) throws RequirementNotFoundException {
        requireAllNonNull(target, editedRequirement);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RequirementNotFoundException();
        }

        if (!target.hasSameTitle(editedRequirement) && contains(editedRequirement)) {
            throw new DuplicateRequirementException();
        }

        internalList.set(index, editedRequirement);
    }

    /**
     * Removes the requirement {@code toRemove} from the list.
     * The requirement must exist in the list.
     */
    public void remove(Requirement toRemove) {
        requireNonNull(toRemove);

        if (!internalList.remove(toRemove)) {
            throw new RequirementNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Requirement> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Requirement> iterator() {
        return internalList.iterator();
    }

    /**
     * Returns true if {@code requirements} list contains only unique requirements.
     */
    private boolean requirementsAreUnique(List<Requirement> requirements) {
        for (int i = 0; i < requirements.size() - 1; i++) {
            for (int j = i + 1; j < requirements.size(); j++) {
                if (requirements.get(i).hasSameTitle(requirements.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // check if same object
            || (other instanceof UniqueRequirementList
            && internalList.equals(((UniqueRequirementList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
