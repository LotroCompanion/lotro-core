package delta.games.lotro.common.effects.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.NumericTools;
import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.Interactable;
import delta.games.lotro.common.effects.AbstractVitalChange;
import delta.games.lotro.common.effects.ApplicationProbability;
import delta.games.lotro.common.effects.ApplyOverTimeEffect;
import delta.games.lotro.common.effects.AreaEffect;
import delta.games.lotro.common.effects.BaseVitalEffect;
import delta.games.lotro.common.effects.BubbleEffect;
import delta.games.lotro.common.effects.ComboEffect;
import delta.games.lotro.common.effects.CountDownEffect;
import delta.games.lotro.common.effects.DispelByResistEffect;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectAndProbability;
import delta.games.lotro.common.effects.EffectDuration;
import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.effects.GenesisEffect;
import delta.games.lotro.common.effects.Hotspot;
import delta.games.lotro.common.effects.InduceCombatStateEffect;
import delta.games.lotro.common.effects.InstantFellowshipEffect;
import delta.games.lotro.common.effects.InstantVitalEffect;
import delta.games.lotro.common.effects.ProcEffect;
import delta.games.lotro.common.effects.PropertyModificationEffect;
import delta.games.lotro.common.effects.ReactiveChange;
import delta.games.lotro.common.effects.ReactiveVitalChange;
import delta.games.lotro.common.effects.ReactiveVitalEffect;
import delta.games.lotro.common.effects.RecallEffect;
import delta.games.lotro.common.effects.ReviveEffect;
import delta.games.lotro.common.effects.ReviveVitalData;
import delta.games.lotro.common.effects.TieredEffect;
import delta.games.lotro.common.effects.TravelEffect;
import delta.games.lotro.common.effects.VitalChangeDescription;
import delta.games.lotro.common.effects.VitalOverTimeEffect;
import delta.games.lotro.common.enums.CombatState;
import delta.games.lotro.common.enums.DamageQualifier;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.ResistCategory;
import delta.games.lotro.common.enums.SkillType;
import delta.games.lotro.common.enums.VitalType;
import delta.games.lotro.common.geo.Position;
import delta.games.lotro.common.geo.io.xml.PositionXMLConstants;
import delta.games.lotro.common.geo.io.xml.PositionXMLParser;
import delta.games.lotro.common.math.LinearFunction;
import delta.games.lotro.common.progression.ProgressionsManager;
import delta.games.lotro.common.properties.ModPropertyList;
import delta.games.lotro.common.properties.io.ModPropertyListIO;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.StatsRegistry;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLParser;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.utils.Proxy;
import delta.games.lotro.utils.enums.EnumXMLUtils;
import delta.games.lotro.utils.i18n.I18nRuntimeUtils;
import delta.games.lotro.utils.maths.Progression;

/**
 * Parser for effect descriptions stored in XML.
 * @author DAM
 */
public class EffectXMLParser
{
  private SingleLocaleLabelsManager _labelsMgr;
  private List<EffectGenerator> _toUpdate;
  private List<EffectAndProbability> _toUpdate2;
  private List<Proxy<Effect>> _toUpdate3;
  private Map<Integer,Effect> _loadedEffects;

  /**
   * Constructor.
   * @param labelsMgr Labels manager.
   */
  public EffectXMLParser(SingleLocaleLabelsManager labelsMgr)
  {
    _labelsMgr=labelsMgr;
    _toUpdate=new ArrayList<EffectGenerator>();
    _toUpdate2=new ArrayList<EffectAndProbability>();
    _loadedEffects=new HashMap<Integer,Effect>();
    _toUpdate3=new ArrayList<Proxy<Effect>>();
  }

