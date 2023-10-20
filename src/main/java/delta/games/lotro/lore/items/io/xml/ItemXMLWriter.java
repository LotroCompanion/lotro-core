package delta.games.lotro.lore.items.io.xml;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.common.enums.EquipmentCategory;
import delta.games.lotro.common.enums.ItemClass;
import delta.games.lotro.common.requirements.io.xml.UsageRequirementsXMLWriter;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLWriter;
import delta.games.lotro.common.utils.valueTables.QualityBasedValuesTable;
import delta.games.lotro.lore.items.Armour;
import delta.games.lotro.lore.items.ArmourType;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemBinding;
import delta.games.lotro.lore.items.ItemCategory;
import delta.games.lotro.lore.items.ItemQuality;
import delta.games.lotro.lore.items.ItemSturdiness;
import delta.games.lotro.lore.items.Weapon;
import delta.games.lotro.lore.items.WeaponType;
import delta.games.lotro.lore.items.carryalls.CarryAll;
import delta.games.lotro.lore.items.details.io.xml.ItemDetailsXMLWriter;
import delta.games.lotro.lore.items.effects.ItemEffectsManager;
import delta.games.lotro.lore.items.effects.io.xml.ItemEffectsXmlIO;
import delta.games.lotro.lore.items.essences.Essence;
import delta.games.lotro.lore.items.essences.EssencesSlotsSetup;
import delta.games.lotro.lore.items.legendary.Legendary;
import delta.games.lotro.lore.items.legendary.LegendaryAttrs;
import delta.games.lotro.lore.items.scaling.Munging;
import delta.games.lotro.lore.items.weapons.WeaponSpeedEntry;

/**
 * Writes LOTRO items to XML files.
 * @author DAM
 */
public class ItemXMLWriter
{
  private ItemDetailsXMLWriter _detailsWriter;

  /**
   * Constructor.
   */
  public ItemXMLWriter()
  {
    _detailsWriter=new ItemDetailsXMLWriter();
  }

  /**
   * Write a file with items.
   * @param toFile Output file.
   * @param items Items to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeItemsFile(File toFile, List<Item> items)
  {
    ItemXMLWriter writer=new ItemXMLWriter();
    Collections.sort(items,new IdentifiableComparator<Item>());
    boolean ok=writer.writeItems(toFile,items,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write items to a XML file.
   * @param outFile Output file.
   * @param items Items to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  private boolean writeItems(File outFile, final List<Item> items, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",ItemXMLConstants.ITEMS_TAG,new AttributesImpl());
        for(Item item : items)
        {
          write(hd,item);
        }
        hd.endElement("","",ItemXMLConstants.ITEMS_TAG);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write an item to the given XML stream.
   * @param hd XML output stream.
   * @param item Item to write.
   * @throws Exception If an error occurs.
   */
  private void write(TransformerHandler hd, Item item) throws Exception
  {
    AttributesImpl itemAttrs=new AttributesImpl();

    // Identifier
    int id=item.getIdentifier();
    if (id!=0)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_KEY_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    // Set identifier
    String setIdentifier=item.getSetKey();
    if (setIdentifier!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_SET_ID_ATTR,XmlWriter.CDATA,setIdentifier);
    }
    // Name
    String name=item.getName();
    if (name!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Icon
    String icon=item.getIcon();
    if (icon!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_ICON_ATTR,XmlWriter.CDATA,icon);
    }
    // Item level
    Integer itemLevel=item.getItemLevel();
    if ((itemLevel!=null) && (itemLevel.intValue()>0))
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(itemLevel.intValue()));
    }
    // Item level offset
    Integer itemLevelOffset=item.getItemLevelOffset();
    if ((itemLevelOffset!=null) && (itemLevelOffset.intValue()>0))
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_LEVEL_OFFSET_ATTR,XmlWriter.CDATA,String.valueOf(itemLevelOffset.intValue()));
    }
    // Category
    ItemCategory category=item.getCategory();
    // Slot
    EquipmentLocation slot=item.getEquipmentLocation();
    if (slot!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_SLOT_ATTR,XmlWriter.CDATA,String.valueOf(slot.getKey()));
    }
    // Category
    if (category!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_CATEGORY_ATTR,XmlWriter.CDATA,category.name());
    }
    // Item class
    ItemClass itemClass=item.getItemClass();
    if (itemClass!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_CLASS_ATTR,XmlWriter.CDATA,String.valueOf(itemClass.getCode()));
    }
    // Tier
    Integer tier=item.getTier();
    if (tier!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_TIER_ATTR,XmlWriter.CDATA,tier.toString());
    }
    // Equipment category
    EquipmentCategory equipmentCategory=item.getEquipmentCategory();
    if (equipmentCategory!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_EQUIPMENT_CATEGORY_ATTR,XmlWriter.CDATA,String.valueOf(equipmentCategory.getCode()));
    }
    // Binding
    ItemBinding binding=item.getBinding();
    if (binding!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_BINDING_ATTR,XmlWriter.CDATA,binding.getKey());
    }
    // Unique
    boolean unique=item.isUnique();
    if (unique)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_UNIQUE_ATTR,XmlWriter.CDATA,String.valueOf(unique));
    }
    // Durability
    Integer durability=item.getDurability();
    if (durability!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_DURABILITY_ATTR,XmlWriter.CDATA,String.valueOf(durability.intValue()));
    }
    // Sturdiness
    ItemSturdiness sturdiness=item.getSturdiness();
    if (sturdiness!=null)
    {
      String sturdinessKey=sturdiness.getKey();
      if (sturdinessKey!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_STURDINESS_ATTR,XmlWriter.CDATA,sturdinessKey);
      }
    }
    // Quality
    ItemQuality quality=item.getQuality();
    if (quality!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_QUALITY_ATTR,XmlWriter.CDATA,quality.getKey());
    }
    // Usage requirements
    UsageRequirementsXMLWriter.write(itemAttrs,item.getUsageRequirements());
    // Description
    String description=item.getDescription();
    if ((description!=null) && (description.length()>0))
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    }
    // Value table ID
    QualityBasedValuesTable valueTable=item.getValueTable();
    if (valueTable!=null)
    {
      int valueTableId=valueTable.getIdentifier();
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_VALUE_TABLE_ID_ATTR,XmlWriter.CDATA,String.valueOf(valueTableId));
    }
    // Stack max
    Integer stackMax=item.getStackMax();
    if (stackMax!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_STACK_MAX_ATTR,XmlWriter.CDATA,String.valueOf(stackMax.intValue()));
    }
    // Essence slot count
    EssencesSlotsSetup setup=item.getEssenceSlotsSetup();
    if (setup!=null)
    {
      String setupStr=setup.toPersistenceString();
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_ESSENCE_SLOTS_ATTR,XmlWriter.CDATA,setupStr);
    }
    // Munging
    Munging munging=item.getMunging();
    if (munging!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_SCALING_ATTR,XmlWriter.CDATA,munging.asString());
    }
    // Armor specific:
    if (item instanceof Armour)
    {
      Armour armour=(Armour)item;
      ArmourType type=armour.getArmourType();
      if (type!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ARMOUR_TYPE_ATTR,XmlWriter.CDATA,type.getKey());
      }
    }
    // Weapon specific:
    else if (item instanceof Weapon)
    {
      Weapon weapon=(Weapon)item;
      // DPS
      float dps=weapon.getDPS();
      itemAttrs.addAttribute("","",ItemXMLConstants.DPS_ATTR,XmlWriter.CDATA,String.valueOf(dps));
      // DPS table ID
      QualityBasedValuesTable dpsTable=weapon.getDPSTable();
      if (dpsTable!=null)
      {
        int dpsTableId=dpsTable.getIdentifier();
        itemAttrs.addAttribute("","",ItemXMLConstants.DPS_TABLE_ID_ATTR,XmlWriter.CDATA,String.valueOf(dpsTableId));
      }
      // Damage min/max
      int minDamage=weapon.getMinDamage();
      itemAttrs.addAttribute("","",ItemXMLConstants.MIN_DAMAGE_ATTR,XmlWriter.CDATA,String.valueOf(minDamage));
      int maxDamage=weapon.getMaxDamage();
      itemAttrs.addAttribute("","",ItemXMLConstants.MAX_DAMAGE_ATTR,XmlWriter.CDATA,String.valueOf(maxDamage));
      // Damage type
      DamageType type=weapon.getDamageType();
      if (type!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.DAMAGE_TYPE_ATTR,XmlWriter.CDATA,type.getKey());
      }
      // Weapon type
      WeaponType weaponType=weapon.getWeaponType();
      if (weaponType!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.WEAPON_TYPE_ATTR,XmlWriter.CDATA,weaponType.getKey());
      }
      // Weapon speed
      WeaponSpeedEntry speed=weapon.getSpeed();
      if (speed!=null)
      {
        int code=speed.getWeaponSpeedCode();
        itemAttrs.addAttribute("","",ItemXMLConstants.WEAPON_SPEED_ATTR,XmlWriter.CDATA,String.valueOf(code));
      }
    }
    if (item instanceof Legendary)
    {
      Legendary legendary=(Legendary)item;
      LegendaryAttrs attrs=legendary.getLegendaryAttrs();
      // Main legacy ID
      int mainLegacyId=attrs.getMainLegacyId();
      if (mainLegacyId!=0)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.MAIN_LEGACY_ID_ATTR,XmlWriter.CDATA,String.valueOf(mainLegacyId));
      }
      // Combat DPS level
      int combatDPSLevel=attrs.getCombatDPSLevel();
      if (combatDPSLevel!=0)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.MAIN_LEGACY_COMBAT_DPS_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(combatDPSLevel));
      }
      // Combat Property Mod level
      int combatPropertyModLevel=attrs.getCombatPropertyModLevel();
      if (combatPropertyModLevel!=0)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.MAIN_LEGACY_COMBAT_PROPERTY_MOD_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(combatPropertyModLevel));
      }
    }
    else if (item instanceof CarryAll)
    {
      CarryAll carryAll=(CarryAll)item;
      // Max items
      int maxItems=carryAll.getMaxItems();
      itemAttrs.addAttribute("","",ItemXMLConstants.CARRY_ALL_MAX_ITEMS_ATTR,XmlWriter.CDATA,String.valueOf(maxItems));
      // Item stack max
      int itemStackMax=carryAll.getItemStackMax();
      itemAttrs.addAttribute("","",ItemXMLConstants.CARRY_ALL_ITEM_STACK_MAX_ATTR,XmlWriter.CDATA,String.valueOf(itemStackMax));
    }
    else if (item instanceof Essence)
    {
      Essence essence=(Essence)item;
      int type=essence.getType().getCode();
      itemAttrs.addAttribute("","",ItemXMLConstants.ESSENCE_TYPE_ATTR,XmlWriter.CDATA,String.valueOf(type));
    }
    hd.startElement("","",ItemXMLConstants.ITEM_TAG,itemAttrs);

    // Stats
    StatsProvider statsProvider=item.getStatsProvider();
    if (statsProvider!=null)
    {
      hd.startElement("","",ItemXMLConstants.STATS_TAG,new AttributesImpl());
      StatsProviderXMLWriter.writeXml(hd,statsProvider);
      hd.endElement("","",ItemXMLConstants.STATS_TAG);
    }

    // Details
    _detailsWriter.writeDetails(hd,item);

    // Effects
    ItemEffectsManager effectsMgr=item.getEffects();
    if (effectsMgr!=null)
    {
      ItemEffectsXmlIO.writeItemEffects(hd,effectsMgr);
    }
    hd.endElement("","",ItemXMLConstants.ITEM_TAG);
  }
}
