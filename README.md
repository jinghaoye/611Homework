# BlackJack and Eritana

For this game , There are 11 classes and 2 of them are abstract class. 

Game: This is a abstract class to represent Games 

CardGame: since the game type of BlackJack and Trinata Ena are card game, this class is a sub abstract class of the Game class. 

TrinataEna: this class is the concrete subclass of CardGame and defined the logic of game TrinataEna, and initial the necessary components of the game. 

BlackJack: this class is the subclass of CardGame and defined the logic of game blackJack, and initial the necessary components of the game 

Card: This class represent the card component. Each card will have two attributes, the value and the type of card 

CardDeck : This class represent the card deck. Each card deck consist of 52 cards. The class has a method to produce a normal card deck which contains exactly 52 cards, and another method to provide the user-defined number of decks. 

Dealer: This class represents the dealer which is also the banker in Trinata Ena . This class defined all the methods and attributes of the dealer. 

Hand: This class represent the hand component of players. When dealer deal card to players, the instance of hand will store the card elements. This class provide the methods to compute the total value of a hand of the player, and will also record the status of the hand 

Player: This class represent the player components. The class defined the necessary attributes of a player, including name, money, hand and bet. This class also defined the methods of a player for playing both games. 

GameManager: This class is kind of like a starting page for players. Since they are two card games, players can choose which game they want to play. Then the game chosen will be called. 

Main: a main class containing the main function to start the program
