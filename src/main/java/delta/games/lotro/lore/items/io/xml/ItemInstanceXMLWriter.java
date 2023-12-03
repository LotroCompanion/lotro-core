package delta.games.lotro.lore.items.io.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.stats.base.io.xml.StatsManagerXMLWriter;
import delta.games.lotro.common.colors.ColorDescription;
import delta.games.lotro.common.id.InternalGameId;
import delta.games.lotro.common.money.io.xml.MoneyXMLWriter;
import delta.games.lotro.common.requirements.io.xml.UsageRequirementXMLConstants;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.WeaponInstance;
import delta.games.lotro.lore.items.essences.EssencesSet;
import delta.games.lotro.lore.items.legendary.LegendaryInstance;
import delta.games.lotro.lore.items.legendary.LegendaryInstanceAttrs;
import delta.games.lotro.lore.items.legendary.io.xml.LegendaryInstanceAttrsXMLWriter;
import delta.games.lotro.lore.items.legendary2.LegendaryInstance2;
import delta.games.lotro.lore.items.legendary2.LegendaryInstanceAttrs2;
import delta.games.lotro.lore.items.legendary2.io.xml.LegendaryInstance2AttrsXMLWriter;

/**
 * Writes LOTRO item instances to XML files.
 * @author DAM
 */
public class ItemInstanceXMLWriter
{
  /**
   * Write an item to the given XML stream.
   * @param hd XML output stream.
   * @param instance Item to write.
   * @throws Exception If an error occurs.
   */
  public void writeItemInstance(TransformerHandler hd, ItemInstance<? extends Item> instance) throws Exception
  {
    Item item=instance.getReference();
    AttributesImpl itemAttrs=new AttributesImpl();

    // Identifier
    int id=item.getIdentifier();
    if (id!=0)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_KEY_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    // Name
    String name=item.getName();
    if (name!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Item level
    Integer itemLevel=instance.getItemLevel();
    if ((itemLevel!=null) && (itemLevel.intValue()>0))
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(itemLevel.intValue()));
    }
    // Durability
    Integer durability=instance.getDurability();
    if (durability!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_DURABILITY_ATTR,XmlWriter.CDATA,String.valueOf(durability.intValue()));
    }
    // Minimum level
    Integer minLevel=instance.getMinLevel();
    if (minLevel!=null)
    {
      itemAttrs.addAttribute("","",UsageRequirementXMLConstants.MIN_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(minLevel.intValue()));
    }
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
    // Weapon specifics
    if (instance instanceof WeaponInstance)
    {
      WeaponInstance<?> wi=(WeaponInstance<?>)instance;
      // Max damage
      Float maxDamage=wi.getMaxDamage();
      if (maxDamage!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.MAX_DAMAGE_ATTR,XmlWriter.CDATA,maxDamage.toString());
      }
      // DPS
      Float dps=wi.getDPS();
      if (dps!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.DPS_ATTR,XmlWriter.CDATA,dps.toString());
      }
      // Damage type
      DamageType damageType=wi.getDamageType();
      if (damageType!=null)
      {
        itemAttrs.addAttribute("","",ItemXMLConstants.DAMAGE_TYPE_ATTR,XmlWriter.CDATA,String.valueOf(damageType.getCode()));
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
    MoneyXMLWriter.writeMoney(hd,instance.getValue());

    // Properties
    Map<String,String> properties=new HashMap<String,String>(instance.getProperties());
    List<String> propertyNames=new ArrayList<String>(properties.keySet());
    Collections.sort(propertyNames);
    for(String propertyName : propertyNames)
    {
      String propertyValue=properties.get(propertyName);
      AttributesImpl attrs=new AttributesImpl();
      attrs.addAttribute("","",ItemXMLConstants.PROPERTY_KEY_ATTR,XmlWriter.CDATA,propertyName);
      attrs.addAttribute("","",ItemXMLConstants.PROPERTY_VALUE_ATTR,XmlWriter.CDATA,propertyValue);
      hd.startElement("","",ItemXMLConstants.PROPERTY_TAG,attrs);
      hd.endElement("","",ItemXMLConstants.PROPERTY_TAG);
    }

    // Stats
    StatsManagerXMLWriter.write(hd,instance.getStatsManager());

    // Essences
    {
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
