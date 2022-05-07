package daos;
import model.User;

import java.util.ArrayList;

public class UserDAO {
    private ArrayList<User> Users = new ArrayList<>();

    public UserDAO() {
        Users.add(new User(1, "Andrei", "andrei@iahu.com"));
        Users.add(new User(2, "Gigel", "gigel@iahu.com"));
        Users.add(new User(3, "Mihai", "mihai@iahu.com"));
        Users.add(new User(4, "George", "george@iahu.com"));
    }

    public ArrayList<User> getUsers() {
        return Users;
    }
}
