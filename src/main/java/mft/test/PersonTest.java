package mft.test;

import mft.model.bl.PersonBl;
import mft.model.entity.Person;
import mft.model.entity.enums.City;
import mft.model.entity.enums.Gender;

import java.time.LocalDate;

public class PersonTest {
    public static void main(String[] args) throws Exception {
//        System.out.println(PersonBl.getPersonBl().findAll());
//        System.out.println(PersonBl.getPersonBl().findById(3));
//        System.out.println(PersonBl.getPersonBl().findByFamily("alipour"));
//        System.out.println(PersonBl.getPersonBl().remove(3));
        System.out.println(PersonBl.getPersonBl().edit(
                Person.builder()
                        .id(4)
                        .name("aaa")
                        .family("bbbb")
                        .gender(Gender.Female)
                        .city(City.Tabriz)
                        .birthDate(LocalDate.now())
                        .build()
        ));

//                System.out.println(PersonBl.getPersonBl().save(
//                Person.builder()
//                        .name("reza")
//                        .family("rezaii")
//                        .gender(Gender.Male)
//                        .city(City.Tehran)
//                        .birthDate(LocalDate.now())
//                        .build()
//        ));


    }
}
