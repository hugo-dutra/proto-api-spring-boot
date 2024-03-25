package com.course.cqrs.protoapi.services.impl;

import com.course.cqrs.protoapi.models.Person;
import com.course.cqrs.protoapi.services.PeopleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.github.javafaker.Faker;
import java.util.UUID;
import java.util.stream.Collectors;

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
                .filter(person -> person.getId().equals(id))
                .findFirst();
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
        Person updatedPerson = changePerson(id,person);
        listOfPeople.add(updatedPerson);
        return updatedPerson;
    }

    private Person changePerson(String personId,Person person) {
        return new Person.PersonBuilder()
                .id(personId)
                .fullName(person.getFullName())
                .birthDate(person.getBirthDate())
                .age(person.getAge())
                .build();
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
                .filter(person -> person.getFullName().contains(name))
                .collect(Collectors.toList());
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
