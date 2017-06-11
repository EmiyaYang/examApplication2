package com.entity;

import com.entity.relation.UserRPaper;
import com.utills.JDBCUtils.SQLIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 10388 on 2017/6/4.
 */
public class UserTransferBean {

    private int id;
    private String name;
    private String email;

    @SQLIgnore
    private List<UserRPaper> userRPapers;

    @SQLIgnore
    private List<PaperBean> paperBeans;

    public UserTransferBean() {
        userRPapers = new ArrayList<>();
        paperBeans = new ArrayList<>();
    }

    public UserTransferBean(int id, String name, String email, String signature, List<UserRPaper> userRPapers, List<PaperBean> paperBean) {
        this();
        this.id = id;
        this.name = name;
        this.email = email;
        this.signature = signature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<UserRPaper> getUserRPapers() {
        return userRPapers;
    }

    public void setUserRPapers(List<UserRPaper> userRPapers) {
        this.userRPapers = userRPapers;
    }

    public List<PaperBean> getPaperBeans() {
        return paperBeans;
    }

    public void setPaperBeans(List<PaperBean> paperBean) {
        this.paperBeans = paperBean;
    }

    private String signature;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}