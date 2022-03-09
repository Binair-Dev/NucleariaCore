package su.binair.andasia.utils.uuid;

import java.net.*;
import com.google.common.base.*;
import java.util.*;
import java.io.*;
import net.minecraft.util.com.google.gson.*;

public class Fetcher
{
    public String fetchUUID(final String username, final Boolean onlinemode) {
        if (onlinemode) {
            try {
                final URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
                final InputStream stream = url.openStream();
                final InputStreamReader inr = new InputStreamReader(stream);
                final BufferedReader reader = new BufferedReader(inr);
                final StringBuilder sb = new StringBuilder();
                String s;
                while ((s = reader.readLine()) != null) {
                    sb.append(s);
                }
                final String result = sb.toString();
                final JsonElement element = new JsonParser().parse(result);
                final JsonObject obj = element.getAsJsonObject();
                String uuid = obj.get("id").toString();
                uuid = uuid.substring(1);
                uuid = uuid.substring(0, uuid.length() - 1);
                return uuid;
            }
            catch (Exception ex) {
                return null;
            }
        }
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes(Charsets.UTF_8)).toString();
    }
    
    public boolean fastLoginFetcher(final String name, final String uuid) {
        return this.fetchUUID(name, true).equals(uuid) || this.fetchUUID(name, false).equals(uuid);
    }
}
