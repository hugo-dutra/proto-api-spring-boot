package com.course.cqrs.protoapi.models;

import lombok.Data;

import java.util.Date;


@Data
public class Person {

    private String id;
    private String fullName;
    private Date birthDate;
    private Integer age;

    public static class PersonBuilder {
        private String id;
        private String fullName;
        private Date birthDate;
        private Integer age;

        public PersonBuilder id(String id) {
            this.id = id;
            return this;
        }

        public PersonBuilder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public PersonBuilder birthDate(Date birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public PersonBuilder age(Integer age) {
            this.age = age;
            return this;
        }

        public Person build() {
            Person person = new Person();
            person.id = this.id;
            person.fullName = this.fullName;
            person.birthDate = this.birthDate;
            person.age = this.age;
            return person;
        }
    }
}
