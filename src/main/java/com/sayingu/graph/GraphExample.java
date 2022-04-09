package com.sayingu.graph;

import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class GraphExample {
    public static void main(String[] args) {
        System.out.println("Java Graph Tutorial");
        System.out.println();

        // Load OAuth settings
        final Properties props = new Properties();
        try {
            props.load(GraphExample.class.getResourceAsStream("/auth.properties"));
        } catch (IOException e) {
            System.out.println(
                    "Unable to read OAuth configuration. Make sure you have a properly formatted oAuth.properties file. See README for details.");
            return;
        }

        final String appId = props.getProperty("app.id");
        final List<String> appScopes = Arrays
                .asList(props.getProperty("app.scopes").split(","));

        // Initialize Graph with auth settings
        Auth.initializeGraphAuth(appId, appScopes);
        final String accessToken = Auth.getUserAccessToken();

        System.out.println("Access token: " + accessToken);

        Scanner input = new Scanner(System.in);

        int choice = -1;

        while (choice != 0) {
            System.out.println("Please choose one of the following options:");
            System.out.println("0. Exit");
            System.out.println("1. Display access token");
            System.out.println("2. View this week's calendar");
            System.out.println("3. Add an event");

            try {
                choice = input.nextInt();
            } catch (InputMismatchException ex) {
                // Skip over non-integer input
            }

            input.nextLine();

            // Process user choice
            switch (choice) {
                case 0:
                    // Exit the program
                    System.out.println("Goodbye...");
                    break;
                case 1:
                    // Display access token
                    break;
                case 2:
                    // List the calendar
                    break;
                case 3:
                    // Create a new event
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }

        input.close();
    }
}
