import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Logger;

import static java.nio.file.Files.lines;
import static java.util.Arrays.asList;

public class App {
    private final static String RESOURCE_PATH_PREFIX = "src/main/resources/";
    private final static String SORTED_FILE_NAME_PREFIX = "sorted_";
    private final static Map<String, String> NUMBERS_TO_FILES = new HashMap<String, String>() {{
        put("1", "one_thousand.txt");
        put("2", "ten_thousand.txt");
        put("3", "fifty_thousand.txt");
        put("4", "one_hundred_thousand.txt");
        put("5", "five_hundred_thousand.txt");
        put("6", "one_million.txt");
        put("7", "three_millions.txt");
    }};

    private static Logger LOGGER;
    private static Scanner scanner;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT.%1$tL] [%4$-7s] %5$s %n");
        LOGGER = Logger.getLogger(App.class.getName());
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) throws IOException {
        printFilesNames();
        String filename = getFileNameFromUser();
        sortBasedOnUserChoice(filename);
    }

    private static void printFilesNames() {
        System.out.println("Which file do you want to sort? (one_thousand.txt is default)");
        NUMBERS_TO_FILES.forEach((key, value) -> System.out.println(key + ") " + value));
    }

    private static String getFileNameFromUser() {
        String chosenFilenameNumber = scanner.nextLine();
        String filename = "one_thousand.txt";
        if (NUMBERS_TO_FILES.containsKey(chosenFilenameNumber)) {
            filename = NUMBERS_TO_FILES.get(chosenFilenameNumber);
        }

        return filename;
    }

    private static Integer[] getDataFromFile(String fileName) throws IOException {
        LOGGER.info("Starting loading file data...");
        Integer[] array = lines(Paths.get(RESOURCE_PATH_PREFIX + fileName))
                .map(Integer::valueOf)
                .toArray(Integer[]::new);
        LOGGER.info("File loaded.");
        return array;
    }

    private static void sortBasedOnUserChoice(String filename) throws IOException {
        Integer[] numbers = null;

        printSortingMenu();
        String response = scanner.nextLine();
        switch (response) {
            case "1":
                numbers = getDataFromFile(filename);
                sort(numbers, App::sortUsingInsertionSortAlgorithm);
                break;
            case "2":
                numbers = getDataFromFile(filename);
                sort(numbers, App::sortUsingBuiltInMethod);
                break;
            case "3":
                numbers = getDataFromFile(filename);
                sort(numbers, unsorted -> sortUsingInsertionSortAlgorithmWithList(new ArrayList<>(asList(unsorted))));
                break;
            case "4":
                numbers = getDataFromFile(filename);
                sort(numbers, unsorted -> sortUsingInsertionSortAlgorithmWithList(new LinkedList<>(asList(unsorted))));
                break;
            default:
                LOGGER.info("Wrong option has been chosen.");
        }

        if (numbers != null) {
            saveDataToFile(numbers, SORTED_FILE_NAME_PREFIX + filename);
        }
    }

    private static void sort(Integer[] numbers, Consumer<Integer[]> sortingMethod) {
        LOGGER.info("Sorting array started.");
        long start = System.currentTimeMillis();
        sortingMethod.accept(numbers);
        long end = System.currentTimeMillis();

        LOGGER.info("Sorting is finished, total sorting time: " + (end - start) + "ms");
    }

    private static void printSortingMenu() {
        System.out.println("Which method you want to sort the numbers?");
        System.out.println("1) Insertion sort using own algorithm");
        System.out.println("2) Insertion sort using builtin algorithm");
        System.out.println("3) Insertion sort using ArrayList");
        System.out.println("4) Insertion sort using LinkedList");
        System.out.println("Q) Quit");
    }

    private static void saveDataToFile(Integer[] array, String fileName) {
        try {
            File file = new File(String.format("src/main/resources/%s", fileName));
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for (int num : array) {
                writer.write(num + "\n");
            }

            writer.close();
            LOGGER.info("Data is written to file: " + fileName);
        } catch (IOException e) {
            LOGGER.severe("Problem with writing to file. Exiting...");
        }

    }

    private static void sortUsingBuiltInMethod(Integer[] array) {
        // TODO
    }

    private static void sortUsingInsertionSortAlgorithm(Integer[] array) {
        // TODO
    }

    private static void sortUsingInsertionSortAlgorithmWithList(List<Integer> list) {
        // TODO
    }
}
