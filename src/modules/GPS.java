package modules;

import utils.Coordinates;
import utils.Data;

public class GPS implements Module{
    Coordinates coordinates;
    @Override
    public Data getData() {
        Data data = new Data();
        data.put("coordinates", coordinates);
        return data;
    }
}
