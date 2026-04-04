import java.util.List;

public class SchedulingConfig {
    private final int quantum;
    private final List<Job> jobs;

    public SchedulingConfig(int quantum, List<Job> jobs) {
        this.quantum = quantum;
        this.jobs = jobs;
    }

    public int getQuantum() {
        return quantum;
    }

    public List<Job> getJobs() {
        return jobs;
    }
}
