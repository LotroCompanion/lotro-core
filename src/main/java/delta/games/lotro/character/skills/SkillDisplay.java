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
import delta.games.lotro.common.effects.AreaEffect;
import delta.games.lotro.common.effects.BaseVitalEffect;
import delta.games.lotro.common.effects.ComboEffect;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectDisplay2;
import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.effects.GenesisEffect;
import delta.games.lotro.common.effects.Hotspot;
import delta.games.lotro.common.enums.DamageQualifier;
import delta.games.lotro.common.enums.GambitIconType;
import delta.games.lotro.common.enums.ImplementUsageType;
import delta.games.lotro.common.enums.ImplementUsageTypes;
import delta.games.lotro.common.enums.PipType;
import delta.games.lotro.common.enums.ResistCategory;
import delta.games.lotro.common.enums.SkillDisplayType;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.lore.items.DamageTypes;
import delta.games.lotro.lore.pip.PipDescription;
import delta.games.lotro.lore.pip.PipsManager;
import delta.games.lotro.utils.Proxy;

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
   * @param skill Skill.
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

  /**
   * Get skill display text.
   * @return Displayable text.
   */
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
    // Gambit
    List<String> gambitLines=getGambitLines(_skillDetails.getGambitData());
    admin.addAll(gambitLines);
    // PIP
    List<String> pipLines=getPIPLines(_skillDetails.getPIPData());
    admin.addAll(pipLines);
    // Misc
    boolean isToggle=_skillDetails.getFlag(SkillFlags.IS_TOGGLE);
    if ((channelingDuration!=null) && (channelingDuration.floatValue()>0))
    {
      admin.add("Channelled Skill");
    }
    else if (isToggle)
    {
      admin.add("Toggle Skill");
    }
    Float cooldown=_skillDetails.getCooldown();
    if ((cooldown!=null) && (cooldown.floatValue()>0))
    {
      // TODO Format duration
      admin.add("Cooldown: "+L10n.getString(cooldown.floatValue(),1)+"s");
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
      String effects=doEffects(attack);
      sb.append(effects);
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
          attackText=minDamageInt+" - "+maxDamageInt;
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

  private String doEffects(SkillAttack attack)
  {
    SkillEffectsManager effectsMgr=attack.getEffects();
    if (effectsMgr==null)
    {
      return "";
    }
    StringBuilder sb=new StringBuilder();
    for(SkillEffectGenerator generator : effectsMgr.getEffects())
    {
      handleEffect(attack,generator,generator.getEffect(),sb);
    }
    return sb.toString().trim();
  }

  private void handleEffect(SkillAttack attack, SkillEffectGenerator generator, Effect effect, StringBuilder sb)
  {
    if (effect instanceof BaseVitalEffect)
    {
      BaseVitalEffect vitalEffect=(BaseVitalEffect)effect;
      EffectDisplay2 d2=new EffectDisplay2(_character,_skillDetails);
      DamageQualifier damageQualifier=attack.getDamageQualifier();
      String display=d2.getVitalEffectDisplay(generator,vitalEffect,damageQualifier);
      sb.append(display).append(EndOfLine.NATIVE_EOL);
    }
    else if (effect instanceof ComboEffect)
    {
      ComboEffect comboEffect=(ComboEffect)effect;
      Proxy<Effect> toExamine=comboEffect.getToExamine();
      handleEffect(attack,generator,toExamine.getObject(),sb);
    }
    else if (effect instanceof GenesisEffect)
    {
      GenesisEffect genesisEffect=(GenesisEffect)effect;
      Hotspot hotspot=genesisEffect.getHotspot();
      if (hotspot!=null)
      {
        for(EffectGenerator hotspotGenerator : hotspot.getEffects())
        {
          handleEffect(attack,generator,hotspotGenerator.getEffect(),sb);
        }
      }
    }
    else if (effect instanceof AreaEffect)
    {
      AreaEffect areaEffect=(AreaEffect)effect;
      for(EffectGenerator childGenerator : areaEffect.getEffects())
      {
        handleEffect(attack,generator,childGenerator.getEffect(),sb);
      }
    }
  }

  private String getGambitText(List<GambitIconType> types)
  {
    StringBuilder sb=new StringBuilder();
    for(GambitIconType type : types)
    {
      if (sb.length()>0)
      {
        sb.append('-');
      }
      sb.append(type.getLabel());
    }
    return sb.toString();
  }

  private List<String> getGambitLines(SkillGambitData data)
  {
    List<String> ret=new ArrayList<String>();
    if (data==null)
    {
      return ret;
    }
    // Requirements
    List<GambitIconType> required=data.getRequired();
    if (required!=null)
    {
      if (required.isEmpty())
      {
        ret.add("Requires: an active Gambit");
      }
      else
      {
        String gambitText=getGambitText(required);
        ret.add("Requires: "+gambitText);
      }
    }
    // Actions
    // - additions
    List<GambitIconType> toAdd=data.getToAdd();
    if ((toAdd!=null) && (!toAdd.isEmpty()))
    {
      String gambitText=getGambitText(toAdd);
      ret.add("Adds: "+gambitText);
    }
    // - removals
    if (data.isClearAllGambits())
    {
      ret.add("Clears All Gambits");
    }
    else
    {
      int toRemove=data.getToRemove();
      if (toRemove==1)
      {
        ret.add("Clears 1 Gambit");
      }
      else if (toRemove>1)
      {
        ret.add("Clears "+toRemove+" Gambits");
      }
    }
    return ret;
  }

  private int getPipChange(SkillPipData data)
  {
    Integer change=data.getChange();
    if (change==null)
    {
      return 0;
    }
    int ret=change.intValue();
    float mods=_character.computeAdditiveModifiers(data.getChangeMods());
    return ret+(int)mods;
  }

  private int getPipRequiredMin(SkillPipData data)
  {
    Integer min=data.getRequiredMinValue();
    if (min==null)
    {
      return -1;
    }
    int ret=min.intValue();
    float mods=_character.computeAdditiveModifiers(data.getRequiredMinValueMods());
    return ret+(int)mods;
  }

  private int getPipRequiredMax(SkillPipData data)
  {
    Integer min=data.getRequiredMaxValue();
    if (min==null)
    {
      return -1;
    }
    int ret=min.intValue();
    float mods=_character.computeAdditiveModifiers(data.getRequiredMaxValueMods());
    return ret+(int)mods;
  }

  private int getPipToggleChange(SkillPipData data)
  {
    Integer change=data.getChangePerInterval();
    if (change==null)
    {
      return 0;
    }
    int ret=change.intValue();
    float mods=_character.computeAdditiveModifiers(data.getChangePerIntervalMods());
    return ret+(int)mods;
  }

  private float getPipToggleChangeInterval(SkillPipData data)
  {
    Float interval=data.getSecondsPerPipChange();
    if (interval==null)
    {
      return 0;
    }
    float ret=interval.floatValue();
    float mods=_character.computeAdditiveModifiers(data.getSecondsPerPipChangeMods());
    return ret+mods;
  }

  private List<String> getPIPWithHome(PipDescription pip, SkillPipData data, String left, String center, String right)
  {
    List<String> ret=new ArrayList<String>();
    int home=pip.getHome();
    int change=getPipChange(data);
    int requiredMin=getPipRequiredMin(data);
    int requiredMax=getPipRequiredMax(data);
    Integer towardHomeInt=data.getTowardHome();
    int requiredMinOffset=requiredMin-home;
    int requiredMaxOffset=requiredMax-home;
    // Change
    if (change<0)
    {
      ret.add("Attunes: "+(-change)+" ("+left+")");
    }
    else if (change>0)
    {
      ret.add("Attunes: "+(change)+" ("+right+")");
    }
    if ((towardHomeInt!=null) && (towardHomeInt.intValue()>0))
    {
      ret.add("Attunes: "+towardHomeInt+" ("+center+")");
    }
    // Requirements
    if ((requiredMin!=-1) && (requiredMinOffset==0))
    {
      ret.add("Requires: No ("+left+")");
    }
    else if ((requiredMin!=-1) && (requiredMinOffset>0) && (requiredMinOffset!=-change))
    {
      // if a minimum positive offset from home is required, but the change from there doesn't make pips return exactly to home (is not a "cost")
      ret.add("Requires: "+requiredMinOffset+" ("+right+")");
    }
    else if ((requiredMax!=-1) && (requiredMaxOffset==0))
    {
      ret.add("Requires: No ("+right+")");
    }
    else if ((requiredMax!=-1) && (requiredMaxOffset<0) && (requiredMaxOffset!=-change))
    {
      // if a minimum negative offset from home is required, but the change from there doesn't make pips return exactly to home (is not a "cost")
      ret.add("Requires: "+(-requiredMaxOffset)+" ("+left+")");
    }
    return ret;
  }

  private List<String> getStandardPip(PipDescription pip, SkillPipData data)
  {
    List<String> ret=new ArrayList<String>();
    int home=pip.getHome();
    int change=getPipChange(data);
    int requiredMin=getPipRequiredMin(data);
    int requiredMax=getPipRequiredMax(data);
    int requiredMinOffset=requiredMin-home;
    int requiredMaxOffset=requiredMax-home;
    String pipName=pip.getName();

    if ((requiredMinOffset>=0) && (requiredMaxOffset>=0))
    {
      ret.add("Requires between "+requiredMinOffset+" and "+requiredMaxOffset+" "+pipName);
    }
    else if ((requiredMinOffset>0) && (requiredMinOffset!=(-change)))
    {
      // if a minimum positive offset from home is required, but the change from there doesn't make pips return exactly to home (is not a "cost")
      ret.add("Requires at least "+requiredMinOffset+" "+pipName);
    }
    if (change<0)
    {
      if (requiredMinOffset+change==0)
      {
        // "cost" when all pips are consumed from minimum required starting point (back to home)
        ret.add("Cost: "+(-change)+" "+pipName);
      }
      else
      {
        ret.add("Removes "+(-change)+" from "+pipName);
      }
    }
    else if (change>0)
    {
      ret.add("Adds "+change+" to "+pipName);
    }
    return ret;
  }

  private void handleTogglePip(PipDescription pip, SkillPipData data, List<String> ret)
  {
    int change=getPipToggleChange(data);
    float interval=getPipToggleChangeInterval(data);
    if ((interval>0) && (change!=0))
    {
      String actionText=(change>0)?"Adds":"Removes";
      String pointText="points";
      String secondText;
      if ((change==-1) || (change==1))
      {
        pointText="point";
      }
      if (interval==1.0f)
      {
        secondText="second";
      }
      else
      {
        secondText=L10n.getString(interval,1)+" seconds";
      }
      String pipName=pip.getName();
      String text=actionText+" "+Math.abs(change)+" "+pipName+" "+pointText+" every "+secondText;
      ret.add(text);
    }
  }

  private List<String> getPIPLines(SkillPipData data)
  {
    List<String> ret=new ArrayList<String>();
    if (data==null)
    {
      return ret;
    }
    PipType type=data.getType();
    PipDescription pip=PipsManager.getInstance().get(type.getCode());
    if (pip==null)
    {
      return ret;
    }
    int min=pip.getMin();
    int home=pip.getHome();

    if (type.getCode()==4)
    {
      // RK attunement: positive: healing, negative: battle, center: steady
      ret=getPIPWithHome(pip,data,"battle","steady","healing");
    }
    else if (type.getCode()==29)
    {
      // Mariner attunement: positive: foreward, negative: aftward, center: balance
      ret=getPIPWithHome(pip,data,"aftward","balance","foreward");
    }
    else if (min==home)
    {
      // Everything with the minimum value is also the home value
      ret=getStandardPip(pip,data);
      handleTogglePip(pip,data,ret);
    }
    return ret;
  }
}
