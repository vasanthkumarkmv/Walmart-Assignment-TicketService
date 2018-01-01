package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class FileUtil {

    public static String readFile(String fileName) {
        StringBuilder result = new StringBuilder("");
        ClassLoader classLoader = FileUtil.class.getClassLoader();
        Scanner scanner = new Scanner(classLoader.getResourceAsStream(fileName));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            result.append(line).append("\n");
        }
        scanner.close();

        return result.toString();

    }
}
