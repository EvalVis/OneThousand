# One Thousand card game

## Note
This game is a variant of One Thousand game. Thus it could slightly vary from the traditional One Thousand game. Also the game could lack some of it's features. However, the main gameplay is working.

## Creating a server and logging in
One person needs to host a One Thousand server application and specify a **port**. The players need to open One Thousand application enter their nicknames, specify host IP and **port**. The player can also specify an email address, in that case, game pictures are saved as they play and after that are sent to the email address specified. That way a player can analize the game if he wants to. However, the email specified must be set up for SMTP protocol. To start playing 3 players are needed, but there are several rooms on the server application, so if there are 9 players, 3 rooms could be created and all nine players could play on 3 tables.

## Card values
* Ace - 11
* Ten - 10
* King - 4
* Queen - 3
* Jack - 2
* Nine - 0

## Mariage values
* Hearts' king and queen - 100
* Diamonds' king and queen - 80
* Clubs' king and queen - 60
* Spades' king and queen - 40

## Rules and gameplay
At the start of the game, 7 cards are handed to each player and 3 cards and placed on the table upside down. The players will be betting to receive these 3 unknown cards. There is a total of 24 cards (from 9 to Ace). Players cards are also placed upside down, so they can play blindly (in this game it's called "black").
The players can reveal their cards but when they would exit blind mode. Players who play blind have double awards if they win. However, consequences also are increased by double in case of losing. When cards are in place, a random player is selected to start a call. It must call for 100. It can also reveal his card if he does not want to play blindly. The other players going clockwise can raise or fold (in this version you can only raise the amount by 10). They can also reveal their cards to stop playing blindly. When only one player has not folded, it wins the 3 cards. These cards must be shown to the other players (however, if the call phase winner chose to play blindly until the end of the call phase the cards are not shown to others). The player can now look at all 10 cards. He must discard 3. Those cards will participate in the score calculations when all cards are played. The player can when increase it's final bet.
The player who won the bet plays a card first. Other players, going clockwise must play the card of the same type (if they don't have one, they can play any card).
When 3 cards are played, the winner is determined by these rules:
* If there is one trump card it wins, if there are two or three, card values determine the winner.
* If there are two or three cards of first's cards type, the highest value card from this type wins.
* Otherwise, the player who played the card first (who won the last 3 card battle), wins.
The player who wins the 3 card battle gets to play first on the upcoming 3 card battle. Also the winner takes his and the losers cards which were played - they will add up to his score. The first player can set trump type or replace the old one if he has marriage suited pairs. If a king or queen was played earlier without playing first, marriage does not happen and points are not awarded as well as the trump is lost.
When all 21 (7 \* 3) cards are played the score is calculated. All taken cards are summed by their values and marriage values are also applied. If the player failed to fulfill his bet, he loses points: if he bet 100 he will lose 100 (unless the player bet playing blindly then it's 200).
The game goes up to 1000 points.