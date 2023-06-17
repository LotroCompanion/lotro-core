package delta.games.lotro.common.effects.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLWriter;

/**
 * Writes effects to XML documents.
 * @author DAM
 */
public class EffectXMLWriter
{
  /**
   * Write some effects to a XML file.
   * @param toFile File to write to.
   * @param effects Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<Effect> effects)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",EffectXMLConstants.EFFECTS_TAG,new AttributesImpl());
        for(Effect effect : effects)
        {
          writeEffect(hd,effect);
        }
        hd.endElement("","",EffectXMLConstants.EFFECTS_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  /**
   * Write an effect to a XML document.
   * @param hd Output.
   * @param effect Data to write.
   * @throws SAXException If an error occurs.
   */
  public static void writeEffect(TransformerHandler hd, Effect effect) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=effect.getIdentifier();
    attrs.addAttribute("","",EffectXMLConstants.EFFECT_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=effect.getName();
    if (name!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants.EFFECT_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Icon
    Integer iconId=effect.getIconId();
    if (iconId!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants.EFFECT_ICON_ID_ATTR,XmlWriter.CDATA,iconId.toString());
    }
    // Duration
    Float duration=effect.getDuration();
    if (duration!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants.EFFECT_DURATION_ATTR,XmlWriter.CDATA,duration.toString());
    }
    hd.startElement("","",EffectXMLConstants.EFFECT_TAG,attrs);
    // Stats
    StatsProvider statsProvider=effect.getStatsProvider();
    if (statsProvider!=null)
    {
      StatsProviderXMLWriter.writeXml(hd,statsProvider);
    }
    hd.endElement("","",EffectXMLConstants.EFFECT_TAG);
  }
}
