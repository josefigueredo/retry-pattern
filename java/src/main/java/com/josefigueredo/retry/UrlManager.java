package com.josefigueredo.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class UrlManager {

    private static Logger LOG = LoggerFactory.getLogger(UrlManager.class);

    public static void getContent(String urlText) throws IOException{
        URL url = new URL(urlText);
        URLConnection conn = url.openConnection();

        // open the stream and put it into BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String inputLine;
        while ((inputLine = br.readLine()) != null) {
            System.out.println(inputLine);
        }
        br.close();

        LOG.info("Done");
    }

}
