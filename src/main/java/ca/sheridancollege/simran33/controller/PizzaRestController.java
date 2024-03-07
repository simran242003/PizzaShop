package ca.sheridancollege.simran33.controller;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ca.sheridancollege.simran33.beans.Cart;
import ca.sheridancollege.simran33.beans.Pizza;
import ca.sheridancollege.simran33.beans.User;
import ca.sheridancollege.simran33.database.DatabaseAccess;

@RestController
@RequestMapping("/api")
public class PizzaRestController {

    @Autowired
    private DatabaseAccess da;

    @PostMapping("/addPizza")
    public ResponseEntity<String> addPizzaAPI(@RequestBody Pizza pizza) {
        da.addPizza(pizza);
        return new ResponseEntity<>("Pizza added successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/deletePizza/{name}")
    public ResponseEntity<String> deletePizzaAPI(@PathVariable String name) {
        Pizza pizza = da.getPizzaByName(name);
        if (pizza != null) {
            da.deletePizza(name);
            return new ResponseEntity<>("Pizza deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Pizza not found", HttpStatus.NOT_FOUND);
    }

}
