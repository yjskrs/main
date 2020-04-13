package igrad.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Utility class for tokenizing arguments string in the format: {@code preamble <prefix>value <prefix>value ...}<br>
 * e.g. {@code preamble text n/CS1010 t/Programming Methodology u/4 s/} where prefixs are {@code n/ t/ u/}.<br>
 * 1. An argument's value can be an empty string in the above example.<br>
 * 2. Leading and trailing whitespaces of an argument value will be discarded.<br>
 * 3. An argument may be repeated and all its values will be accumulated, e.g. {@code n/CS1010 n/CS1231 n/CS3244}.<br>
 */
public class ArgumentTokenizer {

    /**
     * Tokenizes an arguments string and returns an {@code ArgumentMultimap} object that maps prefixes
     * to their respective argument values. Only the given prefixes will be recognized in the arguments
     * string.
     *
     * @param argsString Arguments string in the format: {@code preamble <prefix>value <prefix>value ...}.
     * @param prefixes   Prefixes to tokenize the arguments string with.
     * @return ArgumentMultimap object that maps prefixes to their arguments.
     */
    public static ArgumentMultimap tokenize(String argsString, Prefix... prefixes) {
        List<PrefixPosition> positions = findAllPrefixPositions(argsString, prefixes);
        return extractArguments(argsString, positions);
    }

    /**
     * Finds all zero-based prefix positions in the given arguments string.
     *
     * @param argsString Arguments string in the format: {@code preamble <prefix>value <prefix>value ...}.
     * @param prefixes   Prefixes to find in the arguments string.
     * @return List of zero-based prefix positions in the given arguments string.
     */
    private static List<PrefixPosition> findAllPrefixPositions(String argsString, Prefix... prefixes) {
        return Arrays.stream(prefixes)
            .flatMap(prefix -> findPrefixPositions(argsString, prefix).stream())
            .collect(Collectors.toList());
    }

    /**
     * Finds all positions of a single prefix in the given arguments string.
     *
     * @param argsString Arguments string in the format: {@code preamble <prefix>value <prefix>value ...}.
     * @param prefix     Prefix to find.
     * @return List of zero-based prefix positions in the given arguments string.
     */
    private static List<PrefixPosition> findPrefixPositions(String argsString, Prefix prefix) {
        List<PrefixPosition> positions = new ArrayList<>();

        int prefixPosition = findPrefixPosition(argsString, prefix.getPrefix(), 0);
        while (prefixPosition != -1) {
            PrefixPosition extendedPrefix = new PrefixPosition(prefix, prefixPosition);
            positions.add(extendedPrefix);
            prefixPosition = findPrefixPosition(argsString, prefix.getPrefix(), prefixPosition);
        }

        return positions;
    }

    /**
     * Returns the index of the first occurrence of {@code prefix} in {@code argsString} starting
     * from {@code index}. An occurrence is valid if there is a whitespace before {@code prefix}.
     * Returns -1 if no such occurrence can be found.
     *
     * <p>
     * E.g. if {@code argString} = "n/AB1234s/Y1S2", {@code prefix} = "s/" and {@code index} = 0,
     * this method returns -1 as there are no valid occurrences of "s/" with whitespace before it.
     * However, if {@code argsString} = "n/AB1234 s/Y1S2", {@code prefix} = "s/" and {@code index} = 0,
     * this method returns 9.
     *
     * <p>
     * Fails silently when provided with an invalid {@code index} (i.e. index less than 0
     * or more than the size of {@code argsString}) by returning -1.
     *
     * @param argsString Arguments string in the format: {@code preamble <prefix>value <prefix>value ...}.
     * @param prefix     Prefix to find.
     * @param startIndex Starting index to find {@code prefix} from.
     * @return Zero-based prefix position.
     */
    private static int findPrefixPosition(String argsString, String prefix, int startIndex) {
        if (startIndex == -1 || startIndex >= argsString.length()) {
            return -1;
        }

        int prefixIndex = argsString.indexOf(" " + prefix, startIndex);

        return prefixIndex == -1 ? -1
            : prefixIndex + 1; // +1 as offset for whitespace
    }

