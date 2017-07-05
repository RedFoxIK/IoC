package ua.rd.service;

import ua.rd.domain.Tweet;

/**
 * Created by Vitalii_Sharapov on 7/5/2017.
 */
public interface TweetService {
    Iterable<Tweet> getAllTweets();
}
