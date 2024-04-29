package com.example.entity;

import com.example.entity.bending.fragments.fire.lightning.LightningAttackEntity;
import com.example.entity.bending.fragments.fire.trace.FireTraceAttackEntity;
import com.example.entity.bending.fragments.fire.wave.FireWaveAttackEntity;
import com.example.entity.mob.bender.fire.FireBenderEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEntities extends ModEntityIdentifiers {

    public static final EntityType<FireTraceAttackEntity> FIRE_TRACE_ATTACK = Registry.register(
            Registries.ENTITY_TYPE,
            FIRE_TRACE_ATTACK_ID,
            FabricEntityTypeBuilder.<FireTraceAttackEntity>create(SpawnGroup.MISC, FireTraceAttackEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeBlocks(10)
                    .trackedUpdateRate(100)
                    .build()
    );

    public static final EntityType<FireWaveAttackEntity> FIRE_WAVE_ATTACK = Registry.register(
            Registries.ENTITY_TYPE,
            FIRE_WAVE_ATTACK_ID,
            FabricEntityTypeBuilder.<FireWaveAttackEntity>create(SpawnGroup.MISC, FireWaveAttackEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeBlocks(100)
                    .trackedUpdateRate(100)
                    .build()
    );

    public static final EntityType<LightningAttackEntity> LIGHTNING_ATTACK = Registry.register(
            Registries.ENTITY_TYPE,
            LIGHTNING_ATTACK_ID,
            FabricEntityTypeBuilder.<LightningAttackEntity>create(SpawnGroup.MISC, LightningAttackEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeBlocks(100)
                    .trackedUpdateRate(100)
                    .build()
    );

    public static final EntityType<FireBenderEntity> FIRE_BENDER = Registry.register(
            Registries.ENTITY_TYPE,
            FIRE_BENDER_ID,
            FabricEntityTypeBuilder.<FireBenderEntity>create(SpawnGroup.CREATURE, FireBenderEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 1f)).trackRangeBlocks(100)
                    .trackedUpdateRate(100)
                    .build()
    );


    public static void registerModEntities() {
        FabricDefaultAttributeRegistry.register(FIRE_BENDER, FireBenderEntity.fireBenderAttributeContainer());
    }
}
