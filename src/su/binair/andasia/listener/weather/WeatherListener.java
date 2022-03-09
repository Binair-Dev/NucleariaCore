package su.binair.andasia.listener.weather;

import org.bukkit.event.weather.*;
import org.bukkit.event.*;

public class WeatherListener implements Listener
{
    @EventHandler
    public void onWeatherChange(final WeatherChangeEvent e) {
        e.getWorld().setStorm(false);
        e.getWorld().setThundering(false);
    }
}
