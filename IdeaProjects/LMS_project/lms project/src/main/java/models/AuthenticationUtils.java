package models;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;





public class AuthenticationUtils {
    private static Scanner scanner = new Scanner(System.in);


    public static boolean reAuthenticate(User currentUser) {
        System.out.println("\n=== AUTHENTICATION REQUIRED ===");
        System.out.println("Please re-enter your credentials to continue:");

        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        // Verify credentials match current user
        if (currentUser.getUsername().equals(username) &&
                currentUser.getPassword().equals(password)) {
            System.out.println("Authentication successful!");
            return true;
        } else {
            System.out.println("Authentication failed! Invalid credentials.");
            return false;
        }
    }


    public static boolean reAuthenticate(User currentUser, String operation) {
        System.out.println("\n=== AUTHENTICATION REQUIRED ===");
        System.out.println("To perform '" + operation + "', please re-enter your credentials:");

        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (currentUser.getUsername().equals(username) &&
                currentUser.getPassword().equals(password)) {
            System.out.println("Authentication successful!");
            return true;
        } else {
            System.out.println("Authentication failed! Access denied.");
            return false;
        }
    }
}
