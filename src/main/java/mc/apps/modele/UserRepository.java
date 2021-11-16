package mc.apps.modele;

import java.util.Arrays;
import java.util.List;

public class UserRepository {
    static List<User> list = Arrays.asList(
            new User("James","Bond"),
            new User("Jason","Bourne"),
            new User("John","Doe")
    );

    public static List<User> list() {
        return list;
    }
}
