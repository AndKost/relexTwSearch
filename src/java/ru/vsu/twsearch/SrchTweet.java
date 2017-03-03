package ru.vsu.twsearch;

import javax.faces.bean.SessionScoped;

@SessionScoped
public class SrchTweet {
	private String author;
	private String time;
	private String text;
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public SrchTweet(String author, String time, String text) {
		super();
		this.author = author;
		this.time = time;
		this.text = text;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
