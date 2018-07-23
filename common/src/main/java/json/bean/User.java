package json.bean;

/**
 * @author yidxue
 */
public class User {
    private long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "(" + this.id + " " + this.name + ")";
    }
}
