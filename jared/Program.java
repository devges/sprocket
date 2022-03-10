package com.jared;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

import static com.jared.Utilities.readFromJson;


public class Program {

    public static void main(String[] args) {
        JSONObject inputs = readFromJson(".\\src\\com\\jared\\AmazonInput.json");

        SiteScraper siteScraper = new SiteScraper();
        List<Item> scrapedData = siteScraper.scrapeSite(inputs);

        JSONArray jsonArray = new JSONArray();
        for (Item item : scrapedData) {
            jsonArray.add( item.HashMaptoJSON());
        }

        Utilities.writeToFile(
                jsonArray.toJSONString(),
                "output.json",
                ".\\data");

    }

}
