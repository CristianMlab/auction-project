package daos;
import csv.services.CustomCSVReader;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO implements DAO<User> {
    private List<User> users = new ArrayList<>();

    public UserDAO() {
        try {
            users = CustomCSVReader.getInstance().readAll(User.class, "src/main/resources/csv/users.csv");
        } catch(Exception e){
            users = null;
            e.printStackTrace();
        }
    }

    public void save(User user){
        users.add(user);
    }

    public void delete(User user){
        users.remove(user);
    }

    public void update(User user, String[] params){

    }

    public User get(int id){
        for (User user : users) {
            if (user.getId() == id)
                return user;
        }
        return null;
    }

    public List<User> getAll() {
        return users;
    }
}
