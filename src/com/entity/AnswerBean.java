package com.entity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Tnecesoc on 2017/6/11.
 */
public class AnswerBean {

    private int qid;
    private List<Integer> answer;

    public AnswerBean() {}

    public AnswerBean(int qid, Integer[] answers) {
        this.qid = qid;
        this.answer = Arrays.asList(answers);
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }
}
