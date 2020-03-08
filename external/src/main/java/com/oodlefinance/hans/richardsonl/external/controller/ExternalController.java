package com.oodlefinance.hans.richardsonl.external.controller;

import com.oodlefinance.hans.richardsonl.external.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@EnableDiscoveryClient
@RestController
public class ExternalController {

    @Autowired
    private RestTemplate template;

    @Autowired
    private DiscoveryClient client;

    @Bean
    public RestTemplate template() {
        return new RestTemplate();
    }

    @GetMapping("/test")
    public String invoke() {
        URI uri = client.getInstances("internal").stream().map(si -> si.getUri()).findFirst().map(s -> s.resolve("/test")).get();
        return template.getForObject(uri, String.class);
    }

    @GetMapping("/message")
    @ResponseStatus(HttpStatus.CREATED)
    public List<String> getMessage() {
        URI uri = client.getInstances("internal").stream().map(si -> si.getUri()).findFirst().map(s -> s.resolve("/message")).get();
        return template.getForObject(uri, List.class);
    }

    @PostMapping("/message")
    @ResponseStatus(HttpStatus.OK)
    public void postMessage(@RequestBody Message message) {
        URI uri = client.getInstances("internal").stream().map(si -> si.getUri()).findFirst().map(s -> s.resolve("/message")).get();
        template.postForLocation(uri, message);
    }

    @GetMapping("/message/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String getMessageId(@PathVariable("id") int id){
        URI uri = client.getInstances("internal").stream().map(si -> si.getUri()).findFirst().map(s -> s.resolve("/message/" + id)).get();
        return template.getForObject(uri, String.class);
    }

    @DeleteMapping("/message/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteMessageId(@PathVariable("id") int id){
        HttpEntity<String> entity = new HttpEntity<String>("parameters");
        URI uri = client.getInstances("internal").stream().map(si -> si.getUri()).findFirst().map(s -> s.resolve("/message/" + id)).get();
        ResponseEntity<String> result = template.exchange(uri, HttpMethod.DELETE, entity, String.class);
        return result.getBody();
    }


}
