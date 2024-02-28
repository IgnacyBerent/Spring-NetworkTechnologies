package edu.lb.spring_networktechnologies;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/helloWorld")
    public String hello(){
        return "Hello World!";
    }

    @GetMapping("/helloSomeone")
    public String helloSomeone(@RequestParam String name){
        return "Hello " + name +"!";
    }

}
