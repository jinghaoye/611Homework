public class Player {
    private String name;
    private int money;
    private Hand hand;
    private Hand splitHand;
    private int bet;
    private Boolean isSplited;
    private Boolean folds;

    public Player(){ }

    public Player(String name , int money){
        this.name = name;
        this.money = money;
        this.isSplited = false;
        this.folds = false;
    }

    public void bet( int amount ){
        this.bet = amount;
        money -= bet;

    }

    public void handInitial(Card c1, Card c2){
        hand = new Hand( );
        hand.addCard(c1);
        hand.addCard(c2);
        hand.setBet(bet);
    }

    public void hit( Hand hand, Dealer dealer ){
        hand.addCard( dealer.dealCard() );
    }

    public void stand( Hand hand ){
        hand.setStand(true);
    }

    public Boolean getFolds() {
        return folds;
    }

    public void setFolds(Boolean folds) {
        this.folds = folds;
    }

    public void doubleUp(Hand hand, Dealer dealer ){
        hand.addCard( dealer.dealCard() );
        money -= hand.getBet();
        hand.setBet(hand.getBet()*2);
        hand.setDoubleUp( true );
    }

    public void split( Dealer dealer ){
        splitHand = hand.splitHand();
        splitHand.addCard( dealer.dealCard() );
        splitHand.setBet(hand.getBet());
        money -= hand.getBet();
        hand.addCard( dealer.dealCard() );
        this.isSplited = true;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Hand getSplitHand() {
        return splitHand;
    }

    public String getName() {
        return name;
    }

    public Boolean getSplited() {
        return isSplited;
    }

    public void setSplited(Boolean splited) {
        isSplited = splited;
    }

    public void setSplitHand(Hand splitHand) {
        this.splitHand = splitHand;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setName(String name) {
        this.name = name;
    }
}
