package com.sanjivani.sanjivani_feedback.model;

public class FeedbackModel {

    public FeedbackModel() {
    }

    String feedbackImage, feedbackTitle, feedbackDescribe, feedbackUrl;

    public FeedbackModel(String feedbackImage, String feedbackTitle, String feedbackDescribe, String feedbackUrl) {
        this.feedbackImage = feedbackImage;
        this.feedbackTitle = feedbackTitle;
        this.feedbackDescribe = feedbackDescribe;
        this.feedbackUrl = feedbackUrl;
    }


    public String getFeedbackImage() {
        return feedbackImage;
    }

    public void setFeedbackImage(String feedbackImage) {
        this.feedbackImage = feedbackImage;
    }

    public String getFeedbackTitle() {
        return feedbackTitle;
    }

    public void setFeedbackTitle(String feedbackTitle) {
        this.feedbackTitle = feedbackTitle;
    }

    public String getFeedbackDescribe() {
        return feedbackDescribe;
    }

    public void setFeedbackDescribe(String feedbackDescribe) {
        this.feedbackDescribe = feedbackDescribe;
    }

    public String getFeedbackUrl() {
        return feedbackUrl;
    }

    public void setFeedbackUrl(String feedbackUrl) {
        this.feedbackUrl = feedbackUrl;
    }
}
