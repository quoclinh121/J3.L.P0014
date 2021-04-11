/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnq.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import linhnq.dtos.TblQuestionsDTO;
import linhnq.utils.DBHelper;

/**
 *
 * @author quocl
 */
public class TblQuestionsDAO implements Serializable {

    public List<TblQuestionsDTO> searchQuestion(String searchValue, String subject, String status, int pageNumber, int pageSize) throws SQLException, NamingException {
        List<TblQuestionsDTO> result = null;
        Connection con = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT question_id, question_content, answer_content, answer_correct, createDate, subjectName, status "
                        + "FROM tblQuestions a, tblSubjects b "
                        + "WHERE a.subjectID = b.subjectID AND question_content LIKE ? AND subjectName LIKE ? AND status LIKE ? "
                        + "ORDER BY question_content ASC "
                        + "OFFSET ? * (? - 1) ROWS "
                        + "FETCH NEXT ? ROWS ONLY";
                preStm = con.prepareStatement(sql);
                preStm.setString(1, "%" + searchValue + "%");
                preStm.setString(2, "%" + subject + "%");
                preStm.setString(3, "%" + status + "%");
                preStm.setInt(4, pageSize);
                preStm.setInt(5, pageNumber);
                preStm.setInt(6, pageSize);
                rs = preStm.executeQuery();
                while (rs.next()) {
                    String question_id = rs.getString("question_id");
                    String question_content = rs.getString("question_content");
                    String answer_content = rs.getString("answer_content");
                    String answer_correct = rs.getString("answer_correct");
                    Date createDate = rs.getDate("createDate");
                    String subjectName = rs.getString("subjectName");
                    String statusOfQuest = null;
                    if ("1".equals(rs.getString("status"))) {
                        statusOfQuest = "Active";
                    } else {
                        statusOfQuest = "DeActive";
                    }

                    TblQuestionsDTO dto = new TblQuestionsDTO(question_id, question_content, answer_content, answer_correct, createDate, subjectName, statusOfQuest);

                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.next();
            }
            if (preStm != null) {
                preStm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public int getNoOfRecord(String searchValue, String subject, String status) throws SQLException, NamingException {
        int noOfRecord = 0;
        Connection con = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT COUNT(question_id) AS noOfRecord "
                        + "FROM tblQuestions a, tblSubjects b "
                        + "WHERE a.subjectID = b.subjectID AND question_content LIKE ? AND subjectName LIKE ? AND status LIKE ?";
                preStm = con.prepareStatement(sql);
                preStm.setString(1, "%" + searchValue + "%");
                preStm.setString(2, "%" + subject + "%");
                preStm.setString(3, "%" + status + "%");
                rs = preStm.executeQuery();
                if (rs.next()) {
                    noOfRecord = rs.getInt("noOfRecord");
                }
            }
        } finally {
            if (rs != null) {
                rs.next();
            }
            if (preStm != null) {
                preStm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return noOfRecord;
    }

    public boolean deleteQuestion(String questionID) throws SQLException, NamingException {
        boolean result = false;
        Connection con = null;
        PreparedStatement preStm = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE tblQuestions "
                        + "SET status = ? "
                        + "WHERE question_id = ?";
                preStm = con.prepareStatement(sql);
                preStm.setBoolean(1, false);
                preStm.setString(2, questionID);
                result = preStm.executeUpdate() > 0 ? true : false;
            }
        } finally {
            if (preStm != null) {
                preStm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public TblQuestionsDTO getQuestion(String questionID) throws SQLException, NamingException {
        TblQuestionsDTO dto = null;
        Connection con = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT question_content, answer_content, answer_correct, createDate, subjectName, status "
                        + "FROM tblQuestions a, tblSubjects b "
                        + "WHERE a.subjectID = b.subjectID AND question_id = ?";
                preStm = con.prepareStatement(sql);
                preStm.setString(1, questionID);
                rs = preStm.executeQuery();
                if (rs.next()) {
                    String question_content = rs.getString("question_content");
                    String answer_content = rs.getString("answer_content");
                    String answer_correct = rs.getString("answer_correct");
                    Date createDate = rs.getDate("createDate");
                    String subjectName = rs.getString("subjectName");
                    String statusOfQuest = null;
                    if ("1".equals(rs.getString("status"))) {
                        statusOfQuest = "Active";
                    } else {
                        statusOfQuest = "DeActive";
                    }
                    dto = new TblQuestionsDTO(questionID, question_content, answer_content, answer_correct, createDate, subjectName, statusOfQuest);
                }
            }
        } finally {
            if (rs != null) {
                rs.next();
            }
            if (preStm != null) {
                preStm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return dto;
    }

    public boolean updateQuestion(TblQuestionsDTO dto) throws SQLException, NamingException {
        boolean result = false;
        Connection con = null;
        PreparedStatement preStm = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE tblQuestions "
                        + "SET question_content = ?, answer_content = ?, answer_correct = ?, subjectID = (SELECT subjectID FROM tblSubjects WHERE subjectName = ?), status = ? "
                        + "WHERE question_id = ?";
                preStm = con.prepareStatement(sql);
                preStm.setString(1, dto.getQuestion_content());
                preStm.setString(2, dto.getAnswer_content());
                preStm.setString(3, dto.getAnswer_correct());
                preStm.setString(4, dto.getSubject());
                preStm.setString(5, dto.getStatus());
                preStm.setString(6, dto.getQuestion_id());
                result = preStm.executeUpdate() > 0 ? true : false;
            }
        } finally {
            if (preStm != null) {
                preStm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    private Timestamp convertDate(Date date) {
        Timestamp sqlDate = new Timestamp(date.getTime());
        return sqlDate;
    }

    public boolean createNewQuestion(TblQuestionsDTO dto) throws SQLException, NamingException {
        boolean result = false;
        Connection con = null;
        PreparedStatement preStm = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO tblQuestions(question_id, question_content, answer_content, answer_correct, createDate, subjectID, status) "
                        + "VALUES(?, ?, ?, ?, ?, (SELECT subjectID FROM tblSubjects WHERE subjectName = ?), ?)";
                preStm = con.prepareStatement(sql);
                preStm.setString(1, dto.getQuestion_id());
                preStm.setString(2, dto.getQuestion_content());
                preStm.setString(3, dto.getAnswer_content());
                preStm.setString(4, dto.getAnswer_correct());
                preStm.setTimestamp(5, convertDate(dto.getCreateDate()));
                preStm.setString(6, dto.getSubject());
                preStm.setString(7, dto.getStatus());
                result = preStm.executeUpdate() > 0 ? true : false;
            }
        } finally {
            if (preStm != null) {
                preStm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public List<TblQuestionsDTO> getRandomQuestions(int quantity, String subject) throws SQLException, NamingException {
        List<TblQuestionsDTO> result = null;
        Connection con = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT TOP (?) question_id, question_content, answer_content, answer_correct "
                        + "FROM tblQuestions a, tblSubjects b "
                        + "WHERE a.subjectID = b.subjectID AND subjectName = ? AND status = ? "
                        + "ORDER BY NEWID()";
                preStm = con.prepareStatement(sql);
                preStm.setInt(1, quantity);
                preStm.setString(2, subject);
                preStm.setString(3, "1");
                rs = preStm.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }

                    String question_id = rs.getString("question_id");
                    String question_content = rs.getString("question_content");
                    String answer_content = rs.getString("answer_content");
                    String answer_correct = rs.getString("answer_correct");
                    TblQuestionsDTO dto = new TblQuestionsDTO(question_id, question_content, answer_content, answer_correct, null, subject, "1");
                    result.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (preStm != null) {
                preStm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }
}
