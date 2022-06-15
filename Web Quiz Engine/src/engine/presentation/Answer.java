package engine.presentation;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Answer {

    private boolean success;
    private String feedback;
    private List<Integer> answer;

    @JsonIgnore
    public List<Integer> getAnswer() {
        return answer;
    }

    @JsonProperty
    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public Answer() {

    }

    public Answer(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;

    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}