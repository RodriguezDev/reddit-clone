package com.example.redditclone;

public class Post {
    private String title, content, poster, key;
    private int score;

    public Post() {

    }

    public Post(String newT, String newC, String newP, String newK) {
        title = newT;
        content = newC;
        poster = newP;
        score = 0;
        key = newK;

    }

    public void upvote() {
        score++;
    }

    public void downvote() {
        score--;
    }

    public String getTitle() { return title; }

    public String getContent() {
        return content;
    }

    public String getPoster() {
        return poster;
    }

    public int getScore() {
        return score;
    }

    public String getKey() { return key; }

}
