import java.util.*;

public class FCFS implements Processable{


    List<Job> jobs;
    Queue<Job> queue;
    int currentTime;

    public FCFS(List<Job> jobs) {

        this.jobs = jobs;
        currentTime = 0;

        queue = new PriorityQueue<>();
    }

    public void run(){

        Job ongoinJob = null;
        int ongoingJobEstimatedCompletionTime = 0;

        while(true) {
            enqueueArrivedJobs();
            if(ongoingJobEstimatedCompletionTime == currentTime) {
                if(ongoinJob != null) {
                    calculateTurnAroundTimeAndWaitingTime(ongoinJob, ongoingJobEstimatedCompletionTime);
                    ongoinJob = null;
                }
                if(!queue.isEmpty()) {
                    ongoinJob = queue.remove();
                    ongoingJobEstimatedCompletionTime = ongoinJob.burstTime + currentTime;
                }
            }
            if(ongoinJob == null) {
                ongoingJobEstimatedCompletionTime++;
            }
            currentTime++;
            if(queue.isEmpty() && jobs.isEmpty()) {
                if(ongoinJob != null) {
                    calculateTurnAroundTimeAndWaitingTime(ongoinJob, ongoingJobEstimatedCompletionTime);
                }
                break;
            }
        }
    }


    private void enqueueArrivedJobs() {
        for (int i = 0; i < jobs.size(); i++) {
            Job currentJob = jobs.get(i);
            if(currentJob.arrivalTime == currentTime) {
                this.queue.add(jobs.remove(i));
                i--;
            }

        }
    }

    private void calculateTurnAroundTimeAndWaitingTime(Job job, int ongoingJobEstimatedCompletionTime) {
        job.completionTime = ongoingJobEstimatedCompletionTime;
        job.turnaroundTime = ongoingJobEstimatedCompletionTime - job.arrivalTime;
        job.waitingTime = job.turnaroundTime - job.burstTime;

        System.out.println("Job: " +job.name + " \n"
                + "Completion time: "+job.completionTime + " \n"
                + "Turn Arund Time: "+job.turnaroundTime +" \n"
                + "Waiting Time: "+ job.waitingTime + "\n");
    }
}
