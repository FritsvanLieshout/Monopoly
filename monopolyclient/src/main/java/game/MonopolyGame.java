package game;

import models.Log;
import models.Square;
import models.User;

public class MonopolyGame implements IMonopolyGame {

    User user = new User(1, "Jan");

    public MonopolyGame() { }

    public void moveUser(int playerNr, int dice1, int dice2) {
        int currentPlace = user.getCurrentPlace();
        int newPlace = currentPlace + (dice1 + dice2);
        user.setPlace(newPlace);

        Log.print("Do something");
    }
}
