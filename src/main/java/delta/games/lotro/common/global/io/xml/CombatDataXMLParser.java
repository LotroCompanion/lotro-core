package delta.games.lotro.common.global.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.stats.ratings.ProgressionRatingCurveImpl;
import delta.games.lotro.character.stats.ratings.RatingCurveId;
import delta.games.lotro.character.stats.ratings.RatingsMgr;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.global.CombatData;
import delta.games.lotro.common.global.WeaponStrikeModifiers;
import delta.games.lotro.common.global.WeaponStrikeModifiersManager;
import delta.games.lotro.common.progression.ProgressionsManager;
import delta.games.lotro.lore.items.WeaponType;
import delta.games.lotro.utils.maths.Progression;

/**
 * Parser for data of the combat system stored in XML.
 * @author DAM
 */
public class CombatDataXMLParser
{
  /**
   * Parse a combat data XML file.
   * @param source Source file.
   * @return Parsed data.
   */
  public static CombatData parseCombatDataFile(File source)
  {
    CombatData ret=new CombatData();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      loadCurves(root,ret.getRatingsMgr());
      loadWeaponStrikeModifiers(root,ret.getWeaponStrikeModifiersMgr());
    }
    return ret;
  }

  private static void loadCurves(Element root, RatingsMgr ratingsMgr)
  {
    ProgressionsManager progressionsMgr=ProgressionsManager.getInstance();
    List<Element> curveElements=DOMParsingTools.getChildTagsByName(root,CombatDataXMLConstants.CURVE_TAG);
    for(Element curveElement : curveElements)
    {
      NamedNodeMap attrs=curveElement.getAttributes();
      String curveIdStr=DOMParsingTools.getStringAttribute(attrs,CombatDataXMLConstants.CURVE_ID_ATTR,null);
      RatingCurveId curveId=RatingCurveId.valueOf(curveIdStr);
      int hardCapId=DOMParsingTools.getIntAttribute(attrs,CombatDataXMLConstants.HARD_CAP_ID_ATTR,0);
      Progression hardCap=progressionsMgr.getProgression(hardCapId);
      int ratingId=DOMParsingTools.getIntAttribute(attrs,CombatDataXMLConstants.RATING_ID_ATTR,0);
      Progression rating=progressionsMgr.getProgression(ratingId);
      int targetCapId=DOMParsingTools.getIntAttribute(attrs,CombatDataXMLConstants.TARGET_CAP_ID_ATTR,0);
      Progression targetCap=progressionsMgr.getProgression(targetCapId);
      ProgressionRatingCurveImpl curve=new ProgressionRatingCurveImpl(hardCap,rating,targetCap);
      ratingsMgr.setCurve(curveId,curve);
    }
  }

  private static void loadWeaponStrikeModifiers(Element root, WeaponStrikeModifiersManager mgr)
  {
    LotroEnum<WeaponType> weaponTypeEnum=LotroEnumsRegistry.getInstance().get(WeaponType.class);
    List<Element> childTags=DOMParsingTools.getChildTagsByName(root,WeaponStrikeModifiersXMLConstants.WEAPON_STRIKE_MODIFIERS_TAG);
    for(Element element : childTags)
    {
      NamedNodeMap attrs=element.getAttributes();
      // Type
      Integer typeCode=DOMParsingTools.getIntegerAttribute(attrs,WeaponStrikeModifiersXMLConstants.WEAPON_TYPE_ATTR,null);
      WeaponType type=weaponTypeEnum.getEntry(typeCode.intValue());
      WeaponStrikeModifiers modifiers=new WeaponStrikeModifiers(type);
      // Critical modifier
      Integer criticalModifier=DOMParsingTools.getIntegerAttribute(attrs,WeaponStrikeModifiersXMLConstants.CRITICAL_MULTIPLIER_ADD_MOD_ATTR,null);
      modifiers.setCriticalMultiplierAddMod(criticalModifier);
      // SuperCritical modifier
      Integer superCriticalModifier=DOMParsingTools.getIntegerAttribute(attrs,WeaponStrikeModifiersXMLConstants.SUPERCRITICAL_MULTIPLIER_ADD_MOD_ATTR,null);
      modifiers.setSuperCriticalMultiplierAddMod(superCriticalModifier);
      // Weapon damage multiplier
      Integer weaponDamageMultiplier=DOMParsingTools.getIntegerAttribute(attrs,WeaponStrikeModifiersXMLConstants.WEAPON_DAMAGE_MULTIPLIER_ATTR,null);
      modifiers.setWeaponDamageMultiplier(weaponDamageMultiplier);
      mgr.registerStrikeModifiers(modifiers);
    }
  }
}
