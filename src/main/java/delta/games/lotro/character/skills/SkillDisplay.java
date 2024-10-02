package delta.games.lotro.character.skills;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import delta.common.utils.l10n.L10n;
import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.skills.attack.CharacterDataForSkills;
import delta.games.lotro.character.skills.attack.SkillAttack;
import delta.games.lotro.character.skills.attack.SkillAttackComputer;
import delta.games.lotro.character.skills.attack.SkillAttacks;
import delta.games.lotro.character.skills.geometry.SkillGeometry;
import delta.games.lotro.common.enums.ImplementUsageType;
import delta.games.lotro.common.enums.ImplementUsageTypes;
import delta.games.lotro.common.enums.ResistCategory;
import delta.games.lotro.common.enums.SkillDisplayType;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.lore.items.DamageTypes;

/**
 * COmpute a display of the details of a skill.
 * @author DAM
 */
public class SkillDisplay
{
  private CharacterDataForSkills _character;
  private SkillDescription _skill;
  private SkillDetails _skillDetails;
  private SkillAttackComputer _attackComputer;

  /**
   * Constructor.
   * @param data Access to character data related to skills.
   * @param details Skill details.
   */
  public SkillDisplay(CharacterDataForSkills data, SkillDescription skill, SkillDetails details)
  {
    _character=data;
    _skill=skill;
    _skillDetails=details;
    _attackComputer=new SkillAttackComputer(data,details);
  }

  private int getAoEMaxTargets()
  {
    int ret=0;
    Integer maxTargets=_skillDetails.getMaxTargets();
    if (maxTargets!=null)
    {
      ret=maxTargets.intValue();
    }
    float mod=_character.computeAdditiveModifiers(_skillDetails.getMaxTargetsMods());
    ret+=Math.round(mod);
    return ret;
  }

  private float getRadius()
  {
    SkillGeometry geometry=_skillDetails.getGeometry();
    if (geometry==null)
    {
      return 0.0f;
    }
    return geometry.getRadius();
  }

  private float getRange()
  {
    SkillGeometry geometry=_skillDetails.getGeometry();
    if (geometry==null)
    {
      return 0.0f;
    }
    return geometry.getRange();
  }

  public String getText()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Skill-Id: ").append(_skill.getIdentifier()).append(EndOfLine.NATIVE_EOL);
    sb.append(_skill.getName()).append(EndOfLine.NATIVE_EOL);

