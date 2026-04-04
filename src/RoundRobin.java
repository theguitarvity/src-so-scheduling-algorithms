import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RoundRobin implements Processable{
    private final List<Job> jobs;
    private final int quantum;
    Queue<Job> readyQueue;

    public RoundRobin(List<Job> jobs, int quantum) {
        this.jobs = jobs.stream().sorted().toList();
        this.quantum = quantum;
        this.readyQueue = new LinkedList<>();
    }


    @Override
    public void run() {


        int currentTime = 0;
        int completed = 0;
        int nextJobIndex = 0;

        while(completed < jobs.size()) {
            nextJobIndex = enqueueArrivedJobs(nextJobIndex, currentTime);

            if(readyQueue.isEmpty()) {
                currentTime++;
                continue;
            }

            Job ongoingJob = readyQueue.poll();
            int executionTime = Math.min(quantum, ongoingJob.remainingTime);

            for (int i = 0; i < executionTime; i++) {
                ongoingJob.clock();
                currentTime++;

                nextJobIndex = enqueueArrivedJobs(nextJobIndex, currentTime);
            }

            if(ongoingJob.remainingTime != 0) {
                readyQueue.offer(ongoingJob);
                continue;
            }

            calculateTurnAroundTimeAndWaitingTime(ongoingJob, currentTime);
            completed++;


        }

        finalizeExecution();


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

    private int enqueueArrivedJobs(int nextJobIndex, int currentTime) {

        while(nextJobIndex < jobs.size() && jobs.get(nextJobIndex).arrivalTime <= currentTime) {
            readyQueue.offer(jobs.get(nextJobIndex));
            nextJobIndex++;
        }

        return nextJobIndex;
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


}
