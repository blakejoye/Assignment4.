

package com.mycompany.datapersistenceapp;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DataPersistenceApp {
 private static final String DATA_FILE = "data.txt";
    public static void main(String[] args) {
        createDataFileIfNotExists();

        Scanner scanner = new Scanner(System.in);
        Map<String, String> data = loadData();

        while (true) {
            System.out.println("Select an option:");
            System.out.println("1. Add data");
            System.out.println("2. Read data");
            System.out.println("3. Delete data");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter key: ");
                    String key = scanner.next();
                    System.out.print("Enter value: ");
                    String value = scanner.next();
                    addData(data, key, value);
                    break;
                case 2:
                    System.out.print("Enter key to read: ");
                    String readKey = scanner.next();
                    readData(data, readKey);
                    break;
                case 3:
                    System.out.print("Enter key to delete: ");
                    String deleteKey = scanner.next();
                    deleteData(data, deleteKey);
                    break;
                case 4:
                    saveData(data);
                    scanner.close();
                    System.out.println("Exiting the application.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private static void createDataFileIfNotExists() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Map<String, String> loadData() {
        Map<String, String> data = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    data.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            // Ignore if the file does not exist.
        }
        return data;
    }

    private static void saveData(Map<String, String> data) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                writer.println(entry.getKey() + "=" + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addData(Map<String, String> data, String key, String value) {
        data.put(key, value);
        saveData(data);
        System.out.println("Added: " + key + " => " + value);
    }

    private static void readData(Map<String, String> data, String key) {
        String value = data.get(key);
        if (value != null) {
            System.out.println(key + " => " + value);
        } else {
            System.out.println(key + " not found.");
        }
    }

    private static void deleteData(Map<String, String> data, String key) {
        if (data.containsKey(key)) {
            data.remove(key);
            saveData(data);
            System.out.println("Deleted: " + key);
        } else {
            System.out.println(key + " not found.");
        }
    }
}