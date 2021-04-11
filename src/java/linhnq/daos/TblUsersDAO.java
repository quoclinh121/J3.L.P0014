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
import javax.naming.NamingException;
import linhnq.dtos.TblUsersDTO;
import linhnq.utils.DBHelper;

/**
 *
 * @author quocl
 */
public class TblUsersDAO implements Serializable {

    public TblUsersDTO checkLogin(String email, String password) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        TblUsersDTO dto = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT name, role "
                        + "FROM tblUsers "
                        + "WHERE email = ? AND password =? AND status = 'New'";
                preStm = con.prepareStatement(sql);
                preStm.setString(1, email);
                preStm.setString(2, password);
                rs = preStm.executeQuery();
                if (rs.next()) {
                    String name = rs.getNString("name");
                    String role = rs.getString("role");
                    dto = new TblUsersDTO(email, name, password, role, "New");
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

    public boolean isExistedEmail(String email) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        boolean isExisted = false;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT email "
                        + "FROM tblUsers "
                        + "WHERE email = ?";
                preStm = con.prepareStatement(sql);
                preStm.setString(1, email);
                rs = preStm.executeQuery();
                if (rs.next()) {
                    isExisted = true;
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
        return isExisted;
    }

    public void createNewAccount(TblUsersDTO dto) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement preStm = null;

        try {
            con = DBHelper.makeConnection();
            if(con != null) {
                String sql = "INSERT INTO tblUsers(email, password, name, role, status) "
                        + "VALUES(?, ?, ?, ?, ?)";
                preStm = con.prepareStatement(sql);
                preStm.setString(1, dto.getEmail());
                preStm.setString(2, dto.getPassword());
                preStm.setNString(3, dto.getName());
                preStm.setString(4, dto.getRole());
                preStm.setString(5, dto.getStatus());
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
