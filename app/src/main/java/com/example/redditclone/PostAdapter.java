package com.example.redditclone;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends ArrayAdapter<Post> {
    private Context mcontext;
    private List<Post> postList;

    public PostAdapter(Context context, ArrayList<Post> list) {
        super(context, 0, list);
        mcontext = context;
        postList = list;
    }

    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null) {
            listItem = LayoutInflater.from(mcontext).inflate(R.layout.list_layout,parent,false);
        }

        final Post currentPost = postList.get(position);

        TextView titleText = listItem.findViewById(R.id.postTitle);
        titleText.setText(currentPost.getTitle());
        TextView authorText = listItem.findViewById(R.id.postAuthor);
        authorText.setText(currentPost.getPoster());
        TextView postText = listItem.findViewById(R.id.postBody);
        postText.setText(currentPost.getContent());
        TextView scoreText = listItem.findViewById(R.id.score);
        scoreText.setText(Integer.toString(currentPost.getScore()));

        Button upvote = listItem.findViewById(R.id.upvote);
        upvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPost.upvote();
            }
        });

        Button downvote = listItem.findViewById(R.id.downvote);
        downvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPost.downvote();
            }
        });

        Button deleteButton = listItem.findViewById(R.id.deletePost);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPost.delete();
                postList.remove(currentPost);
            }
        });

        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CommentActivity.class);
                intent.putExtra("post", currentPost);

                getContext().startActivity(intent);
            }
        });

        return listItem;
    }


}
