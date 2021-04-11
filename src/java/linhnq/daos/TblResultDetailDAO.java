/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnq.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.NamingException;
import linhnq.utils.DBHelper;

/**
 *
 * @author quocl
 */
public class TblResultDetailDAO implements Serializable {
    
    public void storedQuestion(String resultID, String questionID, boolean isCorrect) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement preStm = null;
        
        try {
            con = DBHelper.makeConnection();
            if(con != null) {
                String sql = "INSERT INTO tblResultDetail(resultID, question_id, isCorrect) "
                        + "VALUES(?, ?, ?)";
                preStm = con.prepareStatement(sql);
                preStm.setString(1, resultID);
                preStm.setString(2, questionID);
                preStm.setBoolean(3, isCorrect);
                preStm.executeUpdate();
            }
        } finally {
            if (preStm != null) {
                preStm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
