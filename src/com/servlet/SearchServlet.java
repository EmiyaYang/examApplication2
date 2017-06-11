package com.servlet;


import com.google.gson.Gson;
import com.model.Interfaces.SearchLogic;
import com.test.SearchTest;


import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;


//import javax.servlet.*;


/*
 * Created by ssHss on 2017/5/30.
 */
public class SearchServlet extends HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        getSearchResult(request,response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request,response);
    }

    private void getSearchResult(javax.servlet.http.HttpServletRequest request,javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String type = request.getParameter("type");
        String keyword = request.getParameter("keyword");
//        String subject = request.getParameter("subject");
//        int sortWay = Integer.parseInt(request.getParameter("sortWay")) ;
        int sortWay=0;
        PrintWriter out = response.getWriter();
        SearchLogic searcher = new SearchTest();
        Gson gson = new Gson();

        if(type.equals("paper")){
           gson.toJson(searcher.getPaperBean(keyword,sortWay),out);

        }
        else if(type.equals("question")){
            int paperId = Integer.parseInt(request.getParameter("paperId"));
           gson.toJson(searcher.getQuestionBeanByPaperId(paperId,sortWay),out);
        }
        out.flush();
        out.close();
    }
}
