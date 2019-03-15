package com.example.redditclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateCommentActivity extends AppCompatActivity {

    private String parentKey;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private FirebaseUser user;
    EditText commentEditText;
    Button submitComment, cancelComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comment);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        parentKey = b.getString("parentKey");


        user = FirebaseAuth.getInstance().getCurrentUser();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Posts/" + parentKey + "/Comments");

        commentEditText = findViewById(R.id.commentMessageEditText);

        submitComment = findViewById(R.id.submitCommentButton);
        submitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendComment();
                finish();
            }
        });
        cancelComment = findViewById(R.id.cancelCommentButton);
        cancelComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    protected void sendComment() {
        String message = commentEditText.getText().toString();

        if (message.length() > 0) {
            String key = myRef.push().getKey();
            Comment comment = new Comment(key, parentKey, message, user.getEmail(), 0);
            myRef.child(key).setValue(comment);

        } else {
            Toast.makeText(CreateCommentActivity.this, "Enter a comment", Toast.LENGTH_SHORT).show();
        }
    }
}
