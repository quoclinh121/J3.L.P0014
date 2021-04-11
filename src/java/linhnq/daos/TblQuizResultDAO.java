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
import linhnq.dtos.TblQuizResultDTO;
import linhnq.utils.DBHelper;

/**
 *
 * @author quocl
 */
public class TblQuizResultDAO implements Serializable {

    private Timestamp convertDate(Date date) {
        Timestamp sqlDate = new Timestamp(date.getTime());
        return sqlDate;
    }

    public boolean createNewResult(TblQuizResultDTO dto) throws SQLException, NamingException {
        boolean result = false;
        Connection con = null;
        PreparedStatement preStm = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO tblQuizResult(resultID, subject, dueDate, email) "
                        + "VALUES(?, (SELECT subjectID FROM tblSubjects WHERE subjectName = ?), ?, ?)";
                preStm = con.prepareStatement(sql);
                preStm.setString(1, dto.getResultID());
                preStm.setString(2, dto.getSubject());
                preStm.setTimestamp(3, convertDate(dto.getDueDate()));
                preStm.setString(4, dto.getEmail());
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

    public List<TblQuizResultDTO> loadQuizHistory(String subj, String email) throws SQLException, NamingException {
        List<TblQuizResultDTO> result = null;
        Connection con = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT a.resultID, subjectName, dueDate, COUNT(isCorrect) AS correct_answers "
                        + "  FROM tblQuizResult a, tblSubjects b, tblResultDetail c "
                        + "  WHERE a.resultID = c.resultID AND a.subject = b.subjectID AND isCorrect = 1 AND subjectName LIKE ? AND email = ? "
                        + "  GROUP BY a.resultID, subjectName, dueDate "
                        + "  ORDER BY dueDate DESC";
                preStm = con.prepareStatement(sql);
                preStm.setString(1, "%" + subj + "%"); 
                preStm.setString(2, email); 
                rs = preStm.executeQuery();
                while(rs.next()) {
                    String resultID = rs.getString("resultID");
                    String subject = rs.getString("subjectName");
                    Date dueDate = rs.getTimestamp("dueDate");
                    int correct_answer = rs.getInt("correct_answers");
                    TblQuizResultDTO dto = new TblQuizResultDTO(resultID, subject, dueDate, email, correct_answer, 0);
                    
                    if(result == null) {
                        result = new ArrayList<>();
                    }
                    
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
