package delta.games.lotro.lore.items.io.xml;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLConstants;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLParser;
import delta.games.lotro.common.colors.ColorDescription;
import delta.games.lotro.common.colors.ColorsManager;
import delta.games.lotro.common.id.EntityId;
import delta.games.lotro.common.id.ItemInstanceId;
import delta.games.lotro.common.money.Money;
import delta.games.lotro.common.money.io.xml.MoneyXMLParser;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemFactory;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.essences.EssencesSet;
import delta.games.lotro.lore.items.legendary.LegendaryAttrs;
import delta.games.lotro.lore.items.legendary.LegendaryInstance;
import delta.games.lotro.lore.items.legendary.io.xml.LegendaryAttrsXMLParser;

/**
 * Parser for item instance descriptions stored in XML.
 * @author DAM
 */
public class ItemXMLParser
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
      BasicStatsSet stats=BasicStatsSetXMLParser.parseStats(statsTag);
      itemInstance.setOwnStats(stats);
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
    int minimumLevel=DOMParsingTools.getIntAttribute(attrs,ItemXMLConstants.ITEM_MINLEVEL_ATTR,-1);
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
      ItemInstanceId instanceId=ItemInstanceId.fromString(instanceIdStr);
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
      EntityId boundTo=EntityId.fromString(boundToStr);
      itemInstance.setBoundTo(boundTo);
    }
    // Time
    long time=DOMParsingTools.getLongAttribute(attrs,ItemXMLConstants.ITEM_TIME_ATTR,-1);
    if (time>0)
    {
      itemInstance.setTime(Long.valueOf(time));
    }

    // Money
    Money value=MoneyXMLParser.loadMoney(root,null);
    itemInstance.setValue(value);

    // Essences
    Element essencesTag=DOMParsingTools.getChildTagByName(root,ItemXMLConstants.ESSENCES_TAG);
    if (essencesTag!=null)
    {
      List<Element> allEssenceTags=new ArrayList<Element>();
      List<Element> itemTags=DOMParsingTools.getChildTagsByName(essencesTag,ItemXMLConstants.ITEM_TAG,false);
      allEssenceTags.addAll(itemTags);
      List<Element> essenceTags=DOMParsingTools.getChildTagsByName(essencesTag,ItemXMLConstants.ESSENCE_TAG,false);
      allEssenceTags.addAll(essenceTags);
      List<Item> essences=new ArrayList<Item>();
      for(Element essenceTag : essenceTags)
      {
        NamedNodeMap essenceAttrs=essenceTag.getAttributes();
        // Essence ID
        int essenceId=DOMParsingTools.getIntAttribute(essenceAttrs,ItemXMLConstants.ITEM_KEY_ATTR,-1);
        if (essenceId==-1)
        {
          essenceId=DOMParsingTools.getIntAttribute(essenceAttrs,ItemXMLConstants.ESSENCE_ID_ATTR,-1);
        }
        if (essenceId!=-1)
        {
          Item essence=itemsMgr.getItem(essenceId);
          essences.add(essence);
        }
      }
      if (essences.size()>0)
      {
        int nbMaxSlots=item.getEssenceSlots();
        int slots=Math.max(nbMaxSlots,essences.size());
        EssencesSet essencesSet=new EssencesSet(slots);
        int index=0;
        for(Item essence : essences)
        {
          essencesSet.setEssence(index,essence);
          index++;
        }
        itemInstance.setEssences(essencesSet);
      }
    }

    // Handle legendary items
    if (itemInstance instanceof LegendaryInstance)
    {
      LegendaryAttrs legAttrs=((LegendaryInstance)itemInstance).getLegendaryAttributes();
      LegendaryAttrsXMLParser.read(legAttrs,root);
    }
    return itemInstance;
  }
}
