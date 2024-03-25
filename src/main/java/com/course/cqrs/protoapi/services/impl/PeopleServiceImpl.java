package com.course.cqrs.protoapi.services.impl;

import com.course.cqrs.protoapi.models.Person;
import com.course.cqrs.protoapi.services.PeopleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.github.javafaker.Faker;
import java.util.UUID;

@Service
public class PeopleServiceImpl implements PeopleService {

    private final List<Person> listOfPeople = new ArrayList<>();
    private final Faker faker = new Faker();

    @Override
    public List<Person> getPeople() {
        return listOfPeople;
    }

    @Override
    public Optional<Person> getPersonById(String id) {
        return listOfPeople
                .stream()
                .findAny()
                .filter(person -> person.getId().equals(id));
    }

    @Override
    public Person createPerson(Person person) {
        Person Newperson = new Person.PersonBuilder()
                .id(UUID.randomUUID().toString())
                .fullName(person.getFullName())
                .birthDate(person.getBirthDate())
                .age(person.getAge())
                .build();
        listOfPeople.add(Newperson);
        return Newperson;
    }

    @Override
    public Person updatePerson(String id, Person person) {
        Optional<Person> personToUpdate = getPersonById(id);
        if (personToUpdate.isEmpty()) {
            throw new RuntimeException("Person not found");
        }
        listOfPeople.remove(personToUpdate.get());
        listOfPeople.add(person);
        return person;
    }

    @Override
    public void deletePerson(String id) {
        Optional<Person> person = getPersonById(id);
        person.ifPresent(listOfPeople::remove);
        if (person.isEmpty()) {
            throw new RuntimeException("Person not found");
        }
        listOfPeople.remove(person.get());
    }

    @Override
    public List<Person> getPeopleByName(String name) {
        return listOfPeople
                .stream()
                .findAny()
                .filter(person -> person.getFullName().contains(name))
                .stream()
                .toList();
    }

    @Override
    public void generatePeople(Integer quantity) {

        if(quantity <= 0) return;
        listOfPeople.clear();

        for(int i = 0; i < quantity; i++) {
            Person person = new Person.PersonBuilder()
                    .id(UUID.randomUUID().toString())
                    .fullName(faker.name().fullName())
                    .birthDate(faker.date().birthday())
                    .age(faker.number().numberBetween(1, 100))
                    .build();

            listOfPeople.add(person);
        }
    }
}
