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
import delta.games.lotro.character.skills.SkillEffectGenerator;
import delta.games.lotro.character.skills.SkillEffectType;
import delta.games.lotro.character.skills.SingleTypeSkillEffectsManager;
import delta.games.lotro.character.skills.SkillEffectsManager;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectsManager;
import delta.games.lotro.common.enums.ImplementUsageType;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.properties.io.ModPropertyListIO;

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
    List<SingleTypeSkillEffectsManager> mgrs=effectsMgr.getAll();
    if (!mgrs.isEmpty())
    {
      for(SingleTypeSkillEffectsManager mgr : mgrs)
      {
        writeSkillEffects(hd,mgr);
      }
    }
  }

  private static void writeSkillEffects(TransformerHandler hd, SingleTypeSkillEffectsManager effectsMgr) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Type
    SkillEffectType type=effectsMgr.getType();
    if (type!=SkillEffectType.ATTACK)
    {
      attrs.addAttribute("","",SkillEffectsXMLConstants.EFFECTS_MGR_TYPE_ATTR,XmlWriter.CDATA,type.name());
    }
    // Modifiers
    String modifiersStr=ModPropertyListIO.asPersistentString(effectsMgr.getAdditiveModifiers());
    if (!modifiersStr.isEmpty())
    {
      attrs.addAttribute("","",SkillEffectsXMLConstants.EFFECTS_MGR_MODIFIERS_ATTR,XmlWriter.CDATA,modifiersStr);
    }
    // Override
    Integer overridePropertyID=effectsMgr.getOverridePropertyID();
    if (overridePropertyID!=null)
    {
      attrs.addAttribute("","",SkillEffectsXMLConstants.EFFECTS_MGR_OVERRIDE_ATTR,XmlWriter.CDATA,overridePropertyID.toString());
    }
    hd.startElement("","",SkillEffectsXMLConstants.EFFECTS_MGR_TAG,attrs);
    // Effects
    List<SkillEffectGenerator> effects=effectsMgr.getEffects();
    for(SkillEffectGenerator generator : effects)
    {
      writeEffectGenerator(hd,generator);
    }
    hd.endElement("","",SkillEffectsXMLConstants.EFFECTS_MGR_TAG);
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
    // Implement usage
    ImplementUsageType implementUsage=generator.getImplementUsage();
    if (implementUsage!=null)
    {
      attrs.addAttribute("","",SkillEffectsXMLConstants.SKILL_EFFECT_IMPLEMENT_ATTR,XmlWriter.CDATA,String.valueOf(implementUsage.getCode()));
    }
    hd.startElement("","",SkillEffectsXMLConstants.EFFECT_TAG,attrs);
    hd.endElement("","",SkillEffectsXMLConstants.EFFECT_TAG);
  }

  /**
   * Read skill effects.
   * @param parentTag Parent tag.
   * @return the loaded skills manager or <code>null</code>.
   */
  public static SkillEffectsManager readSkillEffects(Element parentTag)
  {
    List<Element> effectsMgrTags=DOMParsingTools.getChildTagsByName(parentTag,SkillEffectsXMLConstants.EFFECTS_MGR_TAG,false);
    if (effectsMgrTags.isEmpty())
    {
      return null;
    }
    SkillEffectsManager ret=new SkillEffectsManager();
    for(Element effectsMgrTag : effectsMgrTags)
    {
      SingleTypeSkillEffectsManager mgr=readSkillEffectsManager(effectsMgrTag);
      ret.setEffects(mgr.getType(),mgr);
    }
    return ret;
  }

  private static SingleTypeSkillEffectsManager readSkillEffectsManager(Element parentTag)
  {
    LotroEnum<ImplementUsageType> implementUsageEnum=LotroEnumsRegistry.getInstance().get(ImplementUsageType.class);

    NamedNodeMap mainAttrs=parentTag.getAttributes();
    // Type
    String typeStr=DOMParsingTools.getStringAttribute(mainAttrs,SkillEffectsXMLConstants.EFFECTS_MGR_TYPE_ATTR,null);
    SkillEffectType type=SkillEffectType.ATTACK;
    if (typeStr!=null)
    {
      type=SkillEffectType.valueOf(typeStr);
    }
    SingleTypeSkillEffectsManager mgr=new SingleTypeSkillEffectsManager(type);
    // Modifiers
    String modifiersStr=DOMParsingTools.getStringAttribute(mainAttrs,SkillEffectsXMLConstants.EFFECTS_MGR_MODIFIERS_ATTR,null);
    mgr.setAdditiveModifiers(ModPropertyListIO.fromPersistedString(modifiersStr));
    // Override
    Integer overridePropertyID=DOMParsingTools.getIntegerAttribute(mainAttrs,SkillEffectsXMLConstants.EFFECTS_MGR_OVERRIDE_ATTR,null);
    mgr.setOverridePropertyID(overridePropertyID);
    // Effects
    List<Element> effectTags=DOMParsingTools.getChildTagsByName(parentTag,SkillEffectsXMLConstants.EFFECT_TAG,false);
    for(Element effectTag : effectTags)
    {
      NamedNodeMap attrs=effectTag.getAttributes();
      // ID
      int id=DOMParsingTools.getIntAttribute(attrs,SkillEffectsXMLConstants.EFFECT_ID_ATTR,0);
      // Spellcraft
      Float spellcraft=DOMParsingTools.getFloatAttribute(attrs,SkillEffectsXMLConstants.EFFECT_SPELLCRAFT_ATTR,null);
      // Duration
      Float duration=DOMParsingTools.getFloatAttribute(attrs,SkillEffectsXMLConstants.EFFECT_DURATION_ATTR,null);
      // Implement usage
      ImplementUsageType implementUsage=null;
      Integer implementUsageCode=DOMParsingTools.getIntegerAttribute(attrs,SkillEffectsXMLConstants.SKILL_EFFECT_IMPLEMENT_ATTR,null);
      if (implementUsageCode!=null)
      {
        implementUsage=implementUsageEnum.getEntry(implementUsageCode.intValue());
      }
      Effect effect=EffectsManager.getInstance().getEffectById(id);
      if (effect!=null)
      {
        SkillEffectGenerator generator=new SkillEffectGenerator(effect,spellcraft,duration);
        generator.setType(type);
        generator.setImplementUsage(implementUsage);
        mgr.addEffect(generator);
      }
      else
      {
        LOGGER.warn("Unknown effect: id="+id);
      }
    }
    return mgr;
  }
}
