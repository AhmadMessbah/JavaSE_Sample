package mft.model.bl;

import lombok.Getter;
import mft.controller.exceptions.DuplicateUsernameException;
import mft.controller.exceptions.NoUserFoundException;
import mft.model.da.UserDa;
import mft.model.entity.User;
import mft.model.tools.CRUD;

import java.util.List;

public class UserBl implements CRUD<User> {
    @Getter
    private static UserBl userBl = new UserBl();

    private UserBl() {
    }

    @Override
    public User save(User user) throws Exception {
        try (UserDa userDa = new UserDa()) {
            if(userDa.findByUsername(user.getUsername()) == null) {
                userDa.save(user);
                return user;
            }
            throw new DuplicateUsernameException();
        }
    }

    @Override
    public User edit(User user) throws Exception {
        try (UserDa userDa = new UserDa()) {
            if (userDa.findById(user.getId()) != null) {
                userDa.edit(user);
                return user;
            } else {
                throw new NoUserFoundException();
            }
        }
    }

    @Override
    public User remove(int id) throws Exception {
        try (UserDa userDa = new UserDa()) {
            User user = userDa.findById(id);
            if (user != null) {
                userDa.remove(id);
                return user;
            } else {
                throw new NoUserFoundException();
            }
        }
    }

    @Override
    public List<User> findAll() throws Exception {
        try (UserDa userDa = new UserDa()) {
            List<User> perosnList = userDa.findAll();
            if (!perosnList.isEmpty()) {
                return perosnList;
            } else {
                throw new NoUserFoundException();
            }
        }
    }

    @Override
    public User findById(int id) throws Exception {
        try (UserDa userDa = new UserDa()) {
            User user = userDa.findById(id);
            if (user != null) {
                return user;
            } else {
                throw new NoUserFoundException();
            }
        }
    }

    public User findByUsername(String username) throws Exception {
        try (UserDa userDa = new UserDa()) {
            User user = userDa.findByUsername(username);
            if (user != null) {
                return user;
            } else {
                throw new NoUserFoundException();
            }
        }
    }

    public User findByUsernameAndPassword(String username, String password) throws Exception {
        try (UserDa userDa = new UserDa()) {
            User user = userDa.findByUsernameAndPassword(username,password);
            if (user != null) {
                return user;
            } else {
                throw new NoUserFoundException();
            }
        }
    }
}
