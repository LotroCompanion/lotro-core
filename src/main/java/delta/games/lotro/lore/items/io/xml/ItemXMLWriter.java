package delta.games.lotro.lore.items.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.base.io.xml.StatsManagerXMLWriter;
import delta.games.lotro.common.colors.ColorDescription;
import delta.games.lotro.common.id.InternalGameId;
import delta.games.lotro.common.money.QualityBasedValueLookupTable;
import delta.games.lotro.common.money.io.xml.MoneyXMLWriter;
import delta.games.lotro.common.requirements.io.xml.UsageRequirementXMLConstants;
import delta.games.lotro.common.requirements.io.xml.UsageRequirementsXMLWriter;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLWriter;
import delta.games.lotro.lore.items.Armour;
import delta.games.lotro.lore.items.ArmourInstance;
import delta.games.lotro.lore.items.ArmourType;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemBinding;
import delta.games.lotro.lore.items.ItemCategory;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.ItemPropertyNames;
import delta.games.lotro.lore.items.ItemQuality;
import delta.games.lotro.lore.items.ItemSturdiness;
import delta.games.lotro.lore.items.Weapon;
import delta.games.lotro.lore.items.WeaponInstance;
import delta.games.lotro.lore.items.WeaponType;
import delta.games.lotro.lore.items.comparators.ItemIdComparator;
import delta.games.lotro.lore.items.essences.EssencesSet;
import delta.games.lotro.lore.items.legendary.Legendary;
import delta.games.lotro.lore.items.legendary.LegendaryAttrs;
import delta.games.lotro.lore.items.legendary.LegendaryInstance;
import delta.games.lotro.lore.items.legendary.LegendaryInstanceAttrs;
import delta.games.lotro.lore.items.legendary.io.xml.LegendaryInstanceAttrsXMLWriter;
import delta.games.lotro.lore.items.legendary2.LegendaryInstance2;
import delta.games.lotro.lore.items.legendary2.LegendaryInstanceAttrs2;
import delta.games.lotro.lore.items.legendary2.io.xml.LegendaryInstance2AttrsXMLWriter;

/**
 * Writes LOTRO items to XML files.
 * @author DAM
 */
