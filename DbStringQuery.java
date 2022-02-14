package com.company;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

public class DbStringQuery {

    public static ResultSet executeQuery(Connection conn, String query) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(query);
        return ps.executeQuery();
    }
}
