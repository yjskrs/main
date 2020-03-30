package igrad.logic.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Stores mapping of prefixes to their respective arguments.
 * Each key may be associated with multiple argument values.
 * Values for a given key are stored in a list, and the insertion ordering is maintained.
 * Keys are unique, but the list of argument values may contain duplicate argument values, i.e. the same argument value
 * can be inserted multiple times for the same prefix.
 */
public class ArgumentMultimap {
    private static final Prefix PREFIX_PREAMBLE = new Prefix("");

    /**
     * Prefixes mapped to their respective arguments
     **/
    private final Map<Prefix, List<String>> argMultimap = new HashMap<>();

    /**
     * Associates the specified argument value with {@code prefix} key in this map.
     * If the map previously contained a mapping for the key, the new value is appended to the list of existing values.
     *
     * @param prefix   Prefix key with which the specified argument value is to be associated
     * @param argValue Argument value to be associated with the specified prefix key
     */
    public void put(Prefix prefix, String argValue) {
        List<String> argValues = getAllValues(prefix);
        argValues.add(argValue);
        argMultimap.put(prefix, argValues);
    }

    /**
     * Returns the last value of {@code prefix}.
     */
    public Optional<String> getValue(Prefix prefix) {
        List<String> values = getAllValues(prefix);
        return values.isEmpty() ? Optional.empty() : Optional.of(values.get(values.size() - 1));
    }

    /**
     * Returns all values of {@code prefix}.
     * If the prefix does not exist or has no values, this will return an empty list.
     * Modifying the returned list will not affect the underlying data structure of the ArgumentMultimap.
     */
    public List<String> getAllValues(Prefix prefix) {
        if (!argMultimap.containsKey(prefix)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(argMultimap.get(prefix));
    }

    /**
     * Returns the preamble (text before the first valid prefix). Trims any leading/trailing spaces.
     */
    public String getPreamble() {
        return getValue(new Prefix("")).orElse("");
    }


    /**
     * Returns true if values of all key-value pairs in the {@code argMultimap} field (of this class),
     * is empty. Also, if {@code checkPreamble} parameter is true, this method checks if those preambles
     * are empty (empty string; ""), else any preamble is ignored.
     *
     * In other words, this method returns true if and only if there are no other arguments or specifiers
     * entered after a command whose format have preambles (i.e, checkPreamble is true);
     * e.g, 'module edit', 'requirement edit', etc.
     * (Note: doesn't apply to all commands; e.g, module delete)
     *
     * For those command whose format have preambles (i.e, checkPreamble is false), this method ignores them
     * and returns true if and only if there are no other arguments within it;
     * e.g, 'module add BLAH' => returns true
     */
    public boolean isEmpty(boolean checkPreamble) {
        /*
         * If our hash-map of key-value pairs contain more than one key then, we are guaranteed that
         * our command is not empty (although they may contain superfluous/missing specifiers, missing/invalid tags).
         * But we can be sure that our command now probably contains the specifier and probably some arguments,
         * and they will never be of the form; module add, module edit, etc. Hence we return false.
         */
        if (argMultimap.size() > 1) {
            return false;
        }

        /*
         * We know that regardless of the case, every command always will have the PREFIX_PREAMBLE key,
         * which is the preamble text. Hence since argMultimap is of size 1 (i.e, only 1 key in the map),
         * that key has to be the PREFIX_PREAMBLE key. Here there are 2 cases; checkPreamble true or false.
         *
         * Now, if checkPreamble is false, then we are not concerned of checking the preambles (whether they
         * are empty or not). If they exists, which in this case, they have to (by the paragraph above) be the
         * PREFIX_PREAMBLE key, then indeed we just ignore them, and conclude that the command is indeed
         * 'empty', e.g,
         * module add BLAH ==> isEmpty will yield true (since we're ignoring preambles)
         *
         * If instead checkPreamble is true, then we are concerned of checking preambles (whether they are empty
         * or not). If they exists, which in this case, they have to (by the paragraph above) be the PREFIX_PREAMBLE
         * key, and if they're empty then indeed we conclude that our command is 'empty', e.g,
         * module edit
         */
        if (checkPreamble) {
            return getPreamble().isEmpty();
        } else {
            return true;
        }
    }
}