    List<String> table=new ArrayList<String>();
    if (_skillDetails.getFlag(SkillFlags.FAST))
    {
      table.add("Fast");
    }
    if (_skillDetails.getFlag(SkillFlags.IMMEDIATE))
    {
      table.add("Immediate");
    }
    StringBuilder uses=new StringBuilder();
    if (_skillDetails.getFlag(SkillFlags.USES_MAGIC))
    {
      uses.append("Tactical Skill");
    }
    if (_skillDetails.getFlag(SkillFlags.USES_MELEE))
    {
      if (uses.length()>0)
      {
        uses.append(", ");
      }
      uses.append("Melee Skill");
    }
    if (_skillDetails.getFlag(SkillFlags.USES_RANGED))
    {
      if (uses.length()>0)
      {
        uses.append(", ");
      }
      uses.append("Ranged Skill");
    }
    if (uses.length()>0)
    {
      table.add(uses.toString());
    }
    int maxTargets=getAoEMaxTargets();
    if (maxTargets>0)
    {
      table.add("Max Targets: "+maxTargets);
    }
    float radius=getRadius();
    if (radius>0)
    {
      table.add("Radius: "+L10n.getString(radius,1)+"m");
    }
    // Induction
    float inductionDuration=SkillDetailsUtils.getInductionDuration(_skillDetails,_character);
    if (inductionDuration>0)
    {
      table.add("Induction: "+L10n.getString(inductionDuration,1)+"s");
    }
    // Channeling duration
    Float channelingDuration=_skillDetails.getChannelingDuration();
    if ((channelingDuration!=null) && (channelingDuration.floatValue()>0))
    {
      table.add("Channel Duration: "+L10n.getString(channelingDuration.floatValue(),1)+"s");
    }
    // Resist category
    ResistCategory resistCategory=_skillDetails.getResistCategory();
    if (resistCategory!=null)
    {
      table.add("Resistance: "+resistCategory.getLabel());
    }
    // Skill types
    Set<SkillDisplayType> displayTypes=_skillDetails.getDisplayTypes();
    if ((displayTypes!=null) && (!displayTypes.isEmpty()))
    {
      StringBuilder types=new StringBuilder();
      for(SkillDisplayType displayType : displayTypes)
      {
        if (types.length()>0)
        {
          types.append(", ");
        }
        types.append(displayType.getLabel());
      }
      table.add("Skill Type: "+types.toString());
    }
    // Range
    float range=getRange();
    if (range>0)
    {
      table.add("Range: "+L10n.getString(range,1)+"m");
    }
    // TODO Display range on right of the first line
    for(String line : table)
    {
      sb.append(line).append(EndOfLine.NATIVE_EOL);
    }
    String description=_skill.getDescription();
    if (!description.isEmpty())
    {
      sb.append(description).append(EndOfLine.NATIVE_EOL);
    }
    String attacks=getAttacksLines();
    if (!attacks.isEmpty())
    {
      sb.append(attacks).append(EndOfLine.NATIVE_EOL);
    }
    // Cost
    List<String> admin=new ArrayList<String>();
    // - power cost
    SkillVitalCostComputer costComputer=new SkillVitalCostComputer(_character);
    SkillCostData costData=_skillDetails.getCostData();
    if (costData!=null)
    {
      Float powerCost=costComputer.getVitalCost(costData.getPowerCost());
      if (powerCost!=null)
      {
        admin.add("Cost: "+L10n.getString(powerCost.floatValue(),0)+" Power");
      }
      Float togglePowerCost=costComputer.getVitalCost(costData.getPowerCostPerSecond());
      if (togglePowerCost!=null)
      {
        admin.add("Cost: "+L10n.getString(togglePowerCost.floatValue(),0)+" Power Per Second");
      }
    }
    for(String line : admin)
    {
      sb.append(line).append(EndOfLine.NATIVE_EOL);
    }
    return sb.toString().trim();
  }

  private String getAttackImplementText(ImplementUsageType implementUsageType)
  {
    if (implementUsageType==ImplementUsageTypes.PRIMARY)
    {
      return "Main-hand";
    }
    if (implementUsageType==ImplementUsageTypes.SECONDARY)
    {
      return "Off-hand";
    }
    if (implementUsageType==ImplementUsageTypes.RANGED)
    {
      return "Ranged";
    }
    return "";
  }

  private String getAttacksLines()
  {
    SkillAttacks attacks=_skillDetails.getAttacks();
    if (attacks==null)
    {
      return "";
    }
    StringBuilder sb=new StringBuilder();
    int nbAttacks=attacks.getAttacks().size();
    if (nbAttacks>1)
    {
      sb.append(nbAttacks).append(" Attacks:").append(EndOfLine.NATIVE_EOL);
    }
    for(SkillAttack attack : attacks.getAttacks())
    {
      float maxDamage=_attackComputer.getAttackDamage(attack,false);
      float minDamage=_attackComputer.getAttackDamage(attack,true);
      String implementText=getAttackImplementText(attack.getImplementUsageType());
      DamageType damageType=attack.getDamageType();
      if (damageType==null)
      {
        damageType=DamageTypes.COMMON;
      }
      // TODO Effects
      String attackText="";
      int maxDamageInt=Math.round(maxDamage);
      int minDamageInt=Math.round(minDamage);
      if (maxDamageInt!=0)
      {
        if (minDamageInt==maxDamageInt)
        {
          attackText=String.valueOf(maxDamageInt);
        }
        else
        {
          attackText=String.valueOf(minDamageInt)+" - "+String.valueOf(maxDamageInt);
        }
        attackText=attackText+" "+damageType.getLabel();
        if (!implementText.isEmpty())
        {
          attackText=attackText+" ("+implementText+")";
        }
        attackText=attackText+" Damage";
        sb.append(attackText).append(EndOfLine.NATIVE_EOL);
      }
    }
    return sb.toString().trim();
  }

  /*
function GetSkillOutputPlainText(aChar,nSkillId)
  local nInductionDuration = GetSkillInductionDuration(aSkill); -- skill's induction duration
  local nChannelingDuration = GetSkillChannelingDuration(aSkill); -- skill's channel duration
  local nAttackCount = GetSkillAttackCount(aSkill); -- skill's number of attacks
  local nCooldown = GetSkillCooldown(aSkill); -- skill's cooldown duration
  local nAOEMaxTargets = GetSkillAOEMaxTargets(aSkill); -- maximum number of targets for AOE
  local nRange = GetSkillRange(aSkill); -- calculated range from maxrange + AOE radius
  local nRadius = GetSkillRadius(aSkill); -- AOE radius
  local nResistCategory = GetSkillResistCategory(aSkill); -- resistance category
  local nDisplaySkillTypeCount = GetSkillDisplaySkillTypeCount(aSkill); -- number of Skill Types for display
  local nTogglePowerCost = GetSkillTogglePowerCost(aSkill);
  local nTogglePowerPercentCost = GetSkillTogglePowerPercentCost(aSkill);
  local nPowerCost = GetSkillPowerCost(aSkill);
  local nPowerPercentCost = GetSkillPowerPercentCost(aSkill);
  local nGambitsAdded = GetSkillGambitsAdded(aSkill);
  local nGambitsClearedCount = GetSkillGambitsClearedCount(aSkill);
  local nGambitsRequired = GetSkillGambitsRequired(aSkill);
  local bGambitsRequiresActive = GetSkillGambitsRequiresActive(aSkill);
  local nPipType = GetSkillPipType(aSkill);
  local bUsesToggle = GetSkillUsesToggle(aSkill);
  
  local nTargetTextWidth = 38;

  aaa
  

  local nEffectTextCount = #aEffectTexts;
  if nEffectTextCount > 0 then
    sOutput = sOutput.."\n";
    for elem = 1, nEffectTextCount do
      sOutput = sOutput..aEffectTexts[elem];
    end
  end

  local aSkillAdmin = {};

  if bGambitsRequiresActive then
    if nGambitsRequired > 0 then
      table.insert(aSkillAdmin,"Requires: "..GetGambitText(nGambitsRequired));
    else
      table.insert(aSkillAdmin,"Requires: an active Gambit");
    end
  end
  if nGambitsAdded > 0 then
    table.insert(aSkillAdmin,"Adds: "..GetGambitText(nGambitsAdded));
  end
  if nGambitsClearedCount == -1 then
    table.insert(aSkillAdmin,"Clears All Gambits");
  elseif nGambitsClearedCount == 1 then
    table.insert(aSkillAdmin,"Clears 1 Gambit");
  elseif nGambitsClearedCount > 1 then
    table.insert(aSkillAdmin,"Clears "..nGambitsClearedCount.." Gambits");
  end
  if nPipType ~= 0 then
    local sPipName = GetSkillPipName(aSkill);
    local nPipMinValue = GetSkillPipMinValue(aSkill);
    local nPipMaxValue = GetSkillPipMaxValue(aSkill);
    local nPipHomeValue = GetSkillPipHomeValue(aSkill);
    local nPipChange = GetSkillPipChange(aSkill);
    local nPipRequiredMinValue = GetSkillPipRequiredMinValue(aSkill);
    local nPipRequiredMaxValue = GetSkillPipRequiredMaxValue(aSkill);
    local nPipTowardHome = GetSkillPipTowardHome(aSkill);
    local nPipToggleChange = GetSkillPipToggleChange(aSkill);
    local nPipToggleInterval = GetSkillPipToggleInterval(aSkill);
    if nPipType == 4 then
      -- Rune-keeper Attunement
      local sPipNameMin = "(B)";
      local sPipNameHome = "(S)"
      local sPipNameMax = "(H)";
      local nPipRequiredMinOffset = nPipRequiredMinValue-nPipHomeValue; -- positive = required healing attunement
      local nPipRequiredMaxOffset = nPipRequiredMaxValue-nPipHomeValue; -- negative = required battle attunement
      if nPipChange < 0 then
        table.insert(aSkillAdmin,"Attunes: "..csm.stringformatvalue("%'d",-nPipChange+csm.DblCalcDev).." "..sPipNameMin);
      elseif nPipChange > 0 then
        table.insert(aSkillAdmin,"Attunes: "..csm.stringformatvalue("%'d",nPipChange+csm.DblCalcDev).." "..sPipNameMax);
      end
      if nPipTowardHome > 0 then
        table.insert(aSkillAdmin,"Attunes: "..csm.stringformatvalue("%'d",nPipTowardHome+csm.DblCalcDev).." "..sPipNameHome);
      end
      if (nPipRequiredMinValue ~= -1 and nPipRequiredMinOffset == 0) then
        table.insert(aSkillAdmin,"Requires: No "..sPipNameMin);
      elseif (nPipRequiredMinValue ~= -1 and nPipRequiredMinOffset > 0) and nPipRequiredMinOffset ~= -nPipChange then
        -- if a minimum positive offset from home is required, but the change from there
        -- doesn't make pips return exactly to home (is not a "cost")
        table.insert(aSkillAdmin,"Requires: "..csm.stringformatvalue("%'d",nPipRequiredMinOffset+csm.DblCalcDev).." "..sPipNameMax);
      elseif (nPipRequiredMaxValue ~= -1 and nPipRequiredMaxOffset == 0) then
        table.insert(aSkillAdmin,"Requires: No "..sPipNameMax);
      elseif (nPipRequiredMaxValue ~= -1 and nPipRequiredMaxOffset < 0) and nPipRequiredMaxOffset ~= -nPipChange then
        -- if a minimum negative offset from home is required, but the change from there
        -- doesn't make pips return exactly to home (is not a "cost")
        table.insert(aSkillAdmin,"Requires: "..csm.stringformatvalue("%'d",-nPipRequiredMaxOffset+csm.DblCalcDev).." "..sPipNameMin);
      end
    elseif nPipType == 29 then
      -- Corsair Balance
    elseif nPipMinValue == nPipHomeValue then
      -- everything with the minimum value is also the home value
      local nPipRequiredMinOffset = nPipRequiredMinValue-nPipHomeValue;
      local nPipRequiredMaxOffset = nPipRequiredMaxValue-nPipHomeValue;
      if nPipRequiredMinOffset >= 0 and nPipRequiredMaxOffset >= 0 then
        table.insert(aSkillAdmin,"Requires between "..csm.stringformatvalue("%'d",nPipRequiredMinOffset+csm.DblCalcDev).." and "..csm.stringformatvalue("%'d",nPipRequiredMaxOffset+csm.DblCalcDev).." "..sPipName);
      elseif nPipRequiredMinOffset > 0 and nPipRequiredMinOffset ~= -nPipChange then
        -- if a minimum positive offset from home is required, but the change from there
        -- doesn't make pips return exactly to home (is not a "cost")
        table.insert(aSkillAdmin,"Requires at least "..csm.stringformatvalue("%'d",nPipRequiredMinOffset+csm.DblCalcDev).." "..sPipName);
      end
      if nPipChange < 0 then
        if nPipRequiredMinOffset+nPipChange == 0 then
          -- "cost" when all pips are consumed from minimum required starting point (back to home)
          table.insert(aSkillAdmin,"Cost: "..csm.stringformatvalue("%'d",-nPipChange+csm.DblCalcDev).." "..sPipName);
        else
          table.insert(aSkillAdmin,"Removes "..csm.stringformatvalue("%'d",-nPipChange+csm.DblCalcDev).." from "..sPipName);
        end
      elseif nPipChange > 0 then
        table.insert(aSkillAdmin,"Adds "..csm.stringformatvalue("%'d",nPipChange+csm.DblCalcDev).." to "..sPipName);
      end
      if nPipToggleInterval > 0 and nPipToggleChange ~= 0 then
        local sActionText;
        local sPointText;
        local sSecondText;
        if nPipToggleChange > 0 then
          --> Adds 1 Wrath point every 3 seconds
          sActionText = "Adds";
        elseif nPipToggleChange < 0 then
          sActionText = "Removes";
        end
        if nPipToggleChange == -1 or nPipToggleChange == 1 then
          sPointText = "point";
        else
          sPointText = "points";
        end
        if nPipToggleInterval == 1 then
          sSecondText = "second";
        else
          sSecondText = csm.stringformatvalue("%.0.1f",nPipToggleInterval+csm.DblCalcDev).." seconds";
        end
        table.insert(aSkillAdmin,sActionText.." "..csm.stringformatvalue("%'d",nPipToggleChange+csm.DblCalcDev).." "..sPipName.." "..sPointText.." every "..sSecondText);
      end
    end
  end

  for elem = 1, #aSkillAdmin do
    if elem == 1 then
      sOutput = sOutput.."\n\n"..aSkillAdmin[elem];
    else
      sOutput = sOutput.."\n"..aSkillAdmin[elem];
    end
  end
  
  if nChannelingDuration > 0 then
    sOutput = sOutput.."\n".."Channelled Skill";
  elseif bUsesToggle then
    sOutput = sOutput.."\n".."Toggle Skill";
  end
  
  if nCooldown > 0 then
    sOutput = sOutput.."\n\n".."Cooldown: "..DurationFormat(nCooldown);
  end

  return sOutput;
end
 */
  
}
