package components;

import utils.Coordinates;

public abstract class Component {
    ComponentType type;
    int id;
    Coordinates coordinates;

    public ComponentType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
