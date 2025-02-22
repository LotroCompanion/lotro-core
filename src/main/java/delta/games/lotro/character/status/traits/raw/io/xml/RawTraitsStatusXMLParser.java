package delta.games.lotro.character.status.traits.raw.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.status.traits.raw.RawTraitsStatus;

/**
 * Parser for raw trait statuses stored in XML.
 * @author DAM
 */
public class RawTraitsStatusXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed status or <code>null</code>.
   */
  public RawTraitsStatus parseXML(File source)
  {
    RawTraitsStatus status=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      status=parseTraitsStatus(root);
    }
    return status;
  }

  /**
   * Parse a raw traits status from an XML document.
   * @param root Root tag.
   * @return the loaded status.
   */
  public static RawTraitsStatus parseTraitsStatus(Element root)
  {
    RawTraitsStatus status=new RawTraitsStatus();
    // Available traits
    List<Element> traitTags=DOMParsingTools.getChildTagsByName(root,RawTraitsStatusXMLConstants.TRAIT_TAG);
    for(Element traitTag : traitTags)
    {
      NamedNodeMap traitAttrs=traitTag.getAttributes();
      int traidId=DOMParsingTools.getIntAttribute(traitAttrs,RawTraitsStatusXMLConstants.TRAIT_ID_ATTR,0);
      int rank=DOMParsingTools.getIntAttribute(traitAttrs,RawTraitsStatusXMLConstants.TRAIT_RANK_ATTR,0);
      if (rank>0)
      {
        status.setTraitRank(traidId,rank);
      }
    }
    return status;
  }
}
