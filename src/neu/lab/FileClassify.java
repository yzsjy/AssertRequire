package neu.lab;

import java.io.*;

public class FileClassify {

    static String targetPath = "/Users/yzsjy/Desktop/DataSet/classify2/";
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/yzsjy/Desktop/newInfo/");
        int num = 0;
        if (file.exists()) {

            if (file.listFiles().length > 0) {
                for (File innerDir : file.listFiles()) {
                    if (innerDir.getPath().equals("/Users/yzsjy/Desktop/newInfo/.DS_Store"))
                        continue;
                    System.out.println(innerDir.getPath());
                    check(innerDir);
                    num++;
                }
            }
        }
        System.out.println(num);
    }

    public static void check(File file) throws IOException {
        String projectPath = null;
        String projectName = null;
        String dependencyInfo;
        FileInputStream inputStream = new FileInputStream(file);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        int i = 1;
        StringBuffer stringBuffer = new StringBuffer();

        String str;
        while ((str = bufferedReader.readLine()) != null) {
            stringBuffer.append(str + "\n");
            if (i == 1) {
                projectName = getProjectName(str);
                projectPath = getProjectPath(str);
            }
            if (i == 2) {
                dependencyInfo = getDependency(str);
                stringBuffer.append(projectPath + " " + dependencyInfo + "\n");
            }
            i++;
        }

        //close
        inputStream.close();
        bufferedReader.close();
        String fileName = targetPath + projectName;

        File file1 = new File(fileName);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        FileWriter fileWriter = new FileWriter(fileName + "/" + file.getName());
        fileWriter.write(stringBuffer.toString());
        fileWriter.close();


    }

    public static String getProjectPath(String info) {
        String pomPath = info.split("@/home/wwww/Wxq/unzip/")[1];
        String path = pomPath.split("/pom.xml")[0];
        return "/Volumes/MacProData/SensorData/unzip/" + path;
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
