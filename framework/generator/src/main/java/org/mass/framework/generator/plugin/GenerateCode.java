package org.mass.framework.generator.plugin;

import org.mass.framework.generator.config.GenerateResource;
import org.mass.framework.generator.config.GeneratorConfigParser;
import org.mass.framework.generator.config.JdbcProperties;
import org.mass.framework.generator.translate.CreateJava;
import org.mass.framework.generator.util.MavenLoaderUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.List;

/**
 * Created by Allen on 2015-11-20.
 */
@Mojo( name = "create")
public class GenerateCode extends AbstractMojo {

    @Parameter( defaultValue = "${basedir}", required = true )
    private File basedir;

    public void execute() throws MojoExecutionException, MojoFailureException {

        GeneratorConfigParser configParser = new GeneratorConfigParser(getLog());
        configParser.config(basedir.getPath());
        JdbcProperties jdbcProperties = configParser.getJdbcProperties();
        List<GenerateResource> generateResources = configParser.getGenerateResources();

        for (GenerateResource generateResource : generateResources) {
            String basePackage = generateResource.getBasePackage();
            MavenLoaderUtil.makeMavenCodeDir(basedir,basePackage);
            CreateJava.create(getLog(),basedir.getAbsolutePath(),MavenLoaderUtil.getMavenCodeDir(basedir, basePackage),generateResource,jdbcProperties);
        }


    }

}

