package simulation.entities.animals;

import simulation.map.Cell;

public class Predator extends Creature {
    // имеет силу атаки
    /* На что может потратить ход хищник:
        Переместиться (чтобы приблизиться к жертве - травоядному)
        Атаковать травоядное. При этом количество HP травоядного уменьшается на силу атаки хищника. Если значение HP жертвы опускается до 0, травоядное исчезает
    */

    public Predator(Cell cell, String name) {
        super(cell, name);

        this.groupName = "predator";
//        hp = 2;
        icon = "\uD83D\uDC06";
    }

//    void makeMove() {
//
//    }
}
