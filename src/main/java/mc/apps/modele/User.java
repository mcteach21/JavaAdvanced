package mc.apps.modele;

//@JavaBean

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
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
