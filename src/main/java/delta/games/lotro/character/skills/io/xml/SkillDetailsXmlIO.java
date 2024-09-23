package delta.games.lotro.character.skills.io.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.character.skills.SkillDetails;
import delta.games.lotro.character.skills.SkillEffectsManager;
import delta.games.lotro.character.skills.attack.SkillAttack;
import delta.games.lotro.character.skills.attack.SkillAttacks;
import delta.games.lotro.character.skills.attack.io.xml.SkillAttacksXmlIO;
import delta.games.lotro.character.skills.geometry.io.xml.SkillGeometryXmlIO;
import delta.games.lotro.common.enums.ResistCategory;
import delta.games.lotro.common.enums.SkillDisplayType;
import delta.games.lotro.common.enums.comparator.LotroEnumEntryCodeComparator;
import delta.games.lotro.common.inductions.Induction;
import delta.games.lotro.common.properties.ModPropertyList;
import delta.games.lotro.common.properties.io.ModPropertyListIO;

/**
 * XML I/O for skill details.
 * @author DAM
 */
public class SkillDetailsXmlIO
{
  /**
   * Write skill details.
   * @param hd Output
   * @param data Data to write.
   * @throws SAXException If an error occurs.
   */
  public static void writeSkillDetails(TransformerHandler hd, SkillDetails data) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    writeSkillDetailsAttributes(attrs,data);
    hd.startElement("","",SkillDetailsXMLConstants.DETAILS_TAG,attrs);
    writeChildTags(hd,data);
    hd.endElement("","",SkillDetailsXMLConstants.DETAILS_TAG);
  }

  private static void writeSkillDetailsAttributes(AttributesImpl attrs, SkillDetails data)
  {
    // ID
    int id=data.getId();
    attrs.addAttribute("","","id",XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=data.getName();
    attrs.addAttribute("","","name",XmlWriter.CDATA,name);
    // Action duration contribution
    Float actionDurationContribution=data.getActionDurationContribution();
    if (actionDurationContribution!=null)
    {
      attrs.addAttribute("","",SkillDetailsXMLConstants.ACTION_DURATION_CONTRIBUTION_ATTR,XmlWriter.CDATA,actionDurationContribution.toString());
    }
    // Induction
    Induction induction=data.getInduction();
    if (induction!=null)
    {
      attrs.addAttribute("","",SkillDetailsXMLConstants.INDUCTION_ID_ATTR,XmlWriter.CDATA,String.valueOf(induction.getIdentifier()));
    }
    // Channeling duration
    Float channelingDuration=data.getChannelingDuration();
    if (channelingDuration!=null)
    {
      attrs.addAttribute("","",SkillDetailsXMLConstants.CHANNELING_DURATION_ATTR,XmlWriter.CDATA,channelingDuration.toString());
    }
    // Cooldown
    Float cooldown=data.getChannelingDuration();
    if (cooldown!=null)
    {
      attrs.addAttribute("","",SkillDetailsXMLConstants.COOLDOWN_ATTR,XmlWriter.CDATA,cooldown.toString());
    }
    // Flags
    int flags=data.getFlags();
    if (flags!=0)
    {
      attrs.addAttribute("","",SkillDetailsXMLConstants.FLAGS_ATTR,XmlWriter.CDATA,String.valueOf(flags));
    }
    // Max targets
    Integer maxTargets=data.getMaxTargets();
    if ((maxTargets!=null) && (maxTargets.intValue()>0))
    {
      attrs.addAttribute("","",SkillDetailsXMLConstants.MAX_TARGETS_ATTR,XmlWriter.CDATA,maxTargets.toString());
    }
    // Max targets modifiers
    ModPropertyList maxTargetsMods=data.getMaxTargetsMods();
    String maxTargetsModsStr=ModPropertyListIO.asPersistentString(maxTargetsMods);
    if (!maxTargetsModsStr.isEmpty())
    {
      attrs.addAttribute("","",SkillDetailsXMLConstants.MAX_TARGETS_MODS_ATTR,XmlWriter.CDATA,maxTargetsModsStr);
    }
    // Resist category
    ResistCategory resistCategory=data.getResistCategory();
    if (resistCategory!=null)
    {
      attrs.addAttribute("","",SkillDetailsXMLConstants.RESIST_CATEGORY_ATTR,XmlWriter.CDATA,String.valueOf(resistCategory.getCode()));
    }
    // Display types
    Set<SkillDisplayType> displayTypes=data.getDisplayTypes();
    if ((displayTypes!=null) && (!displayTypes.isEmpty()))
    {
      String displayTypeCodes=asCodes(displayTypes);
      attrs.addAttribute("","",SkillDetailsXMLConstants.DISPLAY_TYPES_ATTR,XmlWriter.CDATA,displayTypeCodes);
    }
  }

  private static String asCodes(Set<SkillDisplayType> displayTypes)
  {
    List<SkillDisplayType> list=new ArrayList<SkillDisplayType>(displayTypes);
    Collections.sort(list,new LotroEnumEntryCodeComparator<SkillDisplayType>());
    StringBuilder sb=new StringBuilder();
    for(SkillDisplayType type : list)
    {
      if (sb.length()>0)
      {
        sb.append(',');
      }
      sb.append(type.getCode());
    }
    return sb.toString();
  }

  private static void writeChildTags(TransformerHandler hd, SkillDetails data) throws SAXException
  {
    // Geometry
    SkillGeometryXmlIO.writeGeometryData(hd,data.getGeometry());
    // Effects
    SkillEffectsManager effects=data.getEffects();
    if (effects!=null)
    {
      SkillEffectsXmlIO.writeSkillEffects(hd,effects);
    }
    // Vital cost
    SkillCostXmlIO.writeCostData(hd,data.getCostData());
    // PIP
    SkillPipXmlIO.writePipData(hd,data.getPIPData());
    // Gambit
    SkillGambitsXmlIO.writeGambitData(hd,data.getGambitData());
    // Attacks
    SkillAttacks attacks=data.getAttacks();
    if (attacks!=null)
    {
      for(SkillAttack attack : attacks.getAttacks())
      {
        SkillAttacksXmlIO.writeSkillAttack(hd,attack);
      }
    }
  }
}
