package neu.lab;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GetMessage {
    static String projectPath = "sofa-registry-5.4.2";
    static String targetPath = "/Users/yzsjy/Desktop/Set/Set2/" + projectPath + ".txt";
    static List<String> msgs = new ArrayList<>();
//static String targetPath = "/Users/yzsjy/Desktop/azure.txt";
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/yzsjy/Desktop/DataSet/classify2/" + projectPath + "/");
        int num = 0;
        if (file.exists()) {

            if (file.listFiles().length > 0) {
                for (File innerDir : file.listFiles()) {
                    if (innerDir.getPath().equals("/Users/yzsjy/Desktop/DataSet/classify2/" + projectPath + "/.DS_Store"))
                        continue;
                    System.out.println(innerDir.getPath());
                    check(innerDir);

                    num++;
                }
            }
        }
        PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter(new File(targetPath), true)));
        for (String msg : msgs) {
            printer.println(msg);
        }
        printer.close();
        System.out.println(num);
    }

    public static void check(File file) throws IOException {
        String dependencyInfo = null;
        FileInputStream inputStream = new FileInputStream(file);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        int i = 1;

        List<String> messages = new ArrayList<>();
        String temp = null;
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            if (i == 3) {
                dependencyInfo = str;
            }
            if (str.contains("AssertionError")) {
                if (str.contains("AssertionError:..."))
                    continue;
//                messages.add(bufferedReader.readLine());
                messages.add(temp);
            }
            temp = str;
            i++;
        }

        //close
        inputStream.close();
        bufferedReader.close();
//        Set<String> tempSet = new HashSet<>();
//        PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter(new File(targetPath), true)));
        for (String message : messages) {
            String entryClass = getClass(message);
            String method = getMethod(message);
            String msg = dependencyInfo + " " + entryClass + " " + method;
//            String msg = dependencyInfo + " " + getClassAndMethod(message);
//            String msg = dependencyInfo + " " + getClassAndMethod1(message);
            if (!msgs.contains(msg)) {
                msgs.add(msg);
            }
//            printer.println(dependencyInfo + " " + entryClass + " " + method);
//            msgs.add(dependencyInfo + " " + getClassAndMethod(message));
        }
//        for (String msg : tempSet) {
//            printer.println(msg);
//        }
//        printer.close();


    }

    public static String getClass(String info) {
        String txt_one = info.split(" ")[1];
        String txt_two = txt_one.split("\\(")[1];
        String entryClass = txt_two.split("\\)")[0];
//        String entryClass = txt_one.substring(0, txt_one.lastIndexOf(".") - 1);
        return entryClass;
    }

    public static String getMethod(String info) {
        String txt_one = info.split(" ")[1];
        String method = txt_one.split("\\(")[0];
//        String method = txt_one.substring(txt_one.lastIndexOf(".") + 1);
        return method;
    }

    public static String getClassAndMethod(String info) {
        String txt_one = info.split("at ")[1];
        String txt_two = txt_one.split("\\(")[0];
        String entryClass = txt_two.substring(0, txt_two.lastIndexOf(".") - 1);
        String method = txt_two.substring(txt_two.lastIndexOf(".") + 1);
        return entryClass + " " + method;
    }

    public static String getClassAndMethod1(String info) {
        String txt_two = info.split(" ")[1];
        String entryClass = txt_two.substring(0, txt_two.lastIndexOf("."));
        String method = txt_two.substring(txt_two.lastIndexOf(".") + 1);
        return entryClass + " " + method;

    }

    public static String getProjectPath(String info) {
        String pomPath = info.split("@/home/wwww/sensor/unzip/")[1];
        String path = pomPath.split("/pom.xml")[0];
        return "/Volumes/MacProData/unzip/" + path;
    }

    public static String getProjectName(String info) {
        String pomPath = info.split("/")[5];
        return pomPath;
    }

    public static String getDependency(String info) {
        String groupId = info.split("'")[1];
        String artifactId = info.split("'")[3];
        String version = info.split("'")[5];
        return groupId + " " + artifactId + " " + version;

    }
}
