package simulation;

public abstract class Entity {
    /*
    * Положение объектов на карте - индекс из двух чисел
    * Может быть, лучше не массив, а два примитива int?
    * Типа horizontalLocation и verticalLocation
    * Или типа x, y
    */

    public String name;
    public String icon;
    public int positionX;
    public int positionY;

    public Entity(String name, int positionX, int positionY) {
        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
