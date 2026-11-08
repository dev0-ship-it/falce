package com.example.scythemod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ScytheMod implements ModInitializer {
    public static final String MOD_ID = "scythemod";

    public static final Item SCYTHE = Registry.register(
            Registries.ITEM,
            Identifier.of(MOD_ID, "scythe"),
            new SwordItem(
                    ToolMaterials.NETHERITE,
                    new Item.Settings()
                            .fireproof()
                            .attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.NETHERITE, 4, -3.0f))
            )
    );

    // Create the scythe with its requested enchantments.
    public static ItemStack createEnchantedScythe(RegistryWrapper.WrapperLookup lookup) {
        ItemStack stack = new ItemStack(SCYTHE);
        RegistryWrapper.Impl<Enchantment> enchantments = lookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        stack.addEnchantment(enchantments.getOrThrow(Enchantments.SHARPNESS), 6);
        stack.addEnchantment(enchantments.getOrThrow(Enchantments.UNBREAKING), 5);
        stack.addEnchantment(enchantments.getOrThrow(Enchantments.MENDING), 5);
        stack.addEnchantment(enchantments.getOrThrow(Enchantments.FIRE_ASPECT), 3);
        return stack;
    }

    @Override
    public void onInitialize() {
        ItemGroup group = FabricItemGroup.builder()
                .icon(() -> new ItemStack(SCYTHE))
                .displayName(Text.translatable("itemGroup.scythemod"))
                .entries((context, entries) -> entries.add(createEnchantedScythe(context.lookup())))
                .build();
        Registry.register(Registries.ITEM_GROUP, Identifier.of(MOD_ID, "main"), group);
    }
}
