package simulation.entities;

public abstract class Entity {
    public String name;
    public String groupName;
    public String icon;
    public int positionX;
    public int positionY;
    public int[] coordinates = new int[2];

    public Entity(String name, int positionX, int positionY) {
        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
        this.coordinates = new int[] {positionX, positionY};
    }

    public Entity(String name) {
        this.name = name;
    }
}
