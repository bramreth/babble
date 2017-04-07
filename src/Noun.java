import java.io.Serializable;

/**
 * Created by bramreth on 4/7/17.
 */
public abstract class Noun implements Serializable {
    public String name, description;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        name = name;
    }

    public String getDescription() {
        return description;
    }
}
