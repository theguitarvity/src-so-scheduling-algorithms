import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProcessManager {
    static List<Job> jobs;
    public ProcessManager() throws FileNotFoundException {
        initializeJobsList();
    }

    private static void initializeJobsList() throws FileNotFoundException {
        jobs = new ArrayList<>();
        File file = new File("in.txt");
        Scanner fileScanner = new Scanner(file);

        while(fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine().trim();
            if(line.isEmpty()) {
                continue;
            }
            String[] input = line.split("\\s+");
            String name = input[0];
            int arrivalTime = Integer.parseInt(input[1]);
            int burstTime = Integer.parseInt(input[2]);

            Job job = new Job(name, arrivalTime, burstTime);
            jobs.add(job);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        initializeJobsList();
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

            Processable processable = extractStrategyFromUserInput(
                    option,
                    jobs.stream().map(job -> new Job(job.name, job.arrivalTime, job.burstTime))
                            .collect(java.util.stream.Collectors.toCollection(ArrayList::new))
            );


            if(processable == null) {
                System.out.println("Invalid entry, choose again");
                continue;
            }

            processable.run();

        }


        System.out.println("Digite qual o ");

    }

    private static Processable extractStrategyFromUserInput(int option, List<Job> jobs) {
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
