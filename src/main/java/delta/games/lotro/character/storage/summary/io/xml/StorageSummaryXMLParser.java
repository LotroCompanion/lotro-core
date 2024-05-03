package delta.games.lotro.character.storage.summary.io.xml;

import java.io.File;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.storage.summary.CharacterStorageSummary;
import delta.games.lotro.character.storage.summary.SingleStorageSummary;

/**
 * Parser for the storage summaries stored in XML.
 * @author DAM
 */
public class StorageSummaryXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed bags or <code>null</code>.
   */
  public CharacterStorageSummary parseXML(File source)
  {
    CharacterStorageSummary ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseCharacterStorageSummary(root);
    }
    return ret;
  }

  private CharacterStorageSummary parseCharacterStorageSummary(Element root)
  {
    CharacterStorageSummary ret=new CharacterStorageSummary();
    // Bags
    Element bagsTag=DOMParsingTools.getChildTagByName(root,StorageSummaryXMLConstants.BAGS_TAG);
    if (bagsTag!=null)
    {
      parseSingleStorageSummary(bagsTag,ret.getBags());
    }
    // Own vault
    Element ownVaultTag=DOMParsingTools.getChildTagByName(root,StorageSummaryXMLConstants.OWN_VAULT_TAG);
    if (ownVaultTag!=null)
    {
      parseSingleStorageSummary(ownVaultTag,ret.getOwnVault());
    }
    return ret;
  }

  private SingleStorageSummary parseSingleStorageSummary(Element root, SingleStorageSummary ret)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Available
    int available=DOMParsingTools.getIntAttribute(attrs,StorageSummaryXMLConstants.AVAILABLE_ATTR,0);
    ret.setAvailable(available);
    // Max
    int max=DOMParsingTools.getIntAttribute(attrs,StorageSummaryXMLConstants.MAX_ATTR,0);
    ret.setMax(max);
    return ret;
  }
}
