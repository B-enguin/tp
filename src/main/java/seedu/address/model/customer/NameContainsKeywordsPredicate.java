package seedu.address.model.customer;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Customer}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Customer> {
    private final List<String> keywords;

    /**
     * Creates a NameContainsKeywordsPredicate.
     *
     * @param keywords the list of keywords to search for.
     */
    public NameContainsKeywordsPredicate(List<String> keywords) {
        // Create Defensive Copy to prevent modification
        this.keywords = List.copyOf(keywords);
    }

    @Override
    public boolean test(Customer customer) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(customer.getName().fullName, keyword));
    }

    /**
     * Returns a list of the keywords as {@code String}.
     */
    public String getKeywordsAsString() {
        return keywords.stream().map(keyword -> keyword + " ").reduce("", String::concat).trim();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (NameContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
