package ua.rd.domain;

public class Tweet {
    private String user;
    private String message;

    public Tweet() {
    }

    public Tweet(String user, String message) {
        this.user = user;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "user='" + user + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
