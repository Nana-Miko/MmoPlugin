package com.nana.mmoplugin.mmoplugin.util;

import com.nana.mmoplugin.mmoplugin.util.Define.MmoUtil;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class itemUtil implements MmoUtil {

    public static String hasLore(ItemStack itemStack, String ContainsLore) {
        if (itemStack == null) {
            return null;
        }

        if (!itemStack.hasItemMeta()) {
            return null;
        }
        List<String> loreList = itemStack.getItemMeta().getLore();
        ;

        if (loreList == null) {
            return null;
        }

        for (String lore :
                loreList) {
            if (lore.contains(ContainsLore)) {
                //System.out.println(lore);
                String indexLore = lore.substring(lore.indexOf(ContainsLore) + ContainsLore.length());
                //System.out.println(indexLore);
                return indexLore;
            }
        }

        return null;

    }


    public static Set<String> hasLore(ItemStack itemStack, String ContainsLore, Set<String> loreSet) {
        if (itemStack == null) {
            if (!loreSet.isEmpty()) {
                return loreSet;
            } else {
                return null;
            }
        }

        if (!itemStack.hasItemMeta()) {
            if (!loreSet.isEmpty()) {
                return loreSet;
            } else {
                return null;
            }
        }
        List<String> loreList = itemStack.getItemMeta().getLore();
        ;

        if (loreList == null) {
            if (!loreSet.isEmpty()) {
                return loreSet;
            } else {
                return null;
            }
        }

        for (String lore :
                loreList) {
            if (lore.contains(ContainsLore)) {
                //System.out.println(lore);
                String indexLore = lore.substring(lore.indexOf(ContainsLore) + ContainsLore.length());
                loreSet.add(indexLore);
                //System.out.println(indexLore);
            }
        }
        if (!loreSet.isEmpty()) {
            return loreSet;
        } else {
            return null;
        }

    }

    public static List<String> hasLore(ItemStack itemStack, String ContainsLore, List<String> LoreList) {
        if (itemStack == null) {
            if (!LoreList.isEmpty()) {
                return LoreList;
            } else {
                return null;
            }
        }

        if (!itemStack.hasItemMeta()) {
            if (!LoreList.isEmpty()) {
                return LoreList;
            } else {
                return null;
            }
        }
        List<String> loreList = itemStack.getItemMeta().getLore();
        ;

        if (loreList == null) {
            if (!LoreList.isEmpty()) {
                return LoreList;
            } else {
                return null;
            }
        }

        for (String lore :
                loreList) {
            if (lore.contains(ContainsLore)) {
                //System.out.println(lore);
                String indexLore = lore.substring(lore.indexOf(ContainsLore) + ContainsLore.length());
                LoreList.add(indexLore);
                //System.out.println(indexLore);
            }
        }
        if (!LoreList.isEmpty()) {
            return LoreList;
        } else {
            return null;
        }

    }

    public static Map<String,String> hasLore(ItemStack itemStack, Set<String> ContainsLoreSet, Map<String,String> loreMap) {
        if (itemStack == null) {
            if (!loreMap.isEmpty()) {
                return loreMap;
            } else {
                return null;
            }
        }

        if (!itemStack.hasItemMeta()) {
            if (!loreMap.isEmpty()) {
                return loreMap;
            } else {
                return null;
            }
        }
        List<String> loreList = itemStack.getItemMeta().getLore();
        ;

        if (loreList == null) {
            if (!loreMap.isEmpty()) {
                return loreMap;
            } else {
                return null;
            }
        }

        for (String lore :
                loreList) {
            for (String ContainsLore :
                    ContainsLoreSet) {
                if (lore.contains(ContainsLore)) {
                    //System.out.println(lore);
                    String indexLore = lore.substring(lore.indexOf(ContainsLore) + ContainsLore.length());
                    loreMap.put(ContainsLore,indexLore);
                    //System.out.println(indexLore);
                }
            }

        }
        if (!loreMap.isEmpty()) {
            return loreMap;
        } else {
            return null;
        }

    }

    public static Map<String,Double> hasLore(ItemStack itemStack, Set<String> ContainsLoreSet, Map<String,Double> loreMap,Boolean IsAdd) {
        if (itemStack == null) {
            if (!loreMap.isEmpty()) {
                return loreMap;
            } else {
                return null;
            }
        }

        if (!itemStack.hasItemMeta()) {
            if (!loreMap.isEmpty()) {
                return loreMap;
            } else {
                return null;
            }
        }
        List<String> loreList = itemStack.getItemMeta().getLore();
        ;

        if (loreList == null) {
            if (!loreMap.isEmpty()) {
                return loreMap;
            } else {
                return null;
            }
        }

        for (String lore :
                loreList) {
            for (String ContainsLore :
                    ContainsLoreSet) {
                if (lore.contains(ContainsLore)) {
                    //System.out.println(lore);
                    String indexLore = lore.substring(lore.indexOf(ContainsLore) + ContainsLore.length());
                    Double temp = new Double(indexLore);
                    if(!loreMap.containsKey(ContainsLore)){loreMap.put(ContainsLore,temp);}
                    else{loreMap.put(ContainsLore, loreMap.get(ContainsLore)+temp);}
                    //System.out.println(indexLore);
                }
            }

        }
        if (!loreMap.isEmpty()) {
            return loreMap;
        } else {
            return null;
        }

    }




}
