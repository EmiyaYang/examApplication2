package com.test;

import com.entity.*;
import com.entity.relation.UserRPaper;
import com.model.Interfaces.ExamLogic;
import com.utills.gson.JsonManger;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ssHss on 2017/6/11.
 */
public class ExamLogicTest implements ExamLogic {
    public PaperBean getPaperBeanById(int paperId){
        PaperBean paperBean = new PaperBean();
        JsonManger jsonManger = new JsonManger();
        Data data;
        try {
            data = jsonManger.loadDataFormJson();
            for (int i = 0;i<data.getPAPER().size();i++){
                if(paperId==data.getPAPER().get(i).getId()){
                    paperBean.setId(paperId);
                    paperBean.setCreateTime(data.getPAPER().get(i).getCreateTime());
                    paperBean.setBrief(data.getPAPER().get(i).getBrief());
                    paperBean.setName(data.getPAPER().get(i).getName());
                    break;
                }
            }
            int click=0;
            for (int i=0;i<data.getUSER_PAPER().size();i++){
                if(data.getUSER_PAPER().get(i).getPaperId()==paperId){
                    click++;
                }
            }
            paperBean.setClick(click);
        } catch (UnsupportedEncodingException e) {
        }
        return  paperBean;
    }
    public List<QuestionBean> createNewExam(int paperId, int size) {
        List<QuestionBean> tempResult = new ArrayList<>();
        List<QuestionBean> result = new ArrayList<>();
        JsonManger jsonManger = new JsonManger();
        int id = paperId;
        Data data;
        try {
            data = jsonManger.loadDataFormJson();
            for (int i = 0; i < data.getQUESTION().size(); i++) {
                if (id == data.getQUESTION().get(i).getSource()) {
                    tempResult.add(data.getQUESTION().get(i));
                }
            }
            if (tempResult.size() < size)
                return tempResult;
            else {
                Random random = new Random();
                //存放临时的下标
                int temp;
                List<Integer> tempNumList = new ArrayList<Integer>();
                for (int i = 0; i < size; i++) {
                    temp = random.nextInt(tempResult.size());//将产生的随机数作为被抽list的索引
                    if (!tempNumList.contains(temp)) {
                        tempNumList.add(temp);
                    } else {
                        i--;
                    }
                }
                tempNumList.sort(new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return Integer.compare(o1, o2);
                    }
                });
                for (int i = 0; i < tempNumList.size(); i++) {
                    result.add(tempResult.get(tempNumList.get(i)));
                }

            }

        } catch (UnsupportedEncodingException e) {
        }


        return result;
    }

    public List<QuestCorrection> commitAndCorrect(int userId, int paperId, int secondUsed, List<AnswerBean> submission) {
        JsonManger jsonManger = new JsonManger();
        Data data;
        List<QuestCorrection> result = new ArrayList<>();
        int num = 0;
        try {
            data = jsonManger.loadDataFormJson();
            //假设回答的问题id是有序的则不必遍历整个问题集合，只需从上次匹配成功的地方继续往下搜索即可
            for (int i = 0, j = 0; i < submission.size(); i++) {
                QuestCorrection questCorrection = new QuestCorrection();
                for (; j < data.getQUESTION().size(); j++) {
                    if (submission.get(i).getQid() == data.getQUESTION().get(j).getId()) {
                        //设置正确答案
                        questCorrection.setCorrect_choice(data.getQUESTION().get(j).getAnswer());
                        if (submission.get(i).getAnswer().equals(data.getQUESTION().get(j).getAnswer())) {
                            questCorrection.setCorrect(true);
                            num++;
                        } else {
                            questCorrection.setCorrect(false);
                        }
                        break;
                    }
                }
                result.add(questCorrection);
            }
            //新建一条考试记录
            UserRPaper userRPaper = new UserRPaper();
            userRPaper.setUserId(userId);
            userRPaper.setPaperId(paperId);
            userRPaper.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            userRPaper.setTimeUsed(secondUsed);
            userRPaper.setAccuracy(num / submission.size());
            //向本地文件写入新的考试记录
            data.getUSER_PAPER().add(userRPaper);
            jsonManger.saveDataIntoJson(data);

        } catch (UnsupportedEncodingException e) {
        }
        return result;
    }
}
