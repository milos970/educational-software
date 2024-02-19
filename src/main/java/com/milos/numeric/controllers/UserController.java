package com.milos.numeric.controllers;

import com.milos.numeric.dtos.NewPasswordDTO;
import com.milos.numeric.dtos.NewPersonDTO;
import com.milos.numeric.dtos.NewAuthorityDTO;
import com.milos.numeric.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UserController
{
    private final PersonService personService;

    @Autowired
    public UserController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/user/create")
    public void createUser(@Valid @RequestBody NewPersonDTO NewPersonDTO)
    {
        this.personService.create(NewPersonDTO);
    }

    @PatchMapping("/user/{id}/update-password")
    public void updatePassword(@PathVariable int id, @Valid @RequestBody NewPasswordDTO newPasswordDTO)
    {
        this.personService.updatePassword(id, newPasswordDTO);
    }

    @PatchMapping("admin/user/{id}/update-role")
    public void updateAuthority(@PathVariable int id, @Valid @RequestBody NewAuthorityDTO newAuthorityDTO)
    {
        this.personService.updateAuthority(id, newAuthorityDTO);
    }

    @GetMapping("/check-username")
    @ResponseBody
    public boolean checkUsername(@RequestParam("username") String username)
    {
        return this.personService.findByUsername(username);
    }


    @PatchMapping("admin/user/{id}")
    public void deleteUser(@PathVariable int id)
    {
        this.personService.delete(id);
    }



    @PostMapping("admin/")
    public void addCSV(@RequestParam("file") MultipartFile file)
    {

    }



}
