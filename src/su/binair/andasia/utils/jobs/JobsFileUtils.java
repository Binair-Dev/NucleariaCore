package su.binair.andasia.utils.jobs;

import java.io.*;

public class JobsFileUtils
{
    public static void createFile(final File file) throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdir();
            file.createNewFile();
        }
    }
    
    public static void save(final File file, final String text) {
        try {
            createFile(file);
            final FileWriter fw = new FileWriter(file);
            fw.write(text);
            fw.flush();
            fw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String loadContent(final File file) {
        if (file.exists()) {
            try {
                final BufferedReader reader = new BufferedReader(new FileReader(file));
                final StringBuilder text = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    text.append(line);
                }
                reader.close();
                return text.toString();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
