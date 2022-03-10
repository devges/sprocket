package com.jared;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

public class URLmethods {
    private URLmethods() {
        // no constructor
    }

    /**
     * gets webClient-page for a given url
     * @param webClient
     * @param url
     * @return page
     */
    public static HtmlPage getPage(WebClient webClient, String url) {
        try {
            webClient.setCssEnabled(false);
            return webClient.getPage(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }




}
