package com.model.Interfaces;

import com.entity.AnswerBean;
import com.entity.PaperBean;
import com.entity.QuestCorrection;
import com.entity.QuestionBean;

import java.util.List;

/**
 * Created by Tnecesoc on 2017/6/11.
 */
public interface ExamLogic {
    PaperBean getPaperBeanById(int paperId);

    List<QuestionBean> createNewExam(int paperId, int size);

    List<QuestCorrection> commitAndCorrect(int userId, int paperId, int secondUsed, List<AnswerBean> submission);
}
