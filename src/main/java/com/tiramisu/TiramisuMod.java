package com.tiramisu;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class TiramisuMod implements ModInitializer {
	public static final String MOD_ID = "tiramisu";

	public static final Item TIRAMISU = register("tiramisu", new Item.Settings()
			.maxCount(16)
			// 8 açlık (4 et budu), yüksek doygunluk
			.food(
					new FoodComponent.Builder().nutrition(8).saturationModifier(0.7f).build(),
					// 2.4 sn = 48 tick yeme süresi. Animasyon kareleri bu süreye göre ayarlı.
					ConsumableComponents.food().consumeSeconds(2.4f).build())
			// Yedikten sonra boş kase geri döner
			.useRemainder(Items.BOWL));

	private static Item register(String name, Item.Settings settings) {
		Identifier id = Identifier.of(MOD_ID, name);
		RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);
		return Registry.register(Registries.ITEM, key, new TiramisuItem(settings.registryKey(key)));
	}

	@Override
	public void onInitialize() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
				.register(entries -> entries.add(TIRAMISU));
	}
}
