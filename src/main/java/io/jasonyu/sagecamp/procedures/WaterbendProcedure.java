package io.jasonyu.sagecamp.procedures;

import net.minecraft.world.IWorld;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.block.Blocks;

import java.util.Map;

import io.jasonyu.sagecamp.SageCampModElements;
import io.jasonyu.sagecamp.SageCampMod;

@SageCampModElements.ModElement.Tag
public class WaterbendProcedure extends SageCampModElements.ModElement {
	public WaterbendProcedure(SageCampModElements instance) {
		super(instance, 6);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				SageCampMod.LOGGER.warn("Failed to load dependency entity for procedure Waterbend!");
			return;
		}
		if (dependencies.get("itemstack") == null) {
			if (!dependencies.containsKey("itemstack"))
				SageCampMod.LOGGER.warn("Failed to load dependency itemstack for procedure Waterbend!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				SageCampMod.LOGGER.warn("Failed to load dependency world for procedure Waterbend!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		ItemStack itemstack = (ItemStack) dependencies.get("itemstack");
		IWorld world = (IWorld) dependencies.get("world");
		world.setBlockState(
				new BlockPos(
						(int) (entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
								entity.getEyePosition(1f).add(entity.getLook(1f).x * 8, entity.getLook(1f).y * 8, entity.getLook(1f).z * 8),
								RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, entity)).getPos().getX()),
						(int) (entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
								entity.getEyePosition(1f).add(entity.getLook(1f).x * 8, entity.getLook(1f).y * 8, entity.getLook(1f).z * 8),
								RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, entity)).getPos().getY()),
						(int) (entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
								entity.getEyePosition(1f).add(entity.getLook(1f).x * 8, entity.getLook(1f).y * 8, entity.getLook(1f).z * 8),
								RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, entity)).getPos().getZ())),
				Blocks.WATER.getDefaultState(), 3);
		if (entity instanceof PlayerEntity)
			((PlayerEntity) entity).getCooldownTracker().setCooldown(((itemstack)).getItem(), (int) 20);
	}
}
