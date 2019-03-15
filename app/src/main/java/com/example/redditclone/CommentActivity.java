package com.example.redditclone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class CommentActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference commentRef;

    private ChildEventListener childEventListener;
    private ArrayList<Comment> comments;
    private CommentAdapter commentAdapter;

    ListView commentList;
    Button opUpvote, opDownvote, opDelete;
    TextView opTitle, opAuthor, opBody, opScore;
    Post currentPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        currentPost = (Post)intent.getSerializableExtra("post");

        database = FirebaseDatabase.getInstance();
        commentRef = database.getReference("Posts/" + currentPost.getKey() + "/Comments");

        comments = new ArrayList<>();

        childEventListener = new ChildEventListener(){
            @Override
            // Method is run when any new node is added to the database, and once
            // for every existing node when the activity is loaded
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                commentAdapter.add( dataSnapshot.getValue(Comment.class));
                Collections.sort(comments);
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Collections.sort(comments);
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        commentRef.addChildEventListener(childEventListener);

        commentAdapter = new CommentAdapter(this, comments);
        commentList = findViewById(R.id.commentList);
        commentList.setAdapter(commentAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CommentActivity.this, CreateCommentActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("parentKey", currentPost.getKey());
                i.putExtras(bundle);

                startActivity(i);
            }
        });

        opTitle = findViewById(R.id.commentOpTitle);
        opAuthor = findViewById(R.id.commentOpAuthor);
        opBody = findViewById(R.id.commentOpBody);
        opScore = findViewById(R.id.commentOpScore);

        opTitle.setText(currentPost.getTitle());
        opAuthor.setText(currentPost.getPoster());
        opBody.setText(currentPost.getContent());
        opScore.setText(Integer.toString(currentPost.getScore()));

        opUpvote = findViewById(R.id.commentOpUpvote);
        opUpvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPost.upvote();
                opScore.setText(Integer.toString(currentPost.getScore()));
            }
        });
        opDownvote = findViewById(R.id.commentOpDownvote);
        opDownvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPost.downvote();
                opScore.setText(Integer.toString(currentPost.getScore()));
            }
        });
        opDelete = findViewById(R.id.commentOpDelete);
        opDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPost.delete();
                finish();
            }
        });
    }

}
