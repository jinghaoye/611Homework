public class Card {
    private int value;
    private int type; // 1=Spades 2=Hearts 3=clubs 4=diamonds

    public Card(){}

    public Card(int value, int type){ //When a card is initiated, we assign a value and a type to this card
        this.value = value;
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override // we override the tosting function so that we can directly get the information of the card
    public String toString() {
        String type_name=" ";
        switch (type){
            case 1:
                type_name = "Spades";
                break;
            case 2:
                type_name ="Heart";
                break;
            case 3:
                type_name ="Clubs";
                break;
            case 4:
                type_name ="diamonds";
                break;
        }
        String card_str="";
        switch (value){
            case 1:
                return card_str="Ace of "+type_name;
            case 11:
                return card_str="J of "+type_name;
            case 12:
                return card_str="Q of "+type_name;
            case 13:
                return card_str="K of "+type_name;
        }

        card_str =value + " of "+type_name;

        return card_str;
    }
}
