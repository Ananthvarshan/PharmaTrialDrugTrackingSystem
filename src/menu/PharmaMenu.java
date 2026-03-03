package menu;

import java.util.ArrayList;
import java.util.Scanner;

import dao.RoleDAO;
import dao.ClinicalSiteDAO;
import dao.UserAccountDAO;
import dao.ClinicalTrialDAO;
import model.Role;
import model.ClinicalSite;
import model.UserAccount;
import model.ClinicalTrial;

public class PharmaMenu {

    private static Scanner scanner = new Scanner(System.in);

    private static RoleDAO roleDAO = new RoleDAO();
    private static ClinicalSiteDAO siteDAO = new ClinicalSiteDAO();
    private static UserAccountDAO userDAO = new UserAccountDAO();
    private static ClinicalTrialDAO trialDAO = new ClinicalTrialDAO();

    public static void main(String[] args) {
        int choice;
        do {
            showMainMenu();
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> manageRoles();
                case 2 -> manageClinicalSites();
                case 3 -> manageUsers();
                case 4 -> manageClinicalTrials();
                case 0 -> System.out.println("Exiting Pharma Trial System. Goodbye!");
                default -> System.out.println("Invalid choice! Please try again.");
            }

        } while (choice != 0);
    }

    private static void showMainMenu() {
        System.out.println("\n====== Pharma Trial System Menu ======");
        System.out.println("1. Manage Roles");
        System.out.println("2. Manage Clinical Sites");
        System.out.println("3. Manage Users");
        System.out.println("4. Manage Clinical Trials");
        System.out.println("0. Exit");
        System.out.println("=====================================");
    }

    // ---------- Role Management ----------
    private static void manageRoles() {
        int choice;
        do {
            System.out.println("\n--- Role Management ---");
            System.out.println("1. Add Role");
            System.out.println("2. View All Roles");
            System.out.println("3. Update Role");
            System.out.println("4. Delete Role");
            System.out.println("0. Back");
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> addRole();
                case 2 -> viewAllRoles();
                case 3 -> updateRole();
                case 4 -> deleteRole();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice!");
            }

        } while (choice != 0);
    }

    private static void addRole() {
        System.out.print("Enter role name: ");
        String name = scanner.nextLine();
        Role role = new Role();
        role.setName(name);
        if (roleDAO.addRole(role)) {
            System.out.println("✅ Role added successfully!");
        } else {
            System.out.println("❌ Failed to add role.");
        }
    }

    private static void viewAllRoles() {
        ArrayList<Role> roles = roleDAO.getAllRoles();
        System.out.println("\n--- All Roles ---");
        for (Role r : roles) {
            System.out.println("ID: " + r.getId() + ", Name: " + r.getName());
        }
    }

    private static void updateRole() {
        int id = readInt("Enter role ID to update: ");
        Role role = roleDAO.getRoleById(id);
        if (role == null) {
            System.out.println("❌ Role not found.");
            return;
        }
        System.out.print("Enter new role name: ");
        String name = scanner.nextLine();
        role.setName(name);
        if (roleDAO.updateRole(role)) {
            System.out.println("✅ Role updated successfully!");
        } else {
            System.out.println("❌ Failed to update role.");
        }
    }

    private static void deleteRole() {
        int id = readInt("Enter role ID to delete: ");
        if (roleDAO.deleteRole(id)) {
            System.out.println("✅ Role deleted successfully!");
        } else {
            System.out.println("❌ Failed to delete role.");
        }
    }

    // ---------- Clinical Site Management ----------
    private static void manageClinicalSites() {
        int choice;
        do {
            System.out.println("\n--- Clinical Site Management ---");
            System.out.println("1. Add Site");
            System.out.println("2. View All Sites");
            System.out.println("3. Update Site");
            System.out.println("4. Delete Site");
            System.out.println("0. Back");
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> addSite();
                case 2 -> viewAllSites();
                case 3 -> updateSite();
                case 4 -> deleteSite();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice!");
            }

        } while (choice != 0);
    }

    private static void addSite() {
        ClinicalSite site = new ClinicalSite();
        System.out.print("Enter site name: ");
        site.setName(scanner.nextLine());
        System.out.print("Enter location: ");
        site.setLocation(scanner.nextLine());
        System.out.print("Enter image URL: ");
        site.setImageUrl(scanner.nextLine());
        site.setMaxDoctors(readInt("Enter max doctors: "));
        site.setMaxPatients(readInt("Enter max patients: "));

        if (siteDAO.addClinicalSite(site)) {
            System.out.println("✅ Clinical site added successfully!");
        } else {
            System.out.println("❌ Failed to add site.");
        }
    }

    private static void viewAllSites() {
        ArrayList<ClinicalSite> sites = siteDAO.getAllSites();
        System.out.println("\n--- All Clinical Sites ---");
        for (ClinicalSite s : sites) {
            System.out.println("ID: " + s.getId() + ", Name: " + s.getName() +
                               ", Location: " + s.getLocation() +
                               ", Max Doctors: " + s.getMaxDoctors() +
                               ", Max Patients: " + s.getMaxPatients());
        }
    }

    private static void updateSite() {
        int id = readInt("Enter site ID to update: ");
        ClinicalSite site = siteDAO.getSiteById(id);
        if (site == null) {
            System.out.println("❌ Site not found.");
            return;
        }
        System.out.print("Enter new site name: ");
        site.setName(scanner.nextLine());
        System.out.print("Enter new location: ");
        site.setLocation(scanner.nextLine());
        System.out.print("Enter new image URL: ");
        site.setImageUrl(scanner.nextLine());
        site.setMaxDoctors(readInt("Enter max doctors: "));
        site.setMaxPatients(readInt("Enter max patients: "));

        if (siteDAO.updateClinicalSite(site)) {
            System.out.println("✅ Site updated successfully!");
        } else {
            System.out.println("❌ Failed to update site.");
        }
    }

    private static void deleteSite() {
        int id = readInt("Enter site ID to delete: ");
        if (siteDAO.deleteClinicalSite(id)) {
            System.out.println("✅ Site deleted successfully!");
        } else {
            System.out.println("❌ Failed to delete site.");
        }
    }

    // ---------- User Management ----------
    private static void manageUsers() {
        int choice;
        do {
            System.out.println("\n--- User Management ---");
            System.out.println("1. Add User");
            System.out.println("2. View All Users");
            System.out.println("3. Update User");
            System.out.println("4. Delete User");
            System.out.println("0. Back");
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> addUser();
                case 2 -> viewAllUsers();
                case 3 -> updateUser();
                case 4 -> deleteUser();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice!");
            }

        } while (choice != 0);
    }

    private static void addUser() {
        UserAccount user = new UserAccount();
        System.out.print("Enter username: ");
        user.setUsername(scanner.nextLine());
        System.out.print("Enter full name: ");
        user.setFullName(scanner.nextLine());
        System.out.print("Enter email: ");
        user.setEmail(scanner.nextLine());
        System.out.print("Enter password: ");
        user.setPassword(scanner.nextLine());
        user.setRoleId(readInt("Enter role ID: "));
        user.setClinicalSiteId(readInt("Enter clinical site ID: "));
        System.out.print("Enter hospital name: ");
        user.setHospitalName(scanner.nextLine());
        user.setCreatedBy(readInt("Enter creator user ID: "));
        user.setStatus("Active");
        user.setCanRegister(true);

        if (userDAO.addUser(user)) {
            System.out.println("✅ User added successfully!");
        } else {
            System.out.println("❌ Failed to add user.");
        }
    }

    private static void viewAllUsers() {
        ArrayList<UserAccount> users = userDAO.getAllUsers();
        System.out.println("\n--- All Users ---");
        for (UserAccount u : users) {
            System.out.println("ID: " + u.getId() + ", Username: " + u.getUsername() +
                               ", Full Name: " + u.getFullName() +
                               ", Email: " + u.getEmail() +
                               ", Role ID: " + u.getRoleId() +
                               ", Site ID: " + u.getClinicalSiteId());
        }
    }

    private static void updateUser() {
        int id = readInt("Enter user ID to update: ");
        UserAccount user = userDAO.getUserById(id);
        if (user == null) {
            System.out.println("❌ User not found.");
            return;
        }
        System.out.print("Enter new username: ");
        user.setUsername(scanner.nextLine());
        System.out.print("Enter new full name: ");
        user.setFullName(scanner.nextLine());
        System.out.print("Enter new email: ");
        user.setEmail(scanner.nextLine());
        System.out.print("Enter new password: ");
        user.setPassword(scanner.nextLine());
        user.setRoleId(readInt("Enter new role ID: "));
        user.setClinicalSiteId(readInt("Enter new clinical site ID: "));
        System.out.print("Enter new hospital name: ");
        user.setHospitalName(scanner.nextLine());
        user.setStatus("Active");
        user.setCanRegister(true);

        if (userDAO.updateUser(user)) {
            System.out.println("✅ User updated successfully!");
        } else {
            System.out.println("❌ Failed to update user.");
        }
    }

    private static void deleteUser() {
        int id = readInt("Enter user ID to delete: ");
        if (userDAO.deleteUser(id)) {
            System.out.println("✅ User deleted successfully!");
        } else {
            System.out.println("❌ Failed to delete user.");
        }
    }

    // ---------- Clinical Trial Management ----------
    private static void manageClinicalTrials() {
        int choice;
        do {
            System.out.println("\n--- Clinical Trial Management ---");
            System.out.println("1. Add Trial");
            System.out.println("2. View All Trials");
            System.out.println("3. Update Trial");
            System.out.println("4. Delete Trial");
            System.out.println("0. Back");
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> addTrial();
                case 2 -> viewAllTrials();
                case 3 -> updateTrial();
                case 4 -> deleteTrial();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice!");
            }

        } while (choice != 0);
    }

    private static void addTrial() {
        ClinicalTrial trial = new ClinicalTrial();
        System.out.print("Enter trial title: ");
        trial.setTitle(scanner.nextLine());
        System.out.print("Enter description: ");
        trial.setDescription(scanner.nextLine());
        trial.setStartDate(java.sql.Date.valueOf(readString("Enter start date (YYYY-MM-DD): ")));
        trial.setEndDate(java.sql.Date.valueOf(readString("Enter end date (YYYY-MM-DD): ")));
        trial.setSiteId(readInt("Enter clinical site ID: "));
        trial.setCurrentPhase(readString("Enter current phase: "));
        trial.setPhaseStatus("Ongoing");

        if (trialDAO.addClinicalTrial(trial)) {
            System.out.println("✅ Clinical trial added successfully!");
        } else {
            System.out.println("❌ Failed to add trial.");
        }
    }

    private static void viewAllTrials() {
        ArrayList<ClinicalTrial> trials = trialDAO.getAllClinicalTrials();
        System.out.println("\n--- All Clinical Trials ---");
        for (ClinicalTrial t : trials) {
            System.out.println("ID: " + t.getId() + ", Title: " + t.getTitle() +
                               ", Site ID: " + t.getSiteId() +
                               ", Current Phase: " + t.getCurrentPhase() +
                               ", Status: " + t.getPhaseStatus());
        }
    }

    private static void updateTrial() {
        int id = readInt("Enter trial ID to update: ");
        ClinicalTrial trial = trialDAO.getClinicalTrialById(id);
        if (trial == null) {
            System.out.println("❌ Trial not found.");
            return;
        }
        System.out.print("Enter new trial title: ");
        trial.setTitle(scanner.nextLine());
        System.out.print("Enter new description: ");
        trial.setDescription(scanner.nextLine());
        trial.setStartDate(java.sql.Date.valueOf(readString("Enter start date (YYYY-MM-DD): ")));
        trial.setEndDate(java.sql.Date.valueOf(readString("Enter end date (YYYY-MM-DD): ")));
        trial.setSiteId(readInt("Enter clinical site ID: "));
        trial.setCurrentPhase(readString("Enter current phase: "));
        trial.setPhaseStatus(readString("Enter phase status: "));

        if (trialDAO.updateClinicalTrial(trial)) {
            System.out.println("✅ Clinical trial updated successfully!");
        } else {
            System.out.println("❌ Failed to update trial.");
        }
    }

    private static void deleteTrial() {
        int id = readInt("Enter trial ID to delete: ");
        if (trialDAO.deleteClinicalTrial(id)) {
            System.out.println("✅ Clinical trial deleted successfully!");
        } else {
            System.out.println("❌ Failed to delete trial.");
        }
    }

    // ---------- Helper Methods ----------
    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid integer: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return value;
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
