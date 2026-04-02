import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProcessManager {
    public static void main(String[] args) {
        List<Job> jobs = new ArrayList<>();

        while(true) {
            System.out.println("Enter (job name, arrival time, burst time) or type 'confirm' to finish");
            Scanner scan = new Scanner(System.in);
            String[] input = scan.nextLine().split(" ");

            if(input[0].equals("confirm")) {
                break;
            }

            String name = input[0];
            int arrivalTime = Integer.parseInt(input[1]);
            int burstTime = Integer.parseInt(input[2]);

            Job job = new Job(name, arrivalTime, burstTime);
            jobs.add(job);
        }
        FCFS fcfs = new FCFS(jobs);
        fcfs.run();
    }

}
