package su.binair.andasia.utils.bungeemining;

import java.io.*;
import su.binair.andasia.*;

public class BungeeMiningManager
{
    private File minageFolder;
    private File minagequestPlayerFolder;
    private BungeeMiningUtils qu;
    
    public BungeeMiningManager() {
        this.qu = new BungeeMiningUtils();
        final File minageFolder = new File(Main.getInstance().getDataFolder(), "/minage/");
        if (!minageFolder.exists()) {
            minageFolder.mkdir();
        }
        this.minageFolder = minageFolder;
        final File minagequestPlayerFolder = new File(this.getMinageFolder(), "/players/");
        if (!minagequestPlayerFolder.exists()) {
            minagequestPlayerFolder.mkdir();
        }
        this.minagequestPlayerFolder = minagequestPlayerFolder;
    }
    
    public BungeeMiningUtils getBungeeMiningUtils() {
        return this.qu;
    }
    
    public File getMinageFolder() {
        return this.minageFolder;
    }
    
    public File getMinagePlayerReserve() {
        return this.minagequestPlayerFolder;
    }
}
