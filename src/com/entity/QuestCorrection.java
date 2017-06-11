package com.entity;

import com.entity.QuestionBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tnecesoc on 2017/6/11.
 */
public class QuestCorrection {
    private boolean correct;
    private List<Integer> correct_choice;

    public QuestCorrection() {
        correct_choice = new ArrayList<>();
    }

    public QuestCorrection(boolean correct, List<Integer> correct_choice) {
        this.correct = correct;
        this.correct_choice = correct_choice;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public List<Integer> getCorrect_choice() {
        return correct_choice;
    }

    public void setCorrect_choice(List<Integer> correct_choice) {
        this.correct_choice = correct_choice;
    }
}
