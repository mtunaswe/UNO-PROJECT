package game_model;

/**
 * The UserInfo class represents a user in the game, managing the user's 
 * nickname, password, wins, losses, total score, total games, and rank.
 */
public class UserInfo {
    private String nickname;
    private String password;
    private int wins;
    private int losses;
    private int totalScore;
    private int totalGames;
    private int rank;

    /**
     * Constructs a new UserInfo object with the specified details.
     * 
     * @param nickname the user's nickname
     * @param password the user's password
     * @param wins the number of wins the user has
     * @param losses the number of losses the user has
     * @param totalScore the total score the user has accumulated
     * @param totalGames the total number of games the user has played
     */
    public UserInfo(String nickname, String password, int wins, int losses, int totalScore, int totalGames) {
        this.nickname = nickname;
        this.password = password;
        this.wins = wins;
        this.losses = losses;
        this.totalScore = totalScore;
        this.totalGames = totalGames;
    }

    /**
     * Gets the user's nickname.
     * 
     * @return the user's nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets the user's nickname.
     * 
     * @param nickname the new nickname of the user
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Gets the user's password.
     * 
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     * 
     * @param password the new password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the number of wins the user has.
     * 
     * @return the number of wins
     */
    public int getWins() {
        return wins;
    }

    /**
     * Sets the number of wins the user has.
     * 
     * @param wins the new number of wins
     */
    public void setWins(int wins) {
        this.wins = wins;
    }

    /**
     * Gets the number of losses the user has.
     * 
     * @return the number of losses
     */
    public int getLosses() {
        return losses;
    }

    /**
     * Sets the number of losses the user has.
     * 
     * @param losses the new number of losses
     */
    public void setLosses(int losses) {
        this.losses = losses;
    }

    /**
     * Gets the total score the user has accumulated.
     * 
     * @return the total score
     */
    public int getTotalScore() {
        return totalScore;
    }

    /**
     * Sets the total score the user has accumulated.
     * 
     * @param totalScore the new total score
     */
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * Gets the user's rank.
     * 
     * @return the user's rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * Sets the user's rank.
     * 
     * @param rank the new rank
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * Gets the total number of games the user has played.
     * 
     * @return the total number of games
     */
    public int getTotalGames() {
        return totalGames;
    }

    /**
     * Sets the total number of games the user has played.
     * 
     * @param totalGames the new total number of games
     */
    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    /**
     * Calculates the user's win-loss ratio.
     * 
     * @return the win-loss ratio, or the number of wins if there are no losses
     */
    public double getWinLossRatio() {
        return (losses == 0) ? wins : (double) wins / losses;
    }

    /**
     * Calculates the user's average score per game.
     * 
     * @return the average score per game, or the total score if there are no games
     */
    public double getAverageScorePerGame() {
        return (totalGames == 0) ? totalScore : (double) totalScore / totalGames;
    }

    /**
     * Returns a string representation of the user's nickname and total score.
     * 
     * @return a string representation of the user
     */
    @Override
    public String toString() {
        return nickname + " - Score: " + totalScore;
    }
}
