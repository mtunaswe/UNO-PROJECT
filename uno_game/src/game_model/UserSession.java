package game_model;

public class UserSession {
    private static UserInfo currentUser;

    public static UserInfo getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(UserInfo user) {
        currentUser = user;
    }
}