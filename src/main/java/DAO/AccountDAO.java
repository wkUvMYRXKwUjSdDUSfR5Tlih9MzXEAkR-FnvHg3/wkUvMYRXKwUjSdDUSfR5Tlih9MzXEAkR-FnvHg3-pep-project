package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;

public class AccountDAO {

    public Account getAccountByUsername(String username) {
        Connection conn = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM account WHERE username LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Account account = new Account();
                account.setAccount_id(rs.getInt("account_id"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                return account;
            } else {
                return null;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        //return null;
    }

    public Account insertAccount(Account account) {
        Connection conn = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?) ";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            int rows = ps.executeUpdate();
            System.out.println("rows effected " + rows);

            try {
                ResultSet gk = ps.getGeneratedKeys();
                account.setAccount_id(gk.getInt(1));
            } catch(Exception e) {
                System.out.println("gk " + e.getMessage());
            }
            
            
            System.out.println("insertAccount account " + account);
            return account;
        } catch (SQLException e) {
            System.out.println("insertAccount account " + account);
            System.out.println(e.getMessage());
        }
        return null;
    }
    
}
