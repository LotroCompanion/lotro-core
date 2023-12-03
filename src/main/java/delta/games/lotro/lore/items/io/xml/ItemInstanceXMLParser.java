package delta.games.lotro.lore.items.io.xml;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLConstants;
import delta.games.lotro.character.stats.base.io.xml.StatsManagerXMLParser;
import delta.games.lotro.common.colors.ColorDescription;
import delta.games.lotro.common.colors.ColorsManager;
import delta.games.lotro.common.id.InternalGameId;
import delta.games.lotro.common.money.Money;
import delta.games.lotro.common.money.io.xml.MoneyXMLParser;
import delta.games.lotro.common.requirements.io.xml.UsageRequirementXMLConstants;
import delta.games.lotro.common.stats.CustomStatsMergeMode;
import delta.games.lotro.common.stats.StatsManager;
import delta.games.lotro.common.stats.WellKnownStat;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemFactory;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.WeaponInstance;
import delta.games.lotro.lore.items.essences.Essence;
import delta.games.lotro.lore.items.essences.EssencesManager;
import delta.games.lotro.lore.items.essences.EssencesSet;
import delta.games.lotro.lore.items.legendary.LegendaryInstance;
import delta.games.lotro.lore.items.legendary.LegendaryInstanceAttrs;
import delta.games.lotro.lore.items.legendary.io.xml.LegendaryInstanceAttrsXMLParser;
import delta.games.lotro.lore.items.legendary2.LegendaryInstance2;
import delta.games.lotro.lore.items.legendary2.LegendaryInstanceAttrs2;
import delta.games.lotro.lore.items.legendary2.io.xml.LegendaryInstance2AttrsXMLParser;

/**
 * Parser for item instance descriptions stored in XML.
 * @author DAM
 */
