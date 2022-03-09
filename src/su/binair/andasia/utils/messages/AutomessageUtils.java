package su.binair.andasia.utils.messages;

import su.binair.andasia.*;
import su.binair.utils.*;
import java.util.*;

public class AutomessageUtils
{
    public static List<String> automessage;
    
    public static void sendRandomMessage() {
        AutomessageUtils.automessage = (List<String>)Main.getInstance().getConfig("automessage").getConfig().getList("messages");
        final String[] list = { getRandomMessage() };
        ToolBox.broadcast(list);
    }
    
    public static String getRandomMessage() {
        final Random random = new Random();
        final int choice = random.nextInt(AutomessageUtils.automessage.size());
        final String chosen = "     §2§l§a" + AutomessageUtils.automessage.get(choice);
        return chosen;
    }
    
    static {
        AutomessageUtils.automessage = new ArrayList<String>();
    }
}
