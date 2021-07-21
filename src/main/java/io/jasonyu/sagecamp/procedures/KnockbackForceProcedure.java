package io.jasonyu.sagecamp.procedures;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.IWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.block.Blocks;

import java.util.Map;
import java.util.HashMap;

import io.jasonyu.sagecamp.SageCampModElements;
import io.jasonyu.sagecamp.SageCampMod;

@SageCampModElements.ModElement.Tag
public class KnockbackForceProcedure extends SageCampModElements.ModElement {
	public KnockbackForceProcedure(SageCampModElements instance) {
		super(instance, 5);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				SageCampMod.LOGGER.warn("Failed to load dependency entity for procedure KnockbackForce!");
			return;
		}
		if (dependencies.get("sourceentity") == null) {
			if (!dependencies.containsKey("sourceentity"))
				SageCampMod.LOGGER.warn("Failed to load dependency sourceentity for procedure KnockbackForce!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		Entity sourceentity = (Entity) dependencies.get("sourceentity");
		SageCampMod.LOGGER.info((new java.text.DecimalFormat("##.##").format(((Math.sin(Math.toRadians((sourceentity.rotationYaw))) + 180) * 2))));
		SageCampMod.LOGGER
				.info((new java.text.DecimalFormat("##.##").format(Math.abs((Math.sin(Math.toRadians((sourceentity.rotationPitch))) * 2)))));
		SageCampMod.LOGGER.info((new java.text.DecimalFormat("##.##").format((Math.cos(Math.toRadians((sourceentity.rotationYaw))) * 2))));
		if ((((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getHeldItemMainhand() : ItemStack.EMPTY)
				.getItem() == new ItemStack(Blocks.AIR, (int) (1)).getItem())) {
			entity.setMotion((Math.sin(Math.toRadians(((sourceentity.rotationYaw) + 180))) * 2),
					Math.abs((Math.sin(Math.toRadians((sourceentity.rotationPitch))) * 3)),
					(Math.cos(Math.toRadians((sourceentity.rotationYaw))) * 2));
		}
	}

	@SubscribeEvent
	public void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
		Entity entity = event.getTarget();
		PlayerEntity sourceentity = event.getPlayer();
		if (event.getHand() != sourceentity.getActiveHand()) {
			return;
		}
		double i = event.getPos().getX();
		double j = event.getPos().getY();
		double k = event.getPos().getZ();
		IWorld world = event.getWorld();
		Map<String, Object> dependencies = new HashMap<>();
		dependencies.put("x", i);
		dependencies.put("y", j);
		dependencies.put("z", k);
		dependencies.put("world", world);
		dependencies.put("entity", entity);
		dependencies.put("sourceentity", sourceentity);
		dependencies.put("event", event);
		this.executeProcedure(dependencies);
	}
}
