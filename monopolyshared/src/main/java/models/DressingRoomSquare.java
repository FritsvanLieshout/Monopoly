package models;

public class DressingRoomSquare extends Square {
    public DressingRoomSquare(String name) {
        super(name);
    }

    @Override
    public void action(User user, Board board) {
        //Log -> user gaat uit de jail en betaald 500 om er uit te komen/ of hij probeert dubbele ogen te gooien (na 3x 500 betalen)
        //If de user is 'injured' of een rode kaart heeft.
        user.getWallet().subtractMoney(500);
    }
}
