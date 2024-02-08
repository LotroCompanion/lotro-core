package delta.games.lotro.lore.buffs.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.requirements.io.xml.UsageRequirementsXMLWriter;
import delta.games.lotro.lore.buffs.EffectBuff;

/**
 * Writes effect buffs to XML files.
 * @author DAM
 */
public class EffectBuffXMLWriter
{
  /**
   * Write some buffs to a XML file.
   * @param toFile File to write to.
   * @param buffs Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<EffectBuff> buffs)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",EffectBuffXMLConstants.BUFFS_TAG,new AttributesImpl());
        for(EffectBuff buff : buffs)
        {
          writeEffectBuff(hd,buff);
        }
        hd.endElement("","",EffectBuffXMLConstants.BUFFS_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeEffectBuff(TransformerHandler hd, EffectBuff buff) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Key
    String key=buff.getKey();
    if (key.length()>0)
    {
      attrs.addAttribute("","",EffectBuffXMLConstants.BUFF_KEY_ATTR,XmlWriter.CDATA,key);
    }
    // Effect
    Effect effect=buff.getEffect();
    // - ID
    int effectId=effect.getIdentifier();
    attrs.addAttribute("","",EffectBuffXMLConstants.BUFF_EFFECT_ID_ATTR,XmlWriter.CDATA,String.valueOf(effectId));
    // - Name
    String effectName=effect.getName();
    if (effectName!=null)
    {
      attrs.addAttribute("","",EffectBuffXMLConstants.BUFF_EFFECT_NAME_ATTR,XmlWriter.CDATA,effectName);
    }
    // Requirements
    UsageRequirementsXMLWriter.write(attrs,buff.getUsageRequirements());
    hd.startElement("","",EffectBuffXMLConstants.BUFF_TAG,attrs);
    hd.endElement("","",EffectBuffXMLConstants.BUFF_TAG);
  }
}
