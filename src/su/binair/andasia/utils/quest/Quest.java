package su.binair.andasia.utils.quest;

public class Quest
{
    private int id;
    private String name;
    private String description;
    private String type;
    private String recompense_name;
    private String recompense;
    private int amount;
    private String entityBlockIdLocation;
    private boolean status;
    private int isSelectable;
    private int avancement;
    
    public int isSelectable() {
        return this.isSelectable;
    }
    
    public void setSelectable(final int isSelectable) {
        this.isSelectable = isSelectable;
    }
    
    public Quest(final int id) {
        this.status = false;
        this.avancement = 0;
        this.id = id;
    }
    
    public boolean isStatus() {
        return this.status;
    }
    
    public void setStatus(final boolean status) {
        this.status = status;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setAvancement(final int avancement) {
        this.avancement = avancement;
    }
    
    public void setAmount(final int amount) {
        this.amount = amount;
    }
    
    public void addAvancement(final int avancement) {
        this.avancement += avancement;
    }
    
    public void setEntityBlockId(final String entityBlockIdLocation) {
        this.entityBlockIdLocation = entityBlockIdLocation;
    }
    
    public int getAmount() {
        return this.amount;
    }
    
    public int getAvancement() {
        return this.avancement;
    }
    
    public String getEntityBlockId() {
        return this.entityBlockIdLocation;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public String getType() {
        return this.type;
    }
    
    public void setType(final String type) {
        this.type = type;
    }
    
    public String getRecompenseName() {
        return this.recompense_name;
    }
    
    public void setRecompenseName(final String recompense_name) {
        this.recompense_name = recompense_name;
    }
    
    public String getRecompense() {
        return this.recompense;
    }
    
    public void setRecompense(final String recompense) {
        this.recompense = recompense;
    }
}
