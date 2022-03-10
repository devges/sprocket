package com.jared;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utilities {

    public static void writeToFile(String fileData, String fileName, String folderName) {
        try {
            Files.createDirectories(Paths.get(folderName));
            FileWriter myWriter = new FileWriter(folderName + "\\" + fileName);
            myWriter.write(fileData);
            myWriter.flush();
            myWriter.close();
            System.out.println("Wrote to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(JSONArray obj, String fileName, String folderName) {
        writeToFile(obj.toJSONString(), fileName, folderName);
    }

    public static JSONObject readFromJson(String fileName){
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(fileName))
        {
            //Read JSON file
            JSONObject inputList = (JSONObject) jsonParser.parse(reader);

//            System.out.println("Reading: \n" + inputList);
            return inputList;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

}
