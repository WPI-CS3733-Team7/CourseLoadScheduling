package org.dselent.scheduling.server.returnobject;

import java.util.List;
import org.dselent.scheduling.server.model.Request;

public class ClickRequestTabReturnObject {
    List<Request> requestList;

    public ClickRequestTabReturnObject(List<Request> requestList) {
        this.requestList = requestList;
    }
}
