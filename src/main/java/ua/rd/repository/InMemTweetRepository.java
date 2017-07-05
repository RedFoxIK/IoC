package ua.rd.repository;

import ua.rd.domain.Tweet;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Vitalii_Sharapov on 7/5/2017.
 */
public class InMemTweetRepository implements TweetRepository {

    private List<Tweet> tweets = Arrays.asList(
            new Tweet("vitalii", "first tweet"),
            new Tweet("dassem", "first sword")
    );

    @Override
    public Iterable<Tweet> getAllTweets() {
        return tweets;
    }
}
