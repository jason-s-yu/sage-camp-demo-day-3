package io.jasonyu.sagecamp.procedures;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.world.Explosion;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

import java.util.Map;

import io.jasonyu.sagecamp.SageCampModElements;
import io.jasonyu.sagecamp.SageCampMod;

@SageCampModElements.ModElement.Tag
public class ExplosionSmiteEffectProcedure extends SageCampModElements.ModElement {
	public ExplosionSmiteEffectProcedure(SageCampModElements instance) {
		super(instance, 3);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				SageCampMod.LOGGER.warn("Failed to load dependency entity for procedure ExplosionSmiteEffect!");
			return;
		}
		if (dependencies.get("itemstack") == null) {
			if (!dependencies.containsKey("itemstack"))
				SageCampMod.LOGGER.warn("Failed to load dependency itemstack for procedure ExplosionSmiteEffect!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				SageCampMod.LOGGER.warn("Failed to load dependency world for procedure ExplosionSmiteEffect!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		ItemStack itemstack = (ItemStack) dependencies.get("itemstack");
		IWorld world = (IWorld) dependencies.get("world");
		for (int index0 = 0; index0 < (int) (Math.ceil(((Math.random() * 5) + 1))); index0++) {
			new Object() {
				private int ticks = 0;
				private float waitTicks;
				private IWorld world;
				public void start(IWorld world, int waitTicks) {
					this.waitTicks = waitTicks;
					MinecraftForge.EVENT_BUS.register(this);
					this.world = world;
				}

				@SubscribeEvent
				public void tick(TickEvent.ServerTickEvent event) {
					if (event.phase == TickEvent.Phase.END) {
						this.ticks += 1;
						if (this.ticks >= this.waitTicks)
							run();
					}
				}

				private void run() {
					if (world instanceof World && !((World) world).isRemote) {
						((World) world).createExplosion(null, (int) (entity.world
								.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
										entity.getEyePosition(1f).add(entity.getLook(1f).x * 16, entity.getLook(1f).y * 16,
												entity.getLook(1f).z * 16),
										RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, entity))
								.getPos().getX()),
								(int) (entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
										entity.getEyePosition(1f).add(entity.getLook(1f).x * 16, entity.getLook(1f).y * 16,
												entity.getLook(1f).z * 16),
										RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, entity)).getPos().getY()),
								(int) (entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
										entity.getEyePosition(1f).add(entity.getLook(1f).x * 16, entity.getLook(1f).y * 16,
												entity.getLook(1f).z * 16),
										RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, entity)).getPos().getZ()),
								(float) 1, Explosion.Mode.BREAK);
					}
					MinecraftForge.EVENT_BUS.unregister(this);
				}
			}.start(world, (int) 8);
		}
		if (entity instanceof PlayerEntity)
			((PlayerEntity) entity).getCooldownTracker().setCooldown(((itemstack)).getItem(), (int) 30);
	}
}
