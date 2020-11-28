import java.util.ArrayList;
import java.util.Scanner;
import java.util.Scanner;

public class BlackJack extends CardGame {

    private Dealer dealer;
    private ArrayList<Player> players;
    private int numOfPlayer;

    public BlackJack(){
        System.out.println("Enter the number of players. Max number: 7 and Min: number 1");
        numOfPlayer = numberValidation(0,7);
        players = new ArrayList<>();
        if(numOfPlayer > 1){
            System.out.println("1.Players be the dealer  2.Computer be the dealer");
            int dealerChoice = numberValidation(0,3);
            if( dealerChoice == 2){
                for(int i = 0; i<numOfPlayer; i++){
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Enter name for player "+ (i+1));
                    String input = sc.nextLine();
                    String name = String.valueOf(input);
                    players.add( new Player(name,100) );
                }
                dealer = new Dealer();

            }else {
                System.out.println("Enter name for the dealer");
                Scanner sc = new Scanner(System.in);
                String input = sc.nextLine();
                String name = String.valueOf(input);
                dealer = new Dealer( name );
                for(int i = 0; i<numOfPlayer-1; i++){
                    System.out.println("Enter name for player "+ (i+2));
                    input = sc.nextLine();
                    name = String.valueOf(input);
                    players.add( new Player(name,100) );
                }
            }
        }
        else {
            for(int i = 0; i<numOfPlayer; i++){
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter name for player "+ (i+1));
                String input = sc.nextLine();
                String name = String.valueOf(input);
                players.add( new Player(name,100) );
            }
            dealer = new Dealer();
        }
    }

    public void firstDealing(){
        for(Player p : players){
            System.out.println("Player "+ p.getName() +", please enter the amount of money you want to bet");
            int amount = numberValidation(0,p.getMoney()+1);
            p.bet(amount);
            Card c1 = dealer.dealCard();
            Card c2 = dealer.dealCard();
            p.handInitial(c1,c2);
        }
        dealer.handinitial();
    }




    public void takingAction(Player p) {
        if( p.getSplited() ){
            if( !p.getHand().handStatus() && !p.getSplitHand().handStatus()){
                askForPlayerAction(p,p.getHand(),"first hand ");
                askForPlayerAction(p, p.getSplitHand(), "split hand ");
            }
            else if(!p.getHand().handStatus()){
                askForPlayerAction(p,p.getHand(),"first hand ");
            }
            else {
                askForPlayerAction(p, p.getSplitHand(), "split hand ");
            }
        }
        else {
            if(!p.getHand().handStatus()){
                askForPlayerAction(p,p.getHand(),"hand ");
            }
        }

    }

    public void askForPlayerAction(Player p, Hand hand , String handtype){
        boolean validAction = false;

        do{
            System.out.println("Player " + p.getName() + ", Which action do you want to take for " + handtype + "\n"
                    + "1.Hit 2.Stand 3.Split 4.Double Up" +
                    "\n" + "Please type the number of choice.(1-4)");
            int choice = numberValidation(0, 5);
            switch (choice) {
                case 1:
                    System.out.println("hit");
                    p.hit( hand, dealer);
                    validAction = true;
                    break;
                case 2:
                    System.out.println("stand");
                    p.stand( hand );
                    validAction = true;
                    break;
                case 3:
                    if(p.getSplited()){
                        System.out.println("Cannot split twice in a round");
                    }else if(splitValidation(p)) {
                        System.out.println("Split");
                        p.split( dealer );
                        validAction = true;
                    }
                    else {
                        System.out.println("You must have exact two cards with same value in hand for split");
                    }
                    break;
                case 4:
                    System.out.println("Double up");
                    p.doubleUp( hand, dealer);
                    validAction = true;
                    break;
            }

        }while (!validAction);

    }

    private boolean splitValidation(Player p) {
        Hand hand = p.getHand();
        if(hand.getHand().size()==2 && hand.getHand().get(0)==hand.getHand().get(1)){
            return true;
        }
        else {
            return false;
        }
    }

    public int compareHandValue(Hand playerHand , Hand dealerHand){
        if(playerHand.getHandValue() > dealerHand.getHandValue()){
            return 1;
        }
        else if(playerHand.getHandValue() < dealerHand.getHandValue()){
            return -1;
        }
        else {
            return 0;
        }
    }

