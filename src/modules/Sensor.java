package modules;
import utils.Data;

public class Sensor implements Module{
    @Override
    public Data getData() {
        // Sensor senses the accident
        Data data = new Data();
        data.put("isAccident", true);
        return data;
    }
}
