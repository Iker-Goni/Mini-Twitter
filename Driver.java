import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Press enter to open the AdminControlPanel, or enter 0 (int) to exit the program");
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