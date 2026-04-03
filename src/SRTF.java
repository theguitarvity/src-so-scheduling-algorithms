import java.util.List;

/**
 * Preemptive Shortest job first
 */
public class SRTF implements Processable {

    List<Job> jobs;

    public SRTF(List<Job> jobs) {
        this.jobs = jobs.stream().sorted().toList();
    }

    @Override
    public void run() {
        int completed = 0;
        int currentTime = 0;

        while (completed < jobs.size()) {
            Job ongoingJob = null;

            ongoingJob = determinateCurrentJob(ongoingJob, currentTime);

            currentTime++;

            if (ongoingJob != null) {
                boolean finished = processJobExecution(ongoingJob, currentTime);
                if (finished) {
                    completed++;
                }
            }
        }

        finalizeExecution();
    }

    private Job determinateCurrentJob(Job ongoingJob, int currentTime) {
        for (int i = 0; i < jobs.size(); i++) {
            if (!isJobReadyToBeProcessed(ongoingJob, i, currentTime)) {
                continue;
            }
            ongoingJob = jobs.get(i);
        }

        return ongoingJob;
    }

    private void finalizeExecution() {
        double totalWaitingTime = 0, totalTurnAroundtime = 0;

        for (Job job : jobs) {
            totalWaitingTime += job.waitingTime;
            totalTurnAroundtime += job.turnaroundTime;
        }

        double avgWaitingTime = totalWaitingTime / jobs.size();
        double avgTurnAroundTime = totalTurnAroundtime / jobs.size();

        System.out.println("Avg WT: " + avgWaitingTime);
        System.out.println("Avg TAT: " + avgTurnAroundTime);
    }

    private boolean processJobExecution(Job ongoingJob, int currentTime) {
        ongoingJob.clock();

        if (ongoingJob.remainingTime == 0) {
            calculateTurnAroundTimeAndWaitingTime(ongoingJob, currentTime);
            return true;
        }

        return false;
    }

    private boolean isJobReadyToBeProcessed(Job ongoingJob, int i, int currentTime) {
        return jobs.get(i).arrivalTime <= currentTime
                && jobs.get(i).remainingTime > 0
                && (
                ongoingJob == null
                        || jobs.get(i).remainingTime < ongoingJob.remainingTime
        );
    }

    private void calculateTurnAroundTimeAndWaitingTime(Job job, int completionTime) {
        job.completionTime = completionTime;
        job.turnaroundTime = completionTime - job.arrivalTime;
        job.waitingTime = job.turnaroundTime - job.burstTime;

        System.out.println("Job: " + job.name + " \n"
                + "Completion time: " + job.completionTime + " \n"
                + "Turn Around Time: " + job.turnaroundTime + " \n"
                + "Waiting Time: " + job.waitingTime + "\n");
    }
}