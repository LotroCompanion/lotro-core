package delta.games.lotro.lore.items.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLConstants;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLParser;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.money.io.xml.MoneyXMLParser;
import delta.games.lotro.lore.items.Armour;
import delta.games.lotro.lore.items.ArmourType;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemBinding;
import delta.games.lotro.lore.items.ItemCategory;
import delta.games.lotro.lore.items.ItemFactory;
import delta.games.lotro.lore.items.ItemPropertyNames;
import delta.games.lotro.lore.items.ItemQuality;
import delta.games.lotro.lore.items.ItemSturdiness;
import delta.games.lotro.lore.items.Weapon;
import delta.games.lotro.lore.items.WeaponType;
import delta.games.lotro.lore.items.essences.EssencesSet;
import delta.games.lotro.lore.items.legendary.Legendary;
import delta.games.lotro.lore.items.legendary.LegendaryAttrs;
import delta.games.lotro.lore.items.legendary.io.xml.LegendaryAttrsXMLParser;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Parser for item descriptions stored in XML.
 * @author DAM
 */
public class ItemXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed item or <code>null</code>.
   */
  public Item parseXML(File source)
  {
    Item item=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      item=parseItem(root);
    }
    return item;
  }

  /**
   * Build an item from an XML tag.
   * @param root Root XML tag.
   * @return An item.
   */
  public Item parseItem(Element root)
  {
    Item ret=null;
    NamedNodeMap attrs=root.getAttributes();

    // Category
    ItemCategory category=null;
    String categoryStr=DOMParsingTools.getStringAttribute(attrs,ItemXMLConstants.ITEM_CATEGORY_ATTR,null);
    if (categoryStr!=null)
    {
      category=ItemCategory.valueOf(categoryStr);
    }
    ret=ItemFactory.buildItem(category);
    ret.setCategory(category);
    // Icon
    String icon=DOMParsingTools.getStringAttribute(attrs,ItemXMLConstants.ITEM_ICON_ATTR,null);
    ret.setIcon(icon);
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,ItemXMLConstants.ITEM_KEY_ATTR,-1);
    ret.setIdentifier(id);
    // Set identifier
    String setId=DOMParsingTools.getStringAttribute(attrs,ItemXMLConstants.ITEM_SET_ID_ATTR,null);
    ret.setSetKey(setId);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,ItemXMLConstants.ITEM_NAME_ATTR,null);
    ret.setName(name);
    // Item level
    int itemLevel=DOMParsingTools.getIntAttribute(attrs,ItemXMLConstants.ITEM_LEVEL_ATTR,-1);
    if (itemLevel!=-1)
    {
      ret.setItemLevel(Integer.valueOf(itemLevel));
    }
    // Slot
    EquipmentLocation slot=null;
    String slotStr=DOMParsingTools.getStringAttribute(attrs,ItemXMLConstants.ITEM_SLOT_ATTR,null);
    if (slotStr!=null)
    {
      slot=EquipmentLocation.getByKey(slotStr);
    }
    ret.setEquipmentLocation(slot);
    // Sub-category
    String subCategory=DOMParsingTools.getStringAttribute(attrs,ItemXMLConstants.ITEM_SUBCATEGORY_ATTR,null);
    ret.setSubCategory(subCategory);
    // Item binding
    ItemBinding binding=null;
    String bindingStr=DOMParsingTools.getStringAttribute(attrs,ItemXMLConstants.ITEM_BINDING_ATTR,null);
    if (bindingStr!=null)
    {
      binding=ItemBinding.valueOf(bindingStr);
    }
    ret.setBinding(binding);
    // Uniqueness
    boolean unique=DOMParsingTools.getBooleanAttribute(attrs,ItemXMLConstants.ITEM_UNIQUE_ATTR,false);
    ret.setUnique(unique);
    // Bonuses
    List<Element> bonusTags=DOMParsingTools.getChildTagsByName(root,ItemXMLConstants.BONUS_TAG,false);
    List<String> bonuses=new ArrayList<String>();
    if (bonusTags!=null)
    {
      for(Element bonusTag : bonusTags)
      {
        String value=DOMParsingTools.getStringAttribute(bonusTag.getAttributes(),ItemXMLConstants.BONUS_VALUE_ATTR,null);
        if (value!=null)
        {
          bonuses.add(value);
        }
      }
    }
    ret.setBonus(bonuses);
    // Properties
    List<Element> propertyTags=DOMParsingTools.getChildTagsByName(root,ItemXMLConstants.PROPERTY_TAG,false);
    if ((propertyTags!=null) && (propertyTags.size()>0))
    {
      for(Element propertyTag : propertyTags)
      {
        NamedNodeMap propAttrs=propertyTag.getAttributes();

        String propertyName=DOMParsingTools.getStringAttribute(propAttrs,ItemXMLConstants.PROPERTY_KEY_ATTR,null);
        String propertyValue=DOMParsingTools.getStringAttribute(propAttrs,ItemXMLConstants.PROPERTY_VALUE_ATTR,null);
        ret.setProperty(propertyName,propertyValue);
      }
      if (icon==null)
      {
        String iconId=ret.getProperty(ItemPropertyNames.ICON_ID);
        String backgroundIconId=ret.getProperty(ItemPropertyNames.BACKGROUND_ICON_ID);
        if ((iconId!=null) && (backgroundIconId!=null))
        {
          icon=iconId+"-"+backgroundIconId;
          ret.setIcon(icon);
          ret.removeProperty(ItemPropertyNames.ICON_ID);
          ret.removeProperty(ItemPropertyNames.BACKGROUND_ICON_ID);
        }
      }
    }
    // Stats
    Element statsTag=DOMParsingTools.getChildTagByName(root,BasicStatsSetXMLConstants.STATS_TAG);
    if (statsTag!=null)
    {
      BasicStatsSet stats=BasicStatsSetXMLParser.parseStats(statsTag);
      ret.getStats().addStats(stats);
    }
    // Durability
    int durability=DOMParsingTools.getIntAttribute(attrs,ItemXMLConstants.ITEM_DURABILITY_ATTR,-1);
    if (durability!=-1)
    {
      ret.setDurability(Integer.valueOf(durability));
    }
    // Sturdiness
    ItemSturdiness sturdiness=null;
    String sturdinessStr=DOMParsingTools.getStringAttribute(attrs,ItemXMLConstants.ITEM_STURDINESS_ATTR,null);
    if (sturdinessStr!=null)
    {
      sturdiness=ItemSturdiness.valueOf(sturdinessStr);
    }
    ret.setSturdiness(sturdiness);
    // Quality
    ItemQuality quality=null;
    String qualityStr=DOMParsingTools.getStringAttribute(attrs,ItemXMLConstants.ITEM_QUALITY_ATTR,null);
    if (qualityStr!=null)
    {
      quality=ItemQuality.fromCode(qualityStr);
    }
    ret.setQuality(quality);
    // Minimum level
    int minimumLevel=DOMParsingTools.getIntAttribute(attrs,ItemXMLConstants.ITEM_MINLEVEL_ATTR,-1);
    if (minimumLevel!=-1)
    {
      ret.setMinLevel(Integer.valueOf(minimumLevel));
    }
    // Maximum level
    int maximumLevel=DOMParsingTools.getIntAttribute(attrs,ItemXMLConstants.ITEM_MAXLEVEL_ATTR,-1);
    if (maximumLevel!=-1)
    {
      ret.setMaxLevel(Integer.valueOf(maximumLevel));
    }
    // Required class
    CharacterClass cClass=null;
    String requiredClass=DOMParsingTools.getStringAttribute(attrs,ItemXMLConstants.ITEM_REQUIRED_CLASS_ATTR,null);
    if (requiredClass!=null)
    {
      cClass=CharacterClass.getByKey(requiredClass);
    }
    ret.setRequiredClass(cClass);
    // Full description
    String description=DOMParsingTools.getStringAttribute(attrs,ItemXMLConstants.ITEM_DESCRIPTION_ATTR,null);
    ret.setDescription(description);

    // Handle legendary items
    if (ret instanceof Legendary)
    {
      LegendaryAttrs legAttrs=((Legendary)ret).getLegendaryAttrs();
      LegendaryAttrsXMLParser.read(legAttrs,root);
    }

    // Money
    MoneyXMLParser.loadMoney(root,ret.getValue());
    // Stack max
    int stackMax=DOMParsingTools.getIntAttribute(attrs,ItemXMLConstants.ITEM_STACK_MAX_ATTR,-1);
    if (stackMax!=-1)
    {
      ret.setStackMax(Integer.valueOf(stackMax));
    }
    // Essence slots
    int nbEssenceSlots=DOMParsingTools.getIntAttribute(attrs,ItemXMLConstants.ITEM_ESSENCE_SLOTS,0);
    ret.setEssenceSlots(nbEssenceSlots);

    // Essences
    Element essencesTag=DOMParsingTools.getChildTagByName(root,ItemXMLConstants.ESSENCES_TAG);
    if (essencesTag!=null)
    {
      List<Element> essenceTags=DOMParsingTools.getChildTagsByName(essencesTag,ItemXMLConstants.ITEM_TAG,false);
      List<Item> essences=new ArrayList<Item>();
      for(Element essenceTag : essenceTags)
      {
        Item essence=parseItem(essenceTag);
        essences.add(essence);
      }
      if (essences.size()>0)
      {
        int slots=Math.max(ret.getEssenceSlots(),essences.size());
        EssencesSet essencesSet=new EssencesSet(slots);
        int index=0;
        for(Item essence : essences)
        {
          essencesSet.setEssence(index,essence);
          index++;
        }
        ret.setEssences(essencesSet);
      }
    }

    // Armour specific:
    if (category==ItemCategory.ARMOUR)
    {
      Armour armour=(Armour)ret;
      int armourValue=DOMParsingTools.getIntAttribute(attrs,ItemXMLConstants.ARMOUR_ATTR,0);
      if (armourValue!=0)
      {
        ret.getStats().addStat(STAT.ARMOUR,new FixedDecimalsInteger(armourValue));
      }
      String armourTypeStr=DOMParsingTools.getStringAttribute(attrs,ItemXMLConstants.ARMOUR_TYPE_ATTR,null);
      if (armourTypeStr!=null)
      {
        ArmourType type=ArmourType.getArmourTypeByKey(armourTypeStr);
        armour.setArmourType(type);
      }
    }
    // Weapon specific:
    if ((category==ItemCategory.WEAPON) || (category==ItemCategory.LEGENDARY_WEAPON))
    {
      Weapon weapon=(Weapon)ret;
      float dps=DOMParsingTools.getFloatAttribute(attrs,ItemXMLConstants.DPS_ATTR,0.0f);
      weapon.setDPS(dps);
      int minDamage=DOMParsingTools.getIntAttribute(attrs,ItemXMLConstants.MIN_DAMAGE_ATTR,0);
      weapon.setMinDamage(minDamage);
      int maxDamage=DOMParsingTools.getIntAttribute(attrs,ItemXMLConstants.MAX_DAMAGE_ATTR,0);
      weapon.setMaxDamage(maxDamage);
      String damageTypeStr=DOMParsingTools.getStringAttribute(attrs,ItemXMLConstants.DAMAGE_TYPE_ATTR,null);
      if (damageTypeStr!=null)
      {
        DamageType type=DamageType.getDamageTypeByKey(damageTypeStr);
        weapon.setDamageType(type);
      }
      String weaponTypeStr=DOMParsingTools.getStringAttribute(attrs,ItemXMLConstants.WEAPON_TYPE_ATTR,null);
      if (weaponTypeStr!=null)
      {
        WeaponType type=WeaponType.getWeaponType(weaponTypeStr);
        weapon.setWeaponType(type);
      }
    }
    return ret;
  }
}
