package game_model;

import java.io.*;
import java.util.*;

/**
 * The UserFileHandler class provides utility methods to handle user data stored in a text file.
 * The user data includes information such as username, password, wins, losses, total score, and total games.
 * The class supports reading from and writing to the user file.
 */
public class UserFileHandler {
    private static final String USER_FILE = "src/user.txt";

    /**
     * Reads the user data from the user file and returns a list of user details.
     * Each user's details are stored as a string array.
     *
     * @return a list of user details, where each element is a string array containing user data.
     */
    public static List<String[]> readUserFile() {
        List<String[]> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(", ");
                users.add(details);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Writes the provided list of user details to the user file.
     * Each user's details are written as a comma-separated string.
     *
     * @param users a list of user details, where each element is a string array containing user data.
     */
    public static void writeUserFile(List<String[]> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (String[] details : users) {
                writer.write(String.join(", ", details));
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

