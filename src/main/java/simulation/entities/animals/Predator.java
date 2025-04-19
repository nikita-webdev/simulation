package simulation.entities.animals;

import simulation.map.Cell;

import static simulation.config.Icons.PREDATOR_ICON;

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
        icon = PREDATOR_ICON;
    }

//    void makeMove() {
//
//    }
}
