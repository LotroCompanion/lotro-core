package delta.games.lotro.lore.collections.birds.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.collections.birds.BirdDescription;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.utils.i18n.I18nFacade;
import delta.games.lotro.utils.i18n.I18nRuntimeUtils;
/**
 * Parser for the birds stored in XML.
 * @author DAM
 */
public class BirdsXMLParser
{
  private static final Logger LOGGER=LoggerFactory.getLogger(BirdsXMLParser.class);

  private SingleLocaleLabelsManager _i18n;

  /**
   * Constructor.
   */
  public BirdsXMLParser()
  {
    _i18n=I18nFacade.getLabelsMgr("birds");
  }

  /**
   * Parse the birds from an XML file.
   * @param source Source file.
   * @return List of birds.
   */
  public List<BirdDescription> parseBirdsFile(File source)
  {
    List<BirdDescription> ret=new ArrayList<BirdDescription>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> birdTags=DOMParsingTools.getChildTagsByName(root,BirdsXMLConstants.BIRD_TAG);
      for(Element birdTag : birdTags)
      {
        BirdDescription bird=pardseBird(birdTag);
        if (bird!=null)
        {
          ret.add(bird);
        }
      }
    }
    return ret;
  }

  /**
   * Get a bird from an XML tag.
   * @param root Root XML tag.
   * @return A bird or <code>null</code>.
   */
  private BirdDescription pardseBird(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,BirdsXMLConstants.IDENTIFIER_ATTR,0);
    Item item=ItemsManager.getInstance().getItem(id);
    if (item==null)
    {
      LOGGER.warn("Item not found: {}",Integer.valueOf(id));
      return null;
    }
    BirdDescription ret=new BirdDescription(item);
    // Call sound ID
    int callSoundID=DOMParsingTools.getIntAttribute(attrs,BirdsXMLConstants.CALL_SOUND_ID_ATTR,0);
    ret.setCallSoundID(callSoundID);
    // Type code
    int typeCode=DOMParsingTools.getIntAttribute(attrs,BirdsXMLConstants.TYPE_CODE_ATTR,0);
    ret.setTypeCode(typeCode);
    // Elvish name
    String elvishName=DOMParsingTools.getStringAttribute(attrs,BirdsXMLConstants.ELVISH_NAME_ATTR,"");
    elvishName=I18nRuntimeUtils.getLabel(_i18n,elvishName);
    ret.setElvishName(elvishName);
    // Icon ID
    int iconID=DOMParsingTools.getIntAttribute(attrs,BirdsXMLConstants.ICON_ID_ATTR,0);
    ret.setIconID(iconID);
    // Large icon ID
    int largeIconID=DOMParsingTools.getIntAttribute(attrs,BirdsXMLConstants.LARGE_ICON_ID_ATTR,0);
    ret.setLargeIconID(largeIconID);
    return ret;
  }
}
