package simulation.entities.objects;

import simulation.entities.Entity;

import static simulation.config.Icons.TREE_ICON;

public class Tree extends Entity {
    public Tree(String name, int positionX, int positionY) {
        super(name, positionX, positionY);

        icon = TREE_ICON;
        groupName = "tree";
    }
}
