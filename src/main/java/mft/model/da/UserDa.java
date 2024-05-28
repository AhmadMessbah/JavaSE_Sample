package mft.model.da;

import mft.model.entity.User;
import mft.model.tools.CRUD;
import mft.model.tools.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDa implements AutoCloseable, CRUD<User> {
    private final Connection connection;
    private PreparedStatement preparedStatement;

    public UserDa() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getConnection();
    }

    @Override
    public User save(User user) throws Exception {
        user.setId(ConnectionProvider.getConnectionProvider().getNextId("PERSON_SEQ"));

        preparedStatement = connection.prepareStatement(
                "INSERT INTO USER_TBL (ID, USERNAME, PASSWORD, ENABLED) VALUES (?,?,?,?)"
        );
        preparedStatement.setInt(1, user.getId());
        preparedStatement.setString(2, user.getUsername());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.setBoolean(4, user.isEnabled());
        preparedStatement.execute();
        return user;
    }

    @Override
    public User edit(User user) throws Exception {
        preparedStatement = connection.prepareStatement(
                "UPDATE USER_TBL SET USERNAME=?, PASSWORD=?, ENABLED=? WHERE ID=?"
        );
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setBoolean(3, user.isEnabled());
        preparedStatement.setInt(4, user.getId());
        preparedStatement.execute();
        return user;
    }

    @Override
    public User remove(int id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "DELETE FROM USER_TBL WHERE ID=?"
        );
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        return null;
    }

    @Override
    public List<User> findAll() throws Exception {
        List<User> userList = new ArrayList<>();

        preparedStatement = connection.prepareStatement("SELECT * FROM USER_TBL ORDER BY ID");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            User user = User
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .username(resultSet.getString("USERNAME"))
                    .password(resultSet.getString("PASSWORD"))
                    .enabled(resultSet.getBoolean("ENABLED"))
                    .build();

            userList.add(user);
        }

        return userList;
    }

    @Override
    public User findById(int id) throws Exception {
        preparedStatement = connection.prepareStatement("SELECT * FROM USER_TBL WHERE ID=?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        User user = null;
        if (resultSet.next()) {
            user = User
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .username(resultSet.getString("USERNAME"))
                    .password(resultSet.getString("PASSWORD"))
                    .enabled(resultSet.getBoolean("ENABLED"))
                    .build();
        }
        return user;
    }

    public User findByUsername(String username) throws Exception {
        preparedStatement = connection.prepareStatement("SELECT * FROM USER_TBL WHERE USERNAME=? ");
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();

        User user = null;
        if (resultSet.next()) {
            user = User
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .username(resultSet.getString("USERNAME"))
                    .password(resultSet.getString("PASSWORD"))
                    .enabled(resultSet.getBoolean("ENABLED"))
                    .build();
        }
        return user;
    }

    public User findByUsernameAndPassword(String username, String password) throws Exception {
        preparedStatement = connection.prepareStatement("SELECT * FROM USER_TBL WHERE USERNAME=? AND PASSWORD=?");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();

        User user = null;
        if (resultSet.next()) {
            user = User
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .username(resultSet.getString("USERNAME"))
                    .password(resultSet.getString("PASSWORD"))
                    .enabled(resultSet.getBoolean("ENABLED"))
                    .build();
        }
        return user;
    }

    @Override
    public void close() throws Exception {
        preparedStatement.close();
        connection.close();
    }
}
