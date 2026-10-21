package com.scc.Management;

import com.scc.Management.dao.ScenarioDao;
import com.scc.Management.model.Pet;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String LINE = "=======================================================";

    public static void main(String[] args) {
        ScenarioDao dao = new ScenarioDao();
        dao.createTable();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addPet(scanner, dao);
                    break;
                case "2":
                    viewPets(dao);
                    break;
                case "3":
                    System.out.println("\nExiting system... Thank you!");
                    running = false;
                    break;
                default:
                    System.out.println("\nInvalid choice. Please enter 1, 2, or 3.\n");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println(LINE);
        System.out.println("          PET CLINIC VACCINATION LOG");
        System.out.println(LINE);
        System.out.println("[1] Add Pet Record");
        System.out.println("[2] View Vaccination Records");
        System.out.println("[3] Exit System");
        System.out.println(LINE);
        System.out.print("Enter choice: ");
    }

    private static void addPet(Scanner scanner, ScenarioDao dao) {
        System.out.println("\n--- ADD PET VACCINATION RECORD ---");

        System.out.print("Enter Pet Name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("\nERROR: Pet name cannot be empty.\n");
            return;
        }

        System.out.print("Enter Species (DOG / CAT / BIRD): ");
        String species = scanner.nextLine().trim().toUpperCase();
        if (!species.equals("DOG") && !species.equals("CAT") && !species.equals("BIRD")) {
            System.out.println("\nERROR: Species must be DOG, CAT, or BIRD.\n");
            return;
        }

        System.out.print("Enter Vaccine Type (RABIES / DHPP / FVRCP): ");
        String vaccine = scanner.nextLine().trim().toUpperCase();
        if (!vaccine.equals("RABIES") && !vaccine.equals("DHPP") && !vaccine.equals("FVRCP")) {
            System.out.println("\nERROR: Vaccine must be RABIES, DHPP, or FVRCP.\n");
            return;
        }

        System.out.print("Enter Service Fee: ");
        double fee;
        try {
            fee = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("\nERROR: Fee must be a number (e.g., 500.00).\n");
            return;
        }
        if (fee < 0) {
            System.out.println("\nERROR: Fee cannot be negative.\n");
            return;
        }

        Pet pet = new Pet(name, species, vaccine, fee);
        if (dao.insertPet(pet)) {
            System.out.println("\nSUCCESS: Pet vaccination record saved!\n");
        } else {
            System.out.println("\nERROR: Record was not saved.\n");
        }
    }

    private static void viewPets(ScenarioDao dao) {
        List<Pet> pets = dao.getAllPets();
        String border = "+----+--------------------+------------+--------------+----------------+";

        System.out.println();
        System.out.println("=".repeat(border.length()));
        System.out.println("                          PET VACCINATION RECORDS");
        System.out.println("=".repeat(border.length()));

        if (pets.isEmpty()) {
            System.out.println("No records found.\n");
            return;
        }

        System.out.println(border);
        System.out.printf("| %-2s | %-18s | %-10s | %-12s | %-14s |%n", "ID", "PET NAME", "SPECIES", "VACCINE", "FEE");
        System.out.println(border);
        for (Pet p : pets) {
            System.out.printf("| %-2d | %-18s | %-10s | %-12s | %-14.2f |%n",
                    p.getId(), p.getPetName(), p.getSpecies(), p.getVaccine(), p.getFee());
        }
        System.out.println(border);
        System.out.println("Total Records Displayed: " + pets.size() + "\n");
    }
}