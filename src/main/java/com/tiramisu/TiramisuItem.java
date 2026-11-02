package com.tiramisu;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class TiramisuItem extends Item {
	// Animasyon kareleriyle (6 tick'lik adımlar) eşleşen çatal sesleri
	private static final int[] DIP_TICKS = {7, 13, 31, 37}; // çatal tatlıya batıyor / koparıyor
	private static final int[] LIFT_TICKS = {22, 28, 40};   // çatal ağza doğru kalkıyor

	public TiramisuItem(Settings settings) {
		super(settings);
	}

	@Override
	public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
		if (!world.isClient) {
			int elapsed = stack.getMaxUseTime(user) - remainingUseTicks;
			for (int t : DIP_TICKS) {
				if (elapsed == t) {
					world.playSound(null, user.getX(), user.getY(), user.getZ(),
							SoundEvents.BLOCK_CHAIN_HIT, SoundCategory.PLAYERS, 0.6f, 1.8f);
				}
			}
			for (int t : LIFT_TICKS) {
				if (elapsed == t) {
					world.playSound(null, user.getX(), user.getY(), user.getZ(),
							SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.25f, 1.4f);
				}
			}
		}
		super.usageTick(world, user, stack, remainingUseTicks);
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (!world.isClient) {
			world.playSound(null, user.getX(), user.getY(), user.getZ(),
					SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5f, 1.2f);
		}
		return super.finishUsing(stack, world, user);
	}
}