    /**
     * Returns a boolean variable specifying if the specified flag is present.
     *
     * @param argsString e.g. "add n/CS2103T -a"
     * @param flag       the substring "-a" in the argsString.
     * @return True if the flag is present, false otherwise.
     */
    public static boolean isFlagPresent(String argsString, String flag) {
        Pattern pattern = Pattern.compile(flag);
        Matcher matcher = pattern.matcher(argsString);

        return matcher.find();
    }

    /**
     * Strips all flags from the argument string.
     * Flags should only be specified at the end of {@code argString}.
     *
     * @param argsString e.g. "module add n/CS2103T -a"
     * @return {@code argsString} with flags stripped.
     */
    public static String removeFlags(String argsString) {
        int firstFlagIndex = startIndexOfPattern("-[a-z]+", argsString);

        if (firstFlagIndex < 0) {
            return argsString;
        } else {
            return argsString.substring(0, firstFlagIndex);
        }

    }

    /**
     * Returns the start index of a given pattern; {@code regex} found in the string; {@code text}.
     * Returns -1 if the pattern is not found in the string.
     */
    private static int startIndexOfPattern(String regex, String text) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        if (!matcher.find()) {
            return -1;
        }

        return matcher.start();
    }

    /**
     * Extracts prefixes and their respective argument values and returns an {@code ArgumentMultimap}
     * object that maps the extracted prefixes to their arguments. Prefixes are extracted based on
     * their zero-based positions in {@code argsString}.
     *
     * @param argsString      Arguments string in the format: {@code preamble <prefix>value <prefix>value ...}.
     * @param prefixPositions Zero-based positions of all prefixes present in {@code argsString}.
     * @return ArgumentMultimap object that maps prefixes to their arguments.
     */
    private static ArgumentMultimap extractArguments(String argsString, List<PrefixPosition> prefixPositions) {

        // Sort by start position in ascending order
        prefixPositions.sort(Comparator.comparingInt(PrefixPosition::getStartPosition));

        // Insert a PrefixPosition to represent the preamble
        PrefixPosition preambleMarker = new PrefixPosition(new Prefix(""), 0);
        prefixPositions.add(0, preambleMarker);

        // Add a dummy PrefixPosition to represent the end of the string
        PrefixPosition endPositionMarker = new PrefixPosition(new Prefix(""), argsString.length());
        prefixPositions.add(endPositionMarker);

        // Map prefixes to their argument values (if any)
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        for (int i = 0; i < prefixPositions.size() - 1; i++) {
            // Extract and store prefixes and their arguments
            Prefix argPrefix = prefixPositions.get(i).getPrefix();
            String argValue = extractArgumentValue(argsString, prefixPositions.get(i), prefixPositions.get(i + 1));
            argMultimap.put(argPrefix, argValue);
        }

        return argMultimap;
    }

    /**
     * Returns the trimmed value of the argument in {@code argsString} specified by
     * {@code currentPrefixPosition}. The end position for the value is determined
     * by {@code nextPrefixPosition}.
     *
     * @param argsString            Arguments string.
     * @param currentPrefixPosition Prefix position for the current argument.
     * @param nextPrefixPosition    Prefix position for the next argument.
     * @return Trimmed argument value.
     */
    private static String extractArgumentValue(String argsString,
                                               PrefixPosition currentPrefixPosition,
                                               PrefixPosition nextPrefixPosition) {

        // Get prefix length by calling getPrefix on PrefixPosition and getPrefix on Prefix
        int prefixLength = currentPrefixPosition.getPrefix().getPrefix().length();
        int prefixStartPosition = currentPrefixPosition.getStartPosition();

        // Calculate starting position of the value of the argument
        int valuePosition = prefixStartPosition + prefixLength;

        // Extract the value of the argument from argString
        String value = argsString.substring(valuePosition, nextPrefixPosition.getStartPosition());

        return value.trim();
    }

    /**
     * Represents a prefix's position (or otherwise known as index) in an arguments string.
     */
    private static class PrefixPosition {
        private final Prefix prefix;
        private final int startPosition;

        PrefixPosition(Prefix prefix, int startPosition) {
            this.prefix = prefix;
            this.startPosition = startPosition;
        }

        int getStartPosition() {
            return startPosition;
        }

        Prefix getPrefix() {
            return prefix;
        }
    }

}
