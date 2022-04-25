package components;
import java.util.Collection;
import java.util.HashMap;

public class ComponentDatabase<C extends Component>{
    HashMap<Integer, C> database = new HashMap<>();


    public void addComponent(int id, C instance){
        database.put(id, instance);
    }

    public C getId(int id){
        return database.get(id);
    }

    public C getClosest(Component component){
        // some fancy algorithm
        C closet = database.get("closest");
        return closet;
    }

    public Collection<C> getComponents(){
        return database.values();
    }
}
