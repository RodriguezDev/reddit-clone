package com.example.redditclone;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Comment implements Comparable<Comment> {
    String key, parentKey, content, poster;
    int score;

    Comment() {

    }

    Comment(String newKey, String newParentKey, String newContent, String newPoster, int newScore) {
        key = newKey;
        parentKey = newParentKey;
        content = newContent;
        poster = newPoster;
        score = newScore;
    }

    String getKey() {
        return key;
    }
    String getParentKey() {
        return parentKey;
    }
    String getContent() {
        return content;
    }
    String getPoster() {
        return poster;
    }
    int getScore() {
        return score;
    }

    public void upvoteComment() {
        score++;
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Posts/" + parentKey + "/Comments");
        myRef.child(key).setValue(this);
    }

    public void downvoteComment() {
        score--;
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Posts/" + parentKey + "/Comments");
        myRef.child(key).setValue(this);
    }

    public void deleteComment() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Posts/" + parentKey + "/Comments");
        myRef.child(key).removeValue();
    }

    @Override
    public int compareTo(Comment c) {
        return c.getScore() - score;
    }
}
