package delta.games.lotro.character.skills.attack.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.skills.attack.SkillAttack;
import delta.games.lotro.common.enums.DamageQualifier;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.progression.ProgressionsManager;
import delta.games.lotro.common.properties.ModPropertyList;
import delta.games.lotro.common.properties.io.ModPropertyListIO;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.utils.maths.Progression;

/**
 * XML I/O for skill attacks.
 * @author DAM
 */
public class SkillAttacksXmlIO
{
 /**
   * Write skill attack.
   * @param hd Output
   * @param attack Attack.
   * @throws SAXException If an error occurs.
   */
  public static void writeSkillAttack(TransformerHandler hd, SkillAttack attack) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Damage qualifier
    DamageQualifier damageQualifier=attack.getDamageQualifier();
    if (damageQualifier!=null)
    {
      attrs.addAttribute("","",SkillAttacksXMLConstants.ATTACK_DAMAGE_QUALIFIER_ATTR,XmlWriter.CDATA,String.valueOf(damageQualifier.getCode()));
    }
    // DPS modifiers
    ModPropertyList dpsMods=attack.getDPSMods();
    String dpsModsStr=ModPropertyListIO.asPersistentString(dpsMods);
    if (!dpsModsStr.isEmpty())
    {
      attrs.addAttribute("","",SkillAttacksXMLConstants.ATTACK_DPS_MODS_ATTR,XmlWriter.CDATA,dpsModsStr);
    }
    // Max damage modifiers
    ModPropertyList maxDamageMods=attack.getMaxDamageMods();
    String maxDamageModsStr=ModPropertyListIO.asPersistentString(maxDamageMods);
    if (!maxDamageModsStr.isEmpty())
    {
      attrs.addAttribute("","",SkillAttacksXMLConstants.ATTACK_MAX_DAMAGE_MODS_ATTR,XmlWriter.CDATA,maxDamageModsStr);
    }
    // Damage modifiers mods
    ModPropertyList damageModifiersMods=attack.getDamageModifiersMods();
    String damageModifiersModsStr=ModPropertyListIO.asPersistentString(damageModifiersMods);
    if (!damageModifiersModsStr.isEmpty())
    {
      attrs.addAttribute("","",SkillAttacksXMLConstants.ATTACK_DAMAGE_MODIFIERS_MODS_ATTR,XmlWriter.CDATA,damageModifiersModsStr);
    }
    // Damage type
    DamageType damageType=attack.getDamageType();
    if (damageType!=null)
    {
      attrs.addAttribute("","",SkillAttacksXMLConstants.ATTACK_DAMAGE_TYPE_ATTR,XmlWriter.CDATA,String.valueOf(damageQualifier.getCode()));
    }
    // Damage contribution multiplier
    Float damageContributionMultiplier=attack.getDamageContributionMultiplier();
    if (damageContributionMultiplier!=null)
    {
      attrs.addAttribute("","",SkillAttacksXMLConstants.ATTACK_DAMAGE_CONTRIBUTION_MULTIPLIER_ATTR,XmlWriter.CDATA,damageContributionMultiplier.toString());
    }
    // DPS mod progression
    Progression dpsModProgression=attack.getDPSModProgression();
    if (dpsModProgression!=null)
    {
      attrs.addAttribute("","",SkillAttacksXMLConstants.ATTACK_DPS_MOD_PROGRESSION_ATTR,XmlWriter.CDATA,String.valueOf(dpsModProgression.getIdentifier()));
    }
    // Max damage
    float maxDamage=attack.getMaxDamage();
    attrs.addAttribute("","",SkillAttacksXMLConstants.ATTACK_MAX_DAMAGE_ATTR,XmlWriter.CDATA,String.valueOf(maxDamage));
    // Max damage variance
    float maxDamageVariance=attack.getMaxDamageVariance();
    attrs.addAttribute("","",SkillAttacksXMLConstants.ATTACK_MAX_DAMAGE_VARIANCE_ATTR,XmlWriter.CDATA,String.valueOf(maxDamageVariance));
    // Max damage progression
    Progression maxDamageProgression=attack.getMaxDamageProgression();
    if (maxDamageProgression!=null)
    {
      attrs.addAttribute("","",SkillAttacksXMLConstants.ATTACK_MAX_DAMAGE_PROGRESSION_ATTR,XmlWriter.CDATA,String.valueOf(maxDamageProgression.getIdentifier()));
    }
    // Damage modifier
    float damageModifier=attack.getDamageModifier();
    attrs.addAttribute("","",SkillAttacksXMLConstants.ATTACK_DAMAGE_MODIFIER_ATTR,XmlWriter.CDATA,String.valueOf(damageModifier));
    // Implement contribution multiplier
    Float implementContributionMultiplier=attack.getImplementContributionMultiplier();
    if (implementContributionMultiplier!=null)
    {
      attrs.addAttribute("","",SkillAttacksXMLConstants.ATTACK_IMPLEMENT_CONTRIBUTION_MULTIPLIER_ATTR,XmlWriter.CDATA,implementContributionMultiplier.toString());
    }
    // Flags
    int flags=attack.getFlags();
    if (flags!=0)
    {
      attrs.addAttribute("","",SkillAttacksXMLConstants.ATTACK_FLAGS_ATTR,XmlWriter.CDATA,String.valueOf(flags));
    }
    hd.startElement("","",SkillAttacksXMLConstants.ATTACK_TAG,attrs);
    hd.endElement("","",SkillAttacksXMLConstants.ATTACK_TAG);
  }

  /**
   * Read a skill attack.
   * @param attackTag Attack tag.
   * @return the loaded attack.
   */
  public static SkillAttack readSkillAttack(Element attackTag)
  {
    SkillAttack ret=new SkillAttack();
    NamedNodeMap attrs=attackTag.getAttributes();

    // Damage qualifier
    Integer damageQualifierCode=DOMParsingTools.getIntegerAttribute(attrs,SkillAttacksXMLConstants.ATTACK_DAMAGE_QUALIFIER_ATTR,null);
    if (damageQualifierCode!=null)
    {
      LotroEnum<DamageQualifier> damageQualifierEnum=LotroEnumsRegistry.getInstance().get(DamageQualifier.class);
      DamageQualifier damageQualifier=damageQualifierEnum.getEntry(damageQualifierCode.intValue());
      ret.setDamageQualifier(damageQualifier);
    }
    // DPS modifiers
    String dpsModsStr=DOMParsingTools.getStringAttribute(attrs,SkillAttacksXMLConstants.ATTACK_DPS_MODS_ATTR,null);
    ModPropertyList dpsMods=ModPropertyListIO.fromPersistedString(dpsModsStr);
    ret.setDPSMods(dpsMods);
    // Max damage modifiers
    String maxDamageModsStr=DOMParsingTools.getStringAttribute(attrs,SkillAttacksXMLConstants.ATTACK_MAX_DAMAGE_MODS_ATTR,null);
    ModPropertyList maxDamageMods=ModPropertyListIO.fromPersistedString(maxDamageModsStr);
    ret.setMaxDamageMods(maxDamageMods);
    // Damage modifiers mods
    String damageModifiersModsStr=DOMParsingTools.getStringAttribute(attrs,SkillAttacksXMLConstants.ATTACK_DAMAGE_MODIFIERS_MODS_ATTR,null);
    ModPropertyList damageModifiersMods=ModPropertyListIO.fromPersistedString(damageModifiersModsStr);
    ret.setDamageModifiersMods(damageModifiersMods);
    // Damage type
    Integer damageTypeCode=DOMParsingTools.getIntegerAttribute(attrs,SkillAttacksXMLConstants.ATTACK_DAMAGE_TYPE_ATTR,null);
    if (damageTypeCode!=null)
    {
      LotroEnum<DamageType> damageTypeEnum=LotroEnumsRegistry.getInstance().get(DamageType.class);
      DamageType damageType=damageTypeEnum.getEntry(damageTypeCode.intValue());
      ret.setDamageType(damageType);
    }
    // Damage contribution multiplier
    Float damageContributionMultiplier=DOMParsingTools.getFloatAttribute(attrs,SkillAttacksXMLConstants.ATTACK_DAMAGE_CONTRIBUTION_MULTIPLIER_ATTR,null);
    ret.setDamageContributionMultiplier(damageContributionMultiplier);
    // DPS mod progression
    Integer dpsModProgressionID=DOMParsingTools.getIntegerAttribute(attrs,SkillAttacksXMLConstants.ATTACK_DPS_MOD_PROGRESSION_ATTR,null);
    if (dpsModProgressionID!=null)
    {
      Progression dpsModProgression=ProgressionsManager.getInstance().getProgression(dpsModProgressionID.intValue());
      ret.setDPSModProgression(dpsModProgression);
    }
    // Max damage
    float maxDamage=DOMParsingTools.getFloatAttribute(attrs,SkillAttacksXMLConstants.ATTACK_MAX_DAMAGE_ATTR,0);
    ret.setMaxDamage(maxDamage);
    // Max damage variance
    float maxDamageVariance=DOMParsingTools.getFloatAttribute(attrs,SkillAttacksXMLConstants.ATTACK_MAX_DAMAGE_VARIANCE_ATTR,0);
    ret.setMaxDamageVariance(maxDamageVariance);
    // Max damage progression
    Integer maxDamageProgressionID=DOMParsingTools.getIntegerAttribute(attrs,SkillAttacksXMLConstants.ATTACK_MAX_DAMAGE_PROGRESSION_ATTR,null);
    if (maxDamageProgressionID!=null)
    {
      Progression maxDamageProgression=ProgressionsManager.getInstance().getProgression(maxDamageProgressionID.intValue());
      ret.setMaxDamageProgression(maxDamageProgression);
    }
    // Damage modifier
    float damageModifier=DOMParsingTools.getFloatAttribute(attrs,SkillAttacksXMLConstants.ATTACK_DAMAGE_MODIFIER_ATTR,0);
    ret.setDamageModifier(damageModifier);
    // Implement contribution multiplier
    Float implementContributionMultiplier=DOMParsingTools.getFloatAttribute(attrs,SkillAttacksXMLConstants.ATTACK_IMPLEMENT_CONTRIBUTION_MULTIPLIER_ATTR,null);
    ret.setImplementContributionMultiplier(implementContributionMultiplier);
    // Flags
    int flags=DOMParsingTools.getIntAttribute(attrs,SkillAttacksXMLConstants.ATTACK_FLAGS_ATTR,0);
    ret.setFlags(flags);

    return ret;
  }
}
