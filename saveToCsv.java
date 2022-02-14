package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class saveToCsv {

    public static void SaveCsv (ResultSet rs, String fileLocation, String fileName, boolean isAppend) throws SQLException {

        ResultSetMetaData rsmeta = rs.getMetaData();
        File outfile = new File(fileLocation + fileName);
        try (
                FileWriter fos = new FileWriter(new File(fileName), isAppend)) {
            // export column names
            for (int ci = 1; ci <= rsmeta.getColumnCount(); ci++) {
                if (ci > 1)
                    fos.write(",");
                fos.write(rsmeta.getColumnLabel(ci));
            }
            fos.write("\n");
            while (rs.next()) {
                for (int ci = 1; ci <= rsmeta.getColumnCount(); ci++) {
                    if (ci > 1)
                        fos.write(",");
                    fos.write(coalesce(rs.getObject(ci), ""));
                }
                fos.write("\n");
            }
        fos.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("CSV file written to " + outfile.getAbsolutePath());
    }


    private static String coalesce(Object o, String nullValue)
    {
        if(o==null)
            return nullValue;
        return o.toString();
    }

}
