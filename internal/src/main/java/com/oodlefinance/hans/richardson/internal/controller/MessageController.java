package com.oodlefinance.hans.richardson.internal.controller;

import com.oodlefinance.hans.richardson.internal.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class MessageController {

    private static final Map<Integer,String> map = new HashMap<>();

    @GetMapping("/test")
    public String test() {
        return "connected to internal";
    }

    @GetMapping("/message")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getMessage() {
        List<String> response = new ArrayList<>();
        for (String value : map.values()) {
            response.add(value);
        }
        return response;
    }

    @PostMapping("/message")
    @ResponseStatus(HttpStatus.CREATED)
    public void postMessage(@RequestBody Message message) {
        System.out.println(message.getId());
        System.out.println(message.getMessage());
        map.put(message.getId(), message.getMessage());
    }

    @GetMapping("/message/{id}")
    public String getMessageId(@PathVariable("id") int id){
        return map.get(id) != null ? map.get(id) : "no id with message";
    }

    @DeleteMapping("/message/{id}")
    public String deleteMessageId(@PathVariable("id") int id){
        String message = map.get(id);
        return map.remove(id) != null ? "id: " + id + " and message: " + message + " was deleted" : "id does not exist";
    }

}
