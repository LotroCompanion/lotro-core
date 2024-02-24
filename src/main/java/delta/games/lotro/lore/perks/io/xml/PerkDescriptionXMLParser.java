package delta.games.lotro.lore.perks.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectsManager;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.PerkUICategory;
import delta.games.lotro.lore.perks.PerkDescription;
import delta.games.lotro.utils.i18n.I18nFacade;
import delta.games.lotro.utils.i18n.I18nRuntimeUtils;

/**
 * Parser for perk descriptions stored in XML.
 * @author DAM
 */
public class PerkDescriptionXMLParser
{
  private SingleLocaleLabelsManager _i18n;

  /**
   * Constructor.
   */
  public PerkDescriptionXMLParser()
  {
    _i18n=I18nFacade.getLabelsMgr("perks");
  }

  /**
   * Parse a perks XML file.
   * @param source Source file.
   * @return List of parsed perks.
   */
  public List<PerkDescription> parsePerksFile(File source)
  {
    List<PerkDescription> perks=new ArrayList<PerkDescription>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> perkTags=DOMParsingTools.getChildTagsByName(root,PerkDescriptionXMLConstants.PERK_TAG);
      for(Element perkTag : perkTags)
      {
        PerkDescription perk=parsePerk(perkTag);
        perks.add(perk);
      }
    }
    return perks;
  }

  /**
   * Build a perk from an XML tag.
   * @param root Root XML tag.
   * @return A perk.
   */
  private PerkDescription parsePerk(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    PerkDescription perk=new PerkDescription();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,PerkDescriptionXMLConstants.PERK_IDENTIFIER_ATTR,0);
    perk.setIdentifier(id);
    // Name
    String name=_i18n.getLabel(String.valueOf(id));
    perk.setName(name);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,PerkDescriptionXMLConstants.PERK_DESCRIPTION_ATTR,"");
    description=I18nRuntimeUtils.getLabel(_i18n,description);
    perk.setDescription(description);
    // Icon ID
    int iconId=DOMParsingTools.getIntAttribute(attrs,PerkDescriptionXMLConstants.PERK_ICON_ID_ATTR,0);
    perk.setIconId(iconId);
    // Effect
    int effectId=DOMParsingTools.getIntAttribute(attrs,PerkDescriptionXMLConstants.PERK_EFFECT_ID_ATTR,0);
    if (effectId>0)
    {
      Effect effect=EffectsManager.getInstance().getEffectById(effectId);
      perk.setEffect(effect);
    }
    // UI Category
    int categoryCode=DOMParsingTools.getIntAttribute(attrs,PerkDescriptionXMLConstants.PERK_UI_CATEGORY_ATTR,0);
    if (categoryCode>0)
    {
      LotroEnum<PerkUICategory> categoryEnum=LotroEnumsRegistry.getInstance().get(PerkUICategory.class);
      PerkUICategory category=categoryEnum.getEntry(categoryCode);
      perk.setUICategory(category);
    }
    // Min level
    int minLevel=DOMParsingTools.getIntAttribute(attrs,PerkDescriptionXMLConstants.PERK_MIN_LEVEL_ATTR,0);
    perk.setMinLevel(minLevel);
    // Points cost
    int pointsCost=DOMParsingTools.getIntAttribute(attrs,PerkDescriptionXMLConstants.PERK_POINTS_COST_ATTR,0);
    perk.setPointsCost(pointsCost);
    return perk;
  }
}
