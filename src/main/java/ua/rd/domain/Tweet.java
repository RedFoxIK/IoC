package ua.rd.domain;

/**
 * Created by Vitalii_Sharapov on 7/3/2017.
 */
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
