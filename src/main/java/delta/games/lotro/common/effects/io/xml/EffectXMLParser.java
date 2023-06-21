package delta.games.lotro.common.effects.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLParser;
import delta.games.lotro.utils.i18n.I18nRuntimeUtils;

/**
 * Parser for effect descriptions stored in XML.
 * @author DAM
 */
public class EffectXMLParser
{
  /**
   * Parse an effects XML file.
   * @param source Source file.
   * @param labelsMgr Labels manager.
   * @return List of parsed effects.
   */
  public static List<Effect> parseEffectsFile(File source, SingleLocaleLabelsManager labelsMgr)
  {
    List<Effect> effects=new ArrayList<Effect>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> effectTags=DOMParsingTools.getChildTags(root);
      for(Element effectTag : effectTags)
      {
        Effect effect=parseEffect(effectTag,labelsMgr);
        effects.add(effect);
      }
    }
    return effects;
  }

  /**
   * Build an effect from an XML tag.
   * @param root Root XML tag.
   * @param labelsMgr Labels manager.
   * @return An effect.
   */
  public static Effect parseEffect(Element root, SingleLocaleLabelsManager labelsMgr)
  {
    NamedNodeMap attrs=root.getAttributes();
    Effect effect=new Effect();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants.EFFECT_ID_ATTR,0);
    effect.setId(id);
    // Name
    String name;
    if (labelsMgr!=null)
    {
      name=labelsMgr.getLabel(String.valueOf(id));
    }
    else
    {
      name=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants.EFFECT_NAME_ATTR,null);
    }
    effect.setName(name);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants.EFFECT_DESCRIPTION_ATTR,null);
    description=I18nRuntimeUtils.getLabel(labelsMgr,description);
    effect.setDescription(description);
    // Icon ID
    int iconId=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants.EFFECT_ICON_ID_ATTR,-1);
    effect.setIconId((iconId!=-1)?Integer.valueOf(iconId):null);
    // Duration
    float duration=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants.EFFECT_DURATION_ATTR,-1);
    if (duration>=0)
    {
      effect.setDuration(Float.valueOf(duration));
    }
    // Stats
    StatsProvider statsProvider=StatsProviderXMLParser.parseStatsProvider(root,labelsMgr);
    effect.setStatsProvider(statsProvider);

    return effect;
  }
}
