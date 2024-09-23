package delta.games.lotro.character.skills.io.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.NumericTools;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.skills.SkillGambitData;
import delta.games.lotro.common.enums.GambitIconType;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;

/**
 * XML I/O for gambit data of skills.
 * @author DAM
 */
public class SkillGambitsXmlIO
{
  /**
   * Write skill gambit data.
   * @param hd Output
   * @param data Data to write.
   * @throws SAXException If an error occurs.
   */
  public static void writeGambitData(TransformerHandler hd, SkillGambitData data) throws SAXException
  {
    if (data==null)
    {
      return;
    }
    AttributesImpl attrs=new AttributesImpl();
    // Required
    List<GambitIconType> required=data.getRequired();
    if (required!=null)
    {
      String requiredStr=asPersistedString(required);
      attrs.addAttribute("","",SkillGambitsXMLConstants.GAMBIT_REQUIRED_ATTR,XmlWriter.CDATA,requiredStr);
    }
    // To add
    List<GambitIconType> toAdd=data.getToAdd();
    if (toAdd!=null)
    {
      String toAddStr=asPersistedString(toAdd);
      attrs.addAttribute("","",SkillGambitsXMLConstants.GAMBIT_TO_ADD_ATTR,XmlWriter.CDATA,toAddStr);
    }
    // To remove
    String toRemoveStr=null;
    if (data.isClearAllGambits())
    {
      toRemoveStr="all";
    }
    else if (data.getToRemove()>0)
    {
      toRemoveStr=String.valueOf(data.getToRemove());
    }
    if (toRemoveStr!=null)
    {
      attrs.addAttribute("","",SkillGambitsXMLConstants.GAMBIT_TO_REMOVE_ATTR,XmlWriter.CDATA,toRemoveStr);
    }
    hd.startElement("","",SkillGambitsXMLConstants.GAMBIT_TAG,attrs);
    hd.endElement("","",SkillGambitsXMLConstants.GAMBIT_TAG);
  }

  private static String asPersistedString(List<GambitIconType> gambits)
  {
    StringBuilder sb=new StringBuilder();
    for(GambitIconType gambit : gambits)
    {
      if (sb.length()>0)
      {
        sb.append(',');
      }
      sb.append(gambit.getCode());
    }
    return sb.toString();
  }

  private static List<GambitIconType> fromPersistedString(String gambitsStr)
  {
    if (gambitsStr==null)
    {
      return null; // NOSONAR
    }
    List<GambitIconType> ret=new ArrayList<GambitIconType>();
    if (!gambitsStr.isEmpty())
    {
      LotroEnum<GambitIconType> gambitTypesEnum=LotroEnumsRegistry.getInstance().get(GambitIconType.class);
      String[] gambitCodes=gambitsStr.split(",");
      for(String gambitCodeStr : gambitCodes)
      {
        int gambitCode=NumericTools.parseInt(gambitCodeStr,0);
        GambitIconType gambitType=gambitTypesEnum.getEntry(gambitCode);
        ret.add(gambitType);
      }
    }
    return ret;
  }

  /**
   * Read skill gambit data.
   * @param gambitTag Gambit data tag.
   * @return the loaded data.
   */
  public static SkillGambitData readGambitData(Element gambitTag)
  {
    SkillGambitData ret=new SkillGambitData();
    NamedNodeMap attrs=gambitTag.getAttributes();
    // Required
    String requiredStr=DOMParsingTools.getStringAttribute(attrs,SkillGambitsXMLConstants.GAMBIT_REQUIRED_ATTR,null);
    if (requiredStr!=null)
    {
      List<GambitIconType> requiredGambits=fromPersistedString(requiredStr);
      ret.setRequired(requiredGambits);
    }
    // To add
    String toAddStr=DOMParsingTools.getStringAttribute(attrs,SkillGambitsXMLConstants.GAMBIT_TO_ADD_ATTR,null);
    if (toAddStr!=null)
    {
      List<GambitIconType> gambitsToAdd=fromPersistedString(toAddStr);
      ret.setToAdd(gambitsToAdd);
    }
    // To remove
    String toRemoveStr=DOMParsingTools.getStringAttribute(attrs,SkillGambitsXMLConstants.GAMBIT_TO_REMOVE_ATTR,null);
    if (toRemoveStr!=null)
    {
      if ("all".equals(toRemoveStr))
      {
        ret.setClearAllGambits();
      }
      else
      {
        int nbToRemove=NumericTools.parseInt(toRemoveStr,0);
        ret.setToRemove(nbToRemove);
      }
    }
    return ret;
  }
}
