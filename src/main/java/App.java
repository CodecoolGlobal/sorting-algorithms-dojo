import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

import static java.nio.file.Files.lines;
import static java.util.Arrays.asList;

public class App {
    private final static String RESOURCE_PATH_PREFIX = "src/main/resources/";
    private final static String SORTED_FILE_NAME_PREFIX = "sorted_";

    /**
     * This is array from quick sort presentation
     */
    private final static Integer[] ARRAY_FROM_QUICK_SORT_PRESENTATION = {2, 5, 3, 1, 6, 4};

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
        Integer[] numbers = getDataFromFile(filename);
        sortBasedOnUserChoice(numbers);
        saveDataToFile(numbers, SORTED_FILE_NAME_PREFIX + filename);
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

    private static void sortBasedOnUserChoice(Integer[] numbers) {
        boolean run = true;
        while (run) {
            printSortingMenu();
            String response = scanner.nextLine();
            long start = 0;
            long end = 0;

            switch (response) {
                case "1":
                    start = System.currentTimeMillis();
                    sortUsingInsertionSortAlgorithm(numbers);
                    end = System.currentTimeMillis();
                    break;
                case "2":
                    start = System.currentTimeMillis();
                    sortUsingBuiltInMethod(numbers);
                    end = System.currentTimeMillis();
                    break;
                case "3":
                    start = System.currentTimeMillis();
                    sortUsingInsertionSortAlgorithmWithList(new ArrayList<>(asList(numbers)));
                    end = System.currentTimeMillis();
                    break;
                case "4":
                    start = System.currentTimeMillis();
                    sortUsingInsertionSortAlgorithmWithList(new LinkedList<>(asList(numbers)));
                    end = System.currentTimeMillis();
                    break;
                case "Q":
                    run = false;
                    break;
                default:
                    LOGGER.info("Wrong option has been chosen.");
            }
            LOGGER.info("Sorting is finished, total sorting time: " + (end - start) + "ms");
        }
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
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

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
        LOGGER.info("Sorting array started.");
        Arrays.sort(array);
    }

    private static void sortUsingInsertionSortAlgorithm(Integer[] array) {
        LOGGER.info("Sorting array started.");
        int arrayLength = array.length;

        for (int i = 0; i < arrayLength; i++) {
            int key = array[i];
            int j = i - 1;

            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j = j - 1;
            }

            array[j + 1] = key;
        }
    }

    private static void sortUsingInsertionSortAlgorithmWithList(List<Integer> list) {
        int arrayLength = list.size();

        for (int i = 0; i < arrayLength; i++) {
            int key = list.get(i);
            int j = i - 1;

            while (j >= 0 && list.get(j) > key) {
                list.set(j + 1, list.get(j));
                j = j - 1;
            }

            list.set(j + 1, key);
        }
    }

    private static void sortUsingQuickSortAlgorithm(Integer[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private static void quickSort(Integer[] array, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(array, begin, end);

            quickSort(array, begin, partitionIndex - 1);
            quickSort(array, partitionIndex + 1, end);
        }
    }

    private static int partition(Integer[] array, int begin, int end) {
        int pivot = array[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (array[j] <= pivot) {
                i++;

                int swapTemp = array[i];
                array[i] = array[j];
                array[j] = swapTemp;
            }
        }

        int swapTemp = array[i + 1];
        array[i + 1] = array[end];
        array[end] = swapTemp;

        return i + 1;
    }
}
