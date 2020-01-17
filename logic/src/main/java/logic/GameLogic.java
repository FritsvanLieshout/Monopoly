package logic;

import logic_interface.IBoardLogic;
import logic_interface.IGameLogic;
import models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restshared.IMonopolyRestClient;
import server_interface.IServerMessageGenerator;

import java.security.SecureRandom;
import java.util.ArrayList;

public class GameLogic implements IGameLogic {

    private static final Logger log = LoggerFactory.getLogger(GameLogic.class);

    private ArrayList<User> onlineUsers = new ArrayList<>();

    private IServerMessageGenerator messageGenerator;
    private IMonopolyRestClient monopolyRestClient;

    private Board board;
    private IBoardLogic boardLogic;

    private int communityChestCardNr;
    private int changeCardNr;

    private SecureRandom random = new SecureRandom();

    private int goalBonus = 750;

    public GameLogic(IServerMessageGenerator messageGenerator, IMonopolyRestClient monopolyRestClient) {
        this.messageGenerator = messageGenerator;
        this.monopolyRestClient = monopolyRestClient;
        boardLogic = new BoardLogic();
        board = boardLogic.getBoard();
        communityChestCardNr = 1 + random.nextInt(7);
        changeCardNr = 1 + random.nextInt(7);
    }

    @Override
    public int getDice(Dice dice) {
        return dice.getNofDice();
    }

    public User getUser(String sessionId) {
        for (User user : onlineUsers) {
            if (user.getSessionId().equals(sessionId)) return user;
        }
        return null;
    }

    public User getUserByUserId(int userId) {
        for (User user : onlineUsers) {
            if (user.getUserId() == userId) return user;
        }
        return null;
    }

    @Override
    public Square moveUser(int dice, String sessionId) {
        User currentUser = getUser(sessionId);
        int newPlace = board.getPositionOnBoard(currentUser.getCurrentPlace());
        if (!checkIfUserIsInDressingRoom(currentUser)) {
            checkIfUserIsOverStart(currentUser, dice);
            newPlace = board.getPositionOnBoard(currentUser.getCurrentPlace() + dice);
            currentUser.setPlace(newPlace);
            if (newPlace == 2 || newPlace == 17 || newPlace == 33) doCommunityChestCardAction(currentUser);
            if (newPlace == 7 || newPlace == 22 || newPlace == 36) doChangeCardAction(currentUser);
            messageGenerator.updateCurrentUser(currentUser, currentUser.getSessionId());
            messageGenerator.notifyMoveUserMessage(dice, sessionId);
            checkIfSquareIsOwned(currentUser, board);
            checkRandomSquares(currentUser, newPlace);
            varChecksRedCard(currentUser);
            checkIfUserIsBroke(currentUser, board);
        }
        return board.getSquares()[newPlace];
    }

    @Override
    public void redCard(User user) {
        user.setPlace(10);
        user.setInDressingRoom(true);
        messageGenerator.notifyUserHasARedCard(user);
    }

    @Override
    public void buyFootballPlayer(String sessionId) {
        User currentUser = getUser(sessionId);
        for (Square s : board.getSquares()) {
            if (s.getSquareId() == currentUser.getCurrentPlace()) {
                if (s.getPrice() == 0) {
                    messageGenerator.notifyNonValueSquare(sessionId);
                } else {
                    if (s.getOwner() < 0) {
                        if (checkForEnoughMoney(currentUser, s.getPrice())) {
                            currentUser.getWallet().withDrawMoneyOfWallet(s.getPrice());
                            s.setOwner(currentUser.getUserId());
                            messageGenerator.updateCurrentUser(currentUser, currentUser.getSessionId());
                            messageGenerator.updateBoard(currentUser.getSessionId());
                        }
                    }
                    else messageGenerator.notifyPropertyIsAlreadyOwned(s.getOwner(), sessionId);
                  }
            }
        }
    }

    @Override
    public void switchTurn(int playerTurn, String sessionId) {
        if (++playerTurn > onlineUsers.size()) messageGenerator.notifySwitchTurn(1, sessionId);
        else messageGenerator.notifySwitchTurn(playerTurn, sessionId);
    }

