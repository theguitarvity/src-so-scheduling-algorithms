import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProcessManager {
    static List<Job> jobs;

    public static void main(String[] args) throws FileNotFoundException {
        SchedulingConfig config = InputParser.parse("in.txt");
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while(running) {
            printMenu();
            System.out.println("Choose a valid option:");
            int option = scanner.nextInt();

            if(option == 0) {
                running = false;
                continue;
            }

            List<Job> jobs = buildImmutableJobs(config.getJobs());
            Processable processable = extractStrategyFromUserInput(
                    option,
                    jobs,
                    config.getQuantum()

            );


            if(processable == null) {
                System.out.println("Invalid entry, choose again");
                continue;
            }

            processable.run();

        }

        System.out.println("Digite qual o ");

    }

    private static List<Job> buildImmutableJobs(List<Job> jobs) {
        return jobs.stream().map(job -> new Job(job.name, job.arrivalTime, job.burstTime, job.priority))
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));
    }

    private static Processable extractStrategyFromUserInput(int option, List<Job> jobs, int quantum) {
        switch (option) {
            case 1 -> {
                return new FCFS(jobs);
            }
            case 2 -> {
                return new SJF(jobs);
            }
            case 3 -> {
                return new SRTF(jobs);
            }
            case 4 -> {
                return new PriorityScheduling(jobs);
            }
            case 5 ->  {
                return new RoundRobin(jobs, quantum);
            }
        }
        return null;
    }


    private static void printMenu() {
        System.out.println("=================================");
        System.out.println("   CPU Scheduler Simulator");
        System.out.println("=================================");
        System.out.println("1 - FCFS");
        System.out.println("2 - SJF (Non-Preemptive)");
        System.out.println("3 - SRTF");
        System.out.println("4 - Prioridade");
        System.out.println("5 - Round Robin");
        System.out.println("0 - Sair");
        System.out.println("=================================");
    }

}
