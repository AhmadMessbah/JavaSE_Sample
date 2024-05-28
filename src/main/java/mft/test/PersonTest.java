package mft.test;

import mft.model.bl.PersonBl;
import mft.model.bl.UserBl;
import mft.model.entity.Person;
import mft.model.entity.User;
import mft.model.entity.enums.City;
import mft.model.entity.enums.Gender;

import java.time.LocalDate;

public class PersonTest {
    public static void main(String[] args) throws Exception {
        User user =
                User.builder()
                        .username("ali")
                        .password("ali123")
                        .enabled(true)
                        .build();

        Person person =
                Person.builder()
                        .name("ali")
                        .family("alipour")
                        .gender(Gender.Male)
                        .city(City.Shiraz)
                        .birthDate(LocalDate.now())
                        .user(user)
                        .build();

        UserBl.getUserBl().save(user);
        System.out.println(user);

        PersonBl.getPersonBl().save(person);
        System.out.println(person);

//        System.out.println(PersonBl.getPersonBl().findById(2));
//        System.out.println(UserBl.getUserBl().findByUsername("ahmad123"));
        System.out.println(PersonBl.getPersonBl().findAll());

    }
}
