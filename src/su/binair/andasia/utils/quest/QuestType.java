package su.binair.andasia.utils.quest;

public enum QuestType
{
    BREAK("BREAK"), 
    NEAR("NEAR"), 
    PLACE("PLACE"), 
    PICKUP("PICKUP"), 
    KILL("KILL"), 
    DROP("DROP"), 
    INTERACT("INTERACT"),
    CRAFT("CRAFT");
    
    private String name;
    
    private QuestType(final String name) {
        this.name = name;
    }
    
    public String getType() {
        return this.name;
    }
}
