package com.utills;

import com.model.Interfaces.InforLogic;
import com.model.Interfaces.LoginLogic;
import com.test.InforLogicTest;
import com.test.LoginLogicTest;

/*
 * 单例模式管理接口
 */
public class ModelHelper {

    private static ModelHelper instance;

    private LoginLogic loginLogic;
    private InforLogic inforLogic;

    private ModelHelper(){
        this.loginLogic = new LoginLogicTest();
        this.inforLogic = new InforLogicTest();
    }

    public static ModelHelper getInstance(){
        if (instance == null){
            instance = new ModelHelper();
        }
        return instance;
    }

    public static LoginLogic getLoginLogic(){
        return getInstance().loginLogic;
    }

    public static InforLogic getInforgicTest(){ return getInstance().inforLogic; }
}
