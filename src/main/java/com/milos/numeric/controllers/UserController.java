package com.milos.numeric.controllers;

import com.milos.numeric.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController
{
    @Autowired
    private PersonService personService;

    @PatchMapping("/admin/user/{id}")
    public void changeRole(@PathVariable int id, @RequestParam(value = "role") String role)
    {
        //Person person = this.personService.getById(id);
        System.out.println(role);
    }

}
