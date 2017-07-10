package ua.rd.repository;

import ua.rd.domain.Tweet;
import ua.rd.ioc.Benchmark;

import java.util.Arrays;
import java.util.List;


public class InMemTweetRepository implements TweetRepository {

    private List<Tweet> tweets;

    public void init() {
        tweets = Arrays.asList(
                new Tweet("vitalii", "first tweet"),
                new Tweet("dassem", "first sword")
        );
    }

    @Override
    @Benchmark
    public Iterable<Tweet> getAllTweets() {
        return tweets;
    }
}
