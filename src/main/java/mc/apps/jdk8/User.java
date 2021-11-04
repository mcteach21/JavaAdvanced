package mc.apps.jdk8;

import java.beans.JavaBean;

@JavaBean
public class User {
    private static int _id=0;
    private int id;
    private String firstname;
    private String lastname;

    public User() {
        this("John","Doe");
    }

    public User(String firstname, String lastname) {
        this.id = ++_id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getName() {
        return firstname+" "+lastname;
    }
    @Override
    public String toString() {
        return "[" + id + "] " + firstname + " " + lastname ;
    }
}
