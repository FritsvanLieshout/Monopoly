package logic_interface;

import models.User;

public interface IUserLogic {
    boolean registerUser(User user);
    boolean login(User user);
}
