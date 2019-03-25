package com.example.youtubermanager.JDBC;
import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SuppressLint("NewApi")
public class JDBCModel {
    static String ip = "107.150.55.2:20304"; //server IP
    static String classs = "net.sourceforge.jtds.jdbc.Driver"; //khóa này không thay đổi
    static String db = "AndroidProject";//tên database
    static String un = "sa"; //user đăng nhập vào SQL server mặc định là sa
    static String password ="viennv896";
    public Connection getConnectionOf() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {

            Class.forName(classs);
            ConnURL = "jdbc:jtds:sqlserver://" + ip +"/"+ db + ";user=" + un+ ";password=" + password + ";";
            //ConnURL="jdbc:mysql://{hostname}:{port}"
            conn = DriverManager.getConnection(ConnURL);
            ///Connection conn2 = DriverManager.getConnection(url, username, password);

        } catch (SQLException se) {
            Log.e("ERROR1","Không thể tải lớp Driver! "+ se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERROR2","Xuất hiện vấn đề truy cập trong khi tải! "+ e.getMessage());
        } catch (Exception e) {
            Log.e("ERROR3", "Không thể khởi tạo Driver! "+e.getMessage());
        }
        return conn;
    }
}