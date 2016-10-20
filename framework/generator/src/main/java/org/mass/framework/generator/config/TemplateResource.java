package org.mass.framework.generator.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Allen on 2015-11-25 0025.
 */
public class TemplateResource {

    public void getResource(String templateFileName) throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/template/" + templateFileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String s="";
        while((s=br.readLine())!=null)
            System.out.println(s);
    }

    public static String getJarUrl() {
        String url = TemplateResource.class.getResource("").toString();
        return url.substring(0,url.indexOf("!"));
    }

}
