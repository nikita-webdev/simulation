package simulation.entities.objects;

import simulation.entities.Entity;

import static simulation.config.Icons.TREE_ICON;

public class Tree extends Entity {
    public Tree(String name) {
        super(name);

        icon = TREE_ICON;
    }
}
