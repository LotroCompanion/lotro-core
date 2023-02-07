package delta.games.lotro.lore.items.legendary.titles.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.base.io.xml.BasicStatsSetXMLParser;
import delta.games.lotro.common.enums.Genus;
import delta.games.lotro.common.enums.LegendaryTitleCategory;
import delta.games.lotro.common.enums.LegendaryTitleTier;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.lore.items.legendary.titles.LegendaryTitle;
import delta.games.lotro.utils.i18n.I18nFacade;

/**
 * Parser for legendary titles descriptions stored in XML.
 * @author DAM
 */
public class LegendaryTitleXMLParser
{
  private LotroEnum<LegendaryTitleCategory> _category;
  private LotroEnum<Genus> _genus;
  private LotroEnum<LegendaryTitleTier> _tier;
  private SingleLocaleLabelsManager _i18n;

  /**
   * Constructor.
   */
  public LegendaryTitleXMLParser()
  {
    LotroEnumsRegistry registry=LotroEnumsRegistry.getInstance();
    _category=registry.get(LegendaryTitleCategory.class);
    _genus=registry.get(Genus.class);
    _tier=registry.get(LegendaryTitleTier.class);
    _i18n=I18nFacade.getLabelsMgr("legendaryTitles");
  }

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed legendary titles.
   */
  public List<LegendaryTitle> parseXML(File source)
  {
    List<LegendaryTitle> ret=new ArrayList<LegendaryTitle>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> titleTags=DOMParsingTools.getChildTagsByName(root,LegendaryTitleXMLConstants.TITLE_TAG);
      for(Element titleTag : titleTags)
      {
        LegendaryTitle title=parseTitle(titleTag);
        ret.add(title);
      }
    }
    return ret;
  }

  private LegendaryTitle parseTitle(Element root)
  {
    LegendaryTitle title=new LegendaryTitle();

    NamedNodeMap attrs=root.getAttributes();

    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,LegendaryTitleXMLConstants.TITLE_ID_ATTR,0);
    title.setIdentifier(id);
    // Name
    String name=_i18n.getLabel(String.valueOf(id));
    title.setName(name);
    // Category
    int categoryCode=DOMParsingTools.getIntAttribute(attrs,LegendaryTitleXMLConstants.TITLE_CATEGORY_ATTR,0);
    LegendaryTitleCategory category=_category.getEntry(categoryCode);
    title.setCategory(category);
    // Tier
    int tierCode=DOMParsingTools.getIntAttribute(attrs,LegendaryTitleXMLConstants.TITLE_TIER_ATTR,0);
    LegendaryTitleTier tier=_tier.getEntry(tierCode);
    title.setTier(tier);
    // Damage type
    String damageTypeStr=DOMParsingTools.getStringAttribute(attrs,LegendaryTitleXMLConstants.TITLE_DAMAGE_TYPE_ATTR,null);
    DamageType damageType=DamageType.getDamageTypeByKey(damageTypeStr);
    title.setDamageType(damageType);
    // Slayer genus
    int slayerGenusCode=DOMParsingTools.getIntAttribute(attrs,LegendaryTitleXMLConstants.TITLE_SLAYER_ATTR,0);
    Genus slayerGenus=_genus.getEntry(slayerGenusCode);
    title.setSlayerGenusType(slayerGenus);
    // Stats
    Element statsTag=DOMParsingTools.getChildTagByName(root,LegendaryTitleXMLConstants.STATS_TAG);
    if (statsTag!=null)
    {
      BasicStatsSet stats=BasicStatsSetXMLParser.parseStats(statsTag);
      title.getStats().setStats(stats);
    }
    return title;
  }
}
