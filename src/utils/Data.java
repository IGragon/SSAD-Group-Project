package utils;
import java.util.HashMap;
import java.util.Set;

public class Data {
    // for example some JSON format
    HashMap<String, Object> data = new HashMap<>();
    public void put(String key, Object value){
        data.put(key, value);
    }
    public Object get(String key){
        return data.get(key);
    }
    public Set<String> getKeys(){
        return data.keySet();
    }
    public Data merge(Data anotherData){
        for (String key : anotherData.getKeys())
            data.put(key, anotherData.get(key));
        return this;
    }
}
