package mc.apps.modele;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(namespace = "http://mc.apps/jaxb")
@XmlAccessorType(XmlAccessType.FIELD)
public class People {
    private String name;

    @XmlElementWrapper(name = "users")
    @XmlElement(name = "user")
    private List<User> users;

    public People() {
    }

    public People(String name) {
        this.name = name;
    }
    public People(String name, List<User> users) {
        this.name = name;
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
