package delta.games.lotro.character.status.traits.skirmish.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.status.traits.skirmish.SkirmishTraitsStatus;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.TraitNature;

/**
 * Parser for skirmish traits statuses stored in XML.
 * @author DAM
 */
public class SkirmishTraitsStatusXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed status or <code>null</code>.
   */
  public SkirmishTraitsStatus parseXML(File source)
  {
    SkirmishTraitsStatus status=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      status=parseSkirmishTraitsStatus(root);
    }
    return status;
  }

  /**
   * Parse a skirmish traits status from an XML document.
   * @param root Root tag.
   * @return the loaded trait tree status.
   */
  public static SkirmishTraitsStatus parseSkirmishTraitsStatus(Element root)
  {
    SkirmishTraitsStatus ret=new SkirmishTraitsStatus();
    LotroEnum<TraitNature> traitNatures=LotroEnumsRegistry.getInstance().get(TraitNature.class);
    // Slotted traits
    List<Element> slottedTraitsTags=DOMParsingTools.getChildTagsByName(root,SkirmishTraitsStatusXMLConstants.SLOTTED_TRAITS_TAG);
    for(Element slottedTraitsTag : slottedTraitsTags)
    {
      NamedNodeMap slottedTraitsAttrs=slottedTraitsTag.getAttributes();
      // Nature ID
      int natureID=DOMParsingTools.getIntAttribute(slottedTraitsAttrs,SkirmishTraitsStatusXMLConstants.SLOTTED_TRAITS_NATURE_ID_ATTR,0);
      TraitNature nature=traitNatures.getEntry(natureID);
      // Count
      int count=DOMParsingTools.getIntAttribute(slottedTraitsAttrs,SkirmishTraitsStatusXMLConstants.SLOTTED_TRAITS_SLOTS_COUNT_ATTR,0);
      List<Integer> slottedTraitIDs=new ArrayList<Integer>(count);
      List<Element> slottedTraitTags=DOMParsingTools.getChildTagsByName(slottedTraitsTag,SkirmishTraitsStatusXMLConstants.SLOTTED_TRAIT_TAG);
      for(Element slottedTraitTag : slottedTraitTags)
      {
        NamedNodeMap slottedTraitAttrs=slottedTraitTag.getAttributes();
        // Index
        int index=DOMParsingTools.getIntAttribute(slottedTraitAttrs,SkirmishTraitsStatusXMLConstants.SLOTTED_TRAIT_INDEX_ATTR,0);
        // Trait ID
        int traitID=DOMParsingTools.getIntAttribute(slottedTraitAttrs,SkirmishTraitsStatusXMLConstants.SLOTTED_TRAIT_ID_ATTR,0);
        slottedTraitIDs.set(index,Integer.valueOf(traitID));
      }
      ret.setSlottedTraits(nature,slottedTraitIDs);
    }
    // Trait ranks
    List<Element> traitTags=DOMParsingTools.getChildTagsByName(root,SkirmishTraitsStatusXMLConstants.TRAIT_TAG);
    for(Element traitTag : traitTags)
    {
      NamedNodeMap traitAttrs=traitTag.getAttributes();
      // Trait ID
      int traitID=DOMParsingTools.getIntAttribute(traitAttrs,SkirmishTraitsStatusXMLConstants.TRAIT_ID_ATTR,0);
      // Trait rank
      int rank=DOMParsingTools.getIntAttribute(traitAttrs,SkirmishTraitsStatusXMLConstants.TRAIT_RANK_ATTR,0);
      ret.setTraitRank(traitID,rank);
    }
    return ret;
  }
}
