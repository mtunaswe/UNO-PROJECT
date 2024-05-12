package game_model;

public class UserInfo {
	private String nickname;
	private String password;
	private int wins;
	private int losses;
	private int totalScore;
	private int totalGames;
	
	public UserInfo(String nickname, String password, int wins, int losses, int totalScore, int totalGames) {
		this.nickname = nickname;
		this.password = password;
		this.wins = wins;
		this.losses = losses;
		this.totalScore = totalScore;
		this.totalGames = totalGames;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public int getTotalGames() {
		return totalGames;
	}

	public void setTotalGames(int totalGames) {
		this.totalGames = totalGames;
	}
	
	
	

}
