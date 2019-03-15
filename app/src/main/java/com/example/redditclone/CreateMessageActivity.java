package com.example.redditclone;

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


public class CreateMessageActivity extends AppCompatActivity {

    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private FirebaseUser user;
    private Button cancelButton, submitButton;
    private EditText titleEditText, postEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);

        user = FirebaseAuth.getInstance().getCurrentUser();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Posts");

        postEditText = findViewById(R.id.messageEditText);
        titleEditText = findViewById(R.id.titleEditText);

        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSubmit();
            }
        });
    }

    void attemptSubmit() {
        String postText = postEditText.getText().toString();
        String titleText = titleEditText.getText().toString();

        if (postText.length() > 0 && titleText.length() > 0) {
            String key = myRef.push().getKey();
            Post newPost = new Post(titleText, postText, user.getEmail(), key, 0);
            myRef.child(key).setValue(newPost);

            Toast.makeText(CreateMessageActivity.this, "Sent", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(CreateMessageActivity.this, "Title & message must contain text.", Toast.LENGTH_SHORT).show();
        }
    }
}
