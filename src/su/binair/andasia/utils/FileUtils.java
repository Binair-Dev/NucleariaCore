package su.binair.andasia.utils;

import org.bukkit.craftbukkit.libs.com.google.gson.*;
import java.io.*;

public class FileUtils
{
    private Gson gson;
    
    public FileUtils() {
        this.gson = this.createGsonInstance();
    }
    
    private Gson createGsonInstance() {
        return new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();
    }
    
    public String serializeObject(final Object player) {
        return this.gson.toJson(player);
    }
    
    public Object deserializeObject(final String json, final Class<?> object) {
        return this.gson.fromJson(json, (Class)object);
    }
    
    public void createFile(final File file) throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdir();
            file.createNewFile();
        }
    }
    
    public void save(final File file, final String text) {
        try {
            this.createFile(file);
            final FileWriter fw = new FileWriter(file);
            fw.write(text);
            fw.flush();
            fw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String loadContent(final File file) {
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
