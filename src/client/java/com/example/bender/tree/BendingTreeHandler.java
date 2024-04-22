package com.example.bender.tree;

import com.example.bender.tree.node.fire.FireRootTreeItem;
import com.example.gui.screen.BendingTreeScreen;
import net.minecraft.text.Text;

public class BendingTreeHandler {
    public BendingTreeHandler(Type type) {
        this.type = type;
    }

    public Text getTreeScreenTitle() {
        return switch (type) {
            case FIRE -> Text.of("Fire Bending Tree");
            case WATER -> Text.of("Water Bending Tree");
            case EARTH -> Text.of("Earth Bending Tree");
            case AIR -> Text.of("Air Bending Tree");
        };
    }

    public void setListener(BendingTreeScreen bendingTreeScreen) {

    }
    public enum Type {
        FIRE,
        WATER,
        EARTH,
        AIR

    }

    public Type type;

    public FireRootTreeItem getRoot() {
        switch (type) {
            case FIRE:
                return new FireRootTreeItem();
            case WATER:
//                return new WaterRootTreeItem();
            case EARTH:
//                return new EarthRootTreeItem();
            case AIR:
//                return new AirRootTreeItem();
        }
        return null;
    }

    public String toString() {
        return "";
    }



    public static BendingTreeHandler valueOf(Type type, String bendingTreeString) {
        BendingTreeHandler bendingTreeHandler = new BendingTreeHandler(type);

        return bendingTreeHandler;
    }
}
