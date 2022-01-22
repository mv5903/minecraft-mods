package net.kwikmatt.mattmod.world.gen.feature;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.kwikmatt.mattmod.TutorialMod;
import net.kwikmatt.mattmod.block.MattBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.tag.BlockTags;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class ModdedOreConfiguredFeatures {
    public static final RuleTest END_STONE = new BlockMatchRuleTest(Blocks.END_STONE);
    public static final RuleTest STONE = new BlockMatchRuleTest(Blocks.STONE);
    public static final RuleTest DEEPSLATE = new BlockMatchRuleTest(Blocks.DEEPSLATE);
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


    public static final SelectionConfiguration[] LOAD_CONFIGS = {
        new SelectionConfiguration("crimson_ore", BiomeSelectors.foundInTheNether())
    };

    public static void registerFeatures() {
        for (SelectionConfiguration configuration: LOAD_CONFIGS) {
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,
                    new Identifier(TutorialMod.MOD_ID, configuration.itemName), CRIMSON_ORE_CONFIGURED_FEATURE);
            Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(TutorialMod.MOD_ID, configuration.itemName),
                    CRIMSON_ORE_PLACED_FEATURE);
            BiomeModifications.addFeature(configuration.dimension, GenerationStep.Feature.UNDERGROUND_ORES,
                    RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                            new Identifier(TutorialMod.MOD_ID, configuration.itemName)));
        }
    }
}

final class SelectionConfiguration {
    String itemName;
    Predicate<BiomeSelectionContext> dimension;

    SelectionConfiguration(String itemName, Predicate<BiomeSelectionContext> dimension) {
        this.itemName = itemName;
        this.dimension = dimension;
    }
}
