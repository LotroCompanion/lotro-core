package delta.games.lotro.lore.items.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLWriter;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.money.Money;
import delta.games.lotro.common.money.io.xml.MoneyXMLWriter;
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
import delta.games.lotro.lore.items.legendary.LegendaryAttrs;
import delta.games.lotro.lore.items.legendary.LegendaryInstance;
import delta.games.lotro.lore.items.legendary.io.xml.LegendaryAttrsXMLWriter;

/**
 * Writes LOTRO items to XML files.
 * @author DAM
 */
public class ItemXMLWriter
{
  private static final String CDATA="CDATA";

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
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_KEY_ATTR,CDATA,String.valueOf(id));
    }
    if (!isInstance)
    {
      // Set identifier
      String setIdentifier=item.getSetKey();
      if (setIdentifier!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_SET_ID_ATTR,CDATA,setIdentifier);
      }
    }
    // Name
    String name=item.getName();
    if (name!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_NAME_ATTR,CDATA,name);
    }
    if (!isInstance)
    {
      // Icon
      String icon=item.getIcon();
      if (icon!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_ICON_ATTR,CDATA,icon);
      }
    }
    // Item level
    Integer itemLevel=(isInstance?instance.getItemLevel():item.getItemLevel());
    if ((itemLevel!=null) && (itemLevel.intValue()>1))
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_LEVEL_ATTR,CDATA,String.valueOf(itemLevel.intValue()));
    }
    ItemCategory category=item.getCategory();
    if (!isInstance)
    {
      // Slot
      EquipmentLocation slot=item.getEquipmentLocation();
      if (slot!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_SLOT_ATTR,CDATA,String.valueOf(slot.getKey()));
      }
      // Category
      if (category!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_CATEGORY_ATTR,CDATA,category.name());
      }
      // Sub-category
      String subCategory=item.getSubCategory();
      if (subCategory!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_SUBCATEGORY_ATTR,CDATA,subCategory);
      }
      // Binding
      ItemBinding binding=item.getBinding();
      if (binding!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_BINDING_ATTR,CDATA,binding.name());
      }
      // Unique
      boolean unique=item.isUnique();
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_UNIQUE_ATTR,CDATA,String.valueOf(unique));
    }
    // Durability
    Integer durability=(isInstance?instance.getDurability():item.getDurability());
    if (durability!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_DURABILITY_ATTR,CDATA,String.valueOf(durability.intValue()));
    }
    if (!isInstance)
    {
      // Sturdiness
      ItemSturdiness sturdiness=item.getSturdiness();
      if (sturdiness!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_STURDINESS_ATTR,CDATA,sturdiness.name());
      }
      // Quality
      ItemQuality quality=item.getQuality();
      if (quality!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_QUALITY_ATTR,CDATA,quality.getKey());
      }
    }
    // Minimum level
    Integer minLevel=(isInstance?instance.getMinLevel():item.getMinLevel());
    if (minLevel!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_MINLEVEL_ATTR,CDATA,String.valueOf(minLevel.intValue()));
    }
    if (!isInstance)
    {
      // Maximum level
      Integer maxLevel=item.getMaxLevel();
      if (maxLevel!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_MAXLEVEL_ATTR,CDATA,String.valueOf(maxLevel.intValue()));
      }
      // Required class
      CharacterClass requiredClass=item.getRequiredClass();
      if (requiredClass!=null)
      {
        String className=requiredClass.getKey();
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_REQUIRED_CLASS_ATTR,CDATA,className);
      }
      // Description
      String description=item.getDescription();
      if ((description!=null) && (description.length()>0))
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_DESCRIPTION_ATTR,CDATA,description);
      }
      // Stack max
      Integer stackMax=item.getStackMax();
      if (stackMax!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_STACK_MAX_ATTR,CDATA,String.valueOf(stackMax.intValue()));
      }
      // Essence slot count
      int nbEssenceSlots=item.getEssenceSlots();
      if (nbEssenceSlots>0)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_ESSENCE_SLOTS,CDATA,String.valueOf(nbEssenceSlots));
      }
      // Armor specific:
      if (category==ItemCategory.ARMOUR)
      {
        Armour armour=(Armour)item;
        ArmourType type=armour.getArmourType();
        if (type!=null)
        {
          itemAttrs.addAttribute("","",ItemXMLConstants.ARMOUR_TYPE_ATTR,CDATA,type.getKey());
        }
        if (instance instanceof ArmourInstance)
        {
          // Nothing!
        }
      }
      // Weapon specific:
      else if ((category==ItemCategory.WEAPON) || (category==ItemCategory.LEGENDARY_WEAPON))
      {
        Weapon weapon=(Weapon)item;
        float dps=weapon.getDPS();
        itemAttrs.addAttribute("","",ItemXMLConstants.DPS_ATTR,CDATA,String.valueOf(dps));
        int minDamage=weapon.getMinDamage();
        itemAttrs.addAttribute("","",ItemXMLConstants.MIN_DAMAGE_ATTR,CDATA,String.valueOf(minDamage));
        int maxDamage=weapon.getMaxDamage();
        itemAttrs.addAttribute("","",ItemXMLConstants.MAX_DAMAGE_ATTR,CDATA,String.valueOf(maxDamage));
        DamageType type=weapon.getDamageType();
        if (type!=null)
        {
          itemAttrs.addAttribute("","",ItemXMLConstants.DAMAGE_TYPE_ATTR,CDATA,type.getKey());
        }
        WeaponType weaponType=weapon.getWeaponType();
        if (weaponType!=null)
        {
          itemAttrs.addAttribute("","",ItemXMLConstants.WEAPON_TYPE_ATTR,CDATA,weaponType.getKey());
        }
        if (instance instanceof WeaponInstance)
        {
          // Nothing!
        }
      }
    }
    if (isInstance)
    {
      // TODO add instance specific attributes
    }
    hd.startElement("","",ItemXMLConstants.ITEM_TAG,itemAttrs);

    // Handle legendary instances
    if (instance instanceof LegendaryInstance)
    {
      LegendaryAttrs legAttrs=((LegendaryInstance)instance).getLegendaryAttributes();
      LegendaryAttrsXMLWriter.write(hd,legAttrs);
    }
    // Money
    Money value=(isInstance?instance.getValue():item.getValue());
    MoneyXMLWriter.writeMoney(hd,value);

    // Properties
    Map<String,String> properties=item.getProperties();
    List<String> propertyNames=new ArrayList<String>(properties.keySet());
    Collections.sort(propertyNames);
    for(String propertyName : propertyNames)
    {
      // Write reference item properties for items, and write instance properties for item instances...
      boolean isReferenceItemProperty=ItemPropertyNames.isItemReferenceProperty(propertyName);
      if (isReferenceItemProperty!=isInstance)
      {
        String propertyValue=properties.get(propertyName);
        AttributesImpl attrs=new AttributesImpl();
        attrs.addAttribute("","",ItemXMLConstants.PROPERTY_KEY_ATTR,CDATA,propertyName);
        attrs.addAttribute("","",ItemXMLConstants.PROPERTY_VALUE_ATTR,CDATA,propertyValue);
        hd.startElement("","",ItemXMLConstants.PROPERTY_TAG,attrs);
        hd.endElement("","",ItemXMLConstants.PROPERTY_TAG);
      }
    }
    // Stats
    if (!isInstance)
    {
      BasicStatsSet stats=item.getStats();
      StatsProvider statsProvider=item.getStatsProvider();
      if ((statsProvider!=null) && (!isInstance))
      {
        StatsProviderXMLWriter.writeXml(hd,ItemXMLConstants.STATS_TAG,statsProvider,stats);
      }
      else
      {
        BasicStatsSetXMLWriter.write(hd,ItemXMLConstants.STATS_TAG,stats);
      }
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
            essenceAttrs.addAttribute("","",ItemXMLConstants.ESSENCE_ID_ATTR,CDATA,String.valueOf(essenceId));
            essenceAttrs.addAttribute("","",ItemXMLConstants.ESSENCE_NAME_ATTR,CDATA,essenceName);
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
