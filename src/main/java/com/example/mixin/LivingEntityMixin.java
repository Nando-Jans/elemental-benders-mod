package com.example.mixin;

import com.example.util.BendingData;
import com.example.util.EntityDataSaver;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "isFallFlying", at = @At("HEAD"), cancellable = true)
    public void injectIsFallFlying(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity playerEntity = (LivingEntity) (Object) this;
        if (BendingData.getFireFlying((EntityDataSaver) playerEntity)) {
            cir.setReturnValue(true);
        }
    }
}
