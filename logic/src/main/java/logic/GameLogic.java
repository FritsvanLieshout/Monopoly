package logic;

import logic_interface.IBoardLogic;
import logic_interface.IGameLogic;
import models.*;
import restclient.MonopolyRestClient;
import restshared.UserDto;
import server_interface.IServerMessageGenerator;

import java.util.ArrayList;
import java.util.List;

/*
THIS CLASS IS ONLY RESPONSIBLE FOR HANDLING GAME LOGIC / RULES
THE METHODS ARE CALLED FROM THE MESSAGE HANDLERS
WHEN A MESSAGE NEEDS TO BE SENT TO CLIENTS, THE MESSAGE GENERATOR CLASS IS USED
 */
public class GameLogic implements IGameLogic {

    private ArrayList<User> onlineUsers = new ArrayList<>();
    private IServerMessageGenerator messageGenerator;
    private static MonopolyRestClient monopolyRestClient;

    private Board board;
    private BoardLogic boardLogic;

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

    @Override
    public Square moveUser(int dice, String sessionId) {
        User currentUser = getUser(sessionId);
        int newPlace = board.getPositionOnBoard(currentUser.getCurrentPlace());
        if (checkIfUserIsInDressingRoom(currentUser, board)) { }
        else {
            if (checkIfUserIsOverStart(currentUser, dice)) { }
            newPlace = board.getPositionOnBoard(currentUser.getCurrentPlace() + dice);
            currentUser.setPlace(newPlace);
            System.out.println(currentUser.getUsername() + " has dice " + dice + " and goes to " + board.getSquares()[currentUser.getCurrentPlace()].getSquareName());
            messageGenerator.notifyMoveUserMessage(dice, sessionId);
            if (checkIfSquareIsOwned(currentUser, board)) { }
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
       // boolean success = restClient.register(username, password);
        System.out.println("\n");
        System.out.println("Register a new user: ");
        UserDto userDto = monopolyRestClient.registerUser(username, password);

        if (userDto != null) {
            System.out.println("User " + userDto + " added to monopoly game!");
            System.out.println("\n");
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

            //UserDto userDto = monopolyRestClient.loginUser(username, password);
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
        for(User user : onlineUsers) {
            if (user.getSessionId().equals(sessionId)) {
                onlineUsers.remove(user);
            }
        }
    }

    public void startGame() { }

    public void updateUsersInGame() {
        List<String> usernameList = new ArrayList<>();

        for (User user : onlineUsers) {
            usernameList.add(user.getUsername());
        }

        for (User user : onlineUsers) {
            messageGenerator.updateUsersInGame(usernameList, user.getSessionId());
            messageGenerator.updateUserList(onlineUsers, user.getSessionId());
            //One with all users
        }
    }

    private void checkStartingCondition() {
        if (onlineUsers.size() == 2) {
            //startGame();
        }
    }

    private boolean checkUserNameAlreadyExists(String username)
    {
        for(User u : onlineUsers) {
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
