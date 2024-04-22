package com.example.effect;

import com.example.ElementalBendersMod;
import com.example.effect.fire.BurningStatusEffect;
import com.example.effect.fire.ExplodeStatusEffect;
import com.example.effect.fire.LightningBoltStatusEffect;
import com.example.effect.fire.NapalmExplodeStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModStatusEffects {
    public static final Identifier LIGHTNING_BOLT = new Identifier(ElementalBendersMod.MOD_ID, "lightning_bolt");
    public static final Identifier BURNING = new Identifier(ElementalBendersMod.MOD_ID, "burning");
    public static final Identifier NAPALM_EXPLODE = new Identifier(ElementalBendersMod.MOD_ID, "napalm_explode");
    public static final Identifier EXPLODE = new Identifier(ElementalBendersMod.MOD_ID, "explode");


    public static void register() {
        register(LIGHTNING_BOLT, new LightningBoltStatusEffect(StatusEffectCategory.HARMFUL));
        register(BURNING, new BurningStatusEffect(StatusEffectCategory.HARMFUL));
        register(NAPALM_EXPLODE, new NapalmExplodeStatusEffect(StatusEffectCategory.HARMFUL));
        register(EXPLODE, new ExplodeStatusEffect(StatusEffectCategory.HARMFUL));
    }

    private static void register(Identifier id, StatusEffect statusEffect) {
        Registry.register(Registries.STATUS_EFFECT, id, statusEffect);
    }
}
