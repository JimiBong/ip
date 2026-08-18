import java.util.ArrayList;
import java.util.Scanner;

public class Penny {
    public static void main(String[] args) {
        System.out.println("Hi, I'm Penny, what can I do for you?");

        Scanner scanner = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<>();

        while (true) {
            String input = scanner.nextLine();
            input = input.trim();

            if (input.isBlank()) {
                continue;
            }

            if (input.equalsIgnoreCase("bye")) {
                break;
            }

            else if (input.equalsIgnoreCase("list")) {
                if (list.isEmpty()) {
                    System.out.println("List is empty");
                    continue;
                }

                for (int i = 0; i < list.size(); i++) {
                    System.out.println((i + 1) + ". " + list.get(i));
                }
            }

            else {
                list.add(input);
                System.out.println("added: " + input);
            }
        }

        System.out.println("Bye! See you soon!");
    }
}
