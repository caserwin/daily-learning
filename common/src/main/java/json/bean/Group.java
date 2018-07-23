package json.bean;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author yidxue
 */
public class Group {
    private long id;
    private String name;
    private ArrayList<User> users = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<User> getUser() {
        return users;
    }

    public void setUser(ArrayList<User> user) {
        this.users = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    @Override
    public String toString() {
        return this.id + "\t" + this.name + "\t" + Stream.of(users).map(AbstractCollection::toString).collect(Collectors.joining(","));
    }
}
