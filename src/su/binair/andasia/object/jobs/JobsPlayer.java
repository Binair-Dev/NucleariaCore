package su.binair.andasia.object.jobs;

import java.util.*;

public class JobsPlayer
{
    private String username;
    private HashMap<String, Jobs> jobs;
    
    public JobsPlayer(final String username) {
        this.username = username;
        this.jobs = new HashMap<String, Jobs>();
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public Jobs getJobByName(final String name) {
        if (this.jobs.containsKey(name)) {
            return this.jobs.get(name);
        }
        return null;
    }
    
    public void addNewJob(final String name, final int logoId) {
        if (!this.jobs.containsKey(name)) {
            this.jobs.put(name, new Jobs(name, logoId));
        }
    }
    
    public HashMap<String, Jobs> getJobs() {
        return this.jobs;
    }
}
