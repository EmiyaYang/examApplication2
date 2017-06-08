package com.test;

import com.entity.Data;
import com.entity.UserBean;
import com.model.Interfaces.InforLogic;
import com.utills.gson.JsonManger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class InforLogicTest implements InforLogic {
    @Override
    public boolean updateInfor(int id, String name, String signature) {

        JsonManger jsonManger = new JsonManger();

        Data data;

        try {
            data = jsonManger.loadDataFormJson();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }

        List<UserBean> list = data.getUSER();

        UserBean targetUser = list.get(id - 1);

        targetUser.setName(name);
        targetUser.setSignature(signature);

        try {
            jsonManger.saveDataIntoJson(data);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
