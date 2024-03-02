package delta.games.lotro.character.status.traits.shared.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.status.traits.shared.SlottedTraitsStatus;

/**
 * Parser for slotted trait statuses stored in XML.
 * @author DAM
 */
public class SlottedTraitsStatusXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed status or <code>null</code>.
   */
  public SlottedTraitsStatus parseXML(File source)
  {
    SlottedTraitsStatus status=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      status=parseSlottedTraitsStatus(root);
    }
    return status;
  }

  /**
   * Parse a trait tree status from an XML document.
   * @param root Root tag.
   * @return the loaded trait tree status.
   */
  public static SlottedTraitsStatus parseSlottedTraitsStatus(Element root)
  {
    SlottedTraitsStatus status=new SlottedTraitsStatus();
    // Available traits
    List<Element> availableTraitTags=DOMParsingTools.getChildTagsByName(root,SlottedTraitsStatusXMLConstants.TRAIT_AVAILABLE_TAG);
    for(Element availableTraitTag : availableTraitTags)
    {
      NamedNodeMap traitSlotAttrs=availableTraitTag.getAttributes();
      int traidId=DOMParsingTools.getIntAttribute(traitSlotAttrs,SlottedTraitsStatusXMLConstants.TRAIT_SLOT_TRAIT_ID_ATTR,0);
      status.addTraitID(traidId);
    }
    // Slots
    List<Element> traitSlotTags=DOMParsingTools.getChildTagsByName(root,SlottedTraitsStatusXMLConstants.TRAIT_SLOT_TAG);
    int nbSlots=traitSlotTags.size();
    int[] traitIDs=new int[nbSlots];
    for(int i=0;i<nbSlots;i++)
    {
      Element traitSlotTag=traitSlotTags.get(i);
      NamedNodeMap traitSlotAttrs=traitSlotTag.getAttributes();
      int traidId=DOMParsingTools.getIntAttribute(traitSlotAttrs,SlottedTraitsStatusXMLConstants.TRAIT_SLOT_TRAIT_ID_ATTR,0);
      traitIDs[i]=traidId;
    }
    return status;
  }
}
