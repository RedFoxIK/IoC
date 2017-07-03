package ua.rd.domain;

public class Tweet {
    private String user;
    private String msg;

    public Tweet() {
    }

    public Tweet(String user, String msg) {
        this.user = user;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Tweet {" +
                "user ='" + user + '\'' +
                ", msg ='" + msg + '\'' +
                '}';
    }
}
