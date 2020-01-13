package logic;

import logic_interface.IBoardLogic;
import logic_interface.IGameLogic;
import models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restclient.MonopolyRestClient;
import restshared.UserDto;
import server_interface.IServerMessageGenerator;

import java.security.SecureRandom;
import java.util.ArrayList;

public class GameLogic implements IGameLogic {

    private static final Logger log = LoggerFactory.getLogger(GameLogic.class);

    private ArrayList<User> onlineUsers = new ArrayList<>();
    private IServerMessageGenerator messageGenerator;
    private static MonopolyRestClient monopolyRestClient;

    private Board board;
    private IBoardLogic boardLogic;

    private int communityChestCardNr;
    private int changeCardNr;

    private SecureRandom random = new SecureRandom();

    private int goalBonus = 750;

    public GameLogic(IServerMessageGenerator messageGenerator) {
        this.messageGenerator = messageGenerator;
        monopolyRestClient = new MonopolyRestClient();
        boardLogic = new BoardLogic();
        board = boardLogic.getBoard();
        communityChestCardNr = random.nextInt(8);
        changeCardNr = random.nextInt(6);
    }

    public GameLogic() { }

    @Override
    public int getDice(Dice dice) {
        return dice.getNofDice();
    }

    @Override
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
    public void registerNewUser(String username, String password, String sessionId) {
        log.info("[Register a new user]");
        UserDto userDto = monopolyRestClient.registerUser(username, password);

        if (userDto != null) {
            log.info("[User " + userDto + " added to monopoly game] \n");
            messageGenerator.notifyRegisterResult(sessionId, true);
            login(username, password, sessionId);
        }
        else {
            messageGenerator.notifyRegisterResult(sessionId, false);
        }
    }

    @Override
    public void login(String username, String password, String sessionId) {
        if (onlineUsers.size() < 4) {
            if (checkUserNameAlreadyExists(username)) {
                messageGenerator.notifyRegisterResult(sessionId, false);
                return;
            }

            User user = new User(Integer.parseInt(sessionId), sessionId, username);
            messageGenerator.notifyLoginResult(sessionId, user.getUserId());
            onlineUsers.add(user);
            messageGenerator.notifyUserAdded(sessionId, username);
            updateUsersInGame();
            checkStartingCondition();
        }
    }

    @Override
    public void processClientDisconnect(String sessionId)
    {
        for (User user : onlineUsers) {
            if (user.getSessionId().equals(sessionId)) onlineUsers.remove(user);
        }
    }

    public void startGame() {
        messageGenerator.notifyStartGame();
    }

    private void updateUsersInGame() {
        for (User user : onlineUsers) {
            messageGenerator.updateUserList(onlineUsers, user.getSessionId());
        }
    }

    private void checkStartingCondition() {
        if (onlineUsers.size() == 4) startGame();
    }

    private boolean checkUserNameAlreadyExists(String username)
    {
        for(User u : onlineUsers) {
            if (u.getUsername().equals(username)) return true;
        }
        return false;
    }

    private void payRent(User user, Board board) {
        for (Square s : board.getSquares()) {
            User currentUser = getUser(user.getSessionId());
            if (s.getSquareId() == currentUser.getCurrentPlace()) {
                currentUser.getWallet().withDrawMoneyOfWallet(s.getRentPrice());
                User ownedUser = getUserByUserId(s.getOwner());
                ownedUser.getWallet().addMoneyToWallet(s.getRentPrice());
                messageGenerator.notifyPayRent(currentUser, ownedUser);
            }
        }
    }

    private void checkIfUserIsOverStart(User user, int dice) {
        if (user.getCurrentPlace() + dice >= 40) {
            user.getWallet().addMoneyToWallet(2000);
            messageGenerator.notifyUserOverStart(user.getSessionId());
            messageGenerator.updateCurrentUser(user, user.getSessionId());
        }
    }

