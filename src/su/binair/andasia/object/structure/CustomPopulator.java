package su.binair.andasia.object.structure;

public class CustomPopulator
{
    private String name;
    private String schematicName;
    private double rarety;
    
    public CustomPopulator(final String name, final String schematicName, final double rarety) {
        this.name = name;
        this.schematicName = schematicName;
        this.rarety = rarety;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getSchematicName() {
        return this.schematicName;
    }
    
    public double getRarety() {
        return this.rarety;
    }
}
