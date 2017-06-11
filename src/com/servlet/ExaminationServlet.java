package com.servlet;

import com.entity.PaperBean;
import com.entity.QuestionBean;
import com.model.Interfaces.ExamLogic;
import com.test.ExamLogicTest;
import com.utills.Please;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by ssHss on 2017/6/11.
 */
public class ExaminationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        getExamContent(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    private void getExamContent(javax.servlet.http.HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        int paperId = Integer.parseInt(request.getParameter("paperId")) ;
        //问题多少
        int size=20;
        PrintWriter out = response.getWriter();
        List<QuestionBean> questions;
        PaperBean paperBean;
        ExamLogic examLogic = new ExamLogicTest();
        String forDescription = request.getParameter("forDescription");
        if(forDescription!=null){
            paperBean = examLogic.getPaperBeanById(paperId);
            Please.gson.toJson(paperBean,out);
        }else{
            questions = examLogic.createNewExam(paperId,size);
            Please.gson.toJson(questions,out);
        }

        out.flush();
        out.close();

    }
}
