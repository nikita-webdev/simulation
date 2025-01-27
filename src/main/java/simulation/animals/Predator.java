package simulation.animals;

public class Predator extends Creature {
    // имеет силу атаки
    /* На что может потратить ход хищник:
        Переместиться (чтобы приблизиться к жертве - травоядному)
        Атаковать травоядное. При этом количество HP травоядного уменьшается на силу атаки хищника. Если значение HP жертвы опускается до 0, травоядное исчезает
    */

    String name = "P";

    public Predator(String name, int speed, int hp, int positionX, int positionY) {
        super(name, speed, hp, positionX, positionY);
        this.name = name;
    }
}
