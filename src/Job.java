public class Job implements Comparable<Job> {
    String name;
    int arrivalTime;
    int burstTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;
    int remainingTime;
    int priority;


    public Job(String name, int arrivalTime, int burstTime) {
        this(name, arrivalTime, burstTime, 0);
    }

    public Job(String name, int arrivalTime, int burstTime, int priority) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.priority = priority;
    }

    @Override
    public int compareTo(Job o) {
        return Integer.compare(this.arrivalTime, o.arrivalTime);
    }



    public void clock() {
        remainingTime--;
    }
}
