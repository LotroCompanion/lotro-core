package delta.games.lotro.common.effects.io.xml;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.Effect;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLParser;

/**
 * Parser for effect descriptions stored in XML.
 * @author DAM
 */
public class EffectXMLParser
{
  /**
   * Build an effect from an XML tag.
   * @param root Root XML tag.
   * @return An effect.
   */
  public static Effect parseEffect(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    Effect effect=new Effect();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants.EFFECT_ID_ATTR,0);
    effect.setId(id);

    // Stats
    StatsProvider statsProvider=StatsProviderXMLParser.parseStatsProvider(root);
    effect.setStatsProvider(statsProvider);

    return effect;
  }
}
