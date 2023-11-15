import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // used to show off singleton pattern, opening or closing the adminpanel refers to the same instance
        while (true) {
            System.out.print("Press enter to open the AdminControlPanel, or enter 0 (int) to exit the program: ");
            String input = sc.nextLine();
            if (input.equals("0")) {
                sc.close();
                break;
            }

            AdminControlPanel adminPanel = AdminControlPanel.getInstance();
            adminPanel.initialize();
        }
    }
}