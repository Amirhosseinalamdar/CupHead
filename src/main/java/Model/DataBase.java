package Model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DataBase {
    private ArrayList<User> users = new ArrayList<>();
    private static DataBase dataBase;

    private DataBase() {
    }

    public static DataBase getInstance(){
        if(dataBase == null){
            dataBase = new DataBase();
        }
        return dataBase;
    }

    public User getUserByName(String username){
        for(User key: users){
            if(key.getUsername().equals(username)){
                return key;
            }
        }
        return null;
    }

    public void addUser(String username, String password,boolean isLoggedIn){
        users.add(new User(password,username,isLoggedIn));
    }

    public User getLoggedInUser(){
        for(User key: users){
            if(key.isLoggedIn())
                return key;
        }
        return null;
    }
}

