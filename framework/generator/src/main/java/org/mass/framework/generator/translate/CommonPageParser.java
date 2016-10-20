package org.mass.framework.generator.translate;

import org.mass.framework.generator.config.TemplateResource;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.util.Properties;

public class CommonPageParser {

    private static VelocityEngine ve;// = VelocityEngineUtil.getVelocityEngine();

    private static Properties properties;

    private final static String CONTENT_ENCODING = "UTF-8";

    private static boolean isReplace = true;  //是否可以替换文件 true =可以替换，false =不可以替换

    public static void main(String[] args) {

    }

    static {
        try {
            Properties properties = new Properties();

            properties.setProperty(Velocity.RESOURCE_LOADER,"jar");
            properties.setProperty("jar.resource.loader.class","org.apache.velocity.runtime.resource.loader.JarResourceLoader");
            properties.setProperty("jar.resource.loader.path",TemplateResource.getJarUrl());

            VelocityEngine velocityEngine = new VelocityEngine();
            velocityEngine.init(properties);
            ve = velocityEngine;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void WriterPage(org.apache.maven.plugin.logging.Log log, VelocityContext context, String templateName, String fileDirPath, String targetFile) {
        try {
            File file = new File(fileDirPath + targetFile);
            if (!file.exists()) {
                new File(file.getParent()).mkdirs();
            } else {
                if (isReplace) {
                    log.info("替换文件：" + file.getAbsolutePath());
                } else {
                    log.info("生成文件：" + file.getAbsolutePath());
                }
            }

            Template template = ve.getTemplate("template" + "/" + templateName, CONTENT_ENCODING);
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, CONTENT_ENCODING));
            template.merge(context, writer);
            writer.flush();
            writer.close();
            fos.close();
        } catch (Exception e) {
			log.error(e);
        }
    }

}
