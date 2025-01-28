package simulation;

public abstract class Entity {
    /*
    * Положение объектов на карте - индекс из двух чисел
    * Может быть, лучше не массив, а два примитива int?
    * Типа horizontalLocation и verticalLocation
    * Или типа x, y
    */

    public String name;
    int positionY;
    int positionX;

    public Entity(String name, int positionX, int positionY) {
        this.name = name;
        this.positionY = positionY;
        this.positionX = positionX;
    }
}
