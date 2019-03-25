package com.example.youtubermanager;

import com.example.youtubermanager.JDBC.JDBCController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserModel {
    private JDBCController jdbcController = new JDBCController();
    private Connection connection;

    public UserModel() {
        connection = jdbcController.ConnnectionData(); // Tạo kết nối tới database
    }

    public boolean Insert(User objUser) throws SQLException {
        Statement statement = connection.createStatement();// Tạo đối tượng Statement.
        String sql = "insert into AccountTBL([Name],Email,[Password],TypeAccount,TypeLogin,Img) values('" + objUser.getName() + "','"
                + objUser.getEmail() +"','"+ objUser.getPassword()+"',"+ objUser.getTypeAccount()+","+ objUser.getTypeLogin()+",'"
                + objUser.getImg()+"')";

        if (statement.executeUpdate(sql) > 0) { // Dùng lệnh executeUpdate cho các lệnh CRUD
            connection.close();
            return true;
        } else {
            connection.close();
            return false;
        }
    }
    public List<User> getuser(String email, String password) throws SQLException {
        List<User> list = new ArrayList<>();
        User user = new User();
        Statement statement = connection.createStatement();// Tạo đối tượng Statement.
        String sql = "select * from AccountTBL where [email] = '"+email+"' and [Password] ='"+password+"'";
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet. // Mọi kết quả trả về sẽ được lưu trong ResultSet
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            user.setName(rs.getString("[Name]"));
            user.setEmail(rs.getString("Email"));
            user.setPassword(rs.getString("[Password]"));
            user.setTypeAccount(rs.getInt("TypeAccount"));
            user.setTypeLogin(rs.getInt("TypeLogin"));
            user.setImg(rs.getString("Img"));
            list.add(user);// Đọc dữ liệu từ ResultSet
        }
        connection.close();// Đóng kết nối
        return list;
    }
}
