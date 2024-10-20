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
import delta.games.lotro.character.skills.geometry.Arc;
import delta.games.lotro.character.skills.geometry.Shape;
import delta.games.lotro.character.skills.geometry.SkillGeometry;
import delta.games.lotro.common.effects.ApplicationProbability;
import delta.games.lotro.common.effects.ApplyOverTimeEffect;
import delta.games.lotro.common.effects.AreaEffect;
import delta.games.lotro.common.effects.BaseVitalEffect;
import delta.games.lotro.common.effects.ComboEffect;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectDuration;
import delta.games.lotro.common.effects.EffectFlags;
import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.effects.GenesisEffect;
import delta.games.lotro.common.effects.Hotspot;
import delta.games.lotro.common.effects.InduceCombatStateEffect;
import delta.games.lotro.common.effects.InstantFellowshipEffect;
import delta.games.lotro.common.effects.PropertyModificationEffect;
import delta.games.lotro.common.effects.TieredEffect;
import delta.games.lotro.common.effects.display.EffectDisplay2;
import delta.games.lotro.common.effects.display.EffectDisplayUtils;
import delta.games.lotro.common.enums.CombatState;
import delta.games.lotro.common.enums.DamageQualifier;
import delta.games.lotro.common.enums.GambitIconType;
import delta.games.lotro.common.enums.ImplementUsageType;
import delta.games.lotro.common.enums.ImplementUsageTypes;
import delta.games.lotro.common.enums.PipType;
import delta.games.lotro.common.enums.ResistCategory;
import delta.games.lotro.common.enums.SkillDisplayType;
import delta.games.lotro.common.stats.StatUtils;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.lore.pip.PipDescription;
import delta.games.lotro.lore.pip.PipsManager;
import delta.games.lotro.utils.Proxy;

