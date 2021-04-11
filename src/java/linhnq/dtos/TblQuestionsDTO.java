/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnq.dtos;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author quocl
 */
public class TblQuestionsDTO implements Serializable {
    private String question_id;
    private String question_content;
    private String answer_content;
    private String answer_correct;
    private Date createDate;
    private String subject;
    private String status;

    public TblQuestionsDTO(String question_id, String question_content, String answer_content, String answer_correct, Date createDate, String subject, String status) {
        this.question_id = question_id;
        this.question_content = question_content;
        this.answer_content = answer_content;
        this.answer_correct = answer_correct;
        this.createDate = createDate;
        this.subject = subject;
        this.status = status;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_content() {
        return question_content;
    }

    public void setQuestion_content(String question_content) {
        this.question_content = question_content;
    }

    public String getAnswer_content() {
        return answer_content;
    }

    public void setAnswer_content(String answer_content) {
        this.answer_content = answer_content;
    }

    public String getAnswer_correct() {
        return answer_correct;
    }

    public void setAnswer_correct(String answer_correct) {
        this.answer_correct = answer_correct;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
