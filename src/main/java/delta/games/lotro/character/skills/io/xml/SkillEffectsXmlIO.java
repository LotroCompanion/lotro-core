package delta.games.lotro.character.skills.io.xml;

import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.SkillEffectGenerator;
import delta.games.lotro.character.skills.SkillEffectType;
import delta.games.lotro.character.skills.SkillEffectsManager;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectsManager;

/**
 * XML I/O for effects integration in skills.
 * @author DAM
 */
public class SkillEffectsXmlIO
{
  private static final Logger LOGGER=LoggerFactory.getLogger(SkillEffectsXmlIO.class);

  /**
   * Write skill effects.
   * @param hd Output
   * @param effectsMgr Effects manager.
   * @throws SAXException If an error occurs.
   */
  public static void writeSkillEffects(TransformerHandler hd, SkillEffectsManager effectsMgr) throws SAXException
  {
    SkillEffectGenerator[] effects=effectsMgr.getEffects();
    if (effects.length>0)
    {
      for(SkillEffectGenerator generator : effects)
      {
        writeEffectGenerator(hd,generator);
      }
    }
  }

  private static void writeEffectGenerator(TransformerHandler hd, SkillEffectGenerator generator) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    Effect effect=generator.getEffect();
    // ID
    int id=effect.getIdentifier();
    attrs.addAttribute("","",SkillEffectsXMLConstants.EFFECT_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=effect.getName();
    if ((name!=null) && (!name.isEmpty()))
    {
      attrs.addAttribute("","",SkillEffectsXMLConstants.EFFECT_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Spellcraft
    Float spellcraft=generator.getSpellcraft();
    if (spellcraft!=null)
    {
      attrs.addAttribute("","",SkillEffectsXMLConstants.EFFECT_SPELLCRAFT_ATTR,XmlWriter.CDATA,spellcraft.toString());
    }
    // Duration
    Float duration=generator.getDuration();
    if (duration!=null)
    {
      attrs.addAttribute("","",SkillEffectsXMLConstants.EFFECT_DURATION_ATTR,XmlWriter.CDATA,duration.toString());
    }
    // Type
    SkillEffectType type=generator.getType();
    if (type!=SkillEffectType.ATTACK)
    {
      attrs.addAttribute("","",SkillEffectsXMLConstants.SKILL_EFFECT_TYPE_ATTR,XmlWriter.CDATA,type.name());
    }
    hd.startElement("","",SkillEffectsXMLConstants.EFFECT_TAG,attrs);
    hd.endElement("","",SkillEffectsXMLConstants.EFFECT_TAG);
  }

  /**
   * Read skill effects.
   * @param skillTag Parent tag.
   * @param skill The parent skill.
   */
  public static void readSkillEffects(Element skillTag, SkillDescription skill)
  {
    List<Element> effectTags=DOMParsingTools.getChildTagsByName(skillTag,SkillEffectsXMLConstants.EFFECT_TAG);
    for(Element effectTag : effectTags)
    {
      NamedNodeMap attrs=effectTag.getAttributes();
      // ID
      int id=DOMParsingTools.getIntAttribute(attrs,SkillEffectsXMLConstants.EFFECT_ID_ATTR,0);
      // Spellcraft
      Float spellcraft=DOMParsingTools.getFloatAttribute(attrs,SkillEffectsXMLConstants.EFFECT_SPELLCRAFT_ATTR,null);
      // Duration
      Float duration=DOMParsingTools.getFloatAttribute(attrs,SkillEffectsXMLConstants.EFFECT_DURATION_ATTR,null);
      // Type
      String typeStr=DOMParsingTools.getStringAttribute(attrs,SkillEffectsXMLConstants.SKILL_EFFECT_TYPE_ATTR,null);
      SkillEffectType type=SkillEffectType.ATTACK;
      if (typeStr!=null)
      {
        type=SkillEffectType.valueOf(typeStr);
      }
      Effect effect=EffectsManager.getInstance().getEffectById(id);
      if (effect!=null)
      {
        SkillEffectGenerator generator=new SkillEffectGenerator(effect,spellcraft,duration);
        generator.setType(type);
        SkillDescription.addEffect(skill,generator);
      }
      else
      {
        LOGGER.warn("Unknown effect: id="+id+" in skill "+skill.getIdentifier());
      }
    }
  }
}
