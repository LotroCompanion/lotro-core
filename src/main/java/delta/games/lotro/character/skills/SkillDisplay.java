package delta.games.lotro.character.skills;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.common.utils.l10n.L10n;
import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.skills.attack.CharacterDataForSkills;
import delta.games.lotro.character.skills.attack.SkillAttack;
import delta.games.lotro.character.skills.attack.SkillAttackComputer;
import delta.games.lotro.character.skills.attack.SkillAttacks;
import delta.games.lotro.character.skills.effects.EffectProperties;
import delta.games.lotro.character.skills.effects.EffectsFromCharacterDataComputer;
import delta.games.lotro.character.skills.geometry.Arc;
import delta.games.lotro.character.skills.geometry.Shape;
import delta.games.lotro.character.skills.geometry.SkillGeometry;
import delta.games.lotro.character.skills.geometry.SkillPositionalData;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;
import delta.games.lotro.common.Duration;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.display.SkillEffectsDisplay;
import delta.games.lotro.common.enums.DamageQualifier;
import delta.games.lotro.common.enums.DamageQualifiers;
import delta.games.lotro.common.enums.GambitIconType;
import delta.games.lotro.common.enums.ImplementUsageType;
import delta.games.lotro.common.enums.ImplementUsageTypes;
import delta.games.lotro.common.enums.PipType;
import delta.games.lotro.common.enums.ResistCategory;
import delta.games.lotro.common.enums.SkillDisplayType;
import delta.games.lotro.common.properties.ModPropertyList;
import delta.games.lotro.common.stats.StatModifiersComputer;
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
  private static final Logger LOGGER=LoggerFactory.getLogger(SkillDisplay.class);

  private CharacterDataForSkills _character;
  private SkillDescription _skill;
  private SkillDetails _skillDetails;
  private SkillAttackComputer _attackComputer;
  private SkillEffectsDisplay _effectsDisplay;
  private StatModifiersComputer _statModsComputer; 
  private EffectProperties _effectProperties;

  private static final SkillEffectType[] TYPES=new SkillEffectType[]{
      SkillEffectType.TOGGLE,SkillEffectType.USER_TOGGLE,SkillEffectType.USER
  };
  private static final SkillEffectType[] TYPES_CRITICAL=new SkillEffectType[]{SkillEffectType.SELF_CRITICAL};

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
    _attackComputer=new SkillAttackComputer(data);
    _effectsDisplay=new SkillEffectsDisplay(data,skill);
    _statModsComputer=new StatModifiersComputer(data);
    _effectProperties=initEffectProperties();
  }

  private EffectProperties initEffectProperties()
  {
    CharacterData data=_character.getCharacterData();
    EffectsFromCharacterDataComputer c=new EffectsFromCharacterDataComputer();
    EffectProperties effectProperties=c.inspect(data);
    return effectProperties;
  }

  private int getAoEMaxTargets()
  {
    int ret=0;
    Integer maxTargets=_skillDetails.getMaxTargets();
    if (maxTargets!=null)
    {
      ret=maxTargets.intValue();
    }
    float mod=_statModsComputer.computeAdditiveModifiers(_skillDetails.getMaxTargetsMods());
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
    range+=_statModsComputer.computeAdditiveModifiers(geometry.getMaxRangeMods());

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
    LOGGER.debug("Skill display: {}",_skill);
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
    float inductionDuration=SkillDetailsUtils.getInductionDuration(_skillDetails,_statModsComputer);
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
    List<SkillDisplayType> displayTypes=_skillDetails.getDisplayTypes();
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
    SkillAttacks attacks=_skillDetails.getAttacks();
    if (attacks!=null)
    {
      List<String> attackLines=getAttacksLines();
      table.addAll(attackLines);
    }
    // Attack effects (regular and positional)
    if (attacks!=null)
    {
      doRegularAttackEffects(attacks,table);
    }
    // Effects
    doSkillEffects(table,TYPES);
    // Cost
    List<String> costLines=getCostLines();
    table.addAll(costLines);
    // Gambit
    List<String> gambitLines=getGambitLines(_skillDetails.getGambitData());
    table.addAll(gambitLines);
    // PIP
    List<String> pipLines=getPIPLines(_skillDetails.getPIPData());
    table.addAll(pipLines);
    // Effects (critical)
    doSkillEffects(table,TYPES_CRITICAL);
    // Critical attack effects
    if (attacks!=null)
    {
      doCriticalAttackEffects(attacks,table);
    }
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
    // Requirements
    handleRequirements(table);
    // Cooldown
    Float cooldown=_skillDetails.getCooldown();
    if (cooldown!=null)
    {
      float cooldownF=cooldown.floatValue();
      cooldownF+=_statModsComputer.computeAdditiveModifiers(_skillDetails.getCooldownMods());
      if (cooldownF>0f)
      {
        table.add("Cooldown: "+Duration.getShortDurationString(cooldownF));
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

  private float getActionDuration()
  {
    // Calculate Skill Action Duration
    float skillActionDuration=1;
    Float actionDurationContribution=_skillDetails.getActionDurationContribution();
    if (actionDurationContribution!=null)
    {
      skillActionDuration+=actionDurationContribution.floatValue();
    }
    LOGGER.debug("Skill duration (before induction): {}",Float.valueOf(skillActionDuration));
    float inductionDuration=SkillDetailsUtils.getInductionDuration(_skillDetails,_statModsComputer);
    skillActionDuration+=inductionDuration;
    LOGGER.debug("Skill duration: {}",Float.valueOf(skillActionDuration));
    return skillActionDuration;
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
    float skillActionDuration=getActionDuration();
    for(SkillAttack attack : attacks.getAttacks())
    {
      float maxDamage=_attackComputer.getAttackDamage(attack,false,skillActionDuration);
      float minDamage=_attackComputer.getAttackDamage(attack,true,skillActionDuration);
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
    }
    return ret;
  }

  private void doRegularAttackEffects(SkillAttacks attacks, List<String> storage)
  {
    for(SkillAttack attack : attacks.getAttacks())
    {
      doRegularAttackEffects(attack,storage);
    }
  }

  private void doCriticalAttackEffects(SkillAttacks attacks, List<String> storage)
  {
    for(SkillAttack attack : attacks.getAttacks())
    {
      doCriticalAttackEffects(attack,storage);
    }
  }

  private void doRegularAttackEffects(SkillAttack attack, List<String> storage)
  {
    SkillEffectsManager effectsMgr=attack.getEffects();
    if (effectsMgr==null)
    {
      return;
    }
    List<String> effects=getAttackEffects(attack,effectsMgr,SkillEffectType.ATTACK);
    storage.addAll(effects);
    List<String> positionalEffects=getAttackEffects(attack,effectsMgr,SkillEffectType.ATTACK_POSITIONAL);
    if (!positionalEffects.isEmpty())
    {
      String headerLine=getHeaderLine(SkillEffectType.ATTACK_POSITIONAL);
      if (headerLine!=null)
      {
        storage.add(headerLine);
        storage.addAll(positionalEffects);
      }
    }
  }

  private void doCriticalAttackEffects(SkillAttack attack, List<String> storage)
  {
    SkillEffectsManager effectsMgr=attack.getEffects();
    if (effectsMgr==null)
    {
      return;
    }
    List<String> criticalEffects=getAttackEffects(attack,effectsMgr,SkillEffectType.ATTACK_CRITICAL);
    List<String> devastateEffects=getAttackEffects(attack,effectsMgr,SkillEffectType.ATTACK_SUPERCRITICAL);
    if (criticalEffects.equals(devastateEffects))
    {
      if (!criticalEffects.isEmpty())
      {
        storage.add("Apply to target on Critical and Devastating Critical:");
        storage.addAll(criticalEffects);
      }
    }
    else
    {
      if (!criticalEffects.isEmpty())
      {
        storage.add("Apply to target on critical:");
        storage.addAll(criticalEffects);
      }
      if (!devastateEffects.isEmpty())
      {
        storage.add("Apply to target on devastating critical:");
        storage.addAll(devastateEffects);
      }
    }
  }

  private List<String> getAttackEffects(SkillAttack attack, SkillEffectsManager effectsMgr, SkillEffectType type)
  {
    List<String> childStorage=new ArrayList<String>();
    SingleTypeSkillEffectsManager typeEffectsMgr=effectsMgr.getEffects(type);
    if (typeEffectsMgr!=null)
    {
      LOGGER.debug("Display attack effects for type: {}", type);
      for(SkillEffectGenerator generator : getGenerators(typeEffectsMgr))
      {
        handleEffect(attack.getDamageQualifier(),generator,generator.getEffect(),childStorage);
      }
    }
    return childStorage;
  }

  private List<SkillEffectGenerator> getGenerators(SingleTypeSkillEffectsManager typeEffectsMgr)
  {
    List<SkillEffectGenerator> ret=new ArrayList<SkillEffectGenerator>();
    ret.addAll(typeEffectsMgr.getEffects());
    // Additive modifiers
    ModPropertyList modifiers=typeEffectsMgr.getAdditiveModifiers();
    if (modifiers!=null)
    {
      for(Integer propertyID : modifiers.getIDs())
      {
        List<SkillEffectGenerator> effects=_effectProperties.getEffectsForProperty(propertyID.intValue());
        if (effects!=null)
        {
          LOGGER.debug("Got additional effects from property: {} => {}",propertyID,effects);
          ret.addAll(effects);
        }
      }
    }
    // Overrides
    Integer propertyID=typeEffectsMgr.getOverridePropertyID();
    if (propertyID!=null)
    {
      List<SkillEffectGenerator> effects=_effectProperties.getEffectsForProperty(propertyID.intValue());
      if (effects!=null)
      {
        LOGGER.debug("Got override effects from property: {} => {}",propertyID,effects);
        ret.clear();
        ret.addAll(effects);
      }
    }
    return ret;
  }

  private String getHeaderLine(SkillEffectType type)
  {
    if (type==SkillEffectType.ATTACK_POSITIONAL)
    {
      SkillGeometry geometry=_skillDetails.getGeometry();
      if (geometry!=null)
      {
        SkillPositionalData positionalData=geometry.getPositionalData();
        if (positionalData!=null)
        {
          int heading=positionalData.getHeading();
          if (heading==180)
          {
            return "When behind a target:";
          }
        }
      }
    }
    if (type==SkillEffectType.USER_TOGGLE)
    {
      return "On Use:";
    }
    if (type==SkillEffectType.SELF_CRITICAL)
    {
      return "Applied to self on critical:";
    }

    return null;
  }

  private void doSkillEffects(List<String> storage, SkillEffectType[] types)
  {
    SkillEffectsManager effectsMgr=_skillDetails.getEffects();
    if (effectsMgr==null)
    {
      return;
    }
    for(SkillEffectType type : types)
    {
      SingleTypeSkillEffectsManager typeEffectsMgr=effectsMgr.getEffects(type);
      if (typeEffectsMgr!=null)
      {
        LOGGER.debug("Display effects for type: {}", type);
        for(SkillEffectGenerator generator : getGenerators(typeEffectsMgr))
        {
          List<String> childStorage=new ArrayList<String>();
          DamageQualifier damageQualifier=null;
          ImplementUsageType implementUsage=generator.getImplementUsage();
          if (implementUsage==ImplementUsageTypes.TACTICAL_DPS)
          {
            damageQualifier=DamageQualifiers.TACTICAL;
          }
          handleEffect(damageQualifier,generator,generator.getEffect(),childStorage);
          if (!childStorage.isEmpty())
          {
            String header=getHeaderLine(type);
            if (header!=null)
            {
              storage.add(header);
            }
            storage.addAll(childStorage);
          }
        }
      }
    }
  }

  private void handleEffect(DamageQualifier damageQualifier, SkillEffectGenerator generator, Effect effect, List<String> storage)
  {
    _effectsDisplay.handleEffect(damageQualifier,generator,effect,storage);
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
    float mods=_statModsComputer.computeAdditiveModifiers(data.getChangeMods());
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
    float mods=_statModsComputer.computeAdditiveModifiers(data.getRequiredMinValueMods());
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
    float mods=_statModsComputer.computeAdditiveModifiers(data.getRequiredMaxValueMods());
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
    float mods=_statModsComputer.computeAdditiveModifiers(data.getChangePerIntervalMods());
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
    float mods=_statModsComputer.computeAdditiveModifiers(data.getSecondsPerPipChangeMods());
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

  private void handleRequirements(List<String> storage)
  {
    Proxy<TraitDescription> requiredTrait=_skill.getRequiredTrait();
    if (requiredTrait==null)
    {
      return;
    }
    int traitID=requiredTrait.getId();
    TraitDescription trait=TraitsManager.getInstance().getTrait(traitID);
    if (trait!=null)
    {
      String text="Requires: "+trait.getName()+" Trait Equipped";
      storage.add(text);
    }
  }
}
