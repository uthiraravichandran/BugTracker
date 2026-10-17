import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    static String URL;
static String USER;
static String PASSWORD;

static {
    try (FileInputStream in = new FileInputStream("db.properties")) {
        Properties p = new Properties();
        p.load(in);
        URL = p.getProperty("db.url");
        USER = p.getProperty("db.user");
        PASSWORD = p.getProperty("db.password");
    } catch (IOException e) {
        System.out.println("Can't read db.properties. Create it in the project folder.");
        System.exit(1);
    }
}
    static Scanner sc = new Scanner(System.in);

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    static int readInt() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println();
            System.out.println("===== BUG TRACKER =====");
            System.out.println("1. View all bugs");
            System.out.println("2. Add bug");
            System.out.println("3. Update bug status");
            System.out.println("4. Delete bug");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = readInt();

            if (choice == 1) {
                viewBugs();
            } else if (choice == 2) {
                addBug();
            } else if (choice == 3) {
                updateBug();
            } else if (choice == 4) {
                deleteBug();
            } else if (choice == 5) {
                System.out.println("Bye!");
            } else {
                System.out.println("Invalid choice, try again");
            }
        } while (choice != 5);
    }
        static void viewBugs() {
        String sql = "SELECT id, title, status, priority FROM bugs";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println();
            System.out.printf("%-4s| %-26s| %-12s| %s%n", "ID", "Title", "Status", "Priority");
            while (rs.next()) {
                System.out.printf("%-4d| %-26s| %-12s| %s%n",
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("status"),
                        rs.getString("priority"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void addBug() {
        System.out.print("Enter title: ");
        String title = sc.nextLine().trim();
        System.out.print("Enter priority (LOW/MEDIUM/HIGH): ");
        String priority = sc.nextLine().trim().toUpperCase();

        if (title.isEmpty()) {
            System.out.println("Title can't be empty");
            return;
        }
        if (!priority.equals("LOW") && !priority.equals("MEDIUM") && !priority.equals("HIGH")) {
            System.out.println("Priority must be LOW, MEDIUM or HIGH");
            return;
        }

        String sql = "INSERT INTO bugs (title, priority, project_id, assigned_to) VALUES (?, ?, 1, 1)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, priority);
            ps.executeUpdate();
            System.out.println("Bug added successfully!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void updateBug() {
        System.out.print("Enter bug id: ");
        int id = readInt();
        System.out.print("Enter new status (OPEN/IN_PROGRESS/CLOSED): ");
        String status = sc.nextLine().trim().toUpperCase();

        if (!status.equals("OPEN") && !status.equals("IN_PROGRESS") && !status.equals("CLOSED")) {
            System.out.println("Status must be OPEN, IN_PROGRESS or CLOSED");
            return;
        }

        String sql = "UPDATE bugs SET status = ? WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Bug " + id + " updated to " + status);
            } else {
                System.out.println("Bug " + id + " not found");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void deleteBug() {
        System.out.print("Enter bug id to delete: ");
        int id = readInt();

        String sql = "DELETE FROM bugs WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Bug " + id + " deleted");
            } else {
                System.out.println("Bug " + id + " not found");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
