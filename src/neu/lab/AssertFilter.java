package neu.lab;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class AssertFilter {
    public static void main(String[] args) throws IOException {
        String targetPath = "/Users/yzsjy/Desktop/newInfo/";
        File file = new File("/Users/yzsjy/Desktop/autoChangeDependencyVersionLog/");
        int num = 0;
        if (file.exists()) {

            if (file.listFiles().length > 0) {
                for (File innerDir : file.listFiles()) {
                    if (!innerDir.exists() && innerDir.isDirectory()) {
                        continue;
                    }
                    File[] innerList = innerDir.listFiles();
                    if (innerList != null) {
                        for (File logFile : innerList) {
                            System.out.println(logFile.getPath());
                            if (check(logFile)) {
                                System.out.println("target");
                                if (!new File(targetPath + logFile.getParentFile().getName() + logFile.getName()).exists())
                                Files.copy(logFile.toPath(), new File(targetPath + logFile.getParentFile().getName() + logFile.getName()).toPath());
                            }
                            num++;
                        }
                    }
                }
            }
        }
        System.out.println(num);
    }

    public static boolean check(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String str;
        while ((str = bufferedReader.readLine()) != null) {
            if (str.contains("Assert")) {
                return true;
            }
        }

        //close
        inputStream.close();
        bufferedReader.close();

        return false;
    }
}

