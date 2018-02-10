package org.dselent.scheduling.server.returnobject;

import java.util.List;
import java.util.ArrayList;
import org.dselent.scheduling.server.model.User;

public class ClickRequestTabReturnObject {
    String name;
    String username;
    String userState;
    String Email;
    String password;
    List<User> userList;

    public ClickRequestTabReturnObject(String name, String username, String userState, String Email, String password, List<User> userList) {
        this.name = name;
        this.username = username;
        this.userState = userState;
        this.Email = Email;
        this.password = password;
        this.userList = userList;
    }
}
