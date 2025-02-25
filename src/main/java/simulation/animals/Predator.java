package simulation.animals;

public class Predator extends Creature {
    // имеет силу атаки
    /* На что может потратить ход хищник:
        Переместиться (чтобы приблизиться к жертве - травоядному)
        Атаковать травоядное. При этом количество HP травоядного уменьшается на силу атаки хищника. Если значение HP жертвы опускается до 0, травоядное исчезает
    */

    public Predator(String name, int positionX, int positionY) {
        super(name, positionX, positionY);

        this.groupName = "predator";
        speed = 2;
//        hp = 2;
        icon = "\uD83D\uDC06";
    }

//    void makeMove() {
//
//    }
}
