package delta.games.lotro.lore.consumables.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLParser;
import delta.games.lotro.lore.consumables.Consumable;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.utils.i18n.I18nFacade;

/**
 * Parser for consumables stored in XML.
 * @author DAM
 */
public class ConsumableXMLParser
{
  private static final Logger LOGGER=LoggerFactory.getLogger(ConsumableXMLParser.class);

  private SingleLocaleLabelsManager _i18n;

  /**
   * Constructor.
   */
  public ConsumableXMLParser()
  {
    _i18n=I18nFacade.getLabelsMgr("consumables");
  }

  /**
   * Parse consumables from an XML file.
   * @param source Source file.
   * @return List of parsed consumables.
   */
  public List<Consumable> parseConsumablesFile(File source)
  {
    List<Consumable> consumables=new ArrayList<Consumable>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> consumableTags=DOMParsingTools.getChildTagsByName(root,ConsumableXMLConstants.CONSUMABLE_TAG);
      for(Element consumableTag : consumableTags)
      {
        Consumable consumable=parseConsumable(consumableTag);
        if (consumable!=null)
        {
          consumables.add(consumable);
        }
      }
    }
    return consumables;
  }

  /**
   * Build a consumable from an XML tag.
   * @param root Root XML tag.
   * @return A consumable.
   */
  private Consumable parseConsumable(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,ConsumableXMLConstants.CONSUMABLE_IDENTIFIER_ATTR,0);
    Item item=ItemsManager.getInstance().getItem(id);
    if (item==null)
    {
      LOGGER.warn("Cannot find item with ID={}. Ignoring consumable.",Integer.valueOf(id));
      return null;
    }
    Consumable consumable=new Consumable(item);
    // Stats
    StatsProvider statsProvider=consumable.getProvider();
    StatsProviderXMLParser.parseStatsProvider(root,statsProvider,_i18n);
    return consumable;
  }
}