/**
 * Compute a display of the details of a skill.
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
    float range=geometry.getRange();
    range+=_character.computeAdditiveModifiers(geometry.getMaxRangeMods());

    return range;
  }

  private String getRangeDisplay()
  {
    float range=getRange();
    SkillGeometry geometry=_skillDetails.getGeometry();
    if (geometry!=null)
    {
      int minDigits=0;
      Shape shape=geometry.getShape();
      if (shape instanceof Arc)
      {
        minDigits=1;
      }
      return L10n.getString(range,minDigits,1);
    }
    return "";
  }

  /**
   * Get skill display text.
   * @return Displayable text.
   */
  public String getText()
  {
    StringBuilder sb=new StringBuilder();
    for(String line : getLines())
    {
      sb.append(line).append(EndOfLine.NATIVE_EOL);
    }
    return sb.toString().trim();
  }

  /**
   * Get skill display lines.
   * @return A list of displayable lines.
   */
  public List<String> getLines()
  {
    List<String> table=new ArrayList<String>();
    table.add("Skill-Id: "+_skill.getIdentifier());
    table.add(_skill.getName());

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
      table.add("Radius: "+L10n.getString(radius,0)+"m");
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
    // TODO Display range on right of the first line
    String range=getRangeDisplay();
    if (!range.isEmpty())
    {
      table.add("Range: "+range+"m");
    }
    String description=_skill.getDescription();
    if (!description.isEmpty())
    {
      table.add(description.replace("\n",EndOfLine.NATIVE_EOL));
    }
    List<String> attacks=getAttacksLines();
    table.addAll(attacks);
    // Cost
    List<String> costLines=getCostLines();
    table.addAll(costLines);
    // Gambit
    List<String> gambitLines=getGambitLines(_skillDetails.getGambitData());
    table.addAll(gambitLines);
    // PIP
    List<String> pipLines=getPIPLines(_skillDetails.getPIPData());
    table.addAll(pipLines);
    // Misc
    boolean isToggle=_skillDetails.getFlag(SkillFlags.IS_TOGGLE);
    if ((channelingDuration!=null) && (channelingDuration.floatValue()>0))
    {
      table.add("Channelled Skill");
    }
    else if (isToggle)
    {
      table.add("Toggle Skill");
    }
    Float cooldown=_skillDetails.getCooldown();
    if (cooldown!=null)
    {
      float cooldownF=cooldown.floatValue();
      cooldownF+=_character.computeAdditiveModifiers(_skillDetails.getCooldownMods());
      if (cooldownF>0f)
      {
        // TODO Format duration
        table.add("Cooldown: "+L10n.getString(cooldownF,1)+"s");
      }
    }
    return table;
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

  private List<String> getAttacksLines()
  {
    List<String> ret=new ArrayList<String>();
    SkillAttacks attacks=_skillDetails.getAttacks();
    if (attacks==null)
    {
      return ret;
    }
    int nbAttacks=attacks.getAttacks().size();
    if (nbAttacks>1)
    {
      ret.add(nbAttacks+" Attacks:");
    }
    for(SkillAttack attack : attacks.getAttacks())
    {
      float maxDamage=_attackComputer.getAttackDamage(attack,false);
      float minDamage=_attackComputer.getAttackDamage(attack,true);
      String implementText=getAttackImplementText(attack.getImplementUsageType());
      DamageType damageType=_attackComputer.getDamageType(attack);
      String attackText="";
      int maxDamageInt=Math.round(maxDamage);
      int minDamageInt=Math.round(minDamage);
      if (maxDamageInt!=0)
      {
        if (minDamageInt==maxDamageInt)
        {
          attackText=L10n.getString(maxDamageInt);
        }
        else
        {
          attackText=L10n.getString(minDamageInt)+" - "+L10n.getString(maxDamageInt);
        }
        attackText=attackText+" "+damageType.getLabel();
        if (!implementText.isEmpty())
        {
          attackText=attackText+" ("+implementText+")";
        }
        attackText=attackText+" Damage";
        ret.add(attackText);
      }
      doEffects(attack,ret);
    }
    doSkillEffects(ret);
    return ret;
  }

  private void doEffects(SkillAttack attack, List<String> storage)
  {
    SkillEffectsManager effectsMgr=attack.getEffects();
    if (effectsMgr==null)
    {
      return;
    }
    for(SkillEffectGenerator generator : effectsMgr.getEffects())
    {
      handleEffect(attack.getDamageQualifier(),generator,generator.getEffect(),storage);
    }
  }

  private void doSkillEffects(List<String> storage)
  {
    SkillEffectsManager effectsMgr=_skillDetails.getEffects();
    if (effectsMgr==null)
    {
      return;
    }
    for(SkillEffectGenerator generator : effectsMgr.getEffects())
    {
      SkillEffectType type=generator.getType();
      if (type==SkillEffectType.USER_TOGGLE)
      {
        storage.add("On Use:");
      }
      handleEffect(null,generator,generator.getEffect(),storage);
    }
  }

  private void handleEffect(DamageQualifier damageQualifier, SkillEffectGenerator generator, Effect effect, List<String> storage)
  {
    // Check probability
    boolean applicable=checkEffectApplicationProbability(effect);
    if (!applicable)
    {
      return;
    }
    String description=effect.getDescription();
    if (!description.isEmpty())
    {
      storage.add(description);
    }
    if (effect instanceof BaseVitalEffect)
    {
      BaseVitalEffect vitalEffect=(BaseVitalEffect)effect;
      EffectDisplay2 d2=new EffectDisplay2(_character,_skillDetails);
      String display=d2.getVitalEffectDisplay(generator,vitalEffect,damageQualifier);
      storage.add(display);
    }
    else if (effect instanceof ComboEffect)
    {
      ComboEffect comboEffect=(ComboEffect)effect;
      Proxy<Effect> toExamine=comboEffect.getToExamine();
      if (toExamine!=null)
      {
        handleEffect(damageQualifier,generator,toExamine.getObject(),storage);
      }
    }
    else if (effect instanceof GenesisEffect)
    {
      GenesisEffect genesisEffect=(GenesisEffect)effect;
      Hotspot hotspot=genesisEffect.getHotspot();
      if (hotspot!=null)
      {
        for(EffectGenerator hotspotGenerator : hotspot.getEffects())
        {
          handleEffect(damageQualifier,generator,hotspotGenerator.getEffect(),storage);
        }
      }
    }
    else if (effect instanceof AreaEffect)
    {
      AreaEffect areaEffect=(AreaEffect)effect;
      float range=areaEffect.getRange();
      if (!areaEffect.getEffects().isEmpty())
      {
        String line="Effects applied to enemies within "+L10n.getString(range,0)+" metres:";
        storage.add(line);
        for(EffectGenerator childGenerator : areaEffect.getEffects())
        {
          handleEffect(damageQualifier,generator,childGenerator.getEffect(),storage);
        }
      }
    }
    else if (effect instanceof ApplyOverTimeEffect)
    {
      ApplyOverTimeEffect applyOverTimeEffect=(ApplyOverTimeEffect)effect;
      if (!applyOverTimeEffect.getAppliedEffects().isEmpty())
      {
        float interval=applyOverTimeEffect.getInterval();
        String seconds=(interval>1.0f)?" seconds:":" second:";
        String line="Every "+L10n.getString(interval,1)+seconds;
        storage.add(line);
        for(EffectGenerator childGenerator : applyOverTimeEffect.getAppliedEffects())
        {
          handleEffect(damageQualifier,generator,childGenerator.getEffect(),storage);
        }
      }
    }
    else if (effect instanceof PropertyModificationEffect)
    {
      PropertyModificationEffect propModEffect=(PropertyModificationEffect)effect;
      StatsProvider provider=propModEffect.getStatsProvider();
      if (provider!=null)
      {
        int level=_character.getLevel();
        List<String> lines=StatUtils.getFullStatsForDisplay(provider,level);
        storage.addAll(lines);
      }
      boolean expiresOutOfCombat=effect.getBaseFlag(EffectFlags.DURATION_COMBAT_ONLY);
      if (expiresOutOfCombat)
      {
        // TODO Sometimes "Expires if out of combat for a short amount of time."
        storage.add("Expires if out of combat for 9 seconds.");
      }
      EffectDuration effectDuration=propModEffect.getEffectDuration();
      if (effectDuration!=null)
      {
        Float duration=effectDuration.getDuration();
        if (duration!=null)
        {
          String line="Duration: "+L10n.getString(duration.doubleValue(),1)+"s";
          storage.add(line);
        }
      }
    }
    else if (effect instanceof TieredEffect)
    {
      TieredEffect propModEffect=(TieredEffect)effect;
      EffectGenerator firstTier=propModEffect.getTiers().get(0);
      handleEffect(damageQualifier,generator,firstTier.getEffect(),storage);
    }
    else if (effect instanceof InstantFellowshipEffect)
    {
      InstantFellowshipEffect fellowshipEffect=(InstantFellowshipEffect)effect;
      Float range=fellowshipEffect.getRange();
      boolean toPets=fellowshipEffect.appliesToPets();
      if (toPets)
      {
        String line="Effects applied to your animal companion";
        if (range!=null)
        {
          line=line+" within "+L10n.getString(range.doubleValue(),0)+" metres:";
        }
        storage.add(line);
      }
      for(EffectGenerator childEffect : fellowshipEffect.getEffects())
      {
        handleEffect(damageQualifier,generator,childEffect.getEffect(),storage);
      }
    }
    else if (effect instanceof InduceCombatStateEffect)
    {
      showInduceCombatStateEffect(storage,(InduceCombatStateEffect)effect);
    }
  }

  private void showInduceCombatStateEffect(List<String> storage, InduceCombatStateEffect effect)
  {
    float duration=effect.getDuration();
    duration+=_character.computeAdditiveModifiers(effect.getDurationModifiers());
    CombatState state=effect.getCombatState();
    String stateStr="?";
    if (state!=null)
    {
      stateStr=EffectDisplayUtils.getStateLabel(state);
    }
    String text=L10n.getString(duration,1)+"s "+stateStr;
    storage.add(text);
  }

  private boolean checkEffectApplicationProbability(Effect effect)
  {
    ApplicationProbability probability=effect.getApplicationProbability();
    if (probability==ApplicationProbability.ALWAYS)
    {
      return true;
    }
    float probabilityValue=probability.getProbability();
    Integer modifier=probability.getModProperty();
    probabilityValue+=_character.computeAdditiveModifier(modifier);
    return (probabilityValue>0);
  }

  private List<String> getCostLines()
  {
    List<String> ret=new ArrayList<String>();
    SkillVitalCostComputer costComputer=new SkillVitalCostComputer(_character);
    SkillCostData costData=_skillDetails.getCostData();
    if (costData!=null)
    {
      ret.addAll(getMoraleCost(costComputer,costData));
      ret.addAll(getPowerCost(costComputer,costData));
    }
    return ret;
  }

  private List<String> getMoraleCost(SkillVitalCostComputer costComputer, SkillCostData costData)
  {
    List<String> ret=new ArrayList<String>();
    Float moraleCost=costComputer.getVitalCost(costData.getMoraleCost());
    if (moraleCost!=null)
    {
      int moraleCostInt=Math.round(moraleCost.floatValue());
      if (moraleCostInt>0)
      {
        ret.add("Cost: "+L10n.getString(moraleCostInt)+" Morale");
      }
    }
    return ret;
  }

  private List<String> getPowerCost(SkillVitalCostComputer costComputer, SkillCostData costData)
  {
    List<String> ret=new ArrayList<String>();
    // Power cost
    Float powerCost=costComputer.getVitalCost(costData.getPowerCost());
    if (powerCost!=null)
    {
      int powerCostInt=Math.round(powerCost.floatValue());
      if (powerCostInt>0)
      {
        ret.add("Cost: "+L10n.getString(powerCostInt)+" Power");
      }
    }
    // Interval power cost
    Float togglePowerCost=costComputer.getVitalCost(costData.getPowerCostPerSecond());
    if (togglePowerCost!=null)
    {
      ret.add("Cost: "+L10n.getString(togglePowerCost.floatValue(),0)+" Power Per Second");
    }
    return ret;
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
