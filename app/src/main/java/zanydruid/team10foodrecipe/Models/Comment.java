package zanydruid.team10foodrecipe.Models;

/**
 * Created by yizhu on 4/6/16.
 */
public class Comment {

    int CommentId;
    String comment;
    int rating;

    public Comment(String comment, int rating){
        this.comment = comment;
        this.rating = rating;
    }

    public int getCommentId() {
        return CommentId;
    }

    public void setCommentId(int commentId) {
        CommentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