  /**
   * Parse an effects XML file.
   * @param source Source file.
   * @return List of parsed effects.
   */
  public List<Effect> parseEffectsFile(File source)
  {
    List<Effect> effects=new ArrayList<Effect>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> effectTags=DOMParsingTools.getChildTags(root);
      for(Element effectTag : effectTags)
      {
        Effect effect=parseEffect(effectTag);
        effects.add(effect);
      }
    }
    resolveEffects();
    return effects;
  }

  /**
   * Build an effect from an XML tag.
   * @param root Root XML tag.
   * @return An effect.
   */
  private Effect parseEffect(Element root)
  {
    String tagName=root.getTagName();
    Effect ret=null;
    if (EffectXMLConstants.DISPEL_BY_RESIST_TAG.equals(tagName))
    {
      ret=parseDispelByResistEffect(root);
    }
    else if (EffectXMLConstants.GENESIS_TAG.equals(tagName))
    {
      ret=parseGenesisEffect(root);
    }
    else if (EffectXMLConstants.INDUCE_COMBAT_STATE_TAG.equals(tagName))
    {
      ret=parseInduceCombatStateEffect(root);
    }
    else if (EffectXMLConstants.FELLOWSHIP_EFFECT_TAG.equals(tagName))
    {
      ret=parseInstantFellowshipEffect(root);
    }
    else if (EffectXMLConstants.INSTANT_VITAL_EFFECT_TAG.equals(tagName))
    {
      ret=parseInstantVitalEffect(root);
    }
    else if (EffectXMLConstants.PROC_TAG.equals(tagName))
    {
      ret=parseProcEffect(root);
    }
    else if (EffectXMLConstants.REACTIVE_VITAL_EFFECT_TAG.equals(tagName))
    {
      ret=parseReactiveVitalEffect(root);
    }
    else if (EffectXMLConstants.PROPERTY_MOD_EFFECT_TAG.equals(tagName))
    {
      ret=parsePropertyModificationEffect(root);
    }
    else if (EffectXMLConstants.VITAL_OVER_TIME_EFFECT_TAG.equals(tagName))
    {
      ret=parseVitalOverTimeEffect(root);
    }
    else if (EffectXMLConstants.RECALL_EFFECT_TAG.equals(tagName))
    {
      ret=parseRecallEffect(root);
    }
    else if (EffectXMLConstants.TRAVEL_EFFECT_TAG.equals(tagName))
    {
      ret=parseTravelEffect(root);
    }
    else if (EffectXMLConstants.COMBO_EFFECT_TAG.equals(tagName))
    {
      ret=parseComboEffect(root);
    }
    else if (EffectXMLConstants.TIERED_EFFECT_TAG.equals(tagName))
    {
      ret=parseTieredEffect(root);
    }
    else if (EffectXMLConstants.AREA_EFFECT_TAG.equals(tagName))
    {
      ret=parseAreaEffect(root);
    }
    else if (EffectXMLConstants.COUNTDOWN_EFFECT_TAG.equals(tagName))
    {
      ret=parseCountDownEffect(root);
    }
    else if (EffectXMLConstants.APPLY_OVER_TIME_EFFECT_TAG.equals(tagName))
    {
      ret=parseApplyOverTimeEffect(root);
    }
    else if (EffectXMLConstants.BUBBLE_EFFECT_TAG.equals(tagName))
    {
      ret=parseBubbleEffect(root);
    }
    else if (EffectXMLConstants.REVIVE_EFFECT_TAG.equals(tagName))
    {
      ret=parseReviveEffect(root);
    }
    else
    {
      ret=new Effect();
    }
    readSharedAttributes(root,ret);
    _loadedEffects.put(Integer.valueOf(ret.getIdentifier()),ret);
    return ret;
  }

  private DispelByResistEffect parseDispelByResistEffect(Element root)
  {
    DispelByResistEffect ret=new DispelByResistEffect();
    NamedNodeMap attrs=root.getAttributes();
    // Max dispel count
    int maxDispelCount=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants.DISPEL_BY_RESIST_MAX_DISPELCOUNT_ATTR,0);
    ret.setMaxDispelCount(maxDispelCount);
    // Resist categories
    String resistCategories=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants.DISPEL_BY_RESIST_CATEGORIES_ATTR,null);
    List<ResistCategory> categories=EnumXMLUtils.readEnumEntriesList(resistCategories,ResistCategory.class);
    if (!categories.isEmpty())
    {
      for(ResistCategory category : categories)
      {
        ret.addResistCategory(category);
      }
    }
    // Use strength restriction
    boolean useStrengthRestriction=DOMParsingTools.getBooleanAttribute(attrs,EffectXMLConstants.DISPEL_BY_RESIST_USE_STRENGTH_RESTRICTION_ATTR,false);
    ret.setUseStrengthRestriction(useStrengthRestriction);
    // Strength offset
    Integer strengthOffset=DOMParsingTools.getIntegerAttribute(attrs,EffectXMLConstants.DISPEL_BY_RESIST_STRENGTH_OFFSET_ATTR,null);
    ret.setStrengthOffset(strengthOffset);
    return ret;
  }

  private GenesisEffect parseGenesisEffect(Element root)
  {
    GenesisEffect ret=new GenesisEffect();
    NamedNodeMap attrs=root.getAttributes();
    float duration=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants.GENESIS_SUMMON_DURATION_ATTR,-1);
    if (duration>0)
    {
      ret.setSummonDuration(duration);
    }
    boolean permanent=DOMParsingTools.getBooleanAttribute(attrs,EffectXMLConstants.GENESIS_PERMANENT_ATTR,false);
    if (permanent)
    {
      ret.setPermanent();
    }
    // Hotspot
    Element hotspotTag=DOMParsingTools.getChildTagByName(root,EffectXMLConstants.HOTSPOT_TAG);
    if (hotspotTag!=null)
    {
      NamedNodeMap hotspotAttrs=hotspotTag.getAttributes();
      // Identifier
      int id=DOMParsingTools.getIntAttribute(hotspotAttrs,EffectXMLConstants.HOTSPOT_ID_ATTR,0);
      Hotspot hotspot=new Hotspot(id);
      // Name
      String name=DOMParsingTools.getStringAttribute(hotspotAttrs,EffectXMLConstants.HOTSPOT_NAME_ATTR,null);
      hotspot.setName(name);
      // Generators
      List<Element> generatorTags=DOMParsingTools.getChildTagsByName(hotspotTag,EffectXMLConstants.EFFECT_GENERATOR_TAG);
      for(Element generatorTag : generatorTags)
      {
        EffectGenerator generator=readEffectGenerator(generatorTag);
        hotspot.addEffect(generator);
      }
      ret.setHotspot(hotspot);
    }
    // Interactable
    Element summonedTag=DOMParsingTools.getChildTagByName(root,EffectXMLConstants.SUMMONED_TAG);
    if (summonedTag!=null)
    {
      NamedNodeMap summonedAttrs=summonedTag.getAttributes();
      // Identifier
      int id=DOMParsingTools.getIntAttribute(summonedAttrs,EffectXMLConstants.SUMMONED_ID_ATTR,0);
      // Name
      String name=DOMParsingTools.getStringAttribute(summonedAttrs,EffectXMLConstants.SUMMONED_NAME_ATTR,null);
      Proxy<Interactable> i=new Proxy<Interactable>();
      i.setId(id);
      i.setName(name);
      ret.setInteractable(i);
    }
    return ret;
  }

  private InduceCombatStateEffect parseInduceCombatStateEffect(Element root)
  {
    InduceCombatStateEffect ret=new InduceCombatStateEffect();
    NamedNodeMap attrs=root.getAttributes();
    // Duration
    float duration=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants.INDUCE_COMBAT_STATE_DURATION_ATTR,0);
    ret.setDuration(duration);
    // Combat state
    int combatStateCode=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants.INDUCE_COMBAT_STATE_STATE_ATTR,-1);
    if (combatStateCode>=0)
    {
      CombatState combatState=LotroEnumsRegistry.getInstance().get(CombatState.class).getEntry(combatStateCode);
      ret.setCombatState(combatState);
    }
    // Function
    Element functionTag=DOMParsingTools.getChildTagByName(root,EffectXMLConstants.FUNCTION_TAG);
    if (functionTag!=null)
    {
      NamedNodeMap functionAttrs=functionTag.getAttributes();
      float minX=DOMParsingTools.getFloatAttribute(functionAttrs,EffectXMLConstants.FUNCTION_MIN_X_ATTR,0);
      float minY=DOMParsingTools.getFloatAttribute(functionAttrs,EffectXMLConstants.FUNCTION_MIN_Y_ATTR,0);
      float maxX=DOMParsingTools.getFloatAttribute(functionAttrs,EffectXMLConstants.FUNCTION_MAX_X_ATTR,0);
      float maxY=DOMParsingTools.getFloatAttribute(functionAttrs,EffectXMLConstants.FUNCTION_MAX_Y_ATTR,0);
      LinearFunction f=new LinearFunction(minX,maxX,minY,maxY);
      ret.setDurationFunction(f);
    }
    // Duration modifiers
    String durationModsStr=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants.INDUCE_COMBAT_STATE_DURATION_MODS_ATTR,null);
    ret.setDurationModifiers(ModPropertyListIO.fromPersistedString(durationModsStr));
    // Break on harmful skill
    Float breakOnHarmfullSkill=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants.INDUCE_COMBAT_STATE_BREAK_ON_HARMFULL_SKILL_ATTR,null);
    ret.setBreakOnHarmfullSkill(breakOnHarmfullSkill);
    // Break on vital loss
    // - value
    Float breakOnVitalLoss=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants.INDUCE_COMBAT_STATE_BREAK_ON_VITAL_LOSS_ATTR,null);
    ret.setBreakOnVitalLossProbability(breakOnVitalLoss);
    // - modifiers
    String breakOnVitalLossModsStr=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants.INDUCE_COMBAT_STATE_BREAK_ON_VITAL_LOSS_MODS_ATTR,null);
    ret.setBreakOnVitalLossProbabilityModifiers(ModPropertyListIO.fromPersistedString(breakOnVitalLossModsStr));
    // Grace period
    Float gracePeriod=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants.INDUCE_COMBAT_STATE_GRACE_PERIOD_ATTR,null);
    ret.setGracePeriod(gracePeriod);
    String gracePeriodModsStr=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants.INDUCE_COMBAT_STATE_GRACE_PERIOD_MODS_ATTR,null);
    ret.setGracePeriodModifiers(ModPropertyListIO.fromPersistedString(gracePeriodModsStr));
    return ret;
  }

  private InstantFellowshipEffect parseInstantFellowshipEffect(Element root)
  {
    InstantFellowshipEffect ret=new InstantFellowshipEffect();
    NamedNodeMap attrs=root.getAttributes();
    // Apply to raid groups
    boolean applyToRaidGroups=DOMParsingTools.getBooleanAttribute(attrs,EffectXMLConstants.FELLOWSHIP_EFFECT_APPLY_TO_RAID_GROUPS_ATTR,false);
    ret.setAppliesToRaidGroups(applyToRaidGroups);
    // Apply to pets
    boolean applyToPets=DOMParsingTools.getBooleanAttribute(attrs,EffectXMLConstants.FELLOWSHIP_EFFECT_APPLY_TO_PETS_ATTR,false);
    ret.setAppliesToPets(applyToPets);
    // Apply to target
    boolean applyToTarget=DOMParsingTools.getBooleanAttribute(attrs,EffectXMLConstants.FELLOWSHIP_EFFECT_APPLY_TARGET_ATTR,false);
    ret.setAppliesToTarget(applyToTarget);
    // String override
    String override=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants.FELLOWSHIP_EFFECT_STRING_OVERRIDE_ATTR,null);
    override=I18nRuntimeUtils.getLabel(_labelsMgr,override);
    ret.setFellowshipStringOverride(override);
    // Range
    float range=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants.FELLOWSHIP_EFFECT_RANGE_ATTR,-1);
    if (range>0)
    {
      ret.setRange(range);
    }
    // Generators
    List<Element> generatorTags=DOMParsingTools.getChildTagsByName(root,EffectXMLConstants.EFFECT_GENERATOR_TAG);
    for(Element generatorTag : generatorTags)
    {
      EffectGenerator generator=readEffectGenerator(generatorTag);
      ret.addEffect(generator);
    }
    return ret;
  }

  private InstantVitalEffect parseInstantVitalEffect(Element root)
  {
    InstantVitalEffect ret=new InstantVitalEffect();
    NamedNodeMap attrs=root.getAttributes();
    parseBaseVitalEffect(ret,attrs);
    // Multiplicative
    boolean multiplicative=DOMParsingTools.getBooleanAttribute(attrs,EffectXMLConstants.INSTANT_VITAL_EFFECT_MULTIPLICATIVE_ATTR,false);
    ret.setMultiplicative(multiplicative);
    // Initial Change Multiplier
    Float initialChangeMultiplier=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants.INSTANT_VITAL_EFFECT_MULTIPLIER_ATTR,null);
    ret.setInitialChangeMultiplier(initialChangeMultiplier);
    Element vitalChangeTag=DOMParsingTools.getChildTagByName(root,EffectXMLConstants.VITAL_CHANGE_TAG);
    if (vitalChangeTag!=null)
    {
      VitalChangeDescription change=parseVitalChangeDescription(vitalChangeTag);
      ret.setInstantChangeDescription(change);
    }
    return ret;
  }

  private VitalChangeDescription parseVitalChangeDescription(Element vitalChangeTag)
  {
    if (vitalChangeTag==null)
    {
      return null;
    }
    VitalChangeDescription ret=new VitalChangeDescription();
    NamedNodeMap attrs=vitalChangeTag.getAttributes();
    readAbstractVitalChangeAttributes(attrs,ret);
    // Min
    Float min=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants.VITAL_CHANGE_MIN_ATTR,null);
    if (min!=null)
    {
      ret.setMinValue(min.floatValue());
    }
    // Max
    Float max=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants.VITAL_CHANGE_MAX_ATTR,null);
    if (max!=null)
    {
      ret.setMaxValue(max.floatValue());
    }
    return ret;
  }

  private void readAbstractVitalChangeAttributes(NamedNodeMap attrs, AbstractVitalChange change)
  {
    // Constant
    Float constant=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants.VITAL_CHANGE_CONSTANT_ATTR,null);
    if (constant!=null)
    {
      change.setConstant(constant.floatValue());
    }
    // Progression
    int progressionID=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants.VITAL_CHANGE_PROGRESSION_ID_ATTR,0);
    if (progressionID!=0)
    {
      Progression progression=ProgressionsManager.getInstance().getProgression(progressionID);
      change.setProgression(progression);
    }
    // Variance
    Float variance=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants.VITAL_CHANGE_VARIANCE_ATTR,null);
    change.setVariance(variance);
    // Modifiers
    String modifiersStr=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants.VITAL_CHANGE_MODIFIERS_ATTR,null);
    ModPropertyList modifiers=ModPropertyListIO.fromPersistedString(modifiersStr);
    change.setModifiers(modifiers);
    // VPS multiplier
    Float vpsMultiplier=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants.VITAL_CHANGE_VPS_MULTIPLIER_ATTR,null);
    change.setVPSMultiplier(vpsMultiplier);
  }

  private ProcEffect parseProcEffect(Element root)
  {
    ProcEffect ret=new ProcEffect();
    readPropertyMod(root,ret);
    NamedNodeMap attrs=root.getAttributes();
    // Skill types
    String skillTypesStr=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants.PROC_SKILL_TYPES_ATTR,null);
    List<SkillType> skillTypes=EnumXMLUtils.readEnumEntriesList(skillTypesStr,SkillType.class);
    if (!skillTypes.isEmpty())
    {
      ret.setSkillTypes(skillTypes);
    }
    Float procProbability=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants.PROC_PROBABILITY_ATTR,null);
    ret.setProcProbability(procProbability);
    // Triggered effects
    List<Element> generatorTags=DOMParsingTools.getChildTagsByName(root,EffectXMLConstants.EFFECT_GENERATOR_TAG);
    for(Element generatorTag : generatorTags)
    {
      EffectGenerator generator=readEffectGenerator(generatorTag);
      ret.addProcedEffect(generator);
    }
    // Cooldown
    Float cooldown=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants.PROC_COOLDOWN_ATTR,null);
    ret.setCooldown(cooldown);
    return ret;
  }

  private ReactiveVitalEffect parseReactiveVitalEffect(Element root)
  {
    ReactiveVitalEffect ret=new ReactiveVitalEffect();
    readPropertyMod(root,ret);
    NamedNodeMap attrs=root.getAttributes();
    // Incoming damage types
    String damageTypesStr=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants.REACTIVE_VITAL_DAMAGE_TYPES_ATTR,null);
    List<DamageType> damageTypes=EnumXMLUtils.readEnumEntriesList(damageTypesStr,DamageType.class);
    if (!damageTypes.isEmpty())
    {
      for(DamageType damageType : damageTypes)
      {
        ret.addDamageType(damageType);
      }
    }
    // Damage qualifiers
    String damageQualifiersStr=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants.REACTIVE_VITAL_DAMAGE_QUALIFIERS_ATTR,null);
    List<DamageQualifier> damageQualifiers=EnumXMLUtils.readEnumEntriesList(damageQualifiersStr,DamageQualifier.class);
    if (!damageQualifiers.isEmpty())
    {
      for(DamageQualifier damageQualifier : damageQualifiers)
      {
        ret.addDamageQualifier(damageQualifier);
      }
    }
    // Damage type override
    int damageTypeCode=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants.REACTIVE_VITAL_DAMAGE_TYPE_OVERRIDE_ATTR,-1);
    if (damageTypeCode>0)
    {
      DamageType attackerDamageTypeOverride=LotroEnumsRegistry.getInstance().get(DamageType.class).getEntry(damageTypeCode);
      ret.setAttackerDamageTypeOverride(attackerDamageTypeOverride);
    }
    // Attacker reactive change
    Element attackerTag=DOMParsingTools.getChildTagByName(root,EffectXMLConstants.ATTACKER_TAG);
    ReactiveChange attacker=parseReactiveChange(attackerTag);
    ret.setAttackerReactiveChange(attacker);
    // Defender reactive change
    Element defenderTag=DOMParsingTools.getChildTagByName(root,EffectXMLConstants.DEFENDER_TAG);
    ReactiveChange defender=parseReactiveChange(defenderTag);
    ret.setDefenderReactiveChange(defender);
    // Remove on proc
    boolean removeOnProc=DOMParsingTools.getBooleanAttribute(attrs,EffectXMLConstants.REACTIVE_VITAL_REMOVE_ON_PROC_ATTR,false);
    ret.setRemoveOnProc(removeOnProc);
    return ret;
  }

  private ReactiveChange parseReactiveChange(Element changeTag)
  {
    if (changeTag==null)
    {
      return null;
    }
    Element reactiveVitalTag=DOMParsingTools.getChildTagByName(changeTag,EffectXMLConstants.REACTIVE_VITAL_TAG);
    ReactiveVitalChange reactiveVitalChange=parseReactiveVitalChange(reactiveVitalTag);
    Element reactiveEffectTag=DOMParsingTools.getChildTagByName(changeTag,EffectXMLConstants.REACTIVE_EFFECT_TAG);
    EffectAndProbability effectProb=parseReactiveEffectTag(reactiveEffectTag);
    if ((reactiveVitalChange!=null) || (effectProb!=null))
    {
      return new ReactiveChange(reactiveVitalChange,effectProb);
    }
    return null;
  }

  private ReactiveVitalChange parseReactiveVitalChange(Element reactiveVitalTag)
  {
    if (reactiveVitalTag==null)
    {
      return null;
    }
    ReactiveVitalChange ret=new ReactiveVitalChange();
    NamedNodeMap attrs=reactiveVitalTag.getAttributes();
    readAbstractVitalChangeAttributes(attrs,ret);
    // Probability
    float probability=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants.REACTIVE_VITAL_PROBABILITY_ATTR,0);
    ret.setProbability(probability);
    // Multiplicative
    boolean multiplicative=DOMParsingTools.getBooleanAttribute(attrs,EffectXMLConstants.REACTIVE_VITAL_MULTIPLICATIVE_ATTR,false);
    ret.setMultiplicative(multiplicative);
    return ret;
  }

  private EffectAndProbability parseReactiveEffectTag(Element reactiveEffectTag)
  {
    if (reactiveEffectTag==null)
    {
      return null;
    }
    NamedNodeMap attrs=reactiveEffectTag.getAttributes();
    int effectID=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants.REACTIVE_EFFECT_ID_ATTR,0);
    Effect effect=new Effect();
    effect.setId(effectID);
    float probability=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants.REACTIVE_EFFECT_PROBABILITY_ATTR,0);
    EffectAndProbability ret=new EffectAndProbability(effect,probability);
    _toUpdate2.add(ret);
    return ret;
  }

  private PropertyModificationEffect parsePropertyModificationEffect(Element root)
  {
    PropertyModificationEffect ret=new PropertyModificationEffect();
    readPropertyMod(root,ret);
    return ret;
  }

  private VitalOverTimeEffect parseVitalOverTimeEffect(Element root)
  {
    VitalOverTimeEffect ret=new VitalOverTimeEffect();
    NamedNodeMap attrs=root.getAttributes();
    parseBaseVitalEffect(ret,attrs);
    // Initial change
    Element initialChangeTag=DOMParsingTools.getChildTagByName(root,EffectXMLConstants.INITIAL_CHANGE_TAG);
    VitalChangeDescription initialChange=parseVitalChangeDescription(initialChangeTag);
    ret.setInitialChangeDescription(initialChange);
    // Over-time change
    Element overTimeChangeTag=DOMParsingTools.getChildTagByName(root,EffectXMLConstants.OVER_TIME_CHANGE_TAG);
    VitalChangeDescription overTimeChange=parseVitalChangeDescription(overTimeChangeTag);
    ret.setOverTimeChangeDescription(overTimeChange);
    return ret;
  }

  private void parseBaseVitalEffect(BaseVitalEffect ret, NamedNodeMap attrs)
  {
    // Stat
    String statKey=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants.BASE_VITAL_EFFECT_STAT_ATTR,"");
    StatDescription stat=StatsRegistry.getInstance().getByKey(statKey);
    ret.setStat(stat);
    // Damage type
    int damageTypeCode=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants.BASE_VITAL_EFFECT_DAMAGE_TYPE_ATTR,-1);
    if (damageTypeCode>0)
    {
      DamageType damageType=LotroEnumsRegistry.getInstance().get(DamageType.class).getEntry(damageTypeCode);
      ret.setDamageType(damageType);
    }
  }

  private RecallEffect parseRecallEffect(Element root)
  {
    RecallEffect ret=new RecallEffect();
    // Position
    Element positionTag=DOMParsingTools.getChildTagByName(root,PositionXMLConstants.POSITION);
    Position position=PositionXMLParser.parseSimplePosition(positionTag);
    ret.setPosition(position);
    return ret;
  }

  private TravelEffect parseTravelEffect(Element root)
  {
    TravelEffect ret=new TravelEffect();
    NamedNodeMap attrs=root.getAttributes();
    // Scene ID
    int sceneID=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants.TRAVEL_EFFECT_SCENE_ID,0);
    ret.setSceneID(sceneID);
    // Remove from instance
    boolean removeFromInstance=DOMParsingTools.getBooleanAttribute(attrs,EffectXMLConstants.TRAVEL_EFFECT_REMOVE_FROM_INSTANCE,true);
    ret.setRemoveFromInstance(removeFromInstance);
    // Private encounter ID
    Integer privateEncounterID=DOMParsingTools.getIntegerAttribute(attrs,EffectXMLConstants.TRAVEL_EFFECT_PRIVATE_ENCOUNTER_ID,null);
    ret.setPrivateEncounterID(privateEncounterID);
    // Position
    Element destinationTag=DOMParsingTools.getChildTagByName(root,PositionXMLConstants.POSITION);
    Position destination=PositionXMLParser.parseSimplePosition(destinationTag);
    ret.setDestination(destination);
    return ret;
  }

  private ComboEffect parseComboEffect(Element root)
  {
    ComboEffect ret=new ComboEffect();
    List<Element> presentTags=DOMParsingTools.getChildTagsByName(root,EffectXMLConstants.COMBO_PRESENT_EFFECT_TAG);
    for(Element presentTag : presentTags)
    {
      Proxy<Effect> proxy=parseEffectProxy(presentTag);
      ret.addPresentEffect(proxy);
    }
    ret.setToAddIfNotPresent(parseEffectProxy(root,EffectXMLConstants.COMBO_TO_ADD_IF_NOT_PRESENT_TAG));
    ret.setToAddIfPresent(parseEffectProxy(root,EffectXMLConstants.COMBO_TO_ADD_IF_PRESENT_TAG));
    ret.setToGiveBackIfNotPresent(parseEffectProxy(root,EffectXMLConstants.COMBO_TO_GIVE_BACK_IF_NOT_PRESENT_TAG));
    ret.setToGiveBackIfPresent(parseEffectProxy(root,EffectXMLConstants.COMBO_TO_GIVE_BACK_IF_PRESENT_TAG));
    ret.setToExamine(parseEffectProxy(root,EffectXMLConstants.COMBO_TO_EXAMINE_TAG));
    return ret;
  }

  private TieredEffect parseTieredEffect(Element root)
  {
    TieredEffect ret=new TieredEffect();
    List<Element> tierUpTags=DOMParsingTools.getChildTagsByName(root,EffectXMLConstants.TIERED_TIER_UP_TAG);
    for(Element tierUpTag : tierUpTags)
    {
      EffectGenerator generator=readEffectGenerator(tierUpTag);
      ret.addTierEffect(generator);
    }
    Element finalTierTag=DOMParsingTools.getChildTagByName(root,EffectXMLConstants.TIERED_FINAL_TIER_TAG);
    if (finalTierTag!=null)
    {
      EffectGenerator finalTierEffect=readEffectGenerator(finalTierTag);
      ret.setFinalTier(finalTierEffect);
    }
    return ret;
  }

  private AreaEffect parseAreaEffect(Element root)
  {
    AreaEffect ret=new AreaEffect();
    NamedNodeMap attrs=root.getAttributes();
    // Flags
    int flags=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants.AREA_EFFECT_FLAGS_ATTR,0);
    ret.setFlags(flags);
    // Range
    float range=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants.AREA_EFFECT_RANGE_ATTR,0);
    ret.setRange(range);
    // Detection buffer
    float detectionBuffer=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants.AREA_EFFECT_DETECTION_BUFFER_ATTR,0);
    ret.setDetectionBuffer(detectionBuffer);
    // Max targets
    int maxTargets=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants.AREA_EFFECT_MAX_TARGETS_ATTR,0);
    ret.setMaxTargets(maxTargets);
    // Max targets (modifiers)
    String maxTargetsModsStr=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants.AREA_EFFECT_MAX_TARGETS_MODS_ATTR,null);
    ModPropertyList maxTargetsMods=ModPropertyListIO.fromPersistedString(maxTargetsModsStr);
    ret.setMaxTargetsModifiers(maxTargetsMods);
    // Generators
    List<Element> generatorTags=DOMParsingTools.getChildTagsByName(root,EffectXMLConstants.EFFECT_GENERATOR_TAG);
    for(Element generatorTag : generatorTags)
    {
      EffectGenerator generator=readEffectGenerator(generatorTag);
      ret.addEffect(generator);
    }
    return ret;
  }

  private CountDownEffect parseCountDownEffect(Element root)
  {
    CountDownEffect ret=new CountDownEffect();
    readCountDownEffect(root,ret);
    return ret;
  }

  private void readCountDownEffect(Element root, CountDownEffect ret)
  {
    // Stats
    readPropertyMod(root,ret);
    // 'on expire' effect generators
    List<Element> onExpireTags=DOMParsingTools.getChildTagsByName(root,EffectXMLConstants.ON_EXPIRE_TAG);
    for(Element onExpireTag : onExpireTags)
    {
      EffectGenerator onExpireEffect=readEffectGenerator(onExpireTag);
      ret.addOnExpireEffect(onExpireEffect);
    }
    // 'on removal' effect.
    Element onRemovalTag=DOMParsingTools.getChildTagByName(root,EffectXMLConstants.ON_REMOVAL_TAG);
    if (onRemovalTag!=null)
    {
      EffectGenerator onRemovalEffect=readEffectGenerator(onRemovalTag);
      ret.setOnRemovalEffect(onRemovalEffect);
    }
  }

  private ApplyOverTimeEffect parseApplyOverTimeEffect(Element root)
  {
    ApplyOverTimeEffect ret=new ApplyOverTimeEffect();
    // 'initially applied' effect generators
    List<Element> initiallyAppliedTags=DOMParsingTools.getChildTagsByName(root,EffectXMLConstants.INITIALLY_APPLIED_TAG);
    for(Element initiallyAppliedTag : initiallyAppliedTags)
    {
      EffectGenerator initiallyAppliedEffect=readEffectGenerator(initiallyAppliedTag);
      ret.addInitiallyAppliedEffect(initiallyAppliedEffect);
    }
    // 'applied' effects.
    List<Element> appliedTags=DOMParsingTools.getChildTagsByName(root,EffectXMLConstants.APPLIED_TAG);
    for(Element appliedTag : appliedTags)
    {
      EffectGenerator appliedEffect=readEffectGenerator(appliedTag);
      ret.addAppliedEffect(appliedEffect);
    }
    return ret;
  }

  private BubbleEffect parseBubbleEffect(Element root)
  {
    BubbleEffect ret=new BubbleEffect();
    readCountDownEffect(root,ret);
    NamedNodeMap attrs=root.getAttributes();
    // Vital
    String statKey=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants.BUBBLE_VITAL_ATTR,"");
    StatDescription stat=StatsRegistry.getInstance().getByKey(statKey);
    ret.setVital(stat);
    // Value
    Float value=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants.BUBBLE_VALUE_ATTR,null);
    ret.setValue(value);
    // Percentage
    Float percentage=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants.BUBBLE_PERCENTAGE_ATTR,null);
    ret.setPercentage(percentage);
    // Progression
    Integer progressionID=DOMParsingTools.getIntegerAttribute(attrs,EffectXMLConstants.BUBBLE_PROGRESSION_ATTR,null);
    if (progressionID!=null)
    {
      Progression progression=ProgressionsManager.getInstance().getProgression(progressionID.intValue());
      ret.setProgression(progression);
    }
    // Modifiers
    String modifiersStr=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants.BUBBLE_MODS_ATTR,null);
    ModPropertyList modifiers=ModPropertyListIO.fromPersistedString(modifiersStr);
    ret.setModifiers(modifiers);
    return ret;
  }

  private ReviveEffect parseReviveEffect(Element root)
  {
    ReviveEffect ret=new ReviveEffect();
    // Effects
    List<Element> effectTags=DOMParsingTools.getChildTagsByName(root,EffectXMLConstants.EFFECT_TAG);
    for(Element effectTag : effectTags)
    {
      Proxy<Effect> proxy=parseEffectProxy(effectTag);
      ret.addReviveEffect(proxy);
    }
    // Vital data
    LotroEnum<VitalType> vitalEnum=LotroEnumsRegistry.getInstance().get(VitalType.class);
    List<Element> vitalTags=DOMParsingTools.getChildTagsByName(root,EffectXMLConstants.REVIVE_VITAL_TAG);
    for(Element vitalTag : vitalTags)
    {
      NamedNodeMap attrs=vitalTag.getAttributes();
      int vitalTypeCode=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants.REVIVE_VITAL_ATTR,0);
      VitalType vitalType=vitalEnum.getEntry(vitalTypeCode);
      float percentage=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants.REVIVE_PERCENTAGE_ATTR,0f);
      String modifiersStr=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants.REVIVE_MODIFIERS_ATTR,null);
      ModPropertyList modifiers=ModPropertyListIO.fromPersistedString(modifiersStr);
      ReviveVitalData vital=new ReviveVitalData(vitalType,percentage);
      vital.setModifiers(modifiers);
      ret.addReviveVitalData(vital);
    }
    return ret;
  }

  private Proxy<Effect> parseEffectProxy(Element root, String tagName)
  {
    Element tag=DOMParsingTools.getChildTagByName(root,tagName);
    if (tag!=null)
    {
      return parseEffectProxy(tag);
    }
    return null;
  }

  private Proxy<Effect> parseEffectProxy(Element tag)
  {
    Proxy<Effect> proxy=null;
    if (tag!=null)
    {
      proxy=new Proxy<Effect>();
      NamedNodeMap attrs=tag.getAttributes();
      int id=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants.EFFECT_ID_ATTR,0);
      proxy.setId(id);
      _toUpdate3.add(proxy);
    }
    return proxy;
  }

  private void readSharedAttributes(Element root, Effect effect)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants.EFFECT_ID_ATTR,0);
    effect.setId(id);
    // Name
    String name;
    if (_labelsMgr!=null)
    {
      name=_labelsMgr.getLabel(String.valueOf(id));
    }
    else
    {
      name=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants.EFFECT_NAME_ATTR,"");
    }
    effect.setName(name);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants.EFFECT_DESCRIPTION_ATTR,"");
    description=I18nRuntimeUtils.getLabel(_labelsMgr,description);
    effect.setDescription(description);
    // Description override
    String descriptionOverride=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants.EFFECT_DESCRIPTION_OVERRIDE_ATTR,"");
    descriptionOverride=I18nRuntimeUtils.getLabel(_labelsMgr,descriptionOverride);
    effect.setDescriptionOverride(descriptionOverride);
    // Applied description
    String appliedDescription=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants.EFFECT_APPLIED_DESCRIPTION_ATTR,"");
    appliedDescription=I18nRuntimeUtils.getLabel(_labelsMgr,appliedDescription);
    effect.setAppliedDescription(appliedDescription);
    // Icon ID
    int iconId=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants.EFFECT_ICON_ID_ATTR,-1);
    effect.setIconId((iconId!=-1)?Integer.valueOf(iconId):null);

    // Effect Duration
    {
      // - duration
      String durationStr=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants.EFFECT_DURATION_ATTR,null);
      Float duration=null;
      if (durationStr!=null)
      {
        duration=NumericTools.parseFloat(durationStr);
      }
      // - duration modifiers
      String durationModifiersStr=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants.EFFECT_DURATION_MODIFIERS_ATTR,null);
      ModPropertyList durationModifiers=ModPropertyListIO.fromPersistedString(durationModifiersStr);
      // - pulse count
      int pulseCount=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants.EFFECT_PULSE_COUNT_ATTR,0);
      // - pulse count modifiers
      String pulseCountModifiersStr=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants.EFFECT_PULSE_COUNT_MODIFIERS_ATTR,null);
      ModPropertyList pulseCountModifiers=ModPropertyListIO.fromPersistedString(pulseCountModifiersStr);
      if ((duration!=null) || (durationModifiers!=null) || (pulseCount>0) || (pulseCountModifiers!=null))
      {
        EffectDuration effectDuration=new EffectDuration();
        effectDuration.setDuration(duration);
        effectDuration.setDurationModifiers(durationModifiers);
        effectDuration.setPulseCount(pulseCount);
        effectDuration.setPulseCountModifiers(pulseCountModifiers);
        effect.setEffectDuration(effectDuration);
      }
    }

    // Probability
    {
      // - probability
      float probability=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants.EFFECT_PROBABILITY_ATTR,1.0f);
      // - variance
      String varianceStr=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants.EFFECT_PROBABILITY_VARIANCE_ATTR,null);
      float variance=NumericTools.parseFloat(varianceStr,0.0f);
      // - mod property
      int modPropertyID=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants.EFFECT_PROBABILITY_MOD_PROPERTY_ATTR,0);
      ApplicationProbability applicationProbability=ApplicationProbability.from(probability,variance,modPropertyID);
      effect.setApplicationProbability(applicationProbability);
    }
    // Flags
    int flags=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants.BASE_FLAGS_ATTR,0);
    effect.setBaseFlags(flags);
  }

  private EffectGenerator readEffectGenerator(Element generatorTag)
  {
    NamedNodeMap attrs=generatorTag.getAttributes();
    int effectId=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants.EFFECT_GENERATOR_ID_ATTR,0);
    float spellcraftValue=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants.EFFECT_GENERATOR_SPELLCRAFT_ATTR,-1);
    Float spellcraft=null;
    if (spellcraftValue>0)
    {
      spellcraft=Float.valueOf(spellcraftValue);
    }
    Effect effect=new Effect();
    effect.setId(effectId);
    EffectGenerator ret=new EffectGenerator(effect,spellcraft);
    _toUpdate.add(ret);
    return ret;
  }

  private void readPropertyMod(Element root, PropertyModificationEffect effect)
  {
    StatsProvider statsProvider=StatsProviderXMLParser.parseStatsProvider(root,_labelsMgr);
    effect.setStatsProvider(statsProvider);
  }

  private void resolveEffects()
  {
    for(EffectGenerator effectGenerator : _toUpdate)
    {
      int effectID=effectGenerator.getEffect().getIdentifier();
      Effect effect=_loadedEffects.get(Integer.valueOf(effectID));
      effectGenerator.setEffect(effect);
    }
    for(EffectAndProbability effectProb : _toUpdate2)
    {
      int effectID=effectProb.getEffect().getIdentifier();
      Effect effect=_loadedEffects.get(Integer.valueOf(effectID));
      effectProb.setEffect(effect);
    }
    for(Proxy<Effect> proxy : _toUpdate3)
    {
      int effectID=proxy.getId();
      Effect effect=_loadedEffects.get(Integer.valueOf(effectID));
      proxy.setObject(effect);
      proxy.setName(effect.getName());
    }
  }
}
