package com.example.redditclone;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class Post implements Comparable<Post>, Serializable {
    private String title, content, poster, key;
    private int score;

    public Post() { }

    public Post(String newT, String newC, String newP, String newK, int newS) {
        title = newT;
        content = newC;
        poster = newP;
        score = newS;
        key = newK;

    }

    public void upvote() {
        score++;
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Posts");
        myRef.child(key).setValue(this);
    }

    public void downvote() {
        score--;
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Posts");
        myRef.child(key).setValue(this);
    }

    public void delete() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Posts");
        myRef.child(key).removeValue();
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

    @Override
    public int compareTo(Post p) {
        return p.getScore() - score;
    }
}
