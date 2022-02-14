package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {


    public static void main(String[] args) {
    }

    public static Connection getConnection(String url, String user, String pw) throws SQLException {
        System.out.println("get connection");

        Properties connectionProps = new Properties();
        connectionProps.put("user", user);
        connectionProps.put("password", pw);

        return DriverManager.getConnection(url, connectionProps);
    }


}