public class Job implements Comparable<Job> {
    String name;
    int arrivalTime;
    int burstTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;
    int remainingTime;


    public Job(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }


    @Override
    public int compareTo(Job o) {
        return Integer.compare(this.arrivalTime, o.arrivalTime);
    }



    public void clock() {
        remainingTime--;
    }
}
