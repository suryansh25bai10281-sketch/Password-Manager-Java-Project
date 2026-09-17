import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PasswordManagerApp {

    private static final String DATA_FILE = "vault_records_encoded.txt";

    public static volatile boolean isLocked = false;

    private static List<VaultItem> vault = new ArrayList<>();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Starting Personal Security Vault...");

        Thread autoLockThread =
                new Thread(new VaultAutoLockService(120));

        autoLockThread.start();

        boolean running = true;

        while (running) {

            if (isLocked) {
                System.out.println("Session locked. Exiting for security.");
                break;
            }

            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Add Web Account");
            System.out.println("2. Add Financial Account");
            System.out.println("3. Update Password");
            System.out.println("4. Delete Account");
            System.out.println("5. View Vault & Save to Disk (Encoded)");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();

            if (isLocked) {
                break;
            }

            try {

                switch (choice) {

                    case "1":
                        addAccount(scanner, "WEB");
                        break;

                    case "2":
                        addAccount(scanner, "BANK");
                        break;

                    case "3":
                        updatePassword(scanner);
                        break;

                    case "4":
                        deleteAccount(scanner);
                        break;

                    case "5":
                        viewAndSaveVault();
                        break;

                    case "6":
                        System.out.println("Exiting application...");
                        running = false;
                        autoLockThread.interrupt();
                        break;

                    default:
                        System.out.println("Invalid option. Try again.");
                }

            } catch (WeakPasswordException e) {

                System.out.println("Error: " + e.getMessage());

            } catch (IOException e) {

                System.out.println("File Error: Could not save records.");
            }
        }

        scanner.close();
    }

    private static void addAccount(Scanner scanner, String type)
            throws WeakPasswordException {

        System.out.print("Enter Item ID: ");
        String id = scanner.nextLine();

        for (VaultItem item : vault) {

            if (item.getItemId().equalsIgnoreCase(id)) {
                System.out.println(
                        "Error: Item ID already exists in the vault.");
                return;
            }
        }

        System.out.print("Enter Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Username: ");
        String user = scanner.nextLine();

        System.out.print("Enter Password: ");
        String pass = scanner.nextLine();

        if (type.equals("WEB")) {

            System.out.print("Enter URL: ");
            String url = scanner.nextLine();

            vault.add(
                    new WebAccount(id, title, user, pass, url)
            );

        } else {

            System.out.print("Enter Account Number: ");
            String accountNumber = scanner.nextLine();

            vault.add(
                    new FinancialAccount(
                            id, title, user, pass, accountNumber
                    )
            );
        }

        System.out.println(type + " account added successfully.");
    }

    private static void updatePassword(Scanner scanner)
            throws WeakPasswordException {

        System.out.print("Enter Item ID to update: ");
        String id = scanner.nextLine();

        for (VaultItem item : vault) {

            if (item.getItemId().equalsIgnoreCase(id)) {

                System.out.print("Enter new password: ");
                String newPass = scanner.nextLine();

                item.setPassword(newPass);

                System.out.println(
                        "Password updated. New Security Rating: "
                                + item.evaluateSecurity()
                );

                return;
            }
        }

        System.out.println("Account ID not found.");
    }

    private static void deleteAccount(Scanner scanner) {

        System.out.print("Enter Item ID to delete: ");
        String id = scanner.nextLine();

        boolean removed = vault.removeIf(
                item -> item.getItemId().equalsIgnoreCase(id)
        );

        if (removed) {
            System.out.println("Account deleted successfully.");
        } else {
            System.out.println("Account ID not found.");
        }
    }

    private static void viewAndSaveVault()
            throws IOException {

        if (vault.isEmpty()) {
            System.out.println("Vault is empty.");
            return;
        }

        System.out.println("\n--- Current Vault Items ---");

        try (BufferedWriter bw =
                     new BufferedWriter(
                             new FileWriter(DATA_FILE))) {

            for (VaultItem item : vault) {

                item.displayDetails();

                System.out.println(
                        "       Security Rating: "
                                + item.evaluateSecurity()
                );

                bw.write(item.toEncodedString());
                bw.newLine();
            }
        }

        System.out.println(
                "\nVault records Base64 encoded and saved to "
                        + DATA_FILE
        );
    }
}
