package com.security.security.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionController {
    // Methode pour les exception au niveau des accès
    @GetMapping(path ="notAuthorized")
    public String NotAutorized(){
        return "Not Autorization for this role";
    }
}
