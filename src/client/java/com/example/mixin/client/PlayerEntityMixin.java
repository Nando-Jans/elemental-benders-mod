package com.example.mixin.client;

import com.example.util.BendingData;
import com.example.util.EntityDataSaver;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "checkFallFlying", at = @At("HEAD"), cancellable = true)
    public void injectCheckFallFlying(CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity playerEntity = (PlayerEntity) (Object) this;
        if (
                !playerEntity.isOnGround() &&
                !playerEntity.isFallFlying() &&
                !playerEntity.isTouchingWater() &&
                !playerEntity.hasStatusEffect(StatusEffects.LEVITATION) &&
                BendingData.getBendingType((EntityDataSaver) playerEntity) != null)
        {
            playerEntity.startFallFlying();
            cir.setReturnValue(true); // return true to allow flying
        }

        if (playerEntity.isOnGround() && BendingData.getFireFlying((EntityDataSaver) playerEntity)) {
            BendingData.setFireFlying((EntityDataSaver) playerEntity, false);
            cir.setReturnValue(false); // return true to allow flying
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void injectTick(CallbackInfo ci) {
        PlayerEntity playerEntity = (PlayerEntity) (Object) this;
        // test if player is flying with fire bending
        if (BendingData.getFireFlying((EntityDataSaver) playerEntity)) {
            Vec3d vec3d = playerEntity.getVelocity();
            playerEntity.getWorld().addParticle(ParticleTypes.FLAME, playerEntity.getX(), playerEntity.getY() - 0.5, playerEntity.getZ(), -vec3d.x + Math.random() - 0.5, -vec3d.y + Math.random() - 0.5, -vec3d.z + Math.random() - 0.5);
            playerEntity.getWorld().addParticle(ParticleTypes.FLAME, playerEntity.getX(), playerEntity.getY() - 0.5, playerEntity.getZ(), -vec3d.x + Math.random() - 0.5, -vec3d.y + Math.random() - 0.5, -vec3d.z + Math.random() - 0.5);
            playerEntity.getWorld().addParticle(ParticleTypes.FLAME, playerEntity.getX(), playerEntity.getY() - 0.5, playerEntity.getZ(), -vec3d.x + Math.random() - 0.5, -vec3d.y + Math.random() - 0.5, -vec3d.z + Math.random() - 0.5);
            playerEntity.getWorld().addParticle(ParticleTypes.FLAME, playerEntity.getX(), playerEntity.getY() - 0.5, playerEntity.getZ(), -vec3d.x + Math.random() - 0.5, -vec3d.y + Math.random() - 0.5, -vec3d.z + Math.random() - 0.5);
            playerEntity.getWorld().addParticle(ParticleTypes.FLAME, playerEntity.getX(), playerEntity.getY() - 0.5, playerEntity.getZ(), -vec3d.x + Math.random() - 0.5, -vec3d.y + Math.random() - 0.5, -vec3d.z + Math.random() - 0.5);
        }
    }
}
