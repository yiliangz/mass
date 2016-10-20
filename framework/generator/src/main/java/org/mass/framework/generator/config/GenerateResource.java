package org.mass.framework.generator.config;

import java.util.Set;

/**
 * Created by Allen on 2015/11/23.
 */
public class GenerateResource {

    private Set<String> templates;
    private String basePackage;
    private String table;
    private String comment;

    public Set<String> getTemplates() {
        return templates;
    }

    public void setTemplates(Set<String> templates) {
        this.templates = templates;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
