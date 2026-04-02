import java.util.ArrayList;
import java.util.List;

public class FCFS implements Processable{


    List<Job> jobs;

    public FCFS(List<Job> jobs) {
        this.jobs = jobs;
    }

    public void run(){

        List<Job> queue = new ArrayList<>();

        Job ongoinJob = null;
        int ongoingJobEstimatedCompletionTime = 0;
        int currentTime = 0;
        while(true) {
            enqueueArrivedJobs(queue, jobs, currentTime);
            if(ongoingJobEstimatedCompletionTime == currentTime) {
                if(ongoinJob != null){
                    calculateTurnAroundTimeAndWaitingTime(ongoinJob, ongoingJobEstimatedCompletionTime);
                    ongoinJob = null;
                }
                if(!queue.isEmpty()) {
                    ongoinJob = queue.removeFirst();
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


    private void enqueueArrivedJobs(List<Job> queue, List<Job> jobs, int currentTime) {
        for (int i = 0; i < jobs.size(); i++) {
            Job currentJob = jobs.get(i);
            if(currentJob.arrivalTime == currentTime) {
                queue.add(jobs.remove(i));
                i--;
            }

        }
    }

    private static void calculateTurnAroundTimeAndWaitingTime(Job job, int ongoingJobEstimatedCompletionTime) {
        job.completionTime = ongoingJobEstimatedCompletionTime;
        job.turnaroundTime = ongoingJobEstimatedCompletionTime - job.arrivalTime;
        job.waitingTime = job.turnaroundTime - job.burstTime;

        System.out.println("Job: " +job.name + " \n"
                + "Completion time: "+job.completionTime + " \n"
                + "Turn Arund Time: "+job.turnaroundTime +" \n"
                + "Waiting Time: "+ job.waitingTime + "\n");
    }
}
