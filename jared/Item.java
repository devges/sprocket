package com.jared;
import org.json.simple.JSONObject;

import java.util.*;

public class Item {
    private HashMap<String, String> dict = new HashMap<String,String>();

    public Item() {
        new Item(new HashMap<>());
    }

    public Item(HashMap<String, String> dict) {
        this.dict = dict;
    }


    public String getAttribute(String atr) {
        if (!this.dict.containsKey(atr)) {
            System.out.println("error. No attribute: " + atr);
            System.out.println(this.dict.toString());
            throw new AssertionError();
        }
        return this.dict.get(atr);
    }

    public void setAttribute(String key, String val) {
//        if (this.dict.containsKey(key)) {
//            System.out.println("Overwriting key's value. Key: " + key + "; Value: " + val);
//        } else {
//            System.out.println("Setting key-value. Key: " + key + "; Value: " + val);
//        }
        this.dict.put(key, val);
    }

    public JSONObject HashMaptoJSON() {
        return new JSONObject(this.dict);
    }

}