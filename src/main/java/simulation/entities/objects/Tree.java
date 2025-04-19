package simulation.entities.objects;

import simulation.entities.Entity;

public class Tree extends Entity {
    public Tree(String name, int positionX, int positionY) {
        super(name, positionX, positionY);

        icon = "\uD83C\uDF33";
        groupName = "tree";
    }
}