    @Override
    public boolean register(String username, String password, String sessionId) {
        log.info("[Register a new user]");

        boolean checkUsername = monopolyRestClient.checkUsername(username);

        if (checkUsername) {
            User user = (User) monopolyRestClient.registerUser(username, password);
            user.setSessionId(sessionId);

            log.info("[User " + username + " added to monopoly game] \n");
            messageGenerator.notifyRegisterResult(sessionId, true);
            //login(username, password, sessionId);
            return true;
        }
        else {
            messageGenerator.notifyRegisterResult(sessionId, false);
            return false;
        }
    }

    @Override
    public boolean login(String username, String password, String sessionId) {
        if (onlineUsers.size() < 4) {
            if (checkUserNameAlreadyExists(username)) {
                messageGenerator.notifyRegisterResult(sessionId, false);
                return false;
            }

            User user = (User) monopolyRestClient.getUserByCredentials(username, password);

            if (user != null) {
                user.setSessionId(sessionId);
                messageGenerator.notifyLoginResult(sessionId, user.getUserId());
                onlineUsers.add(user);
                messageGenerator.notifyUserAdded(sessionId, username);
                updateUsersInGame();
                checkStartingCondition();
                return true;
            }
        }
        return false;
    }

    @Override
    public void processClientDisconnect(String sessionId) {
        for (User user : onlineUsers) {
            if (user.getSessionId().equals(sessionId)) onlineUsers.remove(user);
        }
    }


    @Override
    public void startGame() { messageGenerator.notifyStartGame(); }

    @Override
    public void updateUsersInGame() {
        for (User user : onlineUsers) {
            messageGenerator.updateUserList(onlineUsers, user.getSessionId());
        }
    }

    @Override
    public boolean checkStartingCondition() {
        if (onlineUsers.size() == 4) {
            startGame();
            return true;
        }
        return false;
    }

    @Override
    public boolean checkUserNameAlreadyExists(String username)
    {
        for(User u : onlineUsers) {
            if (u.getUsername().equals(username)) return true;
        }
        return false;
    }

    @Override
    public void payRent(User user, Board board) {
        for (Square s : board.getSquares()) {
            User currentUser = getUser(user.getSessionId());
            if (s.getSquareId() == currentUser.getCurrentPlace()) {
                currentUser.getWallet().withDrawMoneyOfWallet(s.getRentPrice());
                User ownedUser = getUserByUserId(s.getOwner());
                ownedUser.getWallet().addMoneyToWallet(s.getRentPrice());
                messageGenerator.notifyPayRent(currentUser, ownedUser);
                break;
            }
        }
    }

    @Override
    public boolean checkIfUserIsOverStart(User user, int dice) {
        if (user.getCurrentPlace() + dice >= 40) {
            user.getWallet().addMoneyToWallet(2000);
            messageGenerator.notifyUserOverStart(user.getSessionId());
            messageGenerator.updateCurrentUser(user, user.getSessionId());
            return true;
        }
        return false;
    }

