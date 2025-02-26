package delta.games.lotro.character.status.effects.io.xml;

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
import delta.games.lotro.character.status.effects.CharacterEffectsManager;
import delta.games.lotro.character.status.effects.EffectInstance;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectsManager;
import delta.games.lotro.common.id.InternalGameId;

/**
 * XML I/O for character effects.
 * @author DAM
 */
public class CharacterEffectsXMLIO
{
  private static final Logger LOGGER=LoggerFactory.getLogger(CharacterEffectsXMLIO.class);

  /**
   * Write some character effects to the given XML stream.
   * @param hd XML output stream.
   * @param mgr Data to write.
   * @throws SAXException If an error occurs.
   */
  public static void write(TransformerHandler hd, CharacterEffectsManager mgr) throws SAXException
  {
    if (mgr==null)
    {
      return;
    }
    hd.startElement("","",CharacterEffectsXMLConstants.EFFECTS_TAG,new AttributesImpl());
    for(EffectInstance effectInstance : mgr.getEffects())
    {
      AttributesImpl attrs=new AttributesImpl();
      Effect effect=effectInstance.getEffect();
      if (effect==null)
      {
        continue;
      }
      // ID
      int id=effect.getIdentifier();
      attrs.addAttribute("","",CharacterEffectsXMLConstants.EFFECT_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      String name=effect.getName();
      if ((name!=null) && (!name.isEmpty()))
      {
        attrs.addAttribute("","",CharacterEffectsXMLConstants.EFFECT_NAME_ATTR,XmlWriter.CDATA,name);
      }
      // Spellcraft
      Float spellcraft=effectInstance.getSpellcraft();
      if (spellcraft!=null)
      {
        attrs.addAttribute("","",CharacterEffectsXMLConstants.EFFECT_SPELLCRAFT_ATTR,XmlWriter.CDATA,spellcraft.toString());
      }
      // Cast time
      Long castTime=effectInstance.getCastTime();
      if (castTime!=null)
      {
        attrs.addAttribute("","",CharacterEffectsXMLConstants.EFFECT_CAST_TIME_ATTR,XmlWriter.CDATA,castTime.toString());
      }
      // Caster
      InternalGameId casterID=effectInstance.getCasterID();
      if (casterID!=null)
      {
        attrs.addAttribute("","",CharacterEffectsXMLConstants.EFFECT_CASTER_ID_ATTR,XmlWriter.CDATA,casterID.asPersistedString());
      }
      hd.startElement("","",CharacterEffectsXMLConstants.EFFECT_TAG,attrs);
      hd.endElement("","",CharacterEffectsXMLConstants.EFFECT_TAG);
    }
    hd.endElement("","",CharacterEffectsXMLConstants.EFFECTS_TAG);
  }

  /**
   * Load effects from a XML tag.
   * @param root Root tag.
   * @param effectsMgr Storage for loaded effects.
   */
  public static void parse(Element root, CharacterEffectsManager effectsMgr)
  {
    if (root==null)
    {
      return;
    }
    effectsMgr.clear();
    List<Element> effectTags=DOMParsingTools.getChildTagsByName(root,CharacterEffectsXMLConstants.EFFECT_TAG,false);
    for(Element effectTag : effectTags)
    {
      NamedNodeMap attrs=effectTag.getAttributes();
      // ID
      int id=DOMParsingTools.getIntAttribute(attrs,CharacterEffectsXMLConstants.EFFECT_ID_ATTR,0);
      Effect effect=EffectsManager.getInstance().getEffectById(id);
      if (effect==null)
      {
        LOGGER.warn("Unknown effect ID={}",Integer.valueOf(id));
        continue;
      }
      EffectInstance instance=new EffectInstance(effect);
      // Spellcraft
      Float spellcraft=DOMParsingTools.getFloatAttribute(attrs,CharacterEffectsXMLConstants.EFFECT_SPELLCRAFT_ATTR,null);
      instance.setSpellcraft(spellcraft);
      // Cast time
      long castTime=DOMParsingTools.getLongAttribute(attrs,CharacterEffectsXMLConstants.EFFECT_CAST_TIME_ATTR,0);
      if (castTime!=0)
      {
        instance.setCastTime(Long.valueOf(castTime));
      }
      // Caster
      String casterIDStr=DOMParsingTools.getStringAttribute(attrs,CharacterEffectsXMLConstants.EFFECT_CASTER_ID_ATTR,null);
      if (casterIDStr!=null)
      {
        InternalGameId casterID=InternalGameId.fromString(casterIDStr);
        instance.setCasterID(casterID);
      }
      effectsMgr.addEffect(instance);
    }
  }

}
