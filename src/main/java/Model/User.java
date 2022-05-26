package Model;

import Controller.GameController;

public class User {
    private String password;
    private String username;
    private boolean isLoggedIn;
    private double power;
    private double weakness;
    private double health;
    private boolean devilMode;
    private int score;
    private int thisScore;

    public void setScore(int score) {
        this.score += score;
        GameController.getInstance().showScore();
    }

    public int getScore() {
        return score;
    }

    public void setDevilMode(boolean devilMode) {
        this.devilMode = devilMode;
    }

    public boolean isDevilMode() {
        return devilMode;
    }

    public User(String password, String username, boolean isLoggedIn) {
        this.password = password;
        this.username = username;
        this.isLoggedIn = isLoggedIn;
        power = 1;
        weakness = 1;
        health =5;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public double getPower() {
        return power;
    }

    public double getWeakness() {
        return weakness;
    }

    public double getHealth() {
        return health;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public void setWeakness(double weakness) {
        this.weakness = weakness;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
