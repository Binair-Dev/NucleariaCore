package su.binair.andasia.utils.discord;

public enum DiscordEnum
{
    EVENTS("https://discordapp.com/api/webhooks/715026107568488508/BXg6rrg7Ax9cjEl00ybizHJh1y4CSI1cmwXuhyWpY5SIhlO9cwZZy_OS0bb11yxXvNVt");
    
    private String name;
    
    private DiscordEnum(final String name) {
        this.name = name;
    }
    
    public String getType() {
        return this.name;
    }
}
