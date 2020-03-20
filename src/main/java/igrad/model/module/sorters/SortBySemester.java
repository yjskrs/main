package igrad.model.module.sorters;

import java.util.Comparator;

import igrad.model.module.Module;


/**
 * Sorter for modules by semester
 */
public class SortBySemester implements Comparator<Module> {

    /**
     * Compares the digits in the semester a module is tagged with.
     * Results in an ascending sort.
     */
    public int compare(Module m1, Module m2) {
        int s1 = extractDigits(m1.getSemester().toString());
        int s2 = extractDigits(m2.getSemester().toString());

        return s1 - s2;
    }

    /**
     * Extracts digits from a string
     */
    private int extractDigits(String str) {
        return Integer.parseInt(str.replaceAll("\\D+", ""));
    }

}
