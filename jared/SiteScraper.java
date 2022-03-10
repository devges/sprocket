package com.jared;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class SiteScraper {

    public List<Item> scrapeSite(JSONObject inputs) {

        WebClient web = new WebClient();
        web.setJavaScriptEnabled(false);
        web.setCssEnabled(false);

        HtmlPage page = URLmethods.getPage(web, inputs.get("url").toString());

        assert page != null;
        List<HtmlElement> elements = page.getElementsByIdAndOrName( inputs.get("rootIdOrName").toString() );

        List<Item> products = new ArrayList<>();
        Item item;
        boolean urlSet;
        boolean titleSet;

        for (HtmlElement elem : elements) {
            item = new Item();

            urlSet = false;
            titleSet = false;

            for (HtmlElement childElem : elem.getHtmlElementDescendants()){
//                    System.out.println(childElem.toString());
//                    System.out.println("___ " + childElem.getAttribute("class"));
//                    System.out.println("--- " + childElem.asText() + " ---" );
//                    System.out.println("+++ " + childElem.toString() + " +++" );
//                    childElem.getAttribute("class").containsAll(classes);

                if (hasClassMatch(childElem, inputs, "price")) {
                    item.setAttribute("price", childElem.asText());
                }

                if (hasClassMatch(childElem, inputs, "author")) {
                    item.setAttribute("author", childElem.asText());
                }

                if (childElem.getAttribute("class").equals("a-link-normal")){
                    if (childElem.asText().length() != 0){
                        if (!titleSet) {
                            item.setAttribute("title", childElem.asText());
                            titleSet = true;
                        } else {
                            item.setAttribute("rating", childElem.asText());

                        }
                    }
                }

                if (hasClassMatch(childElem, inputs, "multiple")) {
                    if ((childElem.getAttribute("href").length()  != 0) && (!urlSet)){
                        urlSet = true;
                        String urlNew = childElem.getAttribute("website") + childElem.getAttribute("href");
                        item.setAttribute("url", urlNew);
                    }
                }

            }
            products.add(item);
        }


        return products;
    }

    public static boolean hasMatch(List<String> elementClasses, List<String> searchClasses) {
        for(String searchClass : searchClasses) {
            boolean found = false;
            for(String elementClass : elementClasses) {
                if(elementClass.equals(searchClass)){
                    found = true;
                    break;
                }
            }
            if(!found) {
                return false;
            }

        }
        return true;
    }

    public static boolean hasClassMatch(HtmlElement element, JSONObject inputs, String type){
        return hasMatch(
                List.of(element.getAttribute("class").split(" ")),
                Arrays.asList(inputs.get(type).toString().split(" ")));

    }




}
