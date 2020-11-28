import java.util.ArrayList;

public class Dealer {
    private String name;
    private Hand hand;
    private int dealerProfit = 0;
    private ArrayList<Card> deckOfCards;
    private boolean blackJack;
    private boolean triantaEna;
    private int bank;

    public Dealer(){
        deckOfCards = new CardDeck().getShuffledDeck();
    }
    public Dealer(String name){
        this.name = name;
        deckOfCards = new CardDeck().getShuffledDeck();
        blackJack = false;
    }
    public Dealer(String name, int money){ /** constructor for Trinata Ena **/
        this.name = name;
        triantaEna = false;
        bank = money;
    }


    public Card dealCard(){
        return deckOfCards.remove(0);
    }

    public void addCard(){
        hand.addCard( dealCard() );
    }

    public void handinitial(){
        hand = new Hand( );
        hand.addCard( dealCard() );
        hand.addCard( dealCard() );
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public void reveal(){
        while (hand.getHandValue()<17){
            addCard();
        }
        System.out.println("dealer's hand "+hand);
        System.out.println("The dealer's hand value is "+hand.getHandValue());
    }

    public void reveal(String T){  /** overload method for game TrinataEna **/
        while (hand.getHandValue(31)<27){
            addCard();
        }
        System.out.println("banker's hand "+hand);
        System.out.println("The banker's hand value is "+hand.getHandValue(31));
    }

    public int getDealerProfit() {
        return dealerProfit;
    }

    public void setDealerProfit(int dealerProfit) {
        this.dealerProfit = dealerProfit;
    }

    public void setDeckOfCards(ArrayList<Card> deckOfCards) {
        this.deckOfCards = deckOfCards;
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
