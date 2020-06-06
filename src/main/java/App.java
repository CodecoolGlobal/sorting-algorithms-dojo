import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static java.nio.file.Files.lines;

public class App {
    /**
     * Change the paths in getDataFromFile() method depending how do you run the program:
     * if you run the program from IDE use RESOURCE_PATH_PREFIX
     * if you run the program from cmd use COMMAND_LINE_PATH_PREFIX
     * */
    private final static String RESOURCE_PATH_PREFIX = "src/main/resources/";
    private final static String COMMAND_LINE_PATH_PREFIX = "../resources/";
    private final static String NO_PARAMETER_MESSAGE = "No parameter provided. Exiting...";
    private final static String SORTED_FILE_NAME_PREFIX = "sorted_";

    /**
     * This is array from quick sort presentation
     */
    private final static Integer[] ARRAY_FROM_QUICK_SORT_PRESENTATION = {2, 5, 3, 1, 6, 4};

    private static Logger LOGGER;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT.%1$tL] [%4$-7s] %5$s %n");
        LOGGER = Logger.getLogger(App.class.getName());
    }

    public static void main(String[] args) {
        try {
            if (args != null && args.length > 0) {
                String fileName = args[0];

                if (fileName != null && fileName.length() > 0) {
                    /** If you want to sort numbers from file uncomment the line 44
                     * and comment the 46 line
                     */
//                    Integer[] array = getDataFromFile(fileName);

                    Integer[] array = ARRAY_FROM_QUICK_SORT_PRESENTATION;

                    LOGGER.info("Sorting array started.");
                    long start = System.currentTimeMillis();

                    /** This is the place when you should replace the sorting method
                     * (in case of lists, you should pass ArrayList or LinkedList
                     * e.g.:
                     * List<Integer> integers = Arrays.asList(array); -> returns ArrayList
                     * List<Integer> link = new LinkedList<>(integers))
                     */
                    sortUsingQuickSortAlgorithm(array);

                    long end = System.currentTimeMillis();
                    long elapsedTime = end - start;
                    LOGGER.info("Sorting is finished, total sorting time: " + elapsedTime + "ms");

                    saveDataToFile(array, SORTED_FILE_NAME_PREFIX + fileName);
                } else {
                    LOGGER.info(NO_PARAMETER_MESSAGE);
                }
            } else {
                LOGGER.info(NO_PARAMETER_MESSAGE);
            }
            /** This is hacky (for dojo purpose only) don't catch Exception
             */
        } catch (Exception e) {
            LOGGER.severe("Problem with reading file. Exiting...");
        }
    }

    private static Integer[] getDataFromFile(String fileName) throws IOException {
        LOGGER.info("Starting loading file data...");
        Integer[] array = lines(Paths.get(RESOURCE_PATH_PREFIX + fileName))
                .map(Integer::valueOf)
                .toArray(Integer[]::new);
        LOGGER.info("File loaded.");
        return array;
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
        Arrays.sort(array);
    }

    private static void sortUsingInsertionSortAlgorithm(Integer[] array) {
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
