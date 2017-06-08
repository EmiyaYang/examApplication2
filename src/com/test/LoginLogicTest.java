package com.test;

import com.entity.*;
import com.entity.relation.UserRPaper;
import com.model.Interfaces.LoginLogic;
import com.utills.gson.JsonManger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class LoginLogicTest implements LoginLogic {

    private Data data;
    private List<UserBean> user;
    private List<PaperBean> paper;
    private List<QuestionBean> question;

    private List<UserRPaper> userRPapers;

    private JsonManger jsonManger;

    public LoginLogicTest() {

        jsonManger = new JsonManger();

        try {
            data = jsonManger.loadDataFormJson();
            user = data.getUSER();
            paper = data.getPAPER();
            question = data.getQUESTION();

            userRPapers = data.getUSER_PAPER();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkEmail(String email) {

        for(UserBean x: user){
            if(email.equals(x.getEmail()))
                return true;
        }

        return false;
    }

    @Override
    public UserTransferBean signUp(String email, String password) {

        user.add(new UserBean(user.size(),"新人驾到",email,password,"新兵上任三桶水！"));


        UserTransferBean userTransferBean = new UserTransferBean(user.size(),"新人驾到",email,"新兵上台三桶水！",null,null);


        try {
            jsonManger.saveDataIntoJson(data);
            return userTransferBean;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UserTransferBean signIn(String email, String password) {

        List<UserRPaper> record;

        for(UserBean x: user){
            if(email.equals((x.getEmail())) && password.equals(x.getPassword())){
                record = this.getUserRPaper(x.getId());
                return new UserTransferBean(x.getId(),x.getName(),x.getEmail(),x.getSignature(),record,getPaper(record));
            }
        }
        return null;
    }

    //返回指定用户的考试记录
    List<UserRPaper> getUserRPaper(int id){

        List<UserRPaper> list = new ArrayList<>();

        for(UserRPaper r : userRPapers){
            if(r.getUserId() == id){
                list.add(r);
            }
        }

        return list;
    }

    //返回用户考试记录中对应的试卷集合，长度应与用户的考试记录相同
    List<PaperBean> getPaper(List<UserRPaper> userRPapers){

        List<PaperBean> list = new ArrayList<>();

        for(UserRPaper r : userRPapers){
            for(PaperBean p : paper){
                if(r.getPaperId() == p.getId()){
                    list.add(p);
                }
            }
        }

        return list;
    }
}
