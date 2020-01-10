package logic;

import logic_interface.IBoardLogic;
import logic_interface.IGameLogic;
import models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restclient.MonopolyRestClient;
import restshared.UserDto;
import server_interface.IServerMessageGenerator;

import java.util.ArrayList;

public class GameLogic implements IGameLogic {

    private static final Logger log = LoggerFactory.getLogger(GameLogic.class);

    private ArrayList<User> onlineUsers = new ArrayList<>();
    private IServerMessageGenerator messageGenerator;
    private static MonopolyRestClient monopolyRestClient;

    private Board board;
    private IBoardLogic boardLogic;

    public GameLogic(IServerMessageGenerator messageGenerator) {
        this.messageGenerator = messageGenerator;
        monopolyRestClient = new MonopolyRestClient();
        boardLogic = new BoardLogic();
        board = boardLogic.getBoard();
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
        if (checkIfUserIsInDressingRoom(currentUser)) { /*Nothing*/ }
        else {
            if (checkIfUserIsOverStart(currentUser, dice)) { /*Nothing*/ }
            newPlace = board.getPositionOnBoard(currentUser.getCurrentPlace() + dice);
            currentUser.setPlace(newPlace);
            messageGenerator.updateCurrentUser(currentUser, currentUser.getSessionId());
            messageGenerator.notifyMoveUserMessage(dice, sessionId);
            if (checkIfSquareIsOwned(currentUser, board)) { /*Nothing*/ }
            if (varChecksRedCard(currentUser)) { /*Nothing*/ }
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
                if (s.getSquareId() == 0 || s.getSquareId() == 2 || s.getSquareId() == 4 || s.getSquareId() == 7 || s.getSquareId() == 10 ||
                        s.getSquareId() == 12 || s.getSquareId() == 17 || s.getSquareId() == 20 || s.getSquareId() == 22 || s.getSquareId() == 28 ||
                        s.getSquareId() == 30 || s.getSquareId() == 33 || s.getSquareId() == 36 || s.getSquareId() == 38) {
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
                }
            }
        }
    }

    @Override
    public void switchTurn(int playerTurn) {
        int turn = playerTurn++;
        if (turn > onlineUsers.size()) messageGenerator.notifySwitchTurn(1);
        else messageGenerator.notifySwitchTurn(turn);
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

            messageGenerator.notifyLoginResult(sessionId, "Token");
            User user = new User(Integer.parseInt(sessionId), sessionId, username);
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
        if (onlineUsers.size() == 2) startGame();
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

    private boolean checkIfUserIsOverStart(User user, int dice) {
        if (user.getCurrentPlace() + dice >= 40) {
            user.getWallet().addMoneyToWallet(2000);
            messageGenerator.notifyUserOverStart(user.getSessionId());
            messageGenerator.updateCurrentUser(user, user.getSessionId());
            return true;
        }
        return false;
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
}
