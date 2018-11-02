import java.util.Random;
import java.util.Scanner;

public class Agent1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int width = scanner.nextInt();
        int height = scanner.nextInt();
        scanner.nextLine();
        for (int y = 0; y < height; y++) {
            String input = scanner.nextLine();
        }
        int startX = scanner.nextInt();
        int startY = scanner.nextInt();
        int targetX = scanner.nextInt();
        int targetY = scanner.nextInt();
        int switches = scanner.nextInt();
        for (int i = 0 ; i < switches; i++) {
            int switchX = scanner.nextInt();
            int switchY = scanner.nextInt();
            int blockX = scanner.nextInt();
            int blockY = scanner.nextInt();
            boolean blocking = scanner.nextInt() == 1;
        }

        System.out.println("RLDRUDRDDRRRRRRUULLLLUUUUULULLLLLUURRUULLUUUUUURRLLDDDDDDRRDDRRDDRRDDDDDDRRRRDDLLLLLLLLUUUUUULLLLLLUULLUUUULLURDRDDDDRRDDRRRRRRDDDDRRDDRRRRRRUUUUUUUULLUUUUUULLUU");
        // System.out.println("RRD");
    }
}
