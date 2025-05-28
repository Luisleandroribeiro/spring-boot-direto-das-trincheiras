package academy.devdojo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;

@RestController
@RequestMapping
public class HelloController {
    @GetMapping("hi")
    public String hi (){
        return "OMAE WA MOU SHINDE IRU";
    }

}