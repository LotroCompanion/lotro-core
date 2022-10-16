package delta.games.lotro.character.classes.traitTree.setup.io.xml;

import java.io.File;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.classes.traitTree.setup.TraitTreeSetup;
import delta.games.lotro.character.status.traitTree.TraitTreeStatus;
import delta.games.lotro.character.status.traitTree.io.xml.TraitTreeStatusXMLParser;

/**
 * Parser for trait tree setups stored in XML.
 * @author DAM
 */
public class TraitTreeSetupXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed setup or <code>null</code>.
   */
  public TraitTreeSetup parseXML(File source)
  {
    TraitTreeSetup ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseSetup(root);
    }
    return ret;
  }

  private TraitTreeSetup parseSetup(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();

    TraitTreeStatus status=TraitTreeStatusXMLParser.parseTraitTreeStatus(root);
    if (status==null)
    {
      return null;
    }

    TraitTreeSetup setup=new TraitTreeSetup(status);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,TraitTreeSetupXMLConstants.TRAIT_TREE_SETUP_NAME_ATTR,"");
    setup.setName(name);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,TraitTreeSetupXMLConstants.TRAIT_TREE_SETUP_DESCRIPTION_ATTR,"");
    setup.setDescription(description);
    return setup;
  }
}
