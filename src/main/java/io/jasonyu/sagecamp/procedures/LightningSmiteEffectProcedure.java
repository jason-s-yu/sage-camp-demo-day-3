package io.jasonyu.sagecamp.procedures;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Entity;

import java.util.Map;

import io.jasonyu.sagecamp.SageCampModElements;
import io.jasonyu.sagecamp.SageCampMod;

@SageCampModElements.ModElement.Tag
public class LightningSmiteEffectProcedure extends SageCampModElements.ModElement {
	public LightningSmiteEffectProcedure(SageCampModElements instance) {
		super(instance, 2);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				SageCampMod.LOGGER.warn("Failed to load dependency entity for procedure LightningSmiteEffect!");
			return;
		}
		if (dependencies.get("itemstack") == null) {
			if (!dependencies.containsKey("itemstack"))
				SageCampMod.LOGGER.warn("Failed to load dependency itemstack for procedure LightningSmiteEffect!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				SageCampMod.LOGGER.warn("Failed to load dependency world for procedure LightningSmiteEffect!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		ItemStack itemstack = (ItemStack) dependencies.get("itemstack");
		IWorld world = (IWorld) dependencies.get("world");
		if (world instanceof ServerWorld) {
			LightningBoltEntity _ent = EntityType.LIGHTNING_BOLT.create((World) world);
			_ent.moveForced(
					Vector3d.copyCenteredHorizontally(
							new BlockPos(
									(int) (entity.world.rayTraceBlocks(
											new RayTraceContext(entity.getEyePosition(1f),
													entity.getEyePosition(1f).add(entity.getLook(1f).x * 32, entity.getLook(1f).y * 32,
															entity.getLook(1f).z * 32),
													RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY, entity))
											.getPos().getX()),
									(int) (entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
											entity.getEyePosition(1f).add(entity.getLook(1f).x * 32, entity.getLook(1f).y * 32,
													entity.getLook(1f).z * 32),
											RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY, entity)).getPos().getY()),
									(int) (entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
											entity.getEyePosition(1f).add(entity.getLook(1f).x * 32, entity.getLook(1f).y * 32,
													entity.getLook(1f).z * 32),
											RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY, entity)).getPos().getZ()))));
			_ent.setEffectOnly(false);
			((World) world).addEntity(_ent);
		}
		if (entity instanceof PlayerEntity)
			((PlayerEntity) entity).getCooldownTracker().setCooldown(((itemstack)).getItem(), (int) 20);
	}
}
