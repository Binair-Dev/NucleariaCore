package su.binair.andasia.object.jobs;

public class JobsAction
{
    private String type;
    private int id;
    private double experienceAmount;
    private String name;
    private String jobs;
    private String description;
    
    public JobsAction(final String jobs, final String type, final int id, final double experienceAmount) {
        this.type = type;
        this.id = id;
        this.experienceAmount = experienceAmount;
        this.jobs = jobs;
        this.setName(jobs + "-" + type + "-" + id);
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public String getJobId() {
        return this.jobs;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getType() {
        return this.type;
    }
    
    public int getId() {
        return this.id;
    }
    
    public double getExperienceAmount() {
        return this.experienceAmount;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return this.description;
    }
}
