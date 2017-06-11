package com.entity;

import com.entity.relation.UserRPaper;

import java.util.List;


public class Data {

    private List<UserBean> USER;
    private List<PaperBean> PAPER;
    private List<QuestionBean> QUESTION;
    private List<UserRPaper> USER_PAPER;

    public List<UserBean> getUSER() {
        return USER;
    }

    public void setUSER(List<UserBean> USER) {
        this.USER = USER;
    }

    public List<PaperBean> getPAPER() {
        return PAPER;
    }

    public void setPAPER(List<PaperBean> PAPER) {
        this.PAPER = PAPER;
    }

    public List<QuestionBean> getQUESTION() {
        return QUESTION;
    }

    public void setQUESTION(List<QuestionBean> QUESTION) {
        this.QUESTION = QUESTION;
    }

    public List<UserRPaper> getUSER_PAPER() {
        return USER_PAPER;
    }

    public void setUSER_PAPER(List<UserRPaper> USER_PAPER) {
        this.USER_PAPER = USER_PAPER;
    }
}
