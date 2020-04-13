package igrad.model.requirement;

//@@author yjskrs

import static igrad.testutil.Assert.assertThrows;
import static igrad.testutil.TypicalRequirements.CS_FOUNDATION;
import static igrad.testutil.TypicalRequirements.GENERAL_ELECTIVES;
import static igrad.testutil.TypicalRequirements.UNRESTRICTED_ELECTIVES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import igrad.model.requirement.exceptions.DuplicateRequirementException;
import igrad.model.requirement.exceptions.RequirementNotFoundException;
import igrad.testutil.RequirementBuilder;

public class UniqueRequirementListTest {
    private final UniqueRequirementList uniqueRequirementList = new UniqueRequirementList();

    @Test
    public void contains_nullRequirement_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRequirementList.contains(null));
    }

    @Test
    public void contains_requirementNotInList_returnsFalse() {
        assertFalse(uniqueRequirementList.contains(CS_FOUNDATION));
    }

    @Test
    public void contains_requirementInList_returnsTrue() {
        uniqueRequirementList.add(CS_FOUNDATION);
        assertTrue(uniqueRequirementList.contains(CS_FOUNDATION));
    }

    @Test
    public void contains_requirementWithSameIdentity_returnsTrue() {
        uniqueRequirementList.add(CS_FOUNDATION);
        Requirement editedRequirement = new RequirementBuilder(CS_FOUNDATION)
            .withCreditsOneParameter("40")
            .build();
        assertTrue(uniqueRequirementList.contains(editedRequirement));
    }

    @Test
    public void add_nullRequirement_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRequirementList.add(null));
    }

    @Test
    public void add_requirementIsSameRequirement_throwsDuplicateRequirementException() {
        uniqueRequirementList.add(CS_FOUNDATION);
        assertThrows(DuplicateRequirementException.class, () -> uniqueRequirementList.add(CS_FOUNDATION));
    }

    @Test
    public void add_requirementWithSameIdentity_throwsDuplicateRequirementException() {
        uniqueRequirementList.add(CS_FOUNDATION);
        Requirement editedRequirement = new RequirementBuilder(CS_FOUNDATION)
            .withCreditsOneParameter("40")
            .build();
        assertThrows(DuplicateRequirementException.class, () -> uniqueRequirementList.add(editedRequirement));
    }

    @Test
    public void setRequirement_nullTargetRequirement_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRequirementList
            .setRequirement(null, CS_FOUNDATION));
    }

    @Test
    public void setRequirement_nullEditedRequirement_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRequirementList
            .setRequirement(CS_FOUNDATION, null));
    }

    @Test
    public void setRequirement_targetRequirementNotInList_throwsRequirementNotFoundException() {
        assertThrows(RequirementNotFoundException.class, () -> uniqueRequirementList
            .setRequirement(CS_FOUNDATION, CS_FOUNDATION));
    }

    @Test
    public void setRequirement_editedRequirementIsSameRequirement_success() {
        uniqueRequirementList.add(CS_FOUNDATION);
        uniqueRequirementList.setRequirement(CS_FOUNDATION, CS_FOUNDATION);

        UniqueRequirementList differentListWithSameRequirement = new UniqueRequirementList();
        differentListWithSameRequirement.add(CS_FOUNDATION);

        assertEquals(differentListWithSameRequirement, uniqueRequirementList);
    }

    @Test
    public void setRequirement_editedRequirementWithSameIdentity_success() {
        uniqueRequirementList.add(CS_FOUNDATION);
        Requirement editedRequirement = new RequirementBuilder(CS_FOUNDATION)
            .withTitle("New Title")
            .withCreditsOneParameter("36")
            .build();
        uniqueRequirementList.setRequirement(CS_FOUNDATION, editedRequirement);

        UniqueRequirementList differentListWithSameRequirement = new UniqueRequirementList();
        differentListWithSameRequirement.add(editedRequirement);

        assertEquals(differentListWithSameRequirement, uniqueRequirementList);
    }

    @Test
    public void setRequirement_editedRequirementInList_throwsDuplicateRequirementException() {
        uniqueRequirementList.add(CS_FOUNDATION);
        uniqueRequirementList.add(UNRESTRICTED_ELECTIVES);
        assertThrows(DuplicateRequirementException.class, () -> uniqueRequirementList
            .setRequirement(CS_FOUNDATION, UNRESTRICTED_ELECTIVES));
    }

    @Test
    public void setRequirements_nullRequirementList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRequirementList
            .setRequirements((UniqueRequirementList) null));
        assertThrows(NullPointerException.class, () -> uniqueRequirementList
            .setRequirements((List<Requirement>) null));
    }

    @Test
    public void setRequirements_uniqueRequirementList_replacesOwnListWithProvidedUniqueRequirementList() {
        uniqueRequirementList.add(CS_FOUNDATION);
        uniqueRequirementList.add(GENERAL_ELECTIVES);

        UniqueRequirementList newRequirementList = new UniqueRequirementList();
        newRequirementList.add(UNRESTRICTED_ELECTIVES);

        uniqueRequirementList.setRequirements(newRequirementList);
        assertEquals(newRequirementList, uniqueRequirementList);

        uniqueRequirementList.add(CS_FOUNDATION);
        uniqueRequirementList.add(GENERAL_ELECTIVES);
        newRequirementList.setRequirements(uniqueRequirementList);
        assertEquals(uniqueRequirementList, newRequirementList);
    }

    @Test
    public void setRequirements_list_replacesOwnListWithProvidedList() {
        uniqueRequirementList.add(CS_FOUNDATION);
        List<Requirement> requirementList = Collections.singletonList(GENERAL_ELECTIVES);
        uniqueRequirementList.setRequirements(requirementList);
        UniqueRequirementList newRequirementList = new UniqueRequirementList();
        newRequirementList.add(GENERAL_ELECTIVES);
        assertEquals(newRequirementList, uniqueRequirementList);
    }

    @Test
    public void setRequirements_listWithDuplicateRequirements_throwsDuplicateRequirementException() {
        List<Requirement> listWithDuplicateRequirements = Arrays.asList(CS_FOUNDATION, CS_FOUNDATION);
        assertThrows(DuplicateRequirementException.class, () -> uniqueRequirementList
            .setRequirements(listWithDuplicateRequirements));
    }

    @Test
    public void remove_nullRequirement_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRequirementList.remove(null));
    }

    @Test
    public void remove_requirementFromEmptyList_throwsRequirementNotFoundException() {
        assertThrows(RequirementNotFoundException.class, () -> uniqueRequirementList.remove(CS_FOUNDATION));
    }

    @Test
    public void remove_nonExistingRequirement_throwsRequirementNotFoundException() {
        assertThrows(RequirementNotFoundException.class, () -> uniqueRequirementList.remove(CS_FOUNDATION));
    }

    @Test
    public void remove_existingRequirement_removesRequirement() {
        uniqueRequirementList.add(CS_FOUNDATION);
        uniqueRequirementList.remove(CS_FOUNDATION);
        UniqueRequirementList expectedUniqueRequirementList = new UniqueRequirementList();
        assertEquals(expectedUniqueRequirementList, uniqueRequirementList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> uniqueRequirementList
            .asUnmodifiableObservableList().remove(0));
    }
}
