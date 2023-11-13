package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalCustomers.ALICE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.DeliveryBuilder;

public class DeliveryNameContainsKeywordsPredicateTest {

    @Test
    public void constructor_createsImmutableList() {
        ArrayList<String> predicateKeywordList = new ArrayList<>();
        predicateKeywordList.add("shouldBeHere");
        DeliveryNameContainsKeywordsPredicate deliveryNameContainsKeywordsPredicate =
                new DeliveryNameContainsKeywordsPredicate(predicateKeywordList);
        predicateKeywordList.clear();
        assertEquals(deliveryNameContainsKeywordsPredicate.toString(),
                DeliveryNameContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=[shouldBeHere]}");
    }

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        DeliveryNameContainsKeywordsPredicate firstPredicate =
                new DeliveryNameContainsKeywordsPredicate(firstPredicateKeywordList);
        DeliveryNameContainsKeywordsPredicate secondPredicate =
                new DeliveryNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DeliveryNameContainsKeywordsPredicate firstPredicateCopy =
                new DeliveryNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        DeliveryNameContainsKeywordsPredicate predicate =
                new DeliveryNameContainsKeywordsPredicate(Collections.singletonList("Gabriel"));
        assertTrue(predicate.test(new DeliveryBuilder().withName("Gabriel Milk").build()));

        // Multiple keywords
        predicate = new DeliveryNameContainsKeywordsPredicate(Arrays.asList("Gabriel", "Milk"));
        assertTrue(predicate.test(new DeliveryBuilder().withName("Gabriel Milk").build()));

        // Only one matching keyword
        predicate = new DeliveryNameContainsKeywordsPredicate(Arrays.asList("Gabriel", "Milk"));
        assertTrue(predicate.test(new DeliveryBuilder().withName("Gabriel Milk").build()));

        // Mixed-case keywords
        predicate = new DeliveryNameContainsKeywordsPredicate(Arrays.asList("GaBrIeL", "MiLK"));
        assertTrue(predicate.test(new DeliveryBuilder().withName("Gabriel Milk").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        DeliveryNameContainsKeywordsPredicate predicate =
                new DeliveryNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new DeliveryBuilder().withName("Gabriel Milk").build()));

        // Non-matching keyword
        predicate = new DeliveryNameContainsKeywordsPredicate(Arrays.asList("Gambe"));
        assertFalse(predicate.test(new DeliveryBuilder().withName("Gabriel Milk").build()));

        // Keywords match other fields except name
        predicate = new DeliveryNameContainsKeywordsPredicate(
                Arrays.asList("ALICE", "2023-12-12", "2020-12-12", "CREATED"));
        assertFalse(predicate.test(new DeliveryBuilder()
                .withName("Gabriel Milk")
                .withCustomer(ALICE)
                .withDeliveryDate("2023-12-12")
                .withOrderDate("2020-12-12")
                .withStatus(DeliveryStatus.CREATED).build()));
    }

    @Test
    public void getKeywordsAsStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        DeliveryNameContainsKeywordsPredicate predicate = new DeliveryNameContainsKeywordsPredicate(keywords);

        String expected = "keyword1 keyword2";
        assertEquals(expected, predicate.getKeywordsAsString());
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        DeliveryNameContainsKeywordsPredicate predicate =
                new DeliveryNameContainsKeywordsPredicate(keywords);

        String expected =
                DeliveryNameContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
