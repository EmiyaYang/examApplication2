package com.model.Interfaces;

import com.entity.PaperBean;
import com.entity.QuestionBean;

import java.util.List;

/**
 * Created by Tnecesoc on 2017/6/5.
 */
public interface SearchLogic {

    List<PaperBean> getPaperBean(String keyword, int sortway);

    List<QuestionBean> getQuestionBeanByPaperId(int id, int sortway);

}
