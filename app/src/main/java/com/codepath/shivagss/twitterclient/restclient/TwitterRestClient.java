package com.codepath.shivagss.twitterclient.restclient;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.text.TextUtils;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.LinkedList;
import java.util.List;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterRestClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "5UaQRsEmVo8SxrEa3cK0tH57X";       // Change this
	public static final String REST_CONSUMER_SECRET = "FGtQOoYSPg5BSGqmNBSHVY11B5cjx0UnHmEsoOFpFpqkUBoP8B"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpbasictweets"; // Change this (here and in manifest)

	public TwitterRestClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

    public void getHomeTimeLine(String maxID, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("/statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        if(!TextUtils.isEmpty(maxID)){
            params.put("max_id", ""+(Long.parseLong(maxID)-1));
            params.put("count", "80");
        }
        params.put("since_id", "1");

        client.get(apiUrl, params, handler);
    }

    public void getMentionsTimeLine(String maxID, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("/statuses/mentions_timeline.json");
        RequestParams params = new RequestParams();
        if(!TextUtils.isEmpty(maxID)){
            params.put("max_id", ""+(Long.parseLong(maxID)-1));
            params.put("count", "80");
        }
        params.put("since_id", "1");

        client.get(apiUrl, params, handler);
    }

    public void getUserTimeLine(String userID, String maxID, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("/statuses/user_timeline.json");
        RequestParams params = new RequestParams();
        if(!TextUtils.isEmpty(maxID)){
            params.put("max_id", ""+(Long.parseLong(maxID)-1));
        }
        params.put("since_id", "1");
        params.put("count", "80");
        if(!TextUtils.isEmpty(userID)) {
            params.put("user_id", userID);
        }

        client.get(apiUrl, params, handler);
    }

    public void getFavoritesTimeLine(String userID, String maxID, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("/favorites/list.json");
        RequestParams params = new RequestParams();
        if(!TextUtils.isEmpty(maxID)){
            params.put("max_id", ""+(Long.parseLong(maxID)-1));
            params.put("count", "80");
        }
        params.put("since_id", "1");
        if(!TextUtils.isEmpty(userID)) {
            params.put("user_id", userID);
        }

        client.get(apiUrl, params, handler);
    }

    public void getFriendsTimeLine(String userID, String maxID, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("friends/list.json");
        RequestParams params = new RequestParams();
        if(!TextUtils.isEmpty(maxID)){
            params.put("cursor", ""+(Long.parseLong(maxID)-1));
            params.put("count", "80");
        }
        if(!TextUtils.isEmpty(userID)) {
            params.put("user_id", userID);
        }

        client.get(apiUrl, params, handler);
    }

    public void getFollowersTimeLine(String userID, String maxID, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("followers/list.json");
        RequestParams params = new RequestParams();
        if(!TextUtils.isEmpty(maxID)){
            params.put("cursor", ""+(Long.parseLong(maxID)-1));
            params.put("count", "80");
        }
        if(!TextUtils.isEmpty(userID)) {
            params.put("user_id", userID);
        }

        client.get(apiUrl, params, handler);
    }

    public void getSearchTopTweets(String query, String maxID, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("search/tweets.json");
        RequestParams params = new RequestParams();
        if(!TextUtils.isEmpty(maxID)){
            params.put("max_id", ""+(Long.parseLong(maxID)-1));
            params.put("count", "80");
        }
        params.put("since_id", "1");
        params.put("result_type", "popular");
        params.put("q", query);

        client.get(apiUrl, params, handler);
    }

    public void getSearchAllTweets(String query, String maxID, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("search/tweets.json");
        RequestParams params = new RequestParams();
        if(!TextUtils.isEmpty(maxID)){
            params.put("max_id", ""+(Long.parseLong(maxID)-1));
            params.put("count", "80");
        }
        params.put("since_id", "1");
        params.put("result_type", "recent");
        params.put("q", query);

        client.get(apiUrl, params, handler);
    }

    public void getUserCredentials(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("/account/verify_credentials.json");
        client.get(apiUrl, null, handler);
    }

    public void postTweetUpdate(String tweet, AsyncHttpResponseHandler handler) {
        List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("status", tweet));
        String apiUrl = getApiUrl("/statuses/update.json?" + URLEncodedUtils.format(params, "utf-8"));
        client.post(apiUrl, null, handler);
    }

    public void getTweetDetails(String tweetID, AsyncHttpResponseHandler handler) {
        List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("id", (tweetID)));
        String apiUrl = getApiUrl("/statuses/show.json?" + URLEncodedUtils.format(params, "utf-8"));
        client.get(apiUrl, null, handler);
    }

    public void deleteTweet(String tweetID, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/destroy/" + tweetID+".json" );
        client.post(apiUrl, null, handler);
    }

    public void postReTweet(String tweetID, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/retweet/" + tweetID +".json");
        client.post(apiUrl, null, handler);
    }

    public void postFavoriteTweetUpdate(String tweetID, AsyncHttpResponseHandler handler) {
        List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("id", tweetID));
        String apiUrl = getApiUrl("favorites/create.json?" + URLEncodedUtils.format(params, "utf-8"));
        client.post(apiUrl, null, handler);
    }

    public void postUnFavoriteTweetUpdate(String tweetID, AsyncHttpResponseHandler handler) {
        List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("id", tweetID));
        String apiUrl = getApiUrl("favorites/destroy.json?" + URLEncodedUtils.format(params, "utf-8"));
        client.post(apiUrl, null, handler);
    }

}