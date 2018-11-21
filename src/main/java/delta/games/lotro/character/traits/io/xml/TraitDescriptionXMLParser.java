package delta.games.lotro.character.traits.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.stats.StatProvider;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLConstants;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLParser;

/**
 * Parser for trait descriptions stored in XML.
 * @author DAM
 */
public class TraitDescriptionXMLParser
{
  /**
   * Parse a traits XML file.
   * @param source Source file.
   * @return List of parsed traits.
   */
  public static List<TraitDescription> parseTraitsFile(File source)
  {
    List<TraitDescription> traits=new ArrayList<TraitDescription>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> traitTags=DOMParsingTools.getChildTagsByName(root,TraitDescriptionXMLConstants.TRAIT_TAG);
      for(Element traitTag:traitTags)
      {
        TraitDescription trait=parseTrait(traitTag);
        traits.add(trait);
      }
    }
    return traits;
  }

  /**
   * Build a trait from an XML tag.
   * @param root Root XML tag.
   * @return A trait.
   */
  private static TraitDescription parseTrait(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    TraitDescription trait=new TraitDescription();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,TraitDescriptionXMLConstants.TRAIT_IDENTIFIER_ATTR,0);
    trait.setIdentifier(id);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,TraitDescriptionXMLConstants.TRAIT_NAME_ATTR,null);
    trait.setName(name);
    // Icon ID
    int iconId=DOMParsingTools.getIntAttribute(attrs,TraitDescriptionXMLConstants.TRAIT_ICON_ID_ATTR,0);
    trait.setIconId(iconId);
    // Min level
    int minLevel=DOMParsingTools.getIntAttribute(attrs,TraitDescriptionXMLConstants.TRAIT_MIN_LEVEL_ATTR,1);
    trait.setMinLevel(minLevel);
    // Tiers
    int tiers=DOMParsingTools.getIntAttribute(attrs,TraitDescriptionXMLConstants.TRAIT_TIERS_ATTR,1);
    trait.setTiersCount(tiers);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,TraitDescriptionXMLConstants.TRAIT_DESCRIPTION_ATTR,"");
    trait.setDescription(description);

    // Stats
    StatsProvider statsProvider=trait.getStatsProvider();
    List<Element> statTags=DOMParsingTools.getChildTagsByName(root,StatsProviderXMLConstants.STAT_TAG);
    for(Element statTag : statTags)
    {
      StatProvider statProvider=StatsProviderXMLParser.parseStatProvider(statTag);
      statsProvider.addStatProvider(statProvider);
    }
    return trait;
  }
}
