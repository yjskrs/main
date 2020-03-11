package igrad.model.module;

import java.util.List;
import java.util.function.Predicate;

import igrad.commons.util.StringUtil;

/**
 * Tests that a {@code Module}'s {@code Name} matches any of the keywords given.
 */
public class TitleContainsKeywordsPredicate implements Predicate<Module> {
    private final List<String> keywords;

    public TitleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Module module) {
        return keywords.stream()
            .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(module.getTitle().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TitleContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((TitleContainsKeywordsPredicate) other).keywords)); // state check
    }

}
