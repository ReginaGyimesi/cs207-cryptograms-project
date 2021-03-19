package main.players;

public class Player {
    private String username;
    private double accuracy;
    private int totalGuesses;
    private int totalCorrectGuesses;
    private int cryptogramsPlayed;
    private int cryptogramsCompleted;
    private int cryptogramsSuccessfullyCompleted;

    public Player(String playerName) {
        username = playerName;
        accuracy = 0;
        totalGuesses = 0;
        totalCorrectGuesses = 0;
        cryptogramsPlayed = 0;
        cryptogramsCompleted = 0;
        cryptogramsSuccessfullyCompleted = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public void incrementTotalGuesses() {
        totalGuesses++;
        updateAccuracy();
    }

    public void incrementTotalCorrectGuesses() {
        totalCorrectGuesses++;
        updateAccuracy();
    }

    public void incrementCryptogramsSuccessfullyCompleted() { cryptogramsSuccessfullyCompleted++; }

    public void incrementCryptogramsCompleted() {
        cryptogramsCompleted++;
    }

    public void incrementCryptogramsPlayed() {
        cryptogramsPlayed++;
    }

    public int getNumCryptogramsSuccessfullyCompleted() {
        return cryptogramsSuccessfullyCompleted;
    }

    public int getNumCryptogramsCompleted() {
        return cryptogramsCompleted;
    }

    public int getNumCryptogramsPlayed() {
        return cryptogramsPlayed;
    }

    public int getTotalGuesses() {
        return totalGuesses;
    }

    public int getTotalCorrectGuesses() {
        return totalCorrectGuesses;
    }

    private void updateAccuracy(){
        if(this.totalGuesses != 0){
            this.accuracy = (double)this.totalCorrectGuesses / (double)this.totalGuesses;
        }
    }
}