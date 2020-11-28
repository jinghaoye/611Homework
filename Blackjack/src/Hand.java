import java.util.ArrayList;
public class Hand {
    private ArrayList<Card> hand;
    private int bet = 0;
    private Boolean stand;
    private Boolean doubleUp;
    private Boolean blackJack;
    private Boolean trinataEna;
    private Boolean bust;
    private Boolean qualifiedForAct;
    private int TrinataValue;



    public Hand(  ){
        hand = new ArrayList<>();
        qualifiedForAct = true;
        doubleUp = false;
        stand = false;
        blackJack = false;
        trinataEna = false;
        bust = false;
    }


    public void addCard( Card card ){
        hand.add(card);
    }

    public int getHandValue(){
        int value = 0;
        int numOfAce = 0;
        for(Card c : hand){
            if(c.getValue()==1){
                numOfAce ++;
                value += 11;
            }
            else if (c.getValue()>11){
                value +=10;
            }
            else {
                value += c.getValue();
            }
        }
        while (value > 21 && numOfAce > 0){
            value -= 10;
            numOfAce -= 1;
        }
        if(value > 21){

            bust = true;
        }
        return value;
    }
    /** overload this method for Trianta Ena **/
    public int getHandValue( int bustnumber ){
        int value = 0;
        int numOfAce = 0;
        for(Card c : hand){
            if(c.getValue()==1){
                numOfAce ++;
                value += 11;
            }
            else if (c.getValue()>10){
                value +=10;
            }
            else {
                value += c.getValue();
            }
        }
        if (value > bustnumber && numOfAce > 0){
            value -= 10;
        }
        if(value > bustnumber){
            bust = true;
        }
        setTrinataValue(value);
        return value;
    }

    public Hand splitHand(){
        Hand split = new Hand();
        split.addCard(hand.remove(0));
        return split;
    }

    public Boolean isBlackJack(){
        if(hand.size()>2){
            return false;
        }
        else {
            if(getHandValue() == 21){
                blackJack = true;
            }
        }
        return blackJack;
    }

    public Boolean isTrinataEna(){
        if(hand.size()>3){
            return false;
        }
        else {
            if(getHandValue(31) == 31){
                trinataEna = true;
            }
        }
        return trinataEna;
    }

    public Boolean isStand() {
        return stand;
    }

    public void setStand(Boolean stand) {
        this.stand = stand;
    }

    public Boolean isDoubleUp() {
        return doubleUp;
    }

    public void setDoubleUp(Boolean doubleUp) {
        this.doubleUp = doubleUp;
    }

    public String toString(){
        String str = "[ ";
        for(int i = 0 ; i <hand.size() ; i++){
            if(i == hand.size()-1){
                str += hand.get(i)+" ]";
            }
            else {
                str += hand.get(i) + ", ";
            }
        }
        return str;
    }

    public Boolean handStatus() {
        if(stand || doubleUp || isBlackJack() || getHandValue()>=21){
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean handStatus(String T) { /** overload method for Trinata **/
        if( getHandValue(31) >=31 || stand || isTrinataEna()){
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean isBust() {
        return bust;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public int getTrinataValue() {
        return TrinataValue;
    }

    public void setTrinataValue(int trinataValue) {
        TrinataValue = trinataValue;
    }
}
