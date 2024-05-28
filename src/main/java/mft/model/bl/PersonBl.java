package mft.model.bl;

import lombok.Getter;
import mft.controller.exceptions.FailedRequiermentException;
import mft.controller.exceptions.NoPersonFoundException;
import mft.model.da.PersonDa;
import mft.model.entity.Person;
import mft.model.entity.User;
import mft.model.tools.CRUD;

import java.util.List;
import java.util.stream.Collectors;

public class PersonBl implements CRUD<Person> {
    @Getter
    private static PersonBl personBl = new PersonBl();

    private PersonBl() {
    }

    @Override
    public Person save(Person person) throws Exception {
        try (PersonDa personDa = new PersonDa()) {
            personDa.save(person);
            return person;
        }
    }

    @Override
    public Person edit(Person person) throws Exception {
        try (PersonDa personDa = new PersonDa()) {
            if (personDa.findById(person.getId()) != null) {
                personDa.edit(person);
                return person;
            } else {
                throw new NoPersonFoundException();
            }
        }
    }

    @Override
    public Person remove(int id) throws Exception {
        try (PersonDa personDa = new PersonDa()) {
            Person person = personDa.findById(id);
            if (person != null) {
                personDa.remove(id);
                return person;
            } else {
                throw new NoPersonFoundException();
            }
        }
    }

    @Override
    public List<Person> findAll() throws Exception {
        try (PersonDa personDa = new PersonDa()) {
            List<Person> personList = personDa.findAll();
            if (!personList.isEmpty()) {
//                personList
//                        .stream()
//                        .map(person -> person.setUser(UserBl.getUserBl().findById(person.getUser().getId())))
//                        .collect(Collectors.toList());

                for (Person person : personList) {
                    person.setUser(UserBl.getUserBl().findById(person.getUser().getId()));
                }
                return personList;
            } else {
                throw new NoPersonFoundException();
            }
        }
    }

    @Override
    public Person findById(int id) throws Exception {
        try (PersonDa personDa = new PersonDa()) {
            Person person = personDa.findById(id);
            if (person != null) {
                int userId = person.getUser().getId();
                User user = UserBl.getUserBl().findById(userId);
                person.setUser(user);
                return person;
            } else {
                throw new NoPersonFoundException();
            }
        }
    }

    public List<Person> findByFamily(String family) throws Exception {
        try (PersonDa personDa = new PersonDa()) {
            List<Person> personList = personDa.findByFamily(family);
            if (!personList.isEmpty()) {
                for (Person person : personList) {
                    person.setUser(UserBl.getUserBl().findById(person.getUser().getId()));
                }
                return personList;
            } else {
                throw new NoPersonFoundException();
            }
        }
    }
}
