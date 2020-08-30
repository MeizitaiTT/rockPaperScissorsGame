import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanghaojie on 2020/8/28
 */
public class FileUtil {
    public static void writeFile(String filename, String content) {
        writeFile(filename, content, StandardCharsets.UTF_8);
    }
    public static void writeFile(String filename, String content, Charset charset) {
        File file = new File(filename);
        try(FileWriter fw = new FileWriter(file, charset);) {
            fw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendFile(String filename, String content) {
        appendFile(filename, content, StandardCharsets.UTF_8);
    }
    public static void appendFile(String filename, String content, Charset charset) {
        File file = new File(filename);
        try(FileWriter fw = new FileWriter(file, charset, true);) {
            fw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readLines(String filename) {
        File file = new File(filename);
        ArrayList<String> result = new ArrayList<>();
        if (!file.exists()) {
            return result;
        }
        try(FileReader fr = new FileReader(file);) {

            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while(line != null){
                result.add(line);
                line = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
