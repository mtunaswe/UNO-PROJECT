package game_model;

/**
 * The UserSession class manages the current user's session in the game.
 * It provides static methods to get and set the current user.
 */
public class UserSession {
    private static UserInfo currentUser;

    /**
     * Gets the current user.
     * 
     * @return the current user
     */
    public static UserInfo getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current user.
     * 
     * @param user the user to set as the current user
     */
    public static void setCurrentUser(UserInfo user) {
        currentUser = user;
    }
}
