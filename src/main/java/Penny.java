import java.util.Scanner;

public class Penny {
    public static void main(String[] args) {
        String banner = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        banner += "\nHi, I'm Penny, what can I do for you?";
        System.out.println(banner);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                break;
            }

            System.out.println(input);
        }

        System.out.println("Bye! See you soon!");
    }
}
