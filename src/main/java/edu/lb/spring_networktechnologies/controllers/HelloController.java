package edu.lb.spring_networktechnologies.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/helloWorld")
    public String hello(){
        return "Hello World!";
    }

    @GetMapping("/helloSomeone")
    public String helloSomeone(@RequestParam String fname, @RequestParam String lname){
        return "Hello "+ fname + " " + lname;
    }

    @GetMapping("/addNumbers")
    public Integer addNumbers(@RequestParam Integer num1, @RequestParam Integer num2){
        return num1 + num2;
    }

    @GetMapping("/anotherHello/{name}")
    public String otherHello(@PathVariable String name) {
        return "Hello "+ name + "!";
    }

}