    public void checkWinCondition() {

        if (bustStatus()) {
            System.out.println("dealer wins, because all players are bust");
            return;
        } else {
            dealer.reveal();
            if (dealer.getHand().getHandValue() > 21) {
                System.out.println("dealer is bust");
                for(Player p : players){
                    if (p.getSplited()) {
                        if (!p.getSplitHand().isBust()) {
                            System.out.println("Player " + p.getName()+" 's split hand wins");
                            p.setMoney(p.getMoney()+ 2*p.getSplitHand().getBet());
                            dealer.setDealerProfit( dealer.getDealerProfit() - p.getSplitHand().getBet() );
                        }
                    }
                    if(!p.getHand().isBust()){
                        System.out.println("Player " + p.getName()+" 's hand wins");
                        p.setMoney(p.getMoney()+ 2*p.getHand().getBet());
                        dealer.setDealerProfit( dealer.getDealerProfit() - p.getHand().getBet() );
                    }
                }
                return;
            } else if (dealer.getHand().isBlackJack()) {
                for (Player p : players) {
                    if (p.getSplited()) {
                        if (p.getSplitHand().isBlackJack()) {
                            System.out.println("Player " + p.getName() + " 's split hand ties with dealer");
                            p.setMoney(p.getMoney() + p.getSplitHand().getBet());
                        } else {
                            System.out.println("Player " + p.getName() + " 's split hand loses");
                            dealer.setDealerProfit( dealer.getDealerProfit()+p.getSplitHand().getBet() );
                        }
                    }
                    if (p.getHand().isBlackJack()) {
                        System.out.println("Player " + p.getName() + " ties with dealer");
                    } else {
                        System.out.println("Player " + p.getName() + " loses");
                        dealer.setDealerProfit( dealer.getDealerProfit()+p.getHand().getBet() );
                    }
                }
            }
            else{
                for (Player p : players) {
                    if(p.getSplited()){
                        if(p.getSplitHand().isBlackJack()){
                            System.out.println("Player " + p.getName()+" 's split hand wins");
                            p.setMoney(p.getMoney()+ 2*p.getSplitHand().getBet());
                            dealer.setDealerProfit( dealer.getDealerProfit() - p.getSplitHand().getBet() );
                        }else if(p.getSplitHand().isBust()){
                            continue;
                        }
                        else{
                            int result = compareHandValue(p.getSplitHand(), dealer.getHand());
                            if(result == 1){
                                System.out.println("Player " + p.getName()+" 's split hand wins");
                                p.setMoney(p.getMoney()+ 2*p.getSplitHand().getBet());
                                dealer.setDealerProfit( dealer.getDealerProfit() - p.getSplitHand().getBet() );
                            }else if (result == 0 ){
                                System.out.println("Player " + p.getName()+" 's split hand ties with dealer");
                                p.setMoney(p.getMoney() + p.getSplitHand().getBet());
                            }else {
                                System.out.println("Player " + p.getName()+" 's split hand loses");
                                dealer.setDealerProfit( dealer.getDealerProfit() + p.getSplitHand().getBet() );
                            }
                        }
                    }
                    if(p.getHand().isBlackJack()){
                        System.out.println("Player " + p.getName()+" 's hand wins");
                        p.setMoney(p.getMoney()+ 2*p.getHand().getBet());
                        dealer.setDealerProfit( dealer.getDealerProfit() - p.getHand().getBet() );
                    }else if(p.getHand().isBust()){
                        continue;
                    } else {
                        int result = compareHandValue(p.getHand(), dealer.getHand());
                        if(result == 1){
                            System.out.println("Player " + p.getName()+" 's hand wins");
                            p.setMoney(p.getMoney()+ 2*p.getHand().getBet());
                            dealer.setDealerProfit( dealer.getDealerProfit() - p.getHand().getBet() );
                        }else if (result == 0 ){
                            System.out.println("Player " + p.getName()+" 's hand ties with dealer");
                            p.setMoney(p.getMoney() + p.getHand().getBet());
                        }else {
                            System.out.println("Player " + p.getName()+" 's hand loses");
                            dealer.setDealerProfit( dealer.getDealerProfit() + p.getHand().getBet() );
                        }
                    }
                }
            }
        }
    }


    public boolean bustStatus() {
        boolean allbust = true;
        for (Player p : players) {
            if (p.getSplited()) {
                if (!p.getHand().isBust() || !p.getSplitHand().isBust()) {
                    if (p.getHand().isBust()) {
                        System.out.println("Player " + p.getName() + "'s hand is bust");
                        dealer.setDealerProfit( dealer.getDealerProfit()+p.getHand().getBet() );
                    }
                    if (p.getSplitHand().isBust()){
                        System.out.println("Player " + p.getName() + "'s split hand is bust");
                        dealer.setDealerProfit( dealer.getDealerProfit()+p.getSplitHand().getBet() );
                    }
                    allbust = false;
                }
            }
            if (!p.getHand().isBust()) {
                allbust = false;
            } else {
                System.out.println("Player " + p.getName() + "'s hand is bust");
                dealer.setDealerProfit( dealer.getDealerProfit()+p.getHand().getBet() );
            }
        }

        return allbust;
    }


    public Boolean qualifiedForAction( Player p ){
        if(!p.getSplited()){
            if(p.getHand().handStatus()){
                return false;
            }
            else {
                return true;
            }
        }
        else {
            if(p.getHand().handStatus() && p.getSplitHand().handStatus()){
                return false;
            }
            else {
                return true;
            }
        }
    }

    public void runRound(){

        firstDealing();
        System.out.println("Dealer hand [ "+ dealer.getHand().getHand().get(0)+" ,  ?  ]" );
        for(Player p : players){
            System.out.println("Player "+p.getName()+"'s money is "+p.getMoney());
            System.out.println("Player "+p.getName()+"'s hand "+p.getHand());
            System.out.println("Hand value is "+p.getHand().getHandValue());
        }
        while (true){
            for(Player p : players){
                if( !qualifiedForAction(p) ){
                    System.out.println("Player "+p.getName()+" cannot take action"+"\n");
                    continue;
                }
                else {
                    takingAction( p );
                    System.out.println("Player "+p.getName()+"'s hand "+p.getHand());
                    System.out.println("Hand value is "+p.getHand().getHandValue());
                    if(p.getSplited()){
                        System.out.println("Player "+p.getName()+"'s split hand "+p.getSplitHand());
                        System.out.println("Split hand value is "+p.getSplitHand().getHandValue());
                    }
                }
            }
            if(allActionsDone()){
                checkWinCondition();
                System.out.println(" ");
                System.out.println("The dealer's profit is "+dealer.getDealerProfit());
                for(Player p : players){
                    System.out.println("Player "+p.getName()+"'s money is "+p.getMoney());
                }
                System.out.println(" ");
                return;
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
}
