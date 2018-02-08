package org.dselent.scheduling.server.controller;

import java.util.Map;

import org.dselent.scheduling.server.requests.Click;
import org.dselent.scheduling.server.requests.Submit;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/{user_id}/requests")

public interface RequestsController {
    @RequestMapping(method=RequestMethod.POST, value=Click.REQUEST_NAME)
    public ResponseEntity<String> page(@PathVariable("user_id") Integer id) throws Exception;

    @RequestMapping(method=RequestMethod.POST, value=Submit.REQUEST_NAME)
    public ResponseEntity<String> submit(@RequestBody Map<String, String> request) throws Exception;
}
