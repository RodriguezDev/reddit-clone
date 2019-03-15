package com.example.redditclone;

import android.content.Context;
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

public class CommentAdapter extends ArrayAdapter<Comment> {
    private Context mcontext;
    private List<Comment> commentList;

    public CommentAdapter(Context context, ArrayList<Comment> list) {
        super(context, 0, list);
        mcontext = context;
        commentList = list;
    }

    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null) {
            listItem = LayoutInflater.from(mcontext).inflate(R.layout.comment_layout,parent,false);
        }

        final Comment currentComment = commentList.get(position);
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Posts/" +
                currentComment.getParentKey() + "/Comments");

        TextView commentAuthor = listItem.findViewById(R.id.commentAuthor);
        commentAuthor.setText(currentComment.getPoster());

        TextView commentBody = listItem.findViewById(R.id.commentBody);
        commentBody.setText(currentComment.getContent());

        TextView commentScore = listItem.findViewById(R.id.commentScore);
        commentScore.setText(Integer.toString(currentComment.getScore()));

        Button commentUpvote = listItem.findViewById(R.id.upvoteComment);
        commentUpvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentComment.upvoteComment();
            }
        });

        Button commentDownvote = listItem.findViewById(R.id.downvoteComment);
        commentDownvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentComment.downvoteComment();
            }
        });

        Button commentDelete = listItem.findViewById(R.id.deleteComment);
        commentDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentComment.deleteComment();
                commentList.remove(currentComment);
            }
        });

        return listItem;
    }
}
