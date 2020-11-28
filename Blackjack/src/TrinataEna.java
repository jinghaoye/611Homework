import java.util.ArrayList;
import java.util.Scanner;

public class TrinataEna extends CardGame{
    private Dealer banker;
    private ArrayList<Player> players;
    private int numOfPlayer;
    private ArrayList<Card> cardsDeck;

    public TrinataEna() {
        cardsDeck = new CardDeck().getCardDecks(2);

        System.out.println("Enter the number of players. Max number: 8 and Min: number 2");
        numOfPlayer = numberValidation(1,9);
        players = new ArrayList<>();
        System.out.println("Enter name for the banker");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String name = String.valueOf(input);
        banker = new Dealer( name , 300 );
        banker.setDeckOfCards(cardsDeck);

        for(int i = 0; i<numOfPlayer-1; i++){
            System.out.println("Enter name for player "+ (i+2));
            input = sc.nextLine();
            name = String.valueOf(input);
            players.add( new Player(name,100) );
        }
    }

    public void initialHand(){
        Hand bankerHand = new Hand();
        bankerHand.addCard(banker.dealCard());
        banker.setHand(bankerHand);
        for(Player p : players){
            p.setFolds(false);
            Hand h = new Hand();
            h.addCard( banker.dealCard());
            p.setHand( h );
        }
    }

    public void runRound(){
        initialHand();
        actionAfterFirstDealingCard();
        do{
            askForAction();
        }while (!allActionsDone());
        banker.reveal("T");
        checkWinCondition();

        System.out.println("Banker "+banker.getName()+"'s hand "+banker.getBank());
        for(Player p : players) {
            System.out.println("Player " + p.getName() + "'s money " + p.getMoney());
        }

        bankerRotation();

    }

    public void askForAction(){
        for(Player p : players){
            if(p.getHand().handStatus("T")){
                continue;
            }
            System.out.println(" ");
            System.out.println("Player " + p.getName() + ", Which action do you want to take" + "\n"
                    + "1.Hit 2.Stand");
            int choice = numberValidation(0,3);
            if(choice == 1){
                p.hit(p.getHand(),banker);
                System.out.println("Player " + p.getName() + "'s hand " + p.getHand() + " the hand value is "+p.getHand().getHandValue(31)+"\n");
            }
            else {
                p.stand(p.getHand());
            }
        }
    }

    private boolean allActionsDone() {
        boolean flag = true;
        for(Player p : players){
            if(qualifiedForAction(p)){
                flag = false;
                break;
            }
        }
        return flag;
    }

    private boolean qualifiedForAction(Player p) {
        if(p.getHand().handStatus("T")){
            return false;
        }
        else {
            return true;
        }
    }

    public void actionAfterFirstDealingCard(){
        System.out.println("Banker "+banker.getName()+"'s hand "+banker.getHand());
        for(Player p : players){
            System.out.println("Player "+p.getName()+"'s hand "+ p.getHand());
            System.out.println("1. bet 2. fold without beting");
            int choice = numberValidation(0,3);
            if(choice == 1){
                System.out.println("Enter the amount you want to bet");
                int amount = numberValidation(0,p.getMoney()+1);
                p.bet(amount);
                p.getHand().setBet(amount);
                p.hit(p.getHand(),banker);
                p.hit(p.getHand(),banker);
                System.out.println("Player " + p.getName() + "'s hand " + p.getHand()+ " the hand value is "+p.getHand().getHandValue(31));
            }
            else {
                p.setFolds(true);

                p.stand(p.getHand());
                System.out.println("Player " + p.getName() + " folds " );
            }
        }

    }

    public void bankerRotation() {
        int bank = banker.getBank();
        for (Player p : players) {

            if (p.getMoney() > bank) {
                System.out.println("Player "+p.getName()+" do you want to be the banker  1.Yes  2.No");
                int choice = numberValidation(0, 3);
                if (choice == 1) {
                    String tmpName = p.getName();
                    int tmpbank = p.getMoney();
                    p.setName(banker.getName());
                    p.setMoney(bank);
                    banker.setName(tmpName);
                    banker.setBank(tmpbank);
                    System.out.println("Player "+tmpName+" becomes the banker");
                    return;
                } else {
                    continue;
                }
            }
        }

    }

    @Override
    public void play() {

        do{
            runRound();

        }while (continuePlay());


    }

    private boolean continuePlay() {
        for(Player p : players){
            if(p.getMoney()<=0){
                System.out.println("One player has lost all money and the game ends");
                return false;
            }
        }
        System.out.println("Do you want to continue to play the game"+"\n"+"1.Yes 2.No");
        int choice = numberValidation(0,3);
        if(choice == 1){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void checkWinCondition() {
        if(banker.getHand().isBust()){
            for(Player p:players){
                if(p.getFolds()){
                    continue;
                }
                if( p.getHand().isBust() ){
                    System.out.println("Player "+p.getName()+" is bust ");
                    banker.setBank(banker.getBank()+p.getHand().getBet());
                }
                else {
                    System.out.println("Player "+p.getName()+" wins and "+"player "+p.getName()+"'s hand value is "+p.getHand().getTrinataValue());
                    p.setMoney(p.getMoney() + 2*p.getHand().getBet());
                    banker.setBank(banker.getBank() - p.getHand().getBet());
                }
            }
        }
        else if(banker.getHand().isTrinataEna()){
            for(Player p:players){
                if(p.getFolds()){
                    continue;
                }
                if(p.getHand().isTrinataEna()){
                    System.out.println("Player "+p.getName()+" ties with banker"+"and player "+p.getName()+"'s hand value is "+p.getHand().getTrinataValue());
                    p.setMoney(p.getMoney() + p.getHand().getBet());
                }
                else {
                    System.out.println("Player "+p.getName()+" loses"+" adn player "+p.getName()+"'s hand value is "+p.getHand().getTrinataValue());
                    banker.setBank(banker.getBank()+p.getHand().getBet());
                }
            }
        }
        else {
            for(Player p: players){
                if(p.getFolds()){
                    continue;
                }
                if(p.getHand().isBust()){
                    System.out.println("Player "+p.getName()+" is bust ");
                    banker.setBank(banker.getBank()+p.getHand().getBet());
                    continue;
                }
                if(p.getHand().isTrinataEna()){
                    System.out.println("Player "+p.getName()+" wins "+"and player "+p.getName()+"'s hand value is "+p.getHand().getTrinataValue());
                    p.setMoney(p.getMoney() + 2*p.getHand().getBet());
                    banker.setBank(banker.getBank() - p.getHand().getBet());
                }
                else if(p.getHand().getTrinataValue() > banker.getHand().getTrinataValue()){
                    System.out.println("Player "+p.getName()+" wins "+"and player "+p.getName()+"'s hand value is "+p.getHand().getTrinataValue());
                    p.setMoney(p.getMoney() + 2*p.getHand().getBet());
                    banker.setBank(banker.getBank() - p.getHand().getBet());
                }
                else if(p.getHand().getTrinataValue() < banker.getHand().getTrinataValue()){
                    System.out.println("Player "+p.getName()+" loses "+"and player "+p.getName()+"'s hand value is "+p.getHand().getTrinataValue());
                    banker.setBank(banker.getBank()+p.getHand().getBet());
                }
                else {
                    System.out.println("Player "+p.getName()+" ties with the banker"+" and player "+p.getName()+"'s hand value is "+p.getHand().getTrinataValue());
                    p.setMoney(p.getMoney() + p.getHand().getBet());
                }
            }
        }

    }
}
