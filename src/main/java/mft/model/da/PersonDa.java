package mft.model.da;

import lombok.extern.log4j.Log4j;
import mft.model.entity.Person;
import mft.model.entity.enums.City;
import mft.model.entity.enums.Gender;
import mft.model.tools.CRUD;
import mft.model.tools.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class PersonDa implements AutoCloseable, CRUD<Person> {
    private final Connection connection;
    private PreparedStatement preparedStatement;

    public PersonDa() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getConnection();
    }

    @Override
    public Person save(Person person) throws Exception {
        person.setId(ConnectionProvider.getConnectionProvider().getNextId("PERSON_SEQ"));

        preparedStatement = connection.prepareStatement(
                "INSERT INTO PERSON (ID, NAME, FAMILY, GENDER, BIRTH_DATE, CITY, ALGO, SE, EE) VALUES (?,?,?,?,?,?,?,?,?)"
        );
        preparedStatement.setInt(1, person.getId());
        preparedStatement.setString(2, person.getName());
        preparedStatement.setString(3, person.getFamily());
        preparedStatement.setString(4, person.getGender().name());
        preparedStatement.setDate(5, Date.valueOf(person.getBirthDate()));
        preparedStatement.setString(6, person.getCity().name());
        preparedStatement.setBoolean(7, person.isAlgorithmSkill());
        preparedStatement.setBoolean(8, person.isJavaSESkill());
        preparedStatement.setBoolean(9, person.isJavaEESkill());
        preparedStatement.execute();
        return person;
    }

    @Override
    public Person edit(Person person) throws Exception {
        preparedStatement = connection.prepareStatement(
                "UPDATE PERSON SET NAME=?, FAMILY=?, GENDER=?, BIRTH_DATE=?, CITY=?, ALGO=?, SE=?, EE=? WHERE ID=?"
        );
        preparedStatement.setString(1, person.getName());
        preparedStatement.setString(2, person.getFamily());
        preparedStatement.setString(3, person.getGender().name());
        preparedStatement.setDate(4, Date.valueOf(person.getBirthDate()));
        preparedStatement.setString(5, person.getCity().name());
        preparedStatement.setBoolean(6, person.isAlgorithmSkill());
        preparedStatement.setBoolean(7, person.isJavaSESkill());
        preparedStatement.setBoolean(8, person.isJavaEESkill());
        preparedStatement.setInt(9, person.getId());
        preparedStatement.execute();
        return person;
    }

    @Override
    public Person remove(int id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "DELETE FROM PERSON WHERE ID=?"
        );
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        return null;
    }

    @Override
    public List<Person> findAll() throws Exception {
        List<Person> personList = new ArrayList<>();

        preparedStatement = connection.prepareStatement("SELECT * FROM PERSON ORDER BY ID");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Person person = Person
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .name(resultSet.getString("NAME"))
                    .family(resultSet.getString("FAMILY"))
                    .gender(Gender.valueOf(resultSet.getString("GENDER")))
                    .birthDate(resultSet.getDate("BIRTH_DATE").toLocalDate())
                    .city(City.valueOf(resultSet.getString("CITY")))
                    .algorithmSkill(resultSet.getBoolean("ALGO"))
                    .JavaSESkill(resultSet.getBoolean("SE"))
                    .JavaEESkill(resultSet.getBoolean("EE"))
                    .build();

            personList.add(person);
        }

        return personList;
    }

    @Override
    public Person findById(int id) throws Exception {
        preparedStatement = connection.prepareStatement("SELECT * FROM PERSON WHERE ID=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Person person = null;
        if (resultSet.next()) {
            person = Person
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .name(resultSet.getString("NAME"))
                    .family(resultSet.getString("FAMILY"))
                    .gender(Gender.valueOf(resultSet.getString("GENDER")))
                    .birthDate(resultSet.getDate("BIRTH_DATE").toLocalDate())
                    .city(City.valueOf(resultSet.getString("CITY")))
                    .algorithmSkill(resultSet.getBoolean("ALGO"))
                    .JavaSESkill(resultSet.getBoolean("SE"))
                    .JavaEESkill(resultSet.getBoolean("EE"))
                    .build();
        }
        return person;
    }

    public List<Person> findByFamily(String family) throws Exception {
        List<Person> personList = new ArrayList<>();

        preparedStatement = connection.prepareStatement("SELECT * FROM PERSON WHERE FAMILY LIKE? ORDER BY ID");
        preparedStatement.setString(1, family + "%");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Person person = Person
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .name(resultSet.getString("NAME"))
                    .family(resultSet.getString("FAMILY"))
                    .gender(Gender.valueOf(resultSet.getString("GENDER")))
                    .birthDate(resultSet.getDate("BIRTH_DATE").toLocalDate())
                    .city(City.valueOf(resultSet.getString("CITY")))
                    .algorithmSkill(resultSet.getBoolean("ALGO"))
                    .JavaSESkill(resultSet.getBoolean("SE"))
                    .JavaEESkill(resultSet.getBoolean("EE"))
                    .build();

            personList.add(person);
        }

        return personList;
    }

    @Override
    public void close() throws Exception {
        preparedStatement.close();
        connection.close();
    }
}
