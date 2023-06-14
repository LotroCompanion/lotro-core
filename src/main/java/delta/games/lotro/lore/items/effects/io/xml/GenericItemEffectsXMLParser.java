package delta.games.lotro.lore.items.effects.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.io.xml.EffectXMLConstants;
import delta.games.lotro.common.effects.io.xml.EffectXMLParser;
import delta.games.lotro.common.enums.EquipmentCategory;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.lore.items.effects.GenericItemEffects;
import delta.games.lotro.utils.i18n.I18nFacade;

/**
 * Parser for generic item effects stored in XML.
 * @author DAM
 */
public class GenericItemEffectsXMLParser
{
  private SingleLocaleLabelsManager _i18n;

  /**
   * Constructor.
   */
  public GenericItemEffectsXMLParser()
  {
    _i18n=I18nFacade.getLabelsMgr("genericItemEffects");
  }

  /**
   * Parse an effects XML file.
   * @param source Source file.
   * @return List of parsed effects.
   */
  public List<GenericItemEffects> parseGenericItemEffectsFile(File source)
  {
    List<GenericItemEffects> itemEffects=new ArrayList<GenericItemEffects>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> tags=DOMParsingTools.getChildTags(root);
      for(Element tag : tags)
      {
        GenericItemEffects singleItemEffects=parseItemEffects(tag);
        itemEffects.add(singleItemEffects);
      }
    }
    return itemEffects;
  }

  /**
   * Build an item effects from an XML tag.
   * @param root Root XML tag.
   * @return An item effects.
   */
  private GenericItemEffects parseItemEffects(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    int code=DOMParsingTools.getIntAttribute(attrs,GenericItemEffectsXMLConstants.CATEGORY_CODE_ATTR,0);
    LotroEnum<EquipmentCategory> categoryEnum=LotroEnumsRegistry.getInstance().get(EquipmentCategory.class);
    EquipmentCategory category=categoryEnum.getEntry(code);
    GenericItemEffects ret=new GenericItemEffects(category);
    // Effects
    List<Element> tags=DOMParsingTools.getChildTagsByName(root,EffectXMLConstants.EFFECT_TAG);
    for(Element tag : tags)
    {
      Effect effect=EffectXMLParser.parseEffect(tag,_i18n);
      ret.addEffect(effect);
    }
    return ret;
  }
}
