package ua.rd.service;

import ua.rd.domain.Tweet;
import ua.rd.repository.TweetRepository;

/**
 * Created by Vitalii_Sharapov on 7/5/2017.
 */
public class SimpleTweetService implements TweetService {

    private final TweetRepository tweetRepository;

    public SimpleTweetService(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Override
    public Iterable<Tweet> getAllTweets() {
        return tweetRepository.getAllTweets();
    }
}
