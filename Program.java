package com.company;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class Program {
    private String enterpriseCode       = "";

    public Program() throws SQLException {
        new Program(this.enterpriseCode);
    }

    public Program(String enterpriseCode) throws SQLException {
        this.enterpriseCode = enterpriseCode;

        try (InputStream input = new FileInputStream("src/login.properties")) {

            Properties prop = new Properties();
            prop.load(input);

            String url      = prop.getProperty("db.url");
            String userName = prop.getProperty("db.user");
            String pw       = prop.getProperty("db.password");
            String fileName;
            String fileLocation = "";

            if (Objects.equals(enterpriseCode, "")) {
                fileName = "order_report.csv";
            } else {
                fileName = enterpriseCode + "_order_report.csv";
            }

            Connection cn = Connector.getConnection(url, userName, pw);

            ResultSet queryResults1 = DbStringQuery.executeQuery(cn, getQuery1(enterpriseCode));
            ResultSet queryResults2 = DbStringQuery.executeQuery(cn, getQuery2(enterpriseCode));

            saveToCsv.SaveCsv(queryResults1, fileLocation, fileName, false);
            saveToCsv.SaveCsv(queryResults2, fileLocation, fileName, true);


        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


    public static void main(String[] args) throws SQLException {
        Program myDB = new Program();
    }



    private String getQuery1(String enterpriseCode) {
        String query = "SELECT ORDER_NO, ORDER_DATE " +
                "FROM OMDB.YFS_ORDER_HEADER";
        if (enterpriseCode != "") {
            query +=  " WHERE (ENTERPRISE_KEY='" + this.enterpriseCode + "')";
        }
        return query;
    }

    private String getQuery2(String enterpriseCode) {
        String query = "SELECT " + "" +
                "COUNT(*) AS num_order_lines, " + "" +
                "SUM(ORDERED_QTY) AS total_order_quantity " +
                "FROM OMDB.YFS_ORDER_LINE ";

        if (enterpriseCode != "") {
            query += " WHERE ORDER_HEADER_KEY IN " +
                    "(SELECT ORDER_HEADER_KEY  FROM OMDB.YFS_ORDER_HEADER " +
                    "WHERE (ENTERPRISE_KEY='" + this.enterpriseCode +
                    "')";
        }
            return query;


    }

}
