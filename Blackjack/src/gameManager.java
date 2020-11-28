public class gameManager {

    public gameManager() {
        System.out.println("Which game do you want to play 1.BlackJack 2.Trinata Ena");
        int choice = CardGame.numberValidation(0, 3);
        if (choice == 1) {
            CardGame blackJack = new BlackJack();
            blackJack.play();
        } else {
            CardGame trinataEna = new TrinataEna();
            trinataEna.play();
        }
    }
}
