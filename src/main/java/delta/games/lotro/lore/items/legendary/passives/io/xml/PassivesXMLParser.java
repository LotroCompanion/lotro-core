package delta.games.lotro.lore.items.legendary.passives.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLParser;
import delta.games.lotro.lore.items.legendary.passives.Passive;

/**
 * Parser for passive descriptions stored in XML.
 * @author DAM
 */
public class PassivesXMLParser
{
  /**
   * Parse a passives XML file.
   * @param source Source file.
   * @param labelsMgr Labels manager.
   * @return List of parsed passives.
   */
  public static List<Passive> parsePassivesFile(File source, SingleLocaleLabelsManager labelsMgr)
  {
    List<Passive> passives=new ArrayList<Passive>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> passiveTags=DOMParsingTools.getChildTags(root);
      for(Element passiveTag : passiveTags)
      {
        Passive passive=parsePassive(passiveTag,labelsMgr);
        passives.add(passive);
      }
    }
    return passives;
  }

  /**
   * Build a passive from an XML tag.
   * @param root Root XML tag.
   * @param labelsMgr Labels manager.
   * @return A passive.
   */
  private static Passive parsePassive(Element root, SingleLocaleLabelsManager labelsMgr)
  {
    NamedNodeMap attrs=root.getAttributes();
    Passive passive=new Passive();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,PassivesXMLConstants.PASSIVE_ID_ATTR,0);
    passive.setId(id);
    // Stats
    StatsProvider statsProvider=StatsProviderXMLParser.parseStatsProvider(root,labelsMgr);
    passive.setStatsProvider(statsProvider);
    return passive;
  }
}
