package delta.games.lotro.character.skills.io.xml;

import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.skills.SkillCostData;
import delta.games.lotro.character.skills.SkillDetails;
import delta.games.lotro.character.skills.SkillEffectsManager;
import delta.games.lotro.character.skills.SkillGambitData;
import delta.games.lotro.character.skills.SkillPipData;
import delta.games.lotro.character.skills.attack.SkillAttack;
import delta.games.lotro.character.skills.attack.SkillAttacks;
import delta.games.lotro.character.skills.attack.io.xml.SkillAttacksXMLConstants;
import delta.games.lotro.character.skills.attack.io.xml.SkillAttacksXmlIO;
import delta.games.lotro.character.skills.geometry.SkillGeometry;
import delta.games.lotro.character.skills.geometry.io.xml.SkillGeometryXMLConstants;
import delta.games.lotro.character.skills.geometry.io.xml.SkillGeometryXmlIO;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.ResistCategory;
import delta.games.lotro.common.enums.SkillDisplayType;
import delta.games.lotro.common.inductions.Induction;
import delta.games.lotro.common.inductions.InductionsManager;
import delta.games.lotro.common.properties.ModPropertyList;
import delta.games.lotro.common.properties.io.ModPropertyListIO;
import delta.games.lotro.utils.enums.EnumXMLUtils;

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
    Float cooldown=data.getCooldown();
    if (cooldown!=null)
    {
      attrs.addAttribute("","",SkillDetailsXMLConstants.COOLDOWN_ATTR,XmlWriter.CDATA,cooldown.toString());
    }
    String cooldownModsStr=ModPropertyListIO.asPersistentString(data.getCooldownMods());
    if (!cooldownModsStr.isEmpty())
    {
      attrs.addAttribute("","",SkillDetailsXMLConstants.COOLDOWN_MODS_ATTR,XmlWriter.CDATA,cooldownModsStr);
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
    List<SkillDisplayType> displayTypes=data.getDisplayTypes();
    if ((displayTypes!=null) && (!displayTypes.isEmpty()))
    {
      String displayTypeCodes=asCodes(displayTypes);
      attrs.addAttribute("","",SkillDetailsXMLConstants.DISPLAY_TYPES_ATTR,XmlWriter.CDATA,displayTypeCodes);
    }
  }

  private static String asCodes(List<SkillDisplayType> displayTypes)
  {
    StringBuilder sb=new StringBuilder();
    for(SkillDisplayType type : displayTypes)
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

  /**
   * Read skill details.
   * @param skillDetailsTag Tag to read.
   * @return A skill details.
   */
  public static SkillDetails readSkillDetails(Element skillDetailsTag)
  {
    NamedNodeMap attrs=skillDetailsTag.getAttributes();
    SkillDetails ret=new SkillDetails();
    // Action duration contribution
    Float actionDurationContribution=DOMParsingTools.getFloatAttribute(attrs,SkillDetailsXMLConstants.ACTION_DURATION_CONTRIBUTION_ATTR,null);
    ret.setActionDurationContribution(actionDurationContribution);
    // Induction
    Integer inductionID=DOMParsingTools.getIntegerAttribute(attrs,SkillDetailsXMLConstants.INDUCTION_ID_ATTR,null);
    if (inductionID!=null)
    {
      Induction induction=InductionsManager.getInstance().get(inductionID.intValue());
      ret.setInduction(induction);
    }
    // Channeling duration
    Float channelingDuration=DOMParsingTools.getFloatAttribute(attrs,SkillDetailsXMLConstants.CHANNELING_DURATION_ATTR,null);
    ret.setChannelingDuration(channelingDuration);
    // Cooldown
    Float cooldown=DOMParsingTools.getFloatAttribute(attrs,SkillDetailsXMLConstants.COOLDOWN_ATTR,null);
    ret.setCooldown(cooldown);
    String cooldownModsStr=DOMParsingTools.getStringAttribute(attrs,SkillDetailsXMLConstants.COOLDOWN_MODS_ATTR,null);
    ModPropertyList cooldownMods=ModPropertyListIO.fromPersistedString(cooldownModsStr);
    ret.setCooldownMods(cooldownMods);
    // Flags
    int flags=DOMParsingTools.getIntAttribute(attrs,SkillDetailsXMLConstants.FLAGS_ATTR,0);
    ret.setFlags(flags);
    // Max targets
    Integer maxTargets=DOMParsingTools.getIntegerAttribute(attrs,SkillDetailsXMLConstants.MAX_TARGETS_ATTR,null);
    ret.setMaxTargets(maxTargets);
    // Max targets modifiers
    String maxTargetsModsStr=DOMParsingTools.getStringAttribute(attrs,SkillDetailsXMLConstants.MAX_TARGETS_MODS_ATTR,null);
    ModPropertyList maxTargetsMods=ModPropertyListIO.fromPersistedString(maxTargetsModsStr);
    ret.setMaxTargetsMods(maxTargetsMods);
    // Resist category
    Integer resistCategoryCode=DOMParsingTools.getIntegerAttribute(attrs,SkillDetailsXMLConstants.RESIST_CATEGORY_ATTR,null);
    if (resistCategoryCode!=null)
    {
      LotroEnum<ResistCategory> resistCategoryEnum=LotroEnumsRegistry.getInstance().get(ResistCategory.class);
      ResistCategory resistCategory=resistCategoryEnum.getEntry(resistCategoryCode.intValue());
      ret.setResistCategory(resistCategory);
    }
    // Display types
    String displayTypeCodes=DOMParsingTools.getStringAttribute(attrs,SkillDetailsXMLConstants.DISPLAY_TYPES_ATTR,null);
    if (displayTypeCodes!=null)
    {
      List<SkillDisplayType> displayTypes=EnumXMLUtils.readEnumEntriesList(displayTypeCodes,SkillDisplayType.class);
      ret.setDisplayTypes(displayTypes);
    }
    readChildNodes(ret,skillDetailsTag);
    return ret;
  }

  private static void readChildNodes(SkillDetails ret, Element skillDetailsTag)
  {
    // Geometry
    Element geometryTag=DOMParsingTools.getChildTagByName(skillDetailsTag,SkillGeometryXMLConstants.GEOMETRY_TAG);
    SkillGeometry geometry=SkillGeometryXmlIO.readGeometryData(geometryTag);
    ret.setGeometry(geometry);
    // Effects
    SkillEffectsManager mgr=SkillEffectsXmlIO.readSkillEffects(skillDetailsTag);
    ret.setEffects(mgr);
    // Vital cost
    SkillCostData costData=SkillCostXmlIO.readCostData(skillDetailsTag);
    ret.setCostData(costData);
    // PIP
    Element pipTag=DOMParsingTools.getChildTagByName(skillDetailsTag,SkillPipXMLConstants.PIP_TAG);
    SkillPipData pipData=SkillPipXmlIO.readPipData(pipTag);
    ret.setPIPData(pipData);
    // Gambit
    Element gambitTag=DOMParsingTools.getChildTagByName(skillDetailsTag,SkillGambitsXMLConstants.GAMBIT_TAG);
    SkillGambitData gambitData=SkillGambitsXmlIO.readGambitData(gambitTag);
    ret.setGambitData(gambitData);
    // Attacks
    List<Element> attackTags=DOMParsingTools.getChildTagsByName(skillDetailsTag,SkillAttacksXMLConstants.ATTACK_TAG);
    if (!attackTags.isEmpty())
    {
      SkillAttacks attacks=new SkillAttacks();
      for(Element attackTag : attackTags)
      {
        SkillAttack attack=SkillAttacksXmlIO.readSkillAttack(attackTag);
        attacks.addAttack(attack);
      }
      ret.setAttacks(attacks);
    }
  }
}
