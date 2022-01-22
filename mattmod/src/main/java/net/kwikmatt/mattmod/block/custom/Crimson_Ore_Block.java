package net.kwikmatt.mattmod.block.custom;

import com.ibm.icu.impl.CalendarCache;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.GameRules;

public class Crimson_Ore_Block extends Block {

    public Crimson_Ore_Block(Settings settings) {super(settings);}

    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
        super.onStacksDropped(state, world, pos, stack);
        if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0) {
            int experience = MathHelper.nextInt(world.random, 4, 9); // between 4 and 9 xp
            this.dropExperience(world, pos, experience);
        }
    }
}

