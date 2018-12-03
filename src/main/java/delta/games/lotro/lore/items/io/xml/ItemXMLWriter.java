package delta.games.lotro.lore.items.io.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.StreamTools;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLWriter;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.money.Money;
import delta.games.lotro.common.money.io.xml.MoneyXMLWriter;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLWriter;
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
import delta.games.lotro.lore.items.comparators.ItemIdComparator;
import delta.games.lotro.lore.items.essences.EssencesSet;
import delta.games.lotro.lore.items.legendary.Legendary;
import delta.games.lotro.lore.items.legendary.LegendaryAttrs;
import delta.games.lotro.lore.items.legendary.io.xml.LegendaryAttrsXMLWriter;
import delta.games.lotro.utils.LotroLoggers;

/**
 * Writes LOTRO items to XML files.
 * @author DAM
 */
public class ItemXMLWriter
{
  private static final Logger _logger=LotroLoggers.getLotroLogger();

  private static final String CDATA="CDATA";

  private boolean _referenceMode;

  /**
   * Constructor.
   */
  public ItemXMLWriter()
  {
    this(false);
  }

  /**
   * Constructor.
   * @param referenceMode Mode to use.
   */
  public ItemXMLWriter(boolean referenceMode)
  {
    _referenceMode=referenceMode;
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
  public boolean writeItems(File outFile, List<Item> items, String encoding)
  {
    boolean ret;
    FileOutputStream fos=null;
    try
    {
      File parentFile=outFile.getParentFile();
      if (!parentFile.exists())
      {
        parentFile.mkdirs();
      }
      fos=new FileOutputStream(outFile);
      SAXTransformerFactory tf=(SAXTransformerFactory)TransformerFactory.newInstance();
      TransformerHandler hd=tf.newTransformerHandler();
      Transformer serializer=hd.getTransformer();
      serializer.setOutputProperty(OutputKeys.ENCODING,encoding);
      serializer.setOutputProperty(OutputKeys.INDENT,"yes");

      StreamResult streamResult=new StreamResult(fos);
      hd.setResult(streamResult);
      hd.startDocument();
      hd.startElement("","",ItemXMLConstants.ITEMS_TAG,new AttributesImpl());
      for(Item item : items)
      {
        write(hd,item);
      }
      hd.endElement("","",ItemXMLConstants.ITEMS_TAG);
      hd.endDocument();
      ret=true;
    }
    catch (Exception exception)
    {
      _logger.error("",exception);
      ret=false;
    }
    finally
    {
      StreamTools.close(fos);
    }
    return ret;
  }

  /**
   * Write an item to the given XML stream.
   * @param hd XML output stream.
   * @param item Item to write.
   * @throws Exception If an error occurs.
   */
  public void write(TransformerHandler hd, Item item) throws Exception
  {
    AttributesImpl itemAttrs=new AttributesImpl();

    // Key
    int id=item.getIdentifier();
    if (id!=0)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_KEY_ATTR,CDATA,String.valueOf(id));
    }
    // Set identifier
    String setIdentifier=item.getSetKey();
    if (setIdentifier!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_SET_ID_ATTR,CDATA,setIdentifier);
    }
    // Name
    String name=item.getName();
    if (name!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_NAME_ATTR,CDATA,name);
    }
    // Icon
    String icon=item.getIcon();
    if (icon!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_ICON_ATTR,CDATA,icon);
    }
    // Item level
    Integer itemLevel=item.getItemLevel();
    if ((itemLevel!=null) && (itemLevel.intValue()>1))
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_LEVEL_ATTR,CDATA,String.valueOf(itemLevel.intValue()));
    }
    // Slot
    EquipmentLocation slot=item.getEquipmentLocation();
    if (slot!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_SLOT_ATTR,CDATA,String.valueOf(slot.getKey()));
    }
    // Category
    ItemCategory category=item.getCategory();
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
    // Durability
    Integer durability=item.getDurability();
    if (durability!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_DURABILITY_ATTR,CDATA,String.valueOf(durability.intValue()));
    }
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
    // Minimum level
    Integer minLevel=item.getMinLevel();
    if (minLevel!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_MINLEVEL_ATTR,CDATA,String.valueOf(minLevel.intValue()));
    }
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
    if (description!=null)
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
    }
    hd.startElement("","",ItemXMLConstants.ITEM_TAG,itemAttrs);

    // Handle legendary items
    if (item instanceof Legendary)
    {
      LegendaryAttrs legAttrs=((Legendary)item).getLegendaryAttrs();
      LegendaryAttrsXMLWriter.write(hd,legAttrs);
    }
    // Money
    Money value=item.getValue();
    MoneyXMLWriter.writeMoney(hd,value);
    // Bonuses
    // TODO better
    List<String> bonuses=item.getBonus();
    if (bonuses!=null)
    {
      for(String bonus : bonuses)
      {
        AttributesImpl attrs=new AttributesImpl();
        attrs.addAttribute("","",ItemXMLConstants.BONUS_VALUE_ATTR,CDATA,bonus);
        hd.startElement("","",ItemXMLConstants.BONUS_TAG,attrs);
        hd.endElement("","",ItemXMLConstants.BONUS_TAG);
      }
    }
    // Properties
    Map<String,String> properties=item.getProperties();
    List<String> propertyNames=new ArrayList<String>(properties.keySet());
    Collections.sort(propertyNames);
    for(String propertyName : propertyNames)
    {
      String propertyValue=properties.get(propertyName);
      AttributesImpl attrs=new AttributesImpl();
      attrs.addAttribute("","",ItemXMLConstants.PROPERTY_KEY_ATTR,CDATA,propertyName);
      attrs.addAttribute("","",ItemXMLConstants.PROPERTY_VALUE_ATTR,CDATA,propertyValue);
      hd.startElement("","",ItemXMLConstants.PROPERTY_TAG,attrs);
      hd.endElement("","",ItemXMLConstants.PROPERTY_TAG);
    }
    // Stats
    BasicStatsSet stats=item.getStats();
    StatsProvider statsProvider=item.getStatsProvider();
    if ((statsProvider!=null) && (_referenceMode))
    {
      StatsProviderXMLWriter.writeXml(hd,ItemXMLConstants.STATS_TAG,statsProvider,stats);
    }
    else
    {
      BasicStatsSetXMLWriter.write(hd,ItemXMLConstants.STATS_TAG,stats);
    }

    // Essences
    EssencesSet essences=item.getEssences();
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
          write(hd,essence);
        }
      }
      hd.endElement("","",ItemXMLConstants.ESSENCES_TAG);
    }
    hd.endElement("","",ItemXMLConstants.ITEM_TAG);
  }
}
