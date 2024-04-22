package com.example.core;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class ActivatePowerPacket extends Packet {
    public static void shootEntity(ServerPlayerEntity player, Entity entity, double speed) {
        if (entity instanceof ProjectileEntity) {
            ((ProjectileEntity) entity).setOwner(player);
        }

        entity.setVelocity(
                player.getCameraEntity()
                        .raycast(20.0D, 1.0F, false)
                        .getPos()
                        .subtract(player.getEyePos())
                        .normalize()
                        .multiply(speed)
        );

        entity.setYaw(player.getYaw());
        entity.setPitch(player.getPitch());

        entity.setPosition(player.getPos().add(0, player.getEyeHeight(player.getPose()), 0));
    }

    protected static Object readObject(PacketByteBuf buf) {
        return switch (buf.readByte()) {
            case 0 -> buf.readInt();
            case 1 -> buf.readDouble();
            case 2 -> buf.readFloat();
            case 3 -> buf.readBoolean();
            case 4 -> buf.readString();
            case 5 -> buf.readMap(ActivatePowerPacket::readObject, ActivatePowerPacket::readObject);
            case 6 -> buf.readIdentifier();
            case 7 -> {
                int length = buf.readInt();
                Integer[] arr = new Integer[length];
                for (int i = 0; i < length; i++) {
                    arr[i] = buf.readInt();
                }
                yield arr;
            }
            case 8 -> {
                int length = buf.readInt();
                Double[] arr = new Double[length];
                for (int i = 0; i < length; i++) {
                    arr[i] = buf.readDouble();
                }
                yield arr;
            }
            default -> null;
        };
    };
}
