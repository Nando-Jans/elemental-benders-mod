package com.example.bender;

import com.example.util.BendingData;
import com.example.util.EntityDataSaver;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public abstract class Attack {
    protected static int getLevel(MinecraftClient client) {
        if (client.player == null) {
            return 0;
        }
        return BendingData.getBendingLevel((EntityDataSaver) client.player);
    }

    protected static void writeObject(PacketByteBuf buf, @NotNull Object s) {
        if (s.getClass() == Integer.class) {
            buf.writeByte(0);
            buf.writeInt((Integer) s);
        }
        else if (s.getClass() == Double.class) {
            buf.writeByte(1);
            buf.writeDouble((Double) s);
        }
        else if (s.getClass() == Float.class) {
            buf.writeByte(2);
            buf.writeFloat((Float) s);
        }
        else if (s.getClass() == Boolean.class) {
            buf.writeByte(3);
            buf.writeBoolean((Boolean) s);
        }
        else if (s.getClass() == String.class) {
            buf.writeByte(4);
            buf.writeString((String) s);
        }
        else if (s instanceof Map<?, ?> map) {
            buf.writeByte(5);
            buf.writeMap(
                    map,
                    Attack::writeObject,
                    Attack::writeObject
            );
        }
        else if (s instanceof Identifier) {
            buf.writeByte(6);
            buf.writeIdentifier((Identifier) s);
        }
        else if (s instanceof Integer[] arr) {
            buf.writeByte(7);
            buf.writeInt(arr.length);
            for (Integer i : arr) {
                buf.writeInt(i);
            }
        }
        else if (s instanceof List<?> list) {
            buf.writeByte(8);
            buf.writeInt(list.size());
            for (Object o : list) {
                writeObject(buf, o);
            }
        }
    };
}
