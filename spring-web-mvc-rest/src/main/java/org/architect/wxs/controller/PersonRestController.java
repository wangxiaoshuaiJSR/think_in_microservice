package org.architect.wxs.controller;

import org.architect.wxs.domain.Person;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
public class PersonRestController {

    @GetMapping("/person/{id}")
    public Person person(@PathVariable Long id, @RequestParam(required = false) String name){
        Person person = new Person();
        person.setId(id);
        person.setName(name);
        return person;
    }


    @PostMapping(value = "/person/jsonToproperties",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = "application/properties+person")
    public Person personJsonToProperties(@RequestBody Person person){
        return person;
    }

    @PostMapping(value = "/person/propertiesToJson",
            consumes = "application/properties+person",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Person personPropertiesToJson(@RequestBody Person person){
        return person;
    }

}
