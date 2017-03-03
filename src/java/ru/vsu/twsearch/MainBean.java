package ru.vsu.twsearch;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date; 


@ManagedBean(name="mainBean")
@SessionScoped
public class MainBean implements Serializable {

  private static final long serialVersionUID = 1L;
  private static final String TWITTER_CONSUMER_KEY = "JjibInCJ5nIRvjhkRV8Mm7mJC";
  private static final String TWITTER_SECRET_KEY = "PmTUpJC03VmSikE00M0L7MlegSbOJbSO8qRpOEFmMNlDUpkKTL";
  private static final String TWITTER_ACCESS_TOKEN = "558532494-Jt1aIaOJC1dR31G4BXYlaXqKSrFzVYHIGUEm0STx";
  private static final String TWITTER_ACCESS_TOKEN_SECRET = "BroOEHLexF190ubjAGTSp83yTbkOaItJ0FLATJntVETKn";
  
  private String hashtag = "";
  //Count of tweets
  private int numberOfTweets = 25;
  
  public int getNumberOfTweets() {
	return numberOfTweets;
  }
  public void setNumberOfTweets(int numberOfTweets) {
	this.numberOfTweets = numberOfTweets;
  }

//Result tweets
  private List<SrchTweet> twsResult = null;
  
  public List<SrchTweet> getTwsResult() {
	return twsResult;
  }
  public void setTwsResult(List<SrchTweet> twsResult) {
		this.twsResult = twsResult;
  }
	
  public String getHashtag() {
      return hashtag;
  }
  public void setHashtag(String hashtag) {
      this.hashtag = hashtag;
  }
	
  public void twSearchResult(AjaxBehaviorEvent event) {
	
	  ConfigurationBuilder cb = new ConfigurationBuilder();
	  cb.setDebugEnabled(true)
	      .setOAuthConsumerKey(TWITTER_CONSUMER_KEY)
	      .setOAuthConsumerSecret(TWITTER_SECRET_KEY)
	      .setOAuthAccessToken(TWITTER_ACCESS_TOKEN)
	      .setOAuthAccessTokenSecret(TWITTER_ACCESS_TOKEN_SECRET);
	  TwitterFactory tf = new TwitterFactory(cb.build());
	  Twitter twitter = tf.getInstance();
	  
		  try {
			  if (hashtag != "") {
				  
				  List<SrchTweet> mainresult = new LinkedList<SrchTweet>();
				  Query query = new Query("#" + hashtag);
				  QueryResult result;
				  
			  
			      if (numberOfTweets - mainresult.size() > 100)
				      query.setCount(10);
			      else 
				      query.setCount(numberOfTweets - mainresult.size());
  
	          	  result = twitter.search(query);
				  List<Status> tweets = result.getTweets();
				  
				  for (Status tweet : tweets) {
					  
				      System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
		              
				      String dateText = longDateToString(tweet.getCreatedAt().getTime());
		              
		              mainresult.add(new SrchTweet("@" + tweet.getUser().getScreenName(), dateText, tweet.getText()));
		          }
		          	
				  System.out.println("invoke");
			      twsResult = mainresult; 
			      
			  }
		  } catch (TwitterException te) {
		      te.printStackTrace();
		      System.out.println("Failed to search tweets: " + te.getMessage());
		      System.exit(-1);
	  }			  
	  
  }
  
  private String longDateToString(long valDate) {
      Date date = new Date(valDate);
      SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      return df2.format(date);
  }
}