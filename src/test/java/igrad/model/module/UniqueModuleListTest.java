package igrad.model.module;

import static igrad.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS_1101S;
import static igrad.logic.commands.CommandTestUtil.VALID_TITLE_CS_2100;
import static igrad.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import igrad.model.module.exceptions.DuplicateModuleException;
import igrad.model.module.exceptions.ModuleNotFoundException;
import igrad.testutil.ModuleBuilder;
import igrad.testutil.TypicalModules;

public class UniqueModuleListTest {

    private final UniqueModuleList uniqueModuleList = new UniqueModuleList();
    private final Module cs1101s = TypicalModules.CS1101S;
    private final Module cs2100 = TypicalModules.CS2100;

    @Test
    public void contains_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.contains((Module) null));
    }

    @Test
    public void contains_nullModules_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.contains((List<Module>) null));
    }

    @Test
    public void contains_moduleNotInList_returnsFalse() {
        assertFalse(uniqueModuleList.contains(cs1101s));
    }

    @Test
    public void contains_moduleInList_returnsTrue() {
        uniqueModuleList.add(cs1101s);
        assertTrue(uniqueModuleList.contains(cs1101s));
    }

    @Test
    public void contains_moduleWithSameIdentityFieldsInList_returnsTrue() {
        uniqueModuleList.add(cs1101s);
        Module editedModule = new ModuleBuilder()
            .withModuleCode(VALID_MODULE_CODE_CS_1101S)
            .build();
        assertTrue(uniqueModuleList.contains(editedModule));
    }

    @Test
    public void add_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.add((Module) null));
    }

    @Test
    public void add_duplicateModule_throwsDuplicateModuleException() {
        uniqueModuleList.add(cs1101s);
        assertThrows(DuplicateModuleException.class, () -> uniqueModuleList.add(cs1101s));
    }

    @Test
    public void setModule_nullTargetModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList
            .setModule(null, cs1101s));
    }

    @Test
    public void setModule_nullEditedModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList
            .setModule(cs1101s, null));
    }

    @Test
    public void setModule_targetModuleNotInList_throwsPersonNotFoundException() {
        assertThrows(ModuleNotFoundException.class, () -> uniqueModuleList
            .setModule(cs1101s, cs1101s));
    }

    @Test
    public void setModule_editedModuleIsSameModule_success() {
        uniqueModuleList.add(cs1101s);
        uniqueModuleList.setModule(cs1101s, cs1101s);

        UniqueModuleList differentUniqueModuleList = new UniqueModuleList();
        differentUniqueModuleList.add(cs1101s);

        assertEquals(differentUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModule_editedModuleHasSameIdentity_success() {
        uniqueModuleList.add(cs1101s);
        Module editedModule = new ModuleBuilder(cs1101s)
                .withModuleCode(VALID_MODULE_CODE_CS_1101S)
               .build();
        uniqueModuleList.setModule(cs1101s, editedModule);
        UniqueModuleList differentUniqueModuleList = new UniqueModuleList();
        differentUniqueModuleList.add(editedModule);
        assertEquals(differentUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModule_editedModuleHasDifferentIdentity_success() {
        uniqueModuleList.add(cs1101s);
        uniqueModuleList.setModule(cs1101s, cs2100);
        UniqueModuleList differentUniqueModuleList = new UniqueModuleList();
        differentUniqueModuleList.add(cs2100);
        assertEquals(differentUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueModuleList.add(cs1101s);
        Module newModule = new ModuleBuilder()
            .withTitle(VALID_TITLE_CS_2100)
            .withModuleCode(VALID_MODULE_CODE_CS_1101S)
            .build();
        assertThrows(DuplicateModuleException.class, () -> uniqueModuleList.add(newModule));
    }

    @Test
    public void remove_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.remove((Module) null));
    }

    @Test
    public void remove_moduleDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(ModuleNotFoundException.class, () -> uniqueModuleList.remove(cs1101s));
    }

    @Test
    public void remove_existingModule_removesModule() {
        uniqueModuleList.add(cs1101s);
        uniqueModuleList.remove(cs1101s);
        UniqueModuleList differentUniqueModuleList = new UniqueModuleList();
        assertEquals(differentUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModules_nullUniqueModuleList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList
            .setModules((UniqueModuleList) null));
        assertThrows(NullPointerException.class, () -> uniqueModuleList
            .setModules((List<Module>) null));
    }

    @Test
    public void setModules_uniqueModuleList_replacesOwnListWithProvidedUniqueModuleList() {
        uniqueModuleList.add(cs1101s);
        UniqueModuleList newUniqueModuleList = new UniqueModuleList();
        newUniqueModuleList.add(cs2100);
        uniqueModuleList.setModules(newUniqueModuleList);
        assertEquals(newUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModules_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.setModules((List<Module>) null));
    }

    @Test
    public void setModules_list_replacesOwnListWithProvidedList() {
        uniqueModuleList.add(cs1101s);
        List<Module> moduleList = Collections.singletonList(cs2100);
        uniqueModuleList.setModules(moduleList);
        UniqueModuleList newUniqueModuleList = new UniqueModuleList();
        newUniqueModuleList.add(cs2100);
        assertEquals(newUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModules_listWithDuplicateModules_throwsDuplicateModuleException() {
        List<Module> listWithDuplicateModules = Arrays.asList(cs1101s, cs1101s);
        assertThrows(DuplicateModuleException.class, ()
            -> uniqueModuleList.setModules(listWithDuplicateModules));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueModuleList.asUnmodifiableObservableList().remove(0));
    }
}
