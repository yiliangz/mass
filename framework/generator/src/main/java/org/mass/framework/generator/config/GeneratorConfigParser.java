package org.mass.framework.generator.config;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.maven.plugin.logging.Log;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.logging.FileHandler;

/**
 * Created by Allen on 2015-11-20.
 */
public class GeneratorConfigParser {

    private Log log;

    private Document document;

    private List<String> defaultProperties = Arrays.asList(new String[]{"driver","url","username","password"});

    public GeneratorConfigParser() {

    }

    public GeneratorConfigParser(Log log) {
        this.log = log;
    }

    public void config(String basedir) {
        SAXReader reader = new SAXReader();
        String fileName = "codeGenerator.xml";
        StringBuffer path = new StringBuffer(basedir);
        path.append(File.separator).append("src")
                .append(File.separator).append("test")
                .append(File.separator).append("resources")
                .append(File.separator).append(fileName);
        try {
            log.info("parsing resource: '" + path + "'");
            document = reader.read(new File(path.toString()));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public Element getRoot() {
        return document.getRootElement();
    }

    public JdbcProperties getJdbcProperties() {
        Element jdbcConnection = getRoot().element("jdbcConnection");
        List<Element> connAttrs = jdbcConnection.elements();

        JdbcProperties jdbcProperties = new JdbcProperties();

        for (Element attr: connAttrs) {
            String property = attr.getName();
            if (defaultProperties.contains(property)) {
                try {
                    BeanUtils.setProperty(jdbcProperties,property,attr.getData());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

        }
        log.info("jdbcProperties: url:" + jdbcProperties.getUrl());
        return jdbcProperties;
    }

    public List<GenerateResource> getGenerateResources() {
        List<Element> xmlResources = getRoot().element("generateResources").elements();

        List<GenerateResource> resources = new ArrayList<GenerateResource>();
        for (Element xmlResource : xmlResources) {
            GenerateResource resource = new GenerateResource();

            resource.setBasePackage(xmlResource.attributeValue("basePackage"));
            resource.setTable(xmlResource.attributeValue("table"));
            resource.setComment(xmlResource.attributeValue("comment"));

            log.info("table :" + xmlResource.attributeValue("table"));

            List<Element> xmlSections = xmlResource.elements();
            Set<String> templates = new HashSet<String>();
            for (Element xmlSection : xmlSections) {
                templates.add(xmlSection.getText());
            }
            resource.setTemplates(templates);
            resources.add(resource);
        }

        return resources;
    }


}