public class ItemXMLWriter
{
  /**
   * Constructor.
   */
  public ItemXMLWriter()
  {
    // Nothing!
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
    Collections.sort(items,new ItemIdComparator());
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
  public boolean writeItems(File outFile, final List<Item> items, String encoding)
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
   * @param instance Item to write.
   * @throws Exception If an error occurs.
   */
  public void writeItemInstance(TransformerHandler hd, ItemInstance<? extends Item> instance) throws Exception
  {
    write(hd,instance,instance.getReference());
  }

  /**
   * Write an item to the given XML stream.
   * @param hd XML output stream.
   * @param item Item to write.
   * @throws Exception If an error occurs.
   */
  public void write(TransformerHandler hd, Item item) throws Exception
  {
    write(hd,null,item);
  }

  /**
   * Write an item to the given XML stream.
   * @param hd XML output stream.
   * @param item Item to write.
   * @param instance Write an instance or a model.
   * @throws Exception If an error occurs.
   */
  private void write(TransformerHandler hd, ItemInstance<? extends Item> instance, Item item) throws Exception
  {
    AttributesImpl itemAttrs=new AttributesImpl();

    boolean isInstance=(instance!=null);

    // Identifier
    int id=item.getIdentifier();
    if (id!=0)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_KEY_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    if (!isInstance)
    {
      // Set identifier
      String setIdentifier=item.getSetKey();
      if (setIdentifier!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_SET_ID_ATTR,XmlWriter.CDATA,setIdentifier);
      }
    }
    // Name
    String name=item.getName();
    if (name!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_NAME_ATTR,XmlWriter.CDATA,name);
    }
    if (!isInstance)
    {
      // Icon
      String icon=item.getIcon();
      if (icon!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_ICON_ATTR,XmlWriter.CDATA,icon);
      }
    }
    // Item level
    Integer itemLevel=(isInstance?instance.getItemLevel():item.getItemLevel());
    if ((itemLevel!=null) && (itemLevel.intValue()>0))
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(itemLevel.intValue()));
    }
    // Item level offset
    if (!isInstance)
    {
      Integer itemLevelOffset=item.getItemLevelOffset();
      if ((itemLevelOffset!=null) && (itemLevelOffset.intValue()>0))
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_LEVEL_OFFSET_ATTR,XmlWriter.CDATA,String.valueOf(itemLevelOffset.intValue()));
      }
    }
    ItemCategory category=item.getCategory();
    if (!isInstance)
    {
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
      // Sub-category
      String subCategory=item.getSubCategory();
      if (subCategory!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_SUBCATEGORY_ATTR,XmlWriter.CDATA,subCategory);
      }
      // Binding
      ItemBinding binding=item.getBinding();
      if (binding!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_BINDING_ATTR,XmlWriter.CDATA,binding.name());
      }
      // Unique
      boolean unique=item.isUnique();
      if (unique)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_UNIQUE_ATTR,XmlWriter.CDATA,String.valueOf(unique));
      }
    }
    // Durability
    Integer durability=(isInstance?instance.getDurability():item.getDurability());
    if (durability!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_DURABILITY_ATTR,XmlWriter.CDATA,String.valueOf(durability.intValue()));
    }
    if (!isInstance)
    {
      // Sturdiness
      ItemSturdiness sturdiness=item.getSturdiness();
      if (sturdiness!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_STURDINESS_ATTR,XmlWriter.CDATA,sturdiness.name());
      }
      // Quality
      ItemQuality quality=item.getQuality();
      if (quality!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_QUALITY_ATTR,XmlWriter.CDATA,quality.getKey());
      }
    }
    if (isInstance)
    {
      // Minimum level
      Integer minLevel=(isInstance?instance.getMinLevel():item.getMinLevel());
      if (minLevel!=null)
      {
        itemAttrs.addAttribute("","",UsageRequirementXMLConstants.MIN_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(minLevel.intValue()));
      }
    }
    else
    {
      UsageRequirementsXMLWriter.write(itemAttrs,item.getUsageRequirements());
    }
    if (!isInstance)
    {
      // Description
      String description=item.getDescription();
      if ((description!=null) && (description.length()>0))
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
      }
      // Value table ID
      QualityBasedValueLookupTable valueTable=item.getValueTable();
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
      int nbEssenceSlots=item.getEssenceSlots();
      if (nbEssenceSlots>0)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_ESSENCE_SLOTS_ATTR,XmlWriter.CDATA,String.valueOf(nbEssenceSlots));
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
        if (instance instanceof ArmourInstance)
        {
          // Nothing!
        }
      }
      // Weapon specific:
      else if (item instanceof Weapon)
      {
        Weapon weapon=(Weapon)item;
        float dps=weapon.getDPS();
        itemAttrs.addAttribute("","",ItemXMLConstants.DPS_ATTR,XmlWriter.CDATA,String.valueOf(dps));
        int minDamage=weapon.getMinDamage();
        itemAttrs.addAttribute("","",ItemXMLConstants.MIN_DAMAGE_ATTR,XmlWriter.CDATA,String.valueOf(minDamage));
        int maxDamage=weapon.getMaxDamage();
        itemAttrs.addAttribute("","",ItemXMLConstants.MAX_DAMAGE_ATTR,XmlWriter.CDATA,String.valueOf(maxDamage));
        DamageType type=weapon.getDamageType();
        if (type!=null)
        {
          itemAttrs.addAttribute("","",ItemXMLConstants.DAMAGE_TYPE_ATTR,XmlWriter.CDATA,type.getKey());
        }
        WeaponType weaponType=weapon.getWeaponType();
        if (weaponType!=null)
        {
          itemAttrs.addAttribute("","",ItemXMLConstants.WEAPON_TYPE_ATTR,XmlWriter.CDATA,weaponType.getKey());
        }
        if (instance instanceof WeaponInstance)
        {
          // Nothing!
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
        // Main legacy base rank
        int mainLegacyBaseRank=attrs.getMainLegacyBaseRank();
        if (mainLegacyBaseRank!=0)
        {
          itemAttrs.addAttribute("","",ItemXMLConstants.MAIN_LEGACY_BASE_RANK_ATTR,XmlWriter.CDATA,String.valueOf(mainLegacyBaseRank));
        }
      }
    }
    if (isInstance)
    {
      // Instance ID
      InternalGameId instanceId=instance.getInstanceId();
      if (instanceId!=null)
      {
        String instanceIdStr=instanceId.asPersistedString();
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_INSTANCE_ID_ATTR,XmlWriter.CDATA,instanceIdStr);
      }
      // Birth name
      String birthName=instance.getBirthName();
      if (birthName!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_BIRTH_NAME_ATTR,XmlWriter.CDATA,birthName);
      }
      // Crafter name
      String crafterName=instance.getCrafterName();
      if (crafterName!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_CRAFTER_NAME_ATTR,XmlWriter.CDATA,crafterName);
      }
      // Color
      ColorDescription color=instance.getColor();
      if (color!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_COLOR_CODE_ATTR,XmlWriter.CDATA,String.valueOf(color.getCode()));
      }
      // Bound to
      InternalGameId boundTo=instance.getBoundTo();
      if (boundTo!=null)
      {
        String boundToStr=boundTo.asPersistedString();
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_BOUND_TO_ATTR,XmlWriter.CDATA,boundToStr);
      }
      // Time
      Long time=instance.getTime();
      if (time!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_TIME_ATTR,XmlWriter.CDATA,time.toString());
      }
    }
    hd.startElement("","",ItemXMLConstants.ITEM_TAG,itemAttrs);

    // Handle legendary instances
    if (instance instanceof LegendaryInstance)
    {
      LegendaryInstanceAttrs legAttrs=((LegendaryInstance)instance).getLegendaryAttributes();
      LegendaryInstanceAttrsXMLWriter.write(hd,legAttrs);
    }
    if (instance instanceof LegendaryInstance2)
    {
      LegendaryInstanceAttrs2 legAttrs=((LegendaryInstance2)instance).getLegendaryAttributes();
      LegendaryInstance2AttrsXMLWriter.write(hd,legAttrs);
    }
    // Money
    if (isInstance)
    {
      MoneyXMLWriter.writeMoney(hd,instance.getValue());
    }

    // Properties
    Map<String,String> properties=item.getProperties();
    if (isInstance)
    {
      Map<String,String> allProperties=new HashMap<String,String>(properties);
      allProperties.putAll(instance.getProperties());
      properties=allProperties;
    }
    List<String> propertyNames=new ArrayList<String>(properties.keySet());
    Collections.sort(propertyNames);
    for(String propertyName : propertyNames)
    {
      // Write reference item properties for items, and write instance properties for item instances...
      if ((!isInstance && ItemPropertyNames.isItemReferenceProperty(propertyName)) ||
          (isInstance && ItemPropertyNames.isItemInstanceProperty(propertyName)))
      {
        String propertyValue=properties.get(propertyName);
        AttributesImpl attrs=new AttributesImpl();
        attrs.addAttribute("","",ItemXMLConstants.PROPERTY_KEY_ATTR,XmlWriter.CDATA,propertyName);
        attrs.addAttribute("","",ItemXMLConstants.PROPERTY_VALUE_ATTR,XmlWriter.CDATA,propertyValue);
        hd.startElement("","",ItemXMLConstants.PROPERTY_TAG,attrs);
        hd.endElement("","",ItemXMLConstants.PROPERTY_TAG);
      }
    }
    // Stats
    if (!isInstance)
    {
      StatsProvider statsProvider=item.getStatsProvider();
      if (statsProvider!=null)
      {
        BasicStatsSet stats=item.getStats();
        StatsProviderXMLWriter.writeXml(hd,ItemXMLConstants.STATS_TAG,statsProvider,stats);
      }
    }
    else
    {
      StatsManagerXMLWriter.write(hd,instance.getStatsManager());
    }

    if (isInstance)
    {
      // Essences
      EssencesSet essences=instance.getEssences();
      if (essences!=null)
      {
        AttributesImpl attrs=new AttributesImpl();
        hd.startElement("","",ItemXMLConstants.ESSENCES_TAG,attrs);
        int nbEssences=essences.getSize();
        for(int i=0;i<nbEssences;i++)
        {
          Item essence=essences.getEssence(i);
          if (essence!=null)
          {
            int essenceId=essence.getIdentifier();
            String essenceName=essence.getName();
            AttributesImpl essenceAttrs=new AttributesImpl();
            essenceAttrs.addAttribute("","",ItemXMLConstants.ESSENCE_INDEX_ATTR,XmlWriter.CDATA,String.valueOf(i));
            essenceAttrs.addAttribute("","",ItemXMLConstants.ESSENCE_ID_ATTR,XmlWriter.CDATA,String.valueOf(essenceId));
            essenceAttrs.addAttribute("","",ItemXMLConstants.ESSENCE_NAME_ATTR,XmlWriter.CDATA,essenceName);
            hd.startElement("","",ItemXMLConstants.ESSENCE_TAG,essenceAttrs);
            hd.endElement("","",ItemXMLConstants.ESSENCE_TAG);
          }
        }
        hd.endElement("","",ItemXMLConstants.ESSENCES_TAG);
      }
    }
    hd.endElement("","",ItemXMLConstants.ITEM_TAG);
  }
}
