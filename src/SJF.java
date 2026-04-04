import java.util.Comparator;
import java.util.List;

public class SJF implements Processable{

    List<Job> jobs;

    public SJF(List<Job> jobs) {
        this.jobs = jobs.stream().sorted(
                Comparator.comparing(j -> j.burstTime)
        ).toList();
    }

    @Override
    public void run() {
        int completed = 0;
        int currentTime = 0;

        while (completed < jobs.size()) {
            Job ongoingJob = null;
            ongoingJob = determinateCurrentJob(ongoingJob, currentTime);

            if (ongoingJob == null) {
                currentTime++;
                continue;
            }

            currentTime = processJobExecution(ongoingJob, currentTime);
            completed++;
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

    private int processJobExecution(Job ongoingJob, int currentTime) {
        while(ongoingJob.remainingTime > 0){
            ongoingJob.clock();
            currentTime++;
        }

        calculateTurnAroundTimeAndWaitingTime(ongoingJob, currentTime);

        return currentTime;
    }

    private boolean isJobReadyToBeProcessed(Job ongoingJob, int i, int currentTime) {
        return jobs.get(i).arrivalTime <= currentTime
                && jobs.get(i).remainingTime > 0
                && (
                ongoingJob == null
                        || jobs.get(i).burstTime < ongoingJob.burstTime
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
