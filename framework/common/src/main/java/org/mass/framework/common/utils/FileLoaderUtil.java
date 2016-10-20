package org.mass.framework.common.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Allen on 2015/9/23.
 */
public class FileLoaderUtil {

    public static String getClassPath(Class<?> clazz) {
        return clazz.getClassLoader().getResource("").getPath();
    }

    public static String getMavenRootPath(Class<?> clazz) {
        String path = getClassPath(clazz);
        int end  = path.indexOf("target");
        return path.substring(0,end);
    }

    public static String getMavenCodePath(Class<?> clazz) {
        String path = getMavenRootPath(clazz);
        File file = findDirectory(new File(path + "src" + File.separator), Arrays.asList(new String[]{"repository","bean","controller"}));
        return file.getParent() + File.separator;
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
            return mavenPath.replaceAll("\\\\",".");
        } else {
            return mavenPath.replaceAll(File.separator,".");
        }


    }
}
