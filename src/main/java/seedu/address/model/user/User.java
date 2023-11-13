package seedu.address.model.user;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a User in the address book.
 */
public class User {

    // Identity fields
    private final Username username;
    private final Password hashedPassword;
    // only stored user will have secret question and answer
    private String secretQuestion;
    private String answer;

    /**
     * Overloaded constructor for creating a new user with a hashed password
     *
     * @param isHashed indicates whether the password is hashed
     */
    public User(Username username, Password password, boolean isHashed) {
        requireAllNonNull(username, password);
        this.username = username;
        this.hashedPassword = isHashed ? password : new Password(password.toString());
    }

    /**
     * Constructor used for initialising a user with a secret question and answer
     *
     * @param secretQuestion String representing the secret question
     * @param answer         String representing the answer to the secret question
     */
    public User(Username username, Password password, boolean isHashed, String secretQuestion, String answer) {
        requireAllNonNull(username, password);
        this.username = username;
        this.hashedPassword = isHashed ? password : new Password(password.toString());
        this.secretQuestion = secretQuestion;
        this.answer = answer;
    }

    /**
     * Returns the username of the user.
     *
     * @return username Username object
     */
    public Username getUsername() {
        return username;
    }

    /**
     * Returns the password of the user.
     *
     * @return password Password object
     */
    public Password getPassword() {
        return hashedPassword;
    }

    /**
     * Returns the secret question of the user.
     *
     * @return String representing the secret question
     */
    public String getSecretQuestion() {
        return secretQuestion;
    }

    /**
     * Returns the answer to the secret question of the user.
     *
     * @return String representing the answer to the secret question
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Returns true if both persons have the same username.
     */
    public boolean hasSameUsername(User otherUser) {
        if (otherUser == this) {
            return true;
        }

        return otherUser != null
                && otherUser.getUsername().equals(getUsername());
    }

    /**
     * Returns true if both users have the same username and password.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof User)) {
            return false;
        }

        // ignore secret question and answer
        User otherUser = (User) other;
        return username.equals(otherUser.username)
                && hashedPassword.equals(otherUser.hashedPassword);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(username, hashedPassword);
    }

    @Override
    public String toString() {
        // does not show password for security reason
        return new ToStringBuilder(this)
                .add("username", username)
                .toString();
    }

    /**
     * Checks if the answer is equal to the user's answer.
     * This method is case-insensitive.
     *
     * @param answer The answer to be checked
     * @return true if the answer is equal to the user's answer, false otherwise
     */
    public boolean checkAnswerEquals(String answer) {
        return this.answer.equalsIgnoreCase(answer);
    }

}