    @Override
    public boolean checkIfSquareIsOwned(User user, Board board) {
        for (Square s : board.getSquares()) {
            if (s.getSquareId() == user.getCurrentPlace()) {
                if (s.getOwner() < 0) {
                    return true;
                }
                else if (s.getOwner() != user.getUserId()) {
                    payRent(user, board);
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkIfUserIsInDressingRoom(User user) {
        if (user.isInDressingRoom()) {
            user.getWallet().withDrawMoneyOfWallet(500);
            user.setInDressingRoom(false);
            messageGenerator.notifyUserIsInDressingRoom(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean varChecksRedCard(User user) {
        if (user.getCurrentPlace() == 30) {
            redCard(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean checkForEnoughMoney(User currentUser, int priceOfSquare) {
        if (currentUser.getWallet().getMoney() - priceOfSquare <= 0) {
            messageGenerator.notifyNotEnoughMoney(currentUser.getSessionId());
            return false;
        }
        return true;
    }

    @Override
    public boolean checkIfUserIsBroke(User user, Board board) {
        if (user.getWallet().getMoney() < 0) {
            messageGenerator.notifyUserIsBroke(user);
            user.setBroke(true);

            for (Square s : board.getSquares()) {
                if (s.getOwner() == user.getUserId()) {
                    s.setOwner(-1);
                }
            }
            messageGenerator.updateCurrentUser(user, user.getSessionId());
            return true;
        }
        return false;
    }

    @Override
    public void checkRandomSquares(User user, int newPlace) {
        String message = "";
        switch (newPlace) {
            case 4:
                message = "Pay income tax of €750";
                updateWalletOfUser(user, 750, false);
                break;
            case 20:
                message = "You have won a goal bonus of €" + goalBonus + ", because you scored against Ajax an important goal!";
                updateWalletOfUser(user, goalBonus, true);
                resetGoalBonus();
                break;
            case 38:
                message = "Pay additional tax of €375";
                updateWalletOfUser(user, 375, false);
                break;
        }

        messageGenerator.notifySquareMessage(user, message);
        getLogInformationOfUser(user, message);
    }

    @Override
    public void doCommunityChestCardAction(User user) {
        String message = "";
        switch (communityChestCardNr) {
            case 1:
                message = "Advance to Go -> Collect €2000";
                setPlaceOfUser(user, 0);
                updateWalletOfUser(user, 2000, true);
                break;
            case 2:
                message = "Bank error in your favor. Collect €1500";
                updateWalletOfUser(user, 1500, true);
                break;
            case 3:
                message = "Pay school fees of €2100";
                updateWalletOfUser(user, 2100, false);
                break;
            case 4:
                message = "Receive €250 consultancy fee";
                updateWalletOfUser(user, 250, true);
                break;
            case 5:
                message = "Pay hospital fees of €750";
                updateWalletOfUser(user, 750, false);
                break;
            case 6:
                message = "You have won second price in a beauty contest. Collect €100";
                updateWalletOfUser(user, 100, true);
                break;
            case 7:
                message = "Go to Dressing Room. If you pass Go, you don't collect €2000!";
                setPlaceAndInDressingRoom(user);
                break;
            case 8:
                message = "You receives a yellow card, pay fees of €500";
                updateWalletOfUser(user, 500, false);
                break;
            default:
                break;
        }

        messageGenerator.notifyCardMessage(user, message, true);
        getLogInformationOfUser(user, message);

        if (communityChestCardNr == 8) communityChestCardNr = 1;
        else communityChestCardNr++;
    }

    @Override
    public void doChangeCardAction(User user) {
        String message = "";
        switch (changeCardNr) {
            case 1:
                message = "Advance to Go -> Collect €2000";
                setPlaceOfUser(user, 0);
                updateWalletOfUser(user, 2000, true);
                break;
            case 2:
                message = "Take a walk to Neymar";
                setPlaceOfUser(user, 39);
                break;
            case 3:
                message = "Advance to Rashford -> if you pass Go. Collect €2000";
                if (user.getCurrentPlace() > 11) updateWalletOfUser(user, 2000, true);
                setPlaceOfUser(user, 11);
                break;
            case 4:
                message = "Go back 3 spaces";
                setPlaceOfUser(user, user.getCurrentPlace() - 3);
                break;
            case 5:
                message = "Back pays you dividend of €500";
                updateWalletOfUser(user, 500, true);
                break;
            case 6:
                message = "You have won a crossword competition. Collect €200";
                updateWalletOfUser(user, 200, true);
                break;
            case 7:
                message = "Go directly to Dressing Room. If you pass Go, you don't collect €2000!";
                setPlaceAndInDressingRoom(user);
                break;
            case 8:
                message = "Pay poor tax of €375";
                updateWalletOfUser(user, 375, false);
                goalBonus = goalBonus + 375;
                break;
            default:
                break;
        }

        messageGenerator.notifyCardMessage(user, message, false);
        getLogInformationOfUser(user, message);

        if (changeCardNr == 8) changeCardNr = 1;
        else changeCardNr++;
    }

    private void setPlaceOfUser(User user, int place) { user.setPlace(place); }

    private void updateWalletOfUser(User user, int amount, boolean result) {
        if (result) user.getWallet().addMoneyToWallet(amount);
        else {
            user.getWallet().withDrawMoneyOfWallet(amount);
            updateGoalBonus(amount);
        }
    }

    private void updateGoalBonus(int bonus) { goalBonus = goalBonus + bonus; }

    private void setPlaceAndInDressingRoom(User user) {
        user.setPlace(10);
        user.setInDressingRoom(true);
    }

    private void resetGoalBonus() { this.goalBonus = 500; }

    private void getLogInformationOfUser(User user, String message) { log.info("Username: " + user.getUsername() + ", " + message); }
}