public class ItemInstanceXMLParser
{
  /**
   * Build an item instance from an XML tag.
   * @param root Root XML tag.
   * @return An item instance.
   */
  public ItemInstance<? extends Item> parseItemInstance(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();

    // Item ID
    int id=DOMParsingTools.getIntAttribute(attrs,ItemXMLConstants.ITEM_KEY_ATTR,-1);

    ItemsManager itemsMgr=ItemsManager.getInstance();
    Item item=itemsMgr.getItem(id);
    if (item==null)
    {
      return null;
    }
    // Build instance
    ItemInstance<? extends Item> itemInstance=ItemFactory.buildInstance(item);

    // Item level
    int itemLevel=DOMParsingTools.getIntAttribute(attrs,ItemXMLConstants.ITEM_LEVEL_ATTR,-1);
    if (itemLevel!=-1)
    {
      Integer instanceItemLevel=Integer.valueOf(itemLevel);
      if (!instanceItemLevel.equals(item.getItemLevel()))
      {
        itemInstance.setItemLevel(instanceItemLevel);
      }
    }
    // Properties
    List<Element> propertyTags=DOMParsingTools.getChildTagsByName(root,ItemXMLConstants.PROPERTY_TAG,false);
    if ((propertyTags!=null) && (propertyTags.size()>0))
    {
      for(Element propertyTag : propertyTags)
      {
        NamedNodeMap propAttrs=propertyTag.getAttributes();
        String propertyName=DOMParsingTools.getStringAttribute(propAttrs,ItemXMLConstants.PROPERTY_KEY_ATTR,null);
        String propertyValue=DOMParsingTools.getStringAttribute(propAttrs,ItemXMLConstants.PROPERTY_VALUE_ATTR,null);
        itemInstance.setProperty(propertyName,propertyValue);
      }
    }
    // Stats
    Element statsTag=DOMParsingTools.getChildTagByName(root,BasicStatsSetXMLConstants.STATS_TAG);
    if (statsTag!=null)
    {
      StatsManagerXMLParser.parseStats(statsTag,itemInstance.getStatsManager());
    }
    // Armour
    int armour=DOMParsingTools.getIntAttribute(attrs,ItemXMLConstants.ARMOUR_ATTR,-1);
    if (armour!=-1)
    {
      StatsManager statsManager=itemInstance.getStatsManager();
      if (statsManager.getMode()==CustomStatsMergeMode.SET)
      {
        statsManager.getCustom().setStat(WellKnownStat.ARMOUR,Integer.valueOf(armour));
        statsManager.getResult().setStat(WellKnownStat.ARMOUR,Integer.valueOf(armour));
      }
    }
    // Durability
    int durability=DOMParsingTools.getIntAttribute(attrs,ItemXMLConstants.ITEM_DURABILITY_ATTR,-1);
    if (durability!=-1)
    {
      Integer instanceDurability=Integer.valueOf(durability);
      if (!instanceDurability.equals(item.getDurability()))
      {
        itemInstance.setDurability(instanceDurability);
      }
    }
    // Minimum level
    int minimumLevel=DOMParsingTools.getIntAttribute(attrs,UsageRequirementXMLConstants.MIN_LEVEL_ATTR,-1);
    if (minimumLevel!=-1)
    {
      Integer instanceMinLevel=Integer.valueOf(minimumLevel);
      if (!instanceMinLevel.equals(item.getMinLevel()))
      {
        itemInstance.setMinLevel(instanceMinLevel);
      }
    }
    // Instance specific attributes
    // - Instance ID
    String instanceIdStr=DOMParsingTools.getStringAttribute(attrs,ItemXMLConstants.ITEM_INSTANCE_ID_ATTR,null);
    if (instanceIdStr!=null)
    {
      InternalGameId instanceId=InternalGameId.fromString(instanceIdStr);
      itemInstance.setInstanceId(instanceId);
    }
    // - Birth name
    String birthName=DOMParsingTools.getStringAttribute(attrs,ItemXMLConstants.ITEM_BIRTH_NAME_ATTR,null);
    itemInstance.setBirthName(birthName);
    // - Crafter name
    String crafterName=DOMParsingTools.getStringAttribute(attrs,ItemXMLConstants.ITEM_CRAFTER_NAME_ATTR,null);
    itemInstance.setCrafterName(crafterName);
    // - Color
    float colorCode=DOMParsingTools.getFloatAttribute(attrs,ItemXMLConstants.ITEM_COLOR_CODE_ATTR,Float.NaN);
    ColorDescription color=ColorsManager.getInstance().getColor(colorCode);
    itemInstance.setColor(color);
    // - Bound to
    String boundToStr=DOMParsingTools.getStringAttribute(attrs,ItemXMLConstants.ITEM_BOUND_TO_ATTR,null);
    if (boundToStr!=null)
    {
      InternalGameId boundTo=InternalGameId.fromString(boundToStr);
      itemInstance.setBoundTo(boundTo);
    }
    // Time
    long time=DOMParsingTools.getLongAttribute(attrs,ItemXMLConstants.ITEM_TIME_ATTR,-1);
    if (time>0)
    {
      itemInstance.setTime(Long.valueOf(time));
    }

    // Weapon specifics
    if (itemInstance instanceof WeaponInstance)
    {
      WeaponInstance<?> wi=(WeaponInstance<?>)itemInstance;
      // Max damage
      Float maxDamage=DOMParsingTools.getFloatAttribute(attrs,ItemXMLConstants.MAX_DAMAGE_ATTR,null);
      wi.setMaxDamage(maxDamage);
      // DPS
      Float dps=DOMParsingTools.getFloatAttribute(attrs,ItemXMLConstants.DPS_ATTR,null);
      wi.setDPS(dps);
      // Damage type
      String damageTypeStr=DOMParsingTools.getStringAttribute(attrs,ItemXMLConstants.DAMAGE_TYPE_ATTR,null);
      if (damageTypeStr!=null)
      {
        DamageType type=DamageType.getDamageTypeByKey(damageTypeStr);
        wi.setDamageType(type);
      }
    }
    // Money
    Money value=MoneyXMLParser.loadMoney(root,null);
    itemInstance.setValue(value);

    // Essences
    int nbSlots=item.getEssenceSlots();
    if (nbSlots>0)
    {
      Element essencesTag=DOMParsingTools.getChildTagByName(root,ItemXMLConstants.ESSENCES_TAG);
      if (essencesTag!=null)
      {
        EssencesManager essencesMgr=EssencesManager.getInstance();
        EssencesSet essencesSet=itemInstance.getEssences();
        int currentIndex=0;
        List<Element> allEssenceTags=new ArrayList<Element>();
        List<Element> itemTags=DOMParsingTools.getChildTagsByName(essencesTag,ItemXMLConstants.ITEM_TAG,false);
        allEssenceTags.addAll(itemTags);
        List<Element> essenceTags=DOMParsingTools.getChildTagsByName(essencesTag,ItemXMLConstants.ESSENCE_TAG,false);
        allEssenceTags.addAll(essenceTags);
        for(Element essenceTag : allEssenceTags)
        {
          int index=currentIndex;
          NamedNodeMap essenceAttrs=essenceTag.getAttributes();
          // Essence ID
          int essenceId=DOMParsingTools.getIntAttribute(essenceAttrs,ItemXMLConstants.ITEM_KEY_ATTR,-1);
          if (essenceId==-1)
          {
            essenceId=DOMParsingTools.getIntAttribute(essenceAttrs,ItemXMLConstants.ESSENCE_ID_ATTR,-1);
            // Index
            index=DOMParsingTools.getIntAttribute(essenceAttrs,ItemXMLConstants.ESSENCE_INDEX_ATTR,currentIndex);
          }
          if (essenceId!=-1)
          {
            Essence essence=essencesMgr.getEssence(essenceId);
            if (essence!=null)
            {
              essencesSet.setEssence(index,essence);
              currentIndex++;
            }
          }
        }
      }
    }

    // Handle legendary items
    if (itemInstance instanceof LegendaryInstance)
    {
      LegendaryInstanceAttrs legAttrs=((LegendaryInstance)itemInstance).getLegendaryAttributes();
      LegendaryInstanceAttrsXMLParser.read(legAttrs,root);
    }
    if (itemInstance instanceof LegendaryInstance2)
    {
      LegendaryInstanceAttrs2 legAttrs=((LegendaryInstance2)itemInstance).getLegendaryAttributes();
      LegendaryInstance2AttrsXMLParser.read(legAttrs,root);
    }
    return itemInstance;
  }
}
