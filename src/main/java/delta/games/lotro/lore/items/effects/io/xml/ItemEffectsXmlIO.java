package delta.games.lotro.lore.items.effects.io.xml;

import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.NumericTools;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.effects.Effect2;
import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.effects.EffectsManager;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.effects.ItemEffectsManager;
import delta.games.lotro.lore.items.effects.ItemEffectsManager.Type;
import delta.games.lotro.lore.items.sets.ItemSetEffectsManager;
import delta.games.lotro.lore.items.sets.SetBonus;

/**
 * XML I/O for effects integration in items and item sets.
 * @author DAM
 */
public class ItemEffectsXmlIO
{
  /**
   * Write item effects.
   * @param hd Output
   * @param effectsMgr Effects manager.
   * @throws SAXException If an error occurs.
   */
  public static void writeItemEffects(TransformerHandler hd, ItemEffectsManager effectsMgr) throws SAXException
  {
    // On use
    EffectGenerator[] onUse=effectsMgr.getEffects(Type.ON_USE);
    if (onUse.length>0)
    {
      for(EffectGenerator generator : onUse)
      {
        writeEffectGenerator(hd,generator,Type.ON_USE);
      }
    }
    // On equip
    EffectGenerator[] onEquip=effectsMgr.getEffects(Type.ON_EQUIP);
    if (onEquip.length>0)
    {
      for(EffectGenerator generator : onEquip)
      {
        writeEffectGenerator(hd,generator,Type.ON_EQUIP);
      }
    }
  }

  /**
   * Write set effects.
   * @param hd Output
   * @param effectsMgr Effects manager.
   * @throws SAXException If an error occurs.
   */
  public static void writeSetEffects(TransformerHandler hd, ItemSetEffectsManager effectsMgr) throws SAXException
  {
    EffectGenerator[] onUse=effectsMgr.getEffects();
    if (onUse.length>0)
    {
      for(EffectGenerator generator : onUse)
      {
        writeEffectGenerator(hd,generator,null);
      }
    }
  }

  private static void writeEffectGenerator(TransformerHandler hd, EffectGenerator generator, Type type) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Type
    if (type!=null)
    {
      attrs.addAttribute("","",ItemEffectsXMLConstants.EFFECT_TYPE_ATTR,XmlWriter.CDATA,type.name());
    }
    Effect2 effect=generator.getEffect();
    // ID
    int id=effect.getIdentifier();
    attrs.addAttribute("","",ItemEffectsXMLConstants.EFFECT_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=effect.getName();
    if ((name!=null) && (!name.isEmpty()))
    {
      attrs.addAttribute("","",ItemEffectsXMLConstants.EFFECT_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Spellcraft
    Float spellcraft=generator.getSpellcraft();
    if (spellcraft!=null)
    {
      attrs.addAttribute("","",ItemEffectsXMLConstants.EFFECT_SPELLCRAFT_ATTR,XmlWriter.CDATA,spellcraft.toString());
    }
    hd.startElement("","",ItemEffectsXMLConstants.EFFECT_TAG,attrs);
    hd.endElement("","",ItemEffectsXMLConstants.EFFECT_TAG);
  }

  /**
   * Read item effect.
   * @param attributes Attributes to read from.
   * @param item The parent item.
   */
  public static void readItemEffect(Attributes attributes, Item item)
  {
    // Type
    String typeStr=attributes.getValue(ItemEffectsXMLConstants.EFFECT_TYPE_ATTR);
    Type type=Type.valueOf(typeStr);
    // ID
    String idStr=attributes.getValue(ItemEffectsXMLConstants.EFFECT_ID_ATTR);
    int id=NumericTools.parseInt(idStr,0);
    // Spellcraft
    String spellcraftStr=attributes.getValue(ItemEffectsXMLConstants.EFFECT_SPELLCRAFT_ATTR);
    Float spellcraft=null;
    if (spellcraftStr!=null)
    {
      spellcraft=NumericTools.parseFloat(spellcraftStr);
    }
    Effect2 effect=EffectsManager.getInstance().getEffectById(id);
    if (effect!=null)
    {
      EffectGenerator generator=new EffectGenerator(effect,spellcraft);
      Item.addEffect(item,type,generator);
    }
  }

  /**
   * Read set bonus effects.
   * @param setBonusTag Parent tag.
   * @param setBonus The parent set bonus.
   */
  public static void readSetBonusEffects(Element setBonusTag, SetBonus setBonus)
  {
    List<Element> effectTags=DOMParsingTools.getChildTagsByName(setBonusTag,ItemEffectsXMLConstants.EFFECT_TAG);
    for(Element effectTag : effectTags)
    {
      NamedNodeMap attrs=effectTag.getAttributes();
      // ID
      int id=DOMParsingTools.getIntAttribute(attrs,ItemEffectsXMLConstants.EFFECT_ID_ATTR,0);
      // Spellcraft
      Float spellcraft=DOMParsingTools.getFloatAttribute(attrs,ItemEffectsXMLConstants.EFFECT_SPELLCRAFT_ATTR,null);
      Effect2 effect=EffectsManager.getInstance().getEffectById(id);
      if (effect!=null)
      {
        EffectGenerator generator=new EffectGenerator(effect,spellcraft);
        setBonus.addEffect(generator);
      }
    }
  }
}
