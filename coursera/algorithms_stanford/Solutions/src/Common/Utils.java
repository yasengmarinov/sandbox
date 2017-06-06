package common;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by b06514a on 5/12/2017.
 */
public class Utils {

    public static List<String> parseFile(Class clazz, String filename) {
        Path inputFile = null;
        try {
            inputFile = Paths.get(clazz.getResource(filename).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        List<String> input = null;
        try {
            input = Files.readAllLines(inputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < input.size(); i++) {
            input.set(i, input.get(i).toString());
        }
        return input;
    }

    public static int bitUp(int currentNumber, int position) {
        int tmp = currentNumber;
        tmp |= (1 << position);
        return tmp;
    }

    public static int bitDown(int s, int position) {
        int tmp = s;
        tmp &= ~(1 << position);
        return tmp;
    }
}
