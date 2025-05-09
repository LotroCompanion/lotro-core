package delta.games.lotro.common.requirements.io.xml;

import org.w3c.dom.NamedNodeMap;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.common.utils.xml.SAXParsingTools;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectsManager;
import delta.games.lotro.common.requirements.EffectRequirement;

/**
 * XML I/O for effect requirements.
 * @author DAM
 */
public class EffectRequirementXMLIO implements RequirementSAXWriter<EffectRequirement>,RequirementXMLReader<EffectRequirement>
{
  @Override
  public EffectRequirement readSAX(Attributes attributes)
  {
    Integer effectID=SAXParsingTools.getIntegerAttribute(attributes,UsageRequirementXMLConstants.REQUIRED_EFFECT_ATTR,null);
    return buildRequirement(effectID);
  }

  @Override
  public EffectRequirement readDOM(NamedNodeMap attrs)
  {
    Integer effectID=DOMParsingTools.getIntegerAttribute(attrs,UsageRequirementXMLConstants.REQUIRED_EFFECT_ATTR,null);
    return buildRequirement(effectID);
  }

  private EffectRequirement buildRequirement(Integer effectID)
  {
    EffectRequirement effectRequirement=null;
    if (effectID!=null)
    {
      Effect effect=EffectsManager.getInstance().getEffectById(effectID.intValue());
      if (effect!=null)
      {
        effectRequirement=new EffectRequirement(effect);
      }
    }
    return effectRequirement;
  }

  @Override
  public void write(AttributesImpl attrs, EffectRequirement effectRequirement)
  {
    if (effectRequirement!=null)
    {
      int effectID=effectRequirement.getEffect().getIdentifier();
      attrs.addAttribute("","",UsageRequirementXMLConstants.REQUIRED_EFFECT_ATTR,XmlWriter.CDATA,String.valueOf(effectID));
    }
  }
}
