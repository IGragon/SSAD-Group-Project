package components;
import java.util.Collection;
import java.util.HashMap;

public class ComponentDatabase<C extends Component>{
    HashMap<Integer, C> database = new HashMap<>();

/*
     This one adds component
    only to LOCAL COMPONENT DB!
*/
    public void addComponent(int id, C instance){
        database.put(id, instance);
    }

/*
     This method returns component
    instance by its id.
*/
    public C getId(int id){
        return database.get(id);
    }

/*
    Simply returns closest component (no).
*/
    public C getClosest(Component component){
        // some fancy algorithm
        C closet = database.get("closest");
        return closet;
    }

/*
    Returns a Collection of all
    current database components.
*/
    public Collection<C> getComponents(){
        return database.values();
    }
}
