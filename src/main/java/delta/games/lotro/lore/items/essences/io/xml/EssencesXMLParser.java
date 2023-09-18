package delta.games.lotro.lore.items.essences.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.SocketType;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.essences.Essence;

/**
 * Parser for essences stored in XML.
 * @author DAM
 */
public class EssencesXMLParser
{
  private static final Logger LOGGER=Logger.getLogger(EssencesXMLParser.class);

  /**
   * Parse essences from an XML file.
   * @param source Source file.
   * @return List of parsed essences.
   */
  public static List<Essence> parseEssencesFile(File source)
  {
    List<Essence> ret=new ArrayList<Essence>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> essenceTags=DOMParsingTools.getChildTags(root);
      for(Element essenceTag : essenceTags)
      {
        Essence essence=parseEssence(essenceTag);
        if (essence!=null)
        {
          ret.add(essence);
        }
      }
    }
    return ret;
  }

  private static Essence parseEssence(Element essenceTag)
  {
    NamedNodeMap attrs=essenceTag.getAttributes();
    // Id
    int id=DOMParsingTools.getIntAttribute(attrs,EssencesXMLConstants.ID_ATTR,0);
    Item item=ItemsManager.getInstance().getItem(id);
    if (item==null)
    {
      LOGGER.warn("Unknown item for essence: "+id);
      return null;
    }
    // Socket type
    LotroEnum<SocketType> socketTypesMgr=LotroEnumsRegistry.getInstance().get(SocketType.class);
    int typeCode=DOMParsingTools.getIntAttribute(attrs,EssencesXMLConstants.SOCKET_TYPE_ATTR,0);
    SocketType socketType=socketTypesMgr.getEntry(typeCode);
    Essence ret=new Essence(item,socketType);
    // Tier
    int tier=DOMParsingTools.getIntAttribute(attrs,EssencesXMLConstants.TIER_ATTR,-1);
    if (tier>=0)
    {
      ret.setTier(Integer.valueOf(tier));
    }
    // Uniqueness channel
    /*
    ItemUniquenessChannel uniquenessChannel=null;
    int uniquenessChannelCode=DOMParsingTools.getIntAttribute(attrs,EssencesXMLConstants.UNIQUENESS_CHANNEL_ATTR,0);
    if (uniquenessChannelCode>0)
    {
      LotroEnum<ItemUniquenessChannel> itemUniquenessChannelEnum=LotroEnumsRegistry.getInstance().get(ItemUniquenessChannel.class);
      uniquenessChannel=itemUniquenessChannelEnum.getEntry(uniquenessChannelCode);
      ret.setUniquenessChannel(uniquenessChannel);
    }
    */
    return ret;
  }
}
