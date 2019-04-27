package com.enema.enemaapp.models;

public class ReviewData {

    float review_rating;
    String review_comment;

    public ReviewData(){}

    public ReviewData(float review_rating, String review_comment) {
        this.review_rating = review_rating;
        this.review_comment = review_comment;
    }

    public float getReview_rating() {
        return review_rating;
    }

    public void setReview_rating(float review_rating) {
        this.review_rating = review_rating;
    }

    public String getReview_comment() {
        return review_comment;
    }

    public void setReview_comment(String review_comment) {
        this.review_comment = review_comment;
    }
}
