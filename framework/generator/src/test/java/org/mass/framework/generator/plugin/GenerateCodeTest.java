package org.mass.framework.generator.plugin;

import org.mass.framework.generator.plugin.GenerateCode;
import org.mass.framework.generator.plugin.HelloMojo;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.junit.Test;

import java.io.File;

/**
 * Created by Allen on 2015-11-23 0023.
 */
public class GenerateCodeTest extends AbstractMojoTestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testMojoGoal() throws Exception {
//        File pom = new File( getBasedir(),"src/test/resources/plugin-test-config.xml");
//        GenerateCode mojo = (GenerateCode) lookupMojo ("create", pom );
//        mojo.execute();
    }

}
