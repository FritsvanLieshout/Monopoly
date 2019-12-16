package logic;

import logic_interface.IGameLogic;
import models.*;

public class GameLogic implements IGameLogic {

    @Override
    public int getDice(Dice dice) {
        return dice.getNofDice();
    }

    @Override
    public Square moveUser(User user, Board board, int dice) {
        int newPlace = board.getPositionOnBoard(user.getCurrentPlace());
        if (checkIfUserIsInDressingRoom(user, board)) { }
        else {
            newPlace = board.getPositionOnBoard(user.getCurrentPlace() + dice);
            user.setPlace(newPlace);
            Log.print(user, user.getUsername() + " has dice " + dice + " and goes to " + board.getSquares()[user.getCurrentPlace()].getSquareName());
            if (checkIfUserIsOverStart(user)) { }
            if (checkIfSquareIsOwned(user, board)) { }
        }
        return board.getSquares()[newPlace];
    }

    public boolean checkIfUserIsInDressingRoom(User user, Board board) {
        if (user.isInDressingRoom()) {
            user.getWallet().withDrawMoneyOfWallet(500);
            user.setInDressingRoom(false);
            Log.print(user, user.getUsername() + " stays at " + board.getSquares()[user.getCurrentPlace()].getSquareName() + " and need to pay €500");
            return true;
        }
        return false;
    }

    public void startBonus(User user) {
        user.getWallet().addMoneyToWallet(2000);
        Log.print(user,user.getUsername() + " is over start, 2000 added to " + user.getUsername() + " wallet");
    }

    @Override
    public void redCard(User user) {
        user.setPlace(10); //Need the coordinates of the dressing room.
        user.setInDressingRoom(true);
        Log.print(user, user.getUsername() + " receives a RED card! and has to go to the dressing room");
    }

    @Override
    public void buyFootballPlayer(User user, Board board) {
        for (Square s : board.getSquares()) {
            if (s.getSquareId() == user.getCurrentPlace()) {
                user.getWallet().withDrawMoneyOfWallet(s.getPrice());
                s.setOwner(user.getUserId());
                Log.print(user, s.getSquareName() + " has been added to your club!");
            }
        }
    }

    public boolean checkIfUserIsOverStart(User user) {
        //TODO -> the current place is always higher than 0. Need a check like if the user is again over squareId 0
        if (user.getCurrentPlace() >= 0) {
            user.getWallet().addMoneyToWallet(1000);
            return true;
        }
        return false;
    }

    public boolean checkIfSquareIsOwned(User user, Board board) {
        for (Square s : board.getSquares()) {
            if (s.getSquareId() == user.getCurrentPlace()) {
                if (s.getOwner() != user.getUserId()) {
                    user.getWallet().withDrawMoneyOfWallet(s.getRentPrice());
                    board.getUser(s.getOwner()).getWallet().addMoneyToWallet(s.getRentPrice());
                    return true;
                }
            }
        }
        return false;
    }
}
