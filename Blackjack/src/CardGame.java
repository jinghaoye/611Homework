
import java.util.Scanner;

public abstract class CardGame extends Game{

    public CardGame() {
    }

    public static int numberValidation(int validNum1 , int validNum2){
        Scanner sc = new Scanner(System.in);
        int number;
        int count=0;
        do {
            if(count>0){
                System.out.println("please enter an valid number");
            }
            count++;
            while (!sc.hasNextInt()) {
                System.out.println("That's not an integer number! please enter an valid number");
                sc.next();
            }
            number = sc.nextInt();
        } while (number <= validNum1 || number>= validNum2);
        return number;
    }

    public abstract void checkWinCondition();




}
