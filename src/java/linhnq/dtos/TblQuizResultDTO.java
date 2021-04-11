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
public class TblQuizResultDTO implements Serializable {
    private String resultID;
    private String subject;
    private Date dueDate;
    private String email;
    private int correct_answers;
    private double mark;

    public TblQuizResultDTO(String resultID, String subject, Date dueDate, String email, int correct_answers, double mark) {
        this.resultID = resultID;
        this.subject = subject;
        this.dueDate = dueDate;
        this.email = email;
        this.correct_answers = correct_answers;
        this.mark = mark;
    }

    public String getResultID() {
        return resultID;
    }

    public void setResultID(String resultID) {
        this.resultID = resultID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getCorrect_answers() {
        return correct_answers;
    }

    public void setCorrect_answers(int correct_answers) {
        this.correct_answers = correct_answers;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
}
