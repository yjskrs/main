package igrad.model.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import igrad.commons.core.GuiSettings;
import igrad.model.CourseBook;
import igrad.model.ReadOnlyCourseBook;
import igrad.model.UserPrefs;
import igrad.model.avatar.Avatar;
import igrad.model.module.Credits;
import igrad.model.module.Grade;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.module.Semester;
import igrad.model.module.Title;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;

/**
 * Contains utility methods for populating {@code CourseBook} with sample data.
 */
public class SampleDataUtil {
    //@@author dargohzy
    public static Module[] getSampleModules() {
        return new Module[] {
            new Module(
                new Title("Software Engineering"),
                new ModuleCode("CS2103T"),
                new Credits("4"),
                Optional.of(new Semester("Y1S1")),
                Optional.of(new Grade("A+"))),
            new Module(
                    new Title("Introduction to Operating Systems"),
                    new ModuleCode("CS2106"),
                    new Credits("4"),
                    Optional.of(new Semester("Y2S2")),
                    Optional.of(new Grade("A+"))),
            new Module(
                    new Title("Digital Storytelling"),
                    new ModuleCode("NM3230"),
                    new Credits("4"),
                    Optional.of(new Semester("Y2S@")),
                    Optional.of(new Grade("B"))),
            new Module(
                    new Title("Quantitative Reasoning"),
                    new ModuleCode("GER1000"),
                    new Credits("4"),
                    Optional.of(new Semester("Y1S1")),
                    Optional.of(new Grade("B+"))),
            new Module(
                    new Title("Programming Methodology I"),
                    new ModuleCode("CS1101S"),
                    new Credits("4"),
                    Optional.of(new Semester("Y1S1")),
                    Optional.of(new Grade("C+"))),
            new Module(
                    new Title("Discrete Structures"),
                    new ModuleCode("CS1231"),
                    new Credits("4"),
                    Optional.of(new Semester("Y1S1")),
                    Optional.of(new Grade("C+"))),
            new Module(
                    new Title("Linear Algebra I"),
                    new ModuleCode("MA1101R"),
                    new Credits("4"),
                    Optional.of(new Semester("Y1S1")),
                    Optional.of(new Grade("B+"))),
            new Module(
                    new Title("Communicating in the Information Age"),
                    new ModuleCode("ES2660"),
                    new Credits("4"),
                    Optional.of(new Semester("Y2S1")),
                    Optional.of(new Grade("B"))),
            new Module(
                    new Title("Introduction to Human-Computer Interaction Design"),
                    new ModuleCode("NM2213"),
                    new Credits("4"),
                    Optional.of(new Semester("Y2S2")),
                    Optional.of(new Grade("A"))),
            new Module(
                    new Title("Darwin and Evolution"),
                    new ModuleCode("GET1020"),
                    new Credits("4"),
                    Optional.of(new Semester("Y2S1")),
                    Optional.of(new Grade("B")))
        };
    }
    // @@author

    public static Requirement[] getSampleRequirements() {
        Module[] modules = getSampleModules();

        List<Module> csFoundationModules = new LinkedList<Module>();
        csFoundationModules.add(modules[0]);
        csFoundationModules.add(modules[1]);
        csFoundationModules.add(modules[4]);
        csFoundationModules.add(modules[5]);

        List<Module> unrestrictedElectivesModules = new LinkedList<Module>();
        unrestrictedElectivesModules.add(modules[2]);
        unrestrictedElectivesModules.add(modules[8]);

        List<Module> generalEducationModules = new LinkedList<Module>();
        generalEducationModules.add(modules[3]);
        generalEducationModules.add(modules[9]);

        Requirement csFoundation = new Requirement(
                new RequirementCode("CF0"),
                new igrad.model.requirement.Title("CS Foundation"),
                new igrad.model.requirement.Credits("32"));
        csFoundation.addModules(csFoundationModules);

        Requirement unrestrictedElectives = new Requirement(
                new RequirementCode("UE0"),
                new igrad.model.requirement.Title("Unrestricted Electives"),
                new igrad.model.requirement.Credits("32"));
        unrestrictedElectives.addModules(unrestrictedElectivesModules);

        Requirement generalEducation = new Requirement(
                new RequirementCode("GE0"),
                new igrad.model.requirement.Title("General Education"),
                new igrad.model.requirement.Credits("20"));
        generalEducation.addModules(generalEducationModules);

        return new Requirement[] {
            csFoundation,
            unrestrictedElectives,
            generalEducation
        };
    }

    public static Avatar getSampleAvatar() {
        return Avatar.getSampleAvatar();
    }

    public static Path getSampleCourseBookFilePath() {
        return Paths.get("data", "coursebook.json");
    }

    public static Path getSampleBackupCourseBookFilePath() {
        return Paths.get("data", "backup_coursebook.json");
    }

    public static ReadOnlyCourseBook getSampleCourseBook() {
        CourseBook sampleCourseBook = new CourseBook();
        for (Module sampleModule : getSampleModules()) {
            sampleCourseBook.addModule(sampleModule);
        }
        for (Requirement sampleRequirement : getSampleRequirements()) {
            sampleCourseBook.addRequirement(sampleRequirement);
        }
        return sampleCourseBook;
    }

    public static ReadOnlyCourseBook getEmptyCourseBook() {
        CourseBook emptyCourseBook = new CourseBook();
        return emptyCourseBook;
    }

    public static UserPrefs getSampleUserPrefs() {
        UserPrefs sampleUserPrefs = new UserPrefs();

        sampleUserPrefs.setGuiSettings(new GuiSettings());
        sampleUserPrefs.setCourseBookFilePath(getSampleCourseBookFilePath());
        sampleUserPrefs.setBackupCourseBookFilePath(getSampleBackupCourseBookFilePath());
        sampleUserPrefs.setAvatar(getSampleAvatar());

        return sampleUserPrefs;
    }

}
