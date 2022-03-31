package engine;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Quiz {

    private int id;
    private String title;
    private String text;
    private List<String> options = new CopyOnWriteArrayList<>();
    private int answer;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    @JsonIgnore
    public int getAnswer() {
        return answer;
    }

    @JsonProperty
    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void setIdAfterCreateList(List<Quiz> quizzes) {
        int count = 1;
        for (Quiz quiz : quizzes) {
            quiz.setId(count);
            count++;
        }
    }
}
