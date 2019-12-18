package logic;

import logic_interface.IGameLogic;
import models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameLogic implements IGameLogic {

    private static final Logger LOG = LoggerFactory.getLogger(GameLogic.class);

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
            LOG.debug(user.getUsername() + " has dice " + dice + " and goes to " + board.getSquares()[user.getCurrentPlace()].getSquareName());
            if (checkIfUserIsOverStart(user)) { }
            if (checkIfSquareIsOwned(user, board)) { }
        }
        return board.getSquares()[newPlace];
    }

    @Override
    public void redCard(User user) {
        user.setPlace(10); //Need the coordinates of the dressing room.
        user.setInDressingRoom(true);
        LOG.debug(user.getUsername() + " receives a RED Card! and has to go to the dressing room!");
    }

    @Override
    public void buyFootballPlayer(User user, Board board) {
        for (Square s : board.getSquares()) {
            if (s.getOwner() < 0) {
                if (s.getSquareId() == user.getCurrentPlace()) {
                    user.getWallet().withDrawMoneyOfWallet(s.getPrice());
                    s.setOwner(user.getUserId());
                    LOG.debug(s.getSquareName() + " has been added to " + user.getUsername() + "'s club!");
                    LOG.debug(user.getUsername() + "'s wallet has been updated -> €" + user.getWallet().getMoney());
                    break;
                }
            }
        }
    }

    private void payRent(User user, Board board, Square s) {
        user.getWallet().withDrawMoneyOfWallet(s.getRentPrice());
        board.getUser(s.getOwner()).getWallet().addMoneyToWallet(s.getRentPrice());
        LOG.debug(user.getUsername() + " has to pay rent to " + board.getUser(s.getOwner()));
        LOG.debug(user.getUsername() + "'s wallet has been updated -> €" + user.getWallet().getMoney());
    }

    private boolean checkIfUserIsOverStart(User user) {
        //TODO -> the current place is always higher than 0. Need a check like if the user is again over squareId 0 maybe like roundCount or something like that
        if (user.getCurrentPlace() >= 0) {
            user.getWallet().addMoneyToWallet(2000);
            LOG.debug(user.getUsername() + " is over start, €2000 added to " + user.getUsername() + "'s wallet");
            LOG.debug(user.getUsername() + "'s wallet has been updated -> €" + user.getWallet().getMoney());
            return true;
        }
        return false;
    }

    private boolean checkIfSquareIsOwned(User user, Board board) {
        for (Square s : board.getSquares()) {
            if (s.getSquareId() == user.getCurrentPlace()) {
                if (s.getOwner() < 0) {
                    LOG.debug(s.getSquareName() + " is not owned by another user!");
                    return true;
                }

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
            LOG.debug(user.getUsername() + " stays at " + board.getSquares()[user.getCurrentPlace()].getSquareName() + " and need to pay €500");
            return true;
        }
        return false;
    }
}
