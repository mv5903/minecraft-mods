package net.kwikmatt.mattmod.world.gen.feature;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.kwikmatt.mattmod.TutorialMod;
import net.kwikmatt.mattmod.block.MattBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.decorator.SquarePlacementModifier;
import net.minecraft.world.gen.feature.*;

public class ModdedOreConfiguredFeatures {
    public static final RuleTest NETHERRACK = new BlockMatchRuleTest(Blocks.NETHERRACK);
    public static ConfiguredFeature<?, ?> CRIMSON_ORE_CONFIGURED_FEATURE = Feature.ORE
            .configure(new OreFeatureConfig(
                    ModdedOreConfiguredFeatures.NETHERRACK, // we use OreConfiguredFeatures.NETHERRACK here
                    MattBlocks.CRIMSON_ORE.getDefaultState(),
                    10));

    public static PlacedFeature CRIMSON_ORE_PLACED_FEATURE = CRIMSON_ORE_CONFIGURED_FEATURE.withPlacement(
            CountPlacementModifier.of(50),
            SquarePlacementModifier.of(),
            HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(70)));

}
