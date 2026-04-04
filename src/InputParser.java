import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputParser {
    public static SchedulingConfig parse(String fileName) throws FileNotFoundException {
        List<Job> jobs = new ArrayList<>();
        int quantum = 1;

        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if(line.isEmpty() || line.startsWith("#"))
                continue;

            if(line.contains("=")){
                quantum = extractQuantumFromFile(line, quantum);
                continue;
            }

            String[] input = line.split("\\s+");
            jobs.add(extractJobFromInput(input));


        }
        scanner.close();
        return new SchedulingConfig(quantum, jobs);

    }

    private static Job extractJobFromInput(String[] input) {
        String name = input[0];
        int arrivalTime = Integer.parseInt(input[1]);
        int burstTime = Integer.parseInt(input[2]);

        if (input.length >= 4) {
            int priority = Integer.parseInt(input[3]);
            return new Job(name, arrivalTime, burstTime, priority);
        }

        return new Job(name, arrivalTime, burstTime);
    }

    private static int extractQuantumFromFile(String line, int quantum) {
        String[] config = line.split("=");
        String key = config[0].trim();
        String values = config[1].trim();

        if(key.equalsIgnoreCase("quantum")) {
            quantum = Integer.parseInt(values);
        }

        return quantum;
    }
}
