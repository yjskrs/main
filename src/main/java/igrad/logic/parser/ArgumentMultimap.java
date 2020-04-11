package igrad.logic.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Represents a multimap for storing prefixes mapped with their respective arguments.
 * Each key may be associated with multiple argument values. Values for a given key are stored in a list,
 * where the insertion ordering is maintained.
 * Keys are unique, but the list of argument values may contain duplicate arguments values, i.e. the same
 * argument value can be inserted multiple times for the same prefix.
 */
public class ArgumentMultimap {
    private static final Prefix PREFIX_PREAMBLE = new Prefix("");

    /**
     * Prefixes mapped to their respective arguments.
     */
    private final Map<Prefix, List<String>> argMultimap = new HashMap<>();

    /**
     * Puts the prefix-argument key-value pair into the map.
     * If the map already contains a mapping for the prefix, the new argument is appended to the list
     * of existing ones.
     *
     * @param prefix   Prefix key.
     * @param argValue Argument value associated with the prefix.
     */
    public void put(Prefix prefix, String argValue) {
        List<String> argValues = getAllValues(prefix);
        argValues.add(argValue);
        argMultimap.put(prefix, argValues);
    }

    /**
     * Returns the last instance of {@code prefix} key, if any. Else returns an {@code Optional.empty()}.
     *
     * @param prefix Prefix key.
     * @return Argument value associated with the prefix, if any.
     */
    public Optional<String> getValue(Prefix prefix) {
        List<String> values = getAllValues(prefix);
        return values.isEmpty() ? Optional.empty() : Optional.of(values.get(values.size() - 1));
    }

    /**
     * Returns all instances of {@code prefix} key.
     * If the prefix does not exist or has no values, this will return an empty list.
     * Modifying the returned list will not affect the storage of the key-value pairs in the map.
     *
     * @param prefix Prefix key.
     * @return List of argument values associated with the prefix.
     */
    public List<String> getAllValues(Prefix prefix) {
        if (!argMultimap.containsKey(prefix)) {
            return new ArrayList<>();
        }

        return new ArrayList<>(argMultimap.get(prefix));
    }

    /**
     * Returns the preamble (text before the first valid prefix).
     * Leading or trailing whitespaces are trimmed.
     */
    public String getPreamble() {
        return getValue(new Prefix("")).orElse("");
    }

    //@@author nathanaelseen

    /**
     * Returns true if values of all key-value pairs in the {@code argMultimap} field (of this class),
     * is empty. Also, if {@code checkPreamble} parameter is true, this method checks if the preambles
     * are empty, else any preamble is ignored.
     */
    public boolean isEmpty(boolean checkPreamble) {
        if (checkPreamble) {
            /*
             * For this case, where we don't ignore preambles:
             * If our hash-map has less than or equals to 1 key, that key (if any)
             * is definitely a preamble (not a tag). Now, we check that preamble and
             * if that preamble happens to be the empty string (""), then we know that there
             * is indeed no 'preamble', and hence return true.
             */
            return argMultimap.size() <= 1 && getPreamble().isEmpty();
        } else {
            /*
             * For this case, where we ignore preambles:
             * If our hash-map has less than or equals to 1 key, that key (if any)
             * is definitely a preamble (not a tag). But since we're ignoring preambles
             * we return true when the size <= 1.
             */
            return argMultimap.size() <= 1;
        }
    }
}