    private boolean checkIfSquareIsOwned(User user, Board board) {
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

    private boolean checkIfUserIsInDressingRoom(User user) {
        if (user.isInDressingRoom()) {
            user.getWallet().withDrawMoneyOfWallet(500);
            user.setInDressingRoom(false);
            messageGenerator.notifyUserIsInDressingRoom(user);
            return true;
        }
        return false;
    }

    private boolean varChecksRedCard(User user) {
        if (user.getCurrentPlace() == 30) {
            redCard(user);
            return true;
        }
        return false;
    }

    private boolean checkForEnoughMoney(User currentUser, int priceOfSquare) {
        if (currentUser.getWallet().getMoney() - priceOfSquare <= 0) {
            messageGenerator.notifyNotEnoughMoney(currentUser.getSessionId());
            return false;
        }
        return true;
    }

    private void checkIfUserIsBroke(User user, Board board) {
        if (user.getWallet().getMoney() < 0) {
            messageGenerator.notifyUserIsBroke(user);
            user.setBroke(true);

            for (Square s : board.getSquares()) {
                if (s.getOwner() == user.getUserId()) {
                    s.setOwner(-1);
                }
            }

            messageGenerator.updateCurrentUser(user, user.getSessionId());
        }
    }

    private void checkRandomSquares(User user, int newPlace) {
        String message = "";
        switch (newPlace) {
            case 4:
                message = "Pay income tax of €400";
                user.getWallet().withDrawMoneyOfWallet(200);
                break;
            case 20:
                message = "You won €" + goalBonus + ", because you scored against Ajax an important goal!";
                user.getWallet().addMoneyToWallet(goalBonus);
                this.goalBonus = 500;
                break;
            case 38:
                message = "Pay additional tax of €200";
                user.getWallet().withDrawMoneyOfWallet(400);
                break;
        }

        messageGenerator.notifyCardMessage(user, message);
    }

    private void doCommunityChestCardAction(User user) {
        String message = "";
        switch (communityChestCardNr) {
            case 1:
                message = "Advance to Go -> Collect €2000";
                user.setPlace(0);
                user.getWallet().addMoneyToWallet(2000);
                break;
            case 2:
                message = "Bank error in your favor -> Collect €1500";
                user.getWallet().addMoneyToWallet(1500);
                break;
            case 3:
                message = "Pay school fees of €2100";
                user.getWallet().withDrawMoneyOfWallet(2100);
                goalBonus =+ 2100;
                break;
            case 4:
                message = "Receive €250 consultancy fee";
                user.getWallet().addMoneyToWallet(250);
                break;
            case 5:
                message = "Pay hospital fees of €750";
                user.getWallet().withDrawMoneyOfWallet(750);
                goalBonus =+ 750;
                break;
            case 6:
                message = "You have won second price in a beauty contest -> Collect €100";
                user.getWallet().addMoneyToWallet(100);
                break;
            case 7:
                message = "Go to Dressing Room. If you pass Go, you don't collect €2000!";
                user.setPlace(10);
                user.setInDressingRoom(true);
                break;
            case 8:
                message = "You receives a yellow card, pay fees of €500";
                user.getWallet().withDrawMoneyOfWallet(500);
                goalBonus =+ 500;
                break;
            default:
                message = "Invalid";
                break;
        }

        messageGenerator.notifyCardMessage(user, message);

        if (communityChestCardNr == 8) communityChestCardNr = 1;
        else communityChestCardNr++;
    }

    private void doChangeCardAction(User user) {
        String message = "";
        switch (changeCardNr) {
            case 1:
                message = "Advance to Go -> Collect €2000";
                user.setPlace(0);
                user.getWallet().addMoneyToWallet(2000);
                break;
            case 2:
                message = "Take a walk to Neymar";
                user.setPlace(39);
                break;
            case 3:
                message = "Advance to Rashford -> if you pass Go, collect €2000";
                if (user.getCurrentPlace() > 11) user.getWallet().addMoneyToWallet(2000);
                user.setPlace(11);
                break;
            case 4:
                message = "Go back 3 spaces";
                user.setPlace(user.getCurrentPlace() - 3);
                break;
            case 5:
                message = "Back pays you dividend of €500";
                user.getWallet().addMoneyToWallet(500);
                break;
            case 6:
                message = "You have won a crossword competition -> Collect €200";
                user.getWallet().addMoneyToWallet(200);
                break;
            case 7:
                message = "Go directly to Dressing Room. If you pass Go, you don't collect €2000!";
                user.setPlace(10);
                user.setInDressingRoom(true);
                break;
            case 8:
                message = "Pay poor tax of €375";
                user.getWallet().withDrawMoneyOfWallet(375);
                goalBonus =+ 375;
                break;
            default:
                message = "Invalid";
                break;
        }

        messageGenerator.notifyCardMessage(user, message);

        if (changeCardNr == 8) changeCardNr = 1;
        else changeCardNr++;
    }
}
