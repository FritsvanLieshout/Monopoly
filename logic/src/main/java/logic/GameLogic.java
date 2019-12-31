package logic;

import logic_interface.IGameLogic;
import models.*;
import server_interface.IServerMessageGenerator;

import java.util.ArrayList;

public class GameLogic implements IGameLogic {

    private ArrayList<User> users = new ArrayList<>();
    private IServerMessageGenerator messageGenerator;

    public GameLogic(IServerMessageGenerator messageGenerator) { this.messageGenerator = messageGenerator; }

    public GameLogic() { }

    @Override
    public int getDice(Dice dice) {
        return dice.getNofDice();
    }

    @Override
    public Square moveUser(User user, Board board, int dice) {
        int newPlace = board.getPositionOnBoard(user.getCurrentPlace());
        if (checkIfUserIsInDressingRoom(user, board)) { }
        else {
            if (checkIfUserIsOverStart(user, dice)) { }
            newPlace = board.getPositionOnBoard(user.getCurrentPlace() + dice);
            user.setPlace(newPlace);
            System.out.println(user.getUsername() + " has dice " + dice + " and goes to " + board.getSquares()[user.getCurrentPlace()].getSquareName());
            if (checkIfSquareIsOwned(user, board)) { }
        }
        return board.getSquares()[newPlace];
    }

    @Override
    public void redCard(User user) {
        user.setPlace(10); //Need the coordinates of the dressing room.
        user.setInDressingRoom(true);
        System.out.println(user.getUsername() + " receives a RED Card! and has to go to the dressing room!");
    }

    @Override
    public void buyFootballPlayer(User user, Board board) {
        for (Square s : board.getSquares()) {
            if (s.getOwner() < 0) {
                if (s.getSquareId() == user.getCurrentPlace()) {
                    user.getWallet().withDrawMoneyOfWallet(s.getPrice());
                    s.setOwner(user.getUserId());
                    System.out.println(s.getSquareName() + " has been added to " + user.getUsername() + "'s club!");
                    System.out.println(user.getUsername() + "'s wallet has been updated -> €" + user.getWallet().getMoney());
                    break;
                }
            }
        }
    }

    @Override
    public void switchTurn(Board board, ArrayList<User> users) {
        int current = board.getCurrentTurn();
        if(++current >= users.size()){
            board.setCurrentTurn(1);
        }
    }

    @Override
    public void registerNewUser(String username, String password, String sessionId) {
        boolean success = false;
        //boolean success = restClient.register(username, password);

        if (success) {
            loginUser(username, password, sessionId);
        }
        else {
            messageGenerator.notifyRegisterResult(sessionId, true);
        }
    }

    public void loginUser(String username, String password, String sessionId) {
        if (users.size() < 2) {
            if (checkUserNameAlreadyExists(username)) {
                messageGenerator.notifyRegisterResult(sessionId, false);
                return;
            }

            //String token = restClient.login(username, password);
            //messageGenerator.notifyLoginResult(sessionId, token);
            //if (token != null && !token.equals("")) {
            //    User u = new User(Integer.parseInt(sessionId), username);
            //    users.add(u);
            //    messageGenerator.notifyAddUser(sessionId, username);
            //    checkStartingCondition();
            //}
        }
    }

    private void checkStartCondition() {
        if (users.size() == 4) {
            //startGame();
        }
    }

    private boolean checkUserNameAlreadyExists(String username)
    {
        for(User u : users) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }


    private void payRent(User user, Board board, Square s) {
        user.getWallet().withDrawMoneyOfWallet(s.getRentPrice());
        board.getUser(s.getOwner()).getWallet().addMoneyToWallet(s.getRentPrice());
        System.out.println(user.getUsername() + " has to pay rent to " + board.getUser(s.getOwner()));
        System.out.println(user.getUsername() + "'s wallet has been updated -> €" + user.getWallet().getMoney());
    }

    private boolean checkIfUserIsOverStart(User user, int dice) {
        if (user.getCurrentPlace() + dice >= 40) {
            user.getWallet().addMoneyToWallet(2000);
            System.out.println(user.getUsername() + " is over start, €2000 added to " + user.getUsername() + "'s wallet");
            System.out.println(user.getUsername() + "'s wallet has been updated -> €" + user.getWallet().getMoney());
            return true;
        }
        return false;
    }

    private boolean checkIfSquareIsOwned(User user, Board board) {
        for (Square s : board.getSquares()) {
            if (s.getSquareId() == user.getCurrentPlace()) {
                if (s.getOwner() < 0) {
                    System.out.println(s.getSquareName() + " is not owned by another user!");
                    return true;
                }
                //Call another method for a check of some squares that you can't buy (Start, Red Car,d etc).

                else if (s.getOwner() != user.getUserId()) {
                    payRent(user, board, s);
                    return false;
                }
            }
        }
        return false;
    }

    private boolean checkIfUserIsInDressingRoom(User user, Board board) {
        if (user.isInDressingRoom()) {
            user.getWallet().withDrawMoneyOfWallet(500);
            user.setInDressingRoom(false);
            System.out.println(user.getUsername() + " stays this round at " + board.getSquares()[user.getCurrentPlace()].getSquareName() + " and need to pay €500");
            return true;
        }
        return false;
    }


}
