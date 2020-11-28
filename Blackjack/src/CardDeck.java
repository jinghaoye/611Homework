import java.util.ArrayList;
import java.util.Random;

public class CardDeck {

    private ArrayList<Card> cardDeck = new ArrayList<>(52);
    private ArrayList<Card> userDefined_CardDeck = new ArrayList<>();

    public CardDeck() {

    }

    public ArrayList<Card> getCardDeck() {
        return cardDeck;
    }

    public ArrayList<Card> getShuffledDeck(){
        for(int i = 1 ; i<=13; i++){
            for(int j = 1; j<=4; j++){
                Card cards = new Card(i,j);
                cardDeck.add(cards);
            }
        }
        ArrayList<Card> tempdeck = new ArrayList<>(52);
        Card tempcard;
        Random random = new Random();
        for(int i = 1;i<=52 ;i++){
            do{
                tempcard = cardDeck.get(random.nextInt(52));
            }while (tempdeck.contains(tempcard));
            tempdeck.add(tempcard);
        }
        cardDeck = tempdeck;
        return cardDeck;
    }

    public ArrayList<Card> getCardDecks(int deckNum){
        for(int k=0;k<deckNum;k++){
            for(int i = 1 ; i<=13; i++){
                for(int j = 1; j<=4; j++){
                    Card cards = new Card(i,j);
                    userDefined_CardDeck.add(cards);
                }
            }
        }


        ArrayList<Card> tempdeck = new ArrayList<>();
        Card tempcard;
        Random random = new Random();
        for(int i = 1;i<=userDefined_CardDeck.size() ;i++){
            do{
                tempcard = userDefined_CardDeck.get(random.nextInt(52*deckNum));
            }while (tempdeck.contains(tempcard));
            tempdeck.add(tempcard);
        }
        userDefined_CardDeck = tempdeck;
        return userDefined_CardDeck;
    }
}
