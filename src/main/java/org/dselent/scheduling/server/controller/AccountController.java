package org.dselent.scheduling.server.controller;

import org.dselent.scheduling.server.requests.Click;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

public interface AccountController {
    @RequestMapping(method=RequestMethod.POST, value=Click.REQUEST_NAME)
    public ResponseEntity<String> click( @RequestBody Map<String, String> request) throws Exception;
}
