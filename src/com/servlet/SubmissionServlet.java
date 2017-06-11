package com.servlet;

import com.entity.AnswerBean;
import com.google.gson.reflect.TypeToken;
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

public class SubmissionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        PrintWriter out = response.getWriter();
        List<AnswerBean> submission ;

        String str = request.getParameter("submission");
        int userId = Integer.parseInt(request.getParameter("userId"));
        int paperId = Integer.parseInt(request.getParameter("paperId"));
        int secondUsed = Integer.parseInt(request.getParameter("secondUsed"));
        submission = Please.gson.fromJson(str, new TypeToken<List<AnswerBean>>(){}.getType());
        ExamLogic grader = new ExamLogicTest();

        Please.gson.toJson(grader.commitAndCorrect(userId,paperId,secondUsed,submission),out);

        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

}
