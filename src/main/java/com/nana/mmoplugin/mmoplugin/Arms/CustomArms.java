package com.nana.mmoplugin.mmoplugin.Arms;


import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CustomArms {
    private String BaseArticle;
    private String Name;
    private Boolean UnBreakable;
    private List<String> Lore;
    private List<String> EnchantList;
    private List<Map<?,?>> AttributeMap;

    public CustomArms(FileConfiguration config,String path) {
        this.BaseArticle = config.getString(path+".BaseArticle");
        this.Name = config.getString(path+".Name");
        this.UnBreakable = config.getBoolean(path+".UnBreakable");
        this.Lore = config.getStringList(path+".Lore");
        this.EnchantList = config.getStringList(path+".Enchant");
        this.AttributeMap = config.getMapList(path+".Attribute");
    }

    // 获取自定义物品ItemStack
    public ItemStack CreatCustomArms(){

        ItemStack itemStack = new ItemStack(Material.getMaterial(BaseArticle));
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(Name);
        itemMeta.setUnbreakable(UnBreakable);
        itemMeta.setLore(Lore);


        itemMeta = addEnchant(itemMeta.clone(),EnchantList);

        itemStack.setItemMeta(itemMeta);

        itemStack = addAttribute(itemStack.clone(),AttributeMap);

        return itemStack;
    }

    // 为物品附魔
    private ItemMeta addEnchant(ItemMeta itemMeta,List<String> EnchantList){
        for (String enchants:
             EnchantList) {

            String enchant = enchants.substring(0,enchants.indexOf(','));
            int leval = Integer.parseInt(enchants.substring(enchants.indexOf(',')+1));
            itemMeta.addEnchant(Enchantment.getByName(enchant),leval,true);
        }

        return itemMeta;
    }

    private ItemStack addAttribute(ItemStack itemStack,List<Map<?,?>> AttributeMap){
        ItemMeta itemMeta = itemStack.getItemMeta();

        for (Map<?,?> attribute:
             AttributeMap) {
            //自定义的Attribute
            String AttributeName = ((String) attribute.get("AttributeName"));
            String Name = (String) attribute.get("Name");
            Double Amount = (double) (int) attribute.get("Amount");
            String Operation = (String) attribute.get("Operation");
            AttributeModifier.Operation operation = AttributeModifier.Operation.valueOf(Operation);
            EquipmentSlot equipmentSlot = EquipmentSlot.HAND;
            //查询是否有Slot
            try {
                String Slot = (String) attribute.get("Slot");
                equipmentSlot = EquipmentSlot.valueOf(Slot);
            }catch (Exception e){
                // 不做任何事
            }

            AttributeModifier attributeModifier = new AttributeModifier(UUID.randomUUID(),Name,Amount,operation,equipmentSlot);
            itemMeta.addAttributeModifier(Attribute.valueOf(AttributeName),attributeModifier);
        }


        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

}
