package org.dselent.scheduling.server.returnobject;

import org.dselent.scheduling.server.model.User;

import java.util.List;

public class ClickAccountTabReturnObject {
    String name;
    String username;
    String userState;
    String Email;
    String password;
    List<User> userList;

    public ClickAccountTabReturnObject(String name, String username, String userState, String Email, String password, List<User> userList) {
        this.name = name;
        this.username = username;
        this.userState = userState;
        this.Email = Email;
        this.password = password;
        this.userList = userList;
    }
}
