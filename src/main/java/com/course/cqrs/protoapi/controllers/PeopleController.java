package com.course.cqrs.protoapi.controllers;

import com.course.cqrs.protoapi.models.Person;
import com.course.cqrs.protoapi.services.PeopleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Value("${api.version}")
    private String apiVersion;

    private final PeopleService peopleService;
    PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping("/version")
    public String getApiVersion() {
        return apiVersion;
    }

    @GetMapping("/create-people/{quantity}")
    public String createPeople(@PathVariable("quantity") Integer quantity) {
        peopleService.generatePeople(quantity);
        return String.format("Created %d people", quantity);
    }

    @GetMapping("/")
    public ResponseEntity<List<Person>> getPeople() {
        return ResponseEntity.ok(peopleService.getPeople());
    }

    @PostMapping("/")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        return ResponseEntity.ok(peopleService.createPerson(person));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable("id") String id, @RequestBody Person person) {
        return ResponseEntity.ok(peopleService.updatePerson(id, person));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable("id") String id) {
        peopleService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<Person>> getPeopleByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(peopleService.getPeopleByName(name));
    }

}
