package org.mass.framework.generator.util;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Allen on 2015/9/23.
 */
public class MavenLoaderUtil {

    public static String getClassPath(Class<?> clazz) {
        return clazz.getClassLoader().getResource("").getPath();
    }

    public static File findDirectory(File root, List<String> list) {
        File result = null;
        if (list.contains(root.getName())) {
            result = root;
        } else {
            for (File file : root.listFiles()) {
                if (file.isDirectory()) {
                    result = findDirectory(file,list);
                    if (result != null && list.contains(result.getName())) {
                        break;
                    }
                }
            }
        }
        return result;
    }

    public static String fromMavenPathToPackage(String mavenPath) {
        String index = "main" + File.separator + "java";
        mavenPath = mavenPath.substring(mavenPath.indexOf(index) + index.length() + 1,mavenPath.length()-1);
        System.out.println(mavenPath);

        if (File.separator.equals("\\")) {
            return mavenPath.replace("\\\\", ".");
        } else {
            return mavenPath.replace(File.separator, ".");
        }
    }

    public static String getMavenCodeDir(File basedir,String basePackage) {
        return  basedir.getPath() +
                File.separator + "src" +
                File.separator + "main" +
                File.separator + "java" +
                File.separator + fromPackageToPath(basePackage) + File.separator;
    }

    public static void makeMavenCodeDir(File basedir,String basePackage) {
        String targetPath = getMavenCodeDir(basedir,basePackage);
        File path = new File(targetPath);
        if (!path.exists()) {
            path.mkdirs();
        }
    }

    public static String fromPackageToPath(String packagePath) {
        if (File.separator.equals("\\")) {
            return packagePath.replace(".","\\");
        } else {
            return packagePath.replace(".", File.separator);
        }
    }

}
