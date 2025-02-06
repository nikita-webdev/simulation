package simulation;

public abstract class Entity {
    public String name;
    public String icon;
    public int positionX;
    public int positionY;
    public int[] coordinates;

    public Entity(String name, int positionX, int positionY) {
        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
        this.coordinates = new int[] {positionX, positionY};
    }
}
