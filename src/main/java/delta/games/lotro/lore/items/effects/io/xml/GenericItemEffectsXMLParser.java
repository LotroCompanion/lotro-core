package delta.games.lotro.lore.items.effects.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.effects.Effect2;
import delta.games.lotro.common.effects.EffectsManager;
import delta.games.lotro.common.enums.EquipmentCategory;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.lore.items.effects.GenericItemEffects;

/**
 * Parser for generic item effects stored in XML.
 * @author DAM
 */
public class GenericItemEffectsXMLParser
{
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
    List<Element> tags=DOMParsingTools.getChildTagsByName(root,GenericItemEffectsXMLConstants.EFFECT_TAG);
    for(Element tag : tags)
    {
      NamedNodeMap effectAttrs=tag.getAttributes();
      int effectID=DOMParsingTools.getIntAttribute(effectAttrs,GenericItemEffectsXMLConstants.EFFECT_ID_ATTR,0);
      Effect2 effect=EffectsManager.getInstance().getEffectById(effectID);
      if (effect!=null)
      {
        ret.addEffect(effect);
      }
    }
    return ret;
  }
}
