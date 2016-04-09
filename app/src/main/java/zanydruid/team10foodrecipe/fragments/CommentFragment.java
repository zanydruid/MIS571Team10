package zanydruid.team10foodrecipe.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import zanydruid.team10foodrecipe.Models.Comment;
import zanydruid.team10foodrecipe.R;
import zanydruid.team10foodrecipe.utility.Kitchen;

/**
 * Created by yizhu on 4/5/16.
 */
public class CommentFragment extends Fragment {
    public static final String ARG_COMMENT = "comment";
    private List<Comment> mComments;
    private int mId;

    private TextView mTitleTextView;
    private EditText mCommentInput;
    private RatingBar mRatingBar;
    private Button mCommentButton;
    private ListView mListView;
    private CommentAdapter mAdapter;

    public CommentFragment newInstance(int id){
        Bundle args = new Bundle();
        args.putSerializable(ARG_COMMENT, id);
        CommentFragment fragment = new CommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mId = (int)getArguments().getSerializable(ARG_COMMENT);
        mComments = Kitchen.getInstance(getActivity()).getCommentsById(mId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comments_layout,container,false);
        mTitleTextView = (TextView) view.findViewById(R.id.fragment_comments_title_text_view);
        mTitleTextView.setText("COMMENTS");
        mCommentInput = (EditText) view.findViewById(R.id.fragment_comments_comment);
        mRatingBar = (RatingBar) view.findViewById(R.id.fragment_comments_rating_bar);
        mCommentButton = (Button) view.findViewById(R.id.fragment_comments_submit_button);
        mCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int recipeId = mId;
                String commentString = mCommentInput.getText().toString();
                int rating = (int) mRatingBar.getRating();
                Kitchen.getInstance(getActivity()).writeComment(recipeId,commentString,rating);
                Toast.makeText(getActivity(),"A new comment is inserted.",Toast.LENGTH_SHORT).show();
                mComments = Kitchen.getInstance(getActivity()).getCommentsById(mId);
                Comment lastComment = mComments.get(mComments.size()-1);
                mAdapter.add(lastComment);
                mAdapter.notifyDataSetChanged();
            }
        });
        mListView = (ListView)view.findViewById(R.id.fragment_comments_list_view);
        updateUI(mComments);

        return view;
    }

    public void updateUI(List<Comment> comments){
        if(mAdapter==null){
            mAdapter = new CommentAdapter(getActivity(),comments);
            mListView.setAdapter(mAdapter);
        }else{

            mAdapter.notifyDataSetChanged();
        }
    }

    private class CommentAdapter extends ArrayAdapter<Comment>{

        public CommentAdapter(Context context, List<Comment> comments){
            super(context,0,comments);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Comment comment = getItem(position);
            if(convertView==null){
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.list_view_comment,parent,false);
            }
            RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.list_view_comment_rating_indicator);
            ratingBar.setRating(comment.getRating());

            TextView commentString = (TextView) convertView.findViewById(R.id.list_view_comment_comment_text_view);
            commentString.setText(comment.getComment());
            return convertView;
        }
    }
}
