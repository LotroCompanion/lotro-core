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
import delta.games.lotro.common.effects.ComboEffect;
import delta.games.lotro.common.effects.DispelByResistEffect;
import delta.games.lotro.common.effects.Effect2;
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
import delta.games.lotro.common.effects.VitalChangeDescription;
import delta.games.lotro.common.effects.VitalOverTimeEffect;
import delta.games.lotro.common.enums.CombatState;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumEntry;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.ResistCategory;
import delta.games.lotro.common.enums.SkillType;
import delta.games.lotro.common.geo.Position;
import delta.games.lotro.common.geo.io.xml.PositionXMLConstants;
import delta.games.lotro.common.geo.io.xml.PositionXMLParser;
import delta.games.lotro.common.math.LinearFunction;
import delta.games.lotro.common.progression.ProgressionsManager;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.StatsRegistry;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLParser;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.utils.Proxy;
import delta.games.lotro.utils.i18n.I18nRuntimeUtils;
import delta.games.lotro.utils.maths.Progression;

/**
 * Parser for effect descriptions stored in XML.
 * @author DAM
 */
public class EffectXMLParser2
{
  private SingleLocaleLabelsManager _labelsMgr;
  private List<EffectGenerator> _toUpdate;
  private List<EffectAndProbability> _toUpdate2;
  private List<Proxy<Effect2>> _toUpdate3;
  private Map<Integer,Effect2> _loadedEffects;

  /**
   * Constructor.
   * @param labelsMgr Labels manager.
   */
  public EffectXMLParser2(SingleLocaleLabelsManager labelsMgr)
  {
    _labelsMgr=labelsMgr;
    _toUpdate=new ArrayList<EffectGenerator>();
    _toUpdate2=new ArrayList<EffectAndProbability>();
    _loadedEffects=new HashMap<Integer,Effect2>();
    _toUpdate3=new ArrayList<Proxy<Effect2>>();
  }

  /**
   * Parse an effects XML file.
   * @param source Source file.
   * @return List of parsed effects.
   */
  public List<Effect2> parseEffectsFile(File source)
  {
    List<Effect2> effects=new ArrayList<Effect2>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> effectTags=DOMParsingTools.getChildTags(root);
      for(Element effectTag : effectTags)
      {
        Effect2 effect=parseEffect(effectTag);
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
  private Effect2 parseEffect(Element root)
  {
    String tagName=root.getTagName();
    Effect2 ret=null;
    if (EffectXMLConstants2.DISPEL_BY_RESIST_TAG.equals(tagName))
    {
      ret=parseDispelByResistEffect(root);
    }
    else if (EffectXMLConstants2.GENESIS_TAG.equals(tagName))
    {
      ret=parseGenesisEffect(root);
    }
    else if (EffectXMLConstants2.INDUCE_COMBAT_STATE_TAG.equals(tagName))
    {
      ret=parseInduceCombatStateEffect(root);
    }
    else if (EffectXMLConstants2.FELLOWSHIP_EFFECT_TAG.equals(tagName))
    {
      ret=parseInstantFellowshipEffect(root);
    }
    else if (EffectXMLConstants2.INSTANT_VITAL_EFFECT_TAG.equals(tagName))
    {
      ret=parseInstantVitalEffect(root);
    }
    else if (EffectXMLConstants2.PROC_TAG.equals(tagName))
    {
      ret=parseProcEffect(root);
    }
    else if (EffectXMLConstants2.REACTIVE_VITAL_EFFECT_TAG.equals(tagName))
    {
      ret=parseReactiveVitalEffect(root);
    }
    else if (EffectXMLConstants2.PROPERTY_MOD_EFFECT_TAG.equals(tagName))
    {
      ret=parsePropertyModificationEffect(root);
    }
    else if (EffectXMLConstants2.VITAL_OVER_TIME_EFFECT_TAG.equals(tagName))
    {
      ret=parseVitalOverTimeEffect(root);
    }
    else if (EffectXMLConstants2.RECALL_EFFECT_TAG.equals(tagName))
    {
      ret=parseRecallEffect(root);
    }
    else if (EffectXMLConstants2.COMBO_EFFECT_TAG.equals(tagName))
    {
      ret=parseComboEffect(root);
    }
    else
    {
      ret=new Effect2();
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
    int maxDispelCount=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants2.DISPEL_BY_RESIST_MAX_DISPELCOUNT_ATTR,0);
    ret.setMaxDispelCount(maxDispelCount);
    // Resist categories
    String resistCategories=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants2.DISPEL_BY_RESIST_CATEGORIES_ATTR,null);
    List<ResistCategory> categories=readEnumEntriesList(resistCategories,ResistCategory.class);
    if (categories!=null)
    {
      for(ResistCategory category : categories)
      {
        ret.addResistCategory(category);
      }
    }
    // Use strength restriction
    boolean useStrengthRestriction=DOMParsingTools.getBooleanAttribute(attrs,EffectXMLConstants2.DISPEL_BY_RESIST_USE_STRENGTH_RESTRICTION_ATTR,false);
    ret.setUseStrengthRestriction(useStrengthRestriction);
    // Strength offset
    Integer strengthOffset=DOMParsingTools.getIntegerAttribute(attrs,EffectXMLConstants2.DISPEL_BY_RESIST_STRENGTH_OFFSET_ATTR,null);
    ret.setStrengthOffset(strengthOffset);
    return ret;
  }

  private GenesisEffect parseGenesisEffect(Element root)
  {
    GenesisEffect ret=new GenesisEffect();
    NamedNodeMap attrs=root.getAttributes();
    float duration=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants2.GENESIS_SUMMON_DURATION_ATTR,-1);
    if (duration>0)
    {
      ret.setSummonDuration(duration);
    }
    boolean permanent=DOMParsingTools.getBooleanAttribute(attrs,EffectXMLConstants2.GENESIS_PERMANENT_ATTR,false);
    if (permanent)
    {
      ret.setPermanent();
    }
    // Hotspot
    Element hotspotTag=DOMParsingTools.getChildTagByName(root,EffectXMLConstants2.HOTSPOT_TAG);
    if (hotspotTag!=null)
    {
      NamedNodeMap hotspotAttrs=hotspotTag.getAttributes();
      // Identifier
      int id=DOMParsingTools.getIntAttribute(hotspotAttrs,EffectXMLConstants2.HOTSPOT_ID_ATTR,0);
      Hotspot hotspot=new Hotspot(id);
      // Name
      String name=DOMParsingTools.getStringAttribute(hotspotAttrs,EffectXMLConstants2.HOTSPOT_NAME_ATTR,null);
      hotspot.setName(name);
      // Generators
      List<Element> generatorTags=DOMParsingTools.getChildTagsByName(hotspotTag,EffectXMLConstants2.EFFECT_GENERATOR_TAG);
      for(Element generatorTag : generatorTags)
      {
        EffectGenerator generator=readEffectGenerator(generatorTag);
        hotspot.addEffect(generator);
      }
      ret.setHotspot(hotspot);
    }
    // Interactable
    Element summonedTag=DOMParsingTools.getChildTagByName(root,EffectXMLConstants2.SUMMONED_TAG);
    if (summonedTag!=null)
    {
      NamedNodeMap summonedAttrs=summonedTag.getAttributes();
      // Identifier
      int id=DOMParsingTools.getIntAttribute(summonedAttrs,EffectXMLConstants2.SUMMONED_ID_ATTR,0);
      // Name
      String name=DOMParsingTools.getStringAttribute(summonedAttrs,EffectXMLConstants2.SUMMONED_NAME_ATTR,null);
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
    float duration=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants2.INDUCE_COMBAT_STATE_DURATION_ATTR,0);
    ret.setDuration(duration);
    // Combat state
    int combatStateCode=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants2.INDUCE_COMBAT_STATE_STATE_ATTR,-1);
    if (combatStateCode>=0)
    {
      CombatState combatState=LotroEnumsRegistry.getInstance().get(CombatState.class).getEntry(combatStateCode);
      ret.setCombatState(combatState);
    }
    // Function
    Element functionTag=DOMParsingTools.getChildTagByName(root,EffectXMLConstants2.FUNCTION_TAG);
    if (functionTag!=null)
    {
      NamedNodeMap functionAttrs=functionTag.getAttributes();
      float minX=DOMParsingTools.getFloatAttribute(functionAttrs,EffectXMLConstants2.FUNCTION_MIN_X_ATTR,0);
      float minY=DOMParsingTools.getFloatAttribute(functionAttrs,EffectXMLConstants2.FUNCTION_MIN_Y_ATTR,0);
      float maxX=DOMParsingTools.getFloatAttribute(functionAttrs,EffectXMLConstants2.FUNCTION_MAX_X_ATTR,0);
      float maxY=DOMParsingTools.getFloatAttribute(functionAttrs,EffectXMLConstants2.FUNCTION_MAX_Y_ATTR,0);
      LinearFunction f=new LinearFunction(minX,maxX,minY,maxY);
      ret.setDurationFunction(f);
    }
    return ret;
  }

  private InstantFellowshipEffect parseInstantFellowshipEffect(Element root)
  {
    InstantFellowshipEffect ret=new InstantFellowshipEffect();
    NamedNodeMap attrs=root.getAttributes();
    // Apply to raid groups
    boolean applyToRaidGroups=DOMParsingTools.getBooleanAttribute(attrs,EffectXMLConstants2.FELLOWSHIP_EFFECT_APPLY_TO_RAID_GROUPS_ATTR,false);
    ret.setAppliesToRaidGroups(applyToRaidGroups);
    // Apply to pets
    boolean applyToPets=DOMParsingTools.getBooleanAttribute(attrs,EffectXMLConstants2.FELLOWSHIP_EFFECT_APPLY_TO_PETS_ATTR,false);
    ret.setAppliesToPets(applyToPets);
    // Apply to target
    boolean applyToTarget=DOMParsingTools.getBooleanAttribute(attrs,EffectXMLConstants2.FELLOWSHIP_EFFECT_APPLY_TARGET_ATTR,false);
    ret.setAppliesToTarget(applyToTarget);
    // Range
    float range=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants2.FELLOWSHIP_EFFECT_RANGE_ATTR,-1);
    if (range>0)
    {
      ret.setRange(range);
    }
    // Generators
    List<Element> generatorTags=DOMParsingTools.getChildTagsByName(root,EffectXMLConstants2.EFFECT_GENERATOR_TAG);
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
    // Stat
    String statKey=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants2.INSTANT_VITAL_EFFECT_STAT_ATTR,"");
    StatDescription stat=StatsRegistry.getInstance().getByKey(statKey);
    ret.setStat(stat);
    // Multiplicative
    boolean multiplicative=DOMParsingTools.getBooleanAttribute(attrs,EffectXMLConstants2.INSTANT_VITAL_EFFECT_MULTIPLICATIVE_ATTR,false);
    ret.setMultiplicative(multiplicative);
    Element vitalChangeTag=DOMParsingTools.getChildTagByName(root,EffectXMLConstants2.VITAL_CHANGE_TAG);
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
    Float min=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants2.VITAL_CHANGE_MIN_ATTR,null);
    if (min!=null)
    {
      ret.setMinValue(min.floatValue());
    }
    // Max
    Float max=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants2.VITAL_CHANGE_MAX_ATTR,null);
    if (max!=null)
    {
      ret.setMaxValue(max.floatValue());
    }
    return ret;
  }

  private void readAbstractVitalChangeAttributes(NamedNodeMap attrs, AbstractVitalChange change)
  {
    // Constant
    Float constant=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants2.VITAL_CHANGE_CONSTANT_ATTR,null);
    if (constant!=null)
    {
      change.setConstant(constant.floatValue());
    }
    // Progression
    int progressionID=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants2.VITAL_CHANGE_PROGRESSION_ID_ATTR,0);
    if (progressionID!=0)
    {
      Progression progression=ProgressionsManager.getInstance().getProgression(progressionID);
      change.setProgression(progression);
    }
    // Variance
    Float variance=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants2.VITAL_CHANGE_VARIANCE_ATTR,null);
    change.setVariance(variance);
  }

  private ProcEffect parseProcEffect(Element root)
  {
    ProcEffect ret=new ProcEffect();
    readPropertyMod(root,ret);
    NamedNodeMap attrs=root.getAttributes();
    // Skill types
    String skillTypesStr=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants2.PROC_SKILL_TYPES_ATTR,null);
    List<SkillType> skillTypes=readEnumEntriesList(skillTypesStr,SkillType.class);
    if (skillTypes!=null)
    {
      ret.setSkillTypes(skillTypes);
    }
    Float procProbability=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants2.PROC_PROBABILITY_ATTR,null);
    ret.setProcProbability(procProbability);
    // Triggered effects
    List<Element> generatorTags=DOMParsingTools.getChildTagsByName(root,EffectXMLConstants2.EFFECT_GENERATOR_TAG);
    for(Element generatorTag : generatorTags)
    {
      EffectGenerator generator=readEffectGenerator(generatorTag);
      ret.addProcedEffect(generator);
    }
    // Cooldown
    Float cooldown=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants2.PROC_COOLDOWN_ATTR,null);
    ret.setCooldown(cooldown);
    return ret;
  }

  private ReactiveVitalEffect parseReactiveVitalEffect(Element root)
  {
    ReactiveVitalEffect ret=new ReactiveVitalEffect();
    readPropertyMod(root,ret);
    NamedNodeMap attrs=root.getAttributes();
    // Incoming damage types
    String damageTypesStr=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants2.REACTIVE_VITAL_DAMAGE_TYPES_ATTR,null);
    List<DamageType> damageTypes=readEnumEntriesList(damageTypesStr,DamageType.class);
    if (damageTypes!=null)
    {
      for(DamageType damageType : damageTypes)
      {
        ret.addDamageType(damageType);
      }
    }
    // Damage type override
    int damageTypeCode=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants2.REACTIVE_VITAL_DAMAGE_TYPE_OVERRIDE_ATTR,-1);
    if (damageTypeCode>0)
    {
      DamageType attackerDamageTypeOverride=LotroEnumsRegistry.getInstance().get(DamageType.class).getEntry(damageTypeCode);
      ret.setAttackerDamageTypeOverride(attackerDamageTypeOverride);
    }
    // Attacker reactive change
    Element attackerTag=DOMParsingTools.getChildTagByName(root,EffectXMLConstants2.ATTACKER_TAG);
    ReactiveChange attacker=parseReactiveChange(attackerTag);
    ret.setAttackerReactiveChange(attacker);
    // Defender reactive change
    Element defenderTag=DOMParsingTools.getChildTagByName(root,EffectXMLConstants2.DEFENDER_TAG);
    ReactiveChange defender=parseReactiveChange(defenderTag);
    ret.setDefenderReactiveChange(defender);
    // Remove on proc
    boolean removeOnProc=DOMParsingTools.getBooleanAttribute(attrs,EffectXMLConstants2.REACTIVE_VITAL_REMOVE_ON_PROC_ATTR,false);
    ret.setRemoveOnProc(removeOnProc);
    return ret;
  }

  private ReactiveChange parseReactiveChange(Element changeTag)
  {
    if (changeTag==null)
    {
      return null;
    }
    Element reactiveVitalTag=DOMParsingTools.getChildTagByName(changeTag,EffectXMLConstants2.REACTIVE_VITAL_TAG);
    ReactiveVitalChange reactiveVitalChange=parseReactiveVitalChange(reactiveVitalTag);
    Element reactiveEffectTag=DOMParsingTools.getChildTagByName(changeTag,EffectXMLConstants2.REACTIVE_EFFECT_TAG);
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
    float probability=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants2.REACTIVE_VITAL_PROBABILITY_ATTR,0);
    ret.setProbability(probability);
    // Multiplicative
    boolean multiplicative=DOMParsingTools.getBooleanAttribute(attrs,EffectXMLConstants2.REACTIVE_VITAL_MULTIPLICATIVE_ATTR,false);
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
    int effectID=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants2.REACTIVE_EFFECT_ID_ATTR,0);
    Effect2 effect=new Effect2();
    effect.setId(effectID);
    float probability=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants2.REACTIVE_EFFECT_PROBABILITY_ATTR,0);
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
    // Stat
    String statKey=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants2.VITAL_OVER_TIME_EFFECT_STAT_ATTR,"");
    StatDescription stat=StatsRegistry.getInstance().getByKey(statKey);
    ret.setStat(stat);
    // Damage type
    int damageTypeCode=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants2.VITAL_OVER_TIME_EFFECT_DAMAGE_TYPE_ATTR,-1);
    if (damageTypeCode>0)
    {
      DamageType damageType=LotroEnumsRegistry.getInstance().get(DamageType.class).getEntry(damageTypeCode);
      ret.setDamageType(damageType);
    }
    // Initial change
    Element initialChangeTag=DOMParsingTools.getChildTagByName(root,EffectXMLConstants2.INITIAL_CHANGE_TAG);
    VitalChangeDescription initialChange=parseVitalChangeDescription(initialChangeTag);
    ret.setInitialChangeDescription(initialChange);
    // Over-time change
    Element overTimeChangeTag=DOMParsingTools.getChildTagByName(root,EffectXMLConstants2.OVER_TIME_CHANGE_TAG);
    VitalChangeDescription overTimeChange=parseVitalChangeDescription(overTimeChangeTag);
    ret.setOverTimeChangeDescription(overTimeChange);
    return ret;
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

  private ComboEffect parseComboEffect(Element root)
  {
    ComboEffect ret=new ComboEffect();
    List<Element> presentTags=DOMParsingTools.getChildTagsByName(root,EffectXMLConstants2.COMBO_PRESENT_EFFECT_TAG);
    for(Element presentTag : presentTags)
    {
      Proxy<Effect2> proxy=parseEffectProxy(presentTag);
      ret.addPresentEffect(proxy);
    }
    ret.setToAddIfNotPresent(parseEffectProxy(root,EffectXMLConstants2.COMBO_TO_ADD_IF_NOT_PRESENT_TAG));
    ret.setToAddIfPresent(parseEffectProxy(root,EffectXMLConstants2.COMBO_TO_ADD_IF_PRESENT_TAG));
    ret.setToGiveBackIfNotPresent(parseEffectProxy(root,EffectXMLConstants2.COMBO_TO_GIVE_BACK_IF_NOT_PRESENT_TAG));
    ret.setToGiveBackIfPresent(parseEffectProxy(root,EffectXMLConstants2.COMBO_TO_GIVE_BACK_IF_PRESENT_TAG));
    ret.setToExamine(parseEffectProxy(root,EffectXMLConstants2.COMBO_TO_EXAMINE_TAG));
    return ret;
  }

  private Proxy<Effect2> parseEffectProxy(Element root, String tagName)
  {
    Element tag=DOMParsingTools.getChildTagByName(root,tagName);
    if (tag!=null)
    {
      return parseEffectProxy(tag);
    }
    return null;
  }

  private Proxy<Effect2> parseEffectProxy(Element tag)
  {
    Proxy<Effect2> proxy=null;
    if (tag!=null)
    {
      proxy=new Proxy<Effect2>();
      NamedNodeMap attrs=tag.getAttributes();
      int id=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants2.EFFECT_ID_ATTR,0);
      proxy.setId(id);
      _toUpdate3.add(proxy);
    }
    return proxy;
  }

  private void readSharedAttributes(Element root, Effect2 effect)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants2.EFFECT_ID_ATTR,0);
    effect.setId(id);
    // Name
    String name=_labelsMgr.getLabel(String.valueOf(id));
    effect.setName(name);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants2.EFFECT_DESCRIPTION_ATTR,"");
    description=I18nRuntimeUtils.getLabel(_labelsMgr,description);
    effect.setDescription(description);
    // Description override
    String descriptionOverride=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants2.EFFECT_DESCRIPTION_OVERRIDE_ATTR,"");
    descriptionOverride=I18nRuntimeUtils.getLabel(_labelsMgr,descriptionOverride);
    effect.setDescriptionOverride(descriptionOverride);
    // Applied description
    String appliedDescription=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants2.EFFECT_APPLIED_DESCRIPTION_ATTR,"");
    appliedDescription=I18nRuntimeUtils.getLabel(_labelsMgr,appliedDescription);
    effect.setAppliedDescription(appliedDescription);
    // Icon ID
    int iconId=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants2.EFFECT_ICON_ID_ATTR,-1);
    effect.setIconId((iconId!=-1)?Integer.valueOf(iconId):null);

    // Effect Duration
    {
      // - duration
      String durationStr=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants2.EFFECT_DURATION_ATTR,null);
      Float duration=null;
      if (durationStr!=null)
      {
        duration=NumericTools.parseFloat(durationStr);
      }
      // - pulse count
      int pulseCount=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants2.EFFECT_PULSE_COUNT_ATTR,0);
      // - expires in real time
      boolean expiresInRealTime=DOMParsingTools.getBooleanAttribute(attrs,EffectXMLConstants2.EFFECT_EXPIRES_IN_REAL_TIME_ATTR,false);
      if ((duration!=null) || (pulseCount>0) || (expiresInRealTime))
      {
        EffectDuration effectDuration=new EffectDuration();
        effectDuration.setDuration(duration);
        effectDuration.setPulseCount(pulseCount);
        effectDuration.setExpiresInRealTime(expiresInRealTime);
        effect.setEffectDuration(effectDuration);
      }
    }

    // Probability
    {
      // - probability
      String probabilityStr=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants2.EFFECT_PROBABILITY_ATTR,null);
      float probability=NumericTools.parseFloat(probabilityStr,1.0f);
      // - variance
      String varianceStr=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants2.EFFECT_PROBABILITY_VARIANCE_ATTR,null);
      float variance=NumericTools.parseFloat(varianceStr,0.0f);
      // - mod property
      int modPropertyID=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants2.EFFECT_PROBABILITY_MOD_PROPERTY_ATTR,0);
      ApplicationProbability applicationProbability=ApplicationProbability.from(probability,variance,modPropertyID);
      effect.setApplicationProbability(applicationProbability);
    }
  }

  private EffectGenerator readEffectGenerator(Element generatorTag)
  {
    NamedNodeMap attrs=generatorTag.getAttributes();
    int effectId=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants2.EFFECT_GENERATOR_ID_ATTR,0);
    float spellcraftValue=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants2.EFFECT_GENERATOR_SPELLCRAFT_ATTR,-1);
    Float spellcraft=null;
    if (spellcraftValue>0)
    {
      spellcraft=Float.valueOf(spellcraftValue);
    }
    Effect2 effect=new Effect2();
    effect.setId(effectId);
    EffectGenerator ret=new EffectGenerator(effect,spellcraft);
    _toUpdate.add(ret);
    return ret;
  }

  private <T extends LotroEnumEntry> List<T> readEnumEntriesList(String value, Class<T> enumEntryClass)
  {
    if ((value==null) || (value.length()==0))
    {
      return null;
    }
    List<T> ret=new ArrayList<T>();
    LotroEnum<T> lotroEnum=LotroEnumsRegistry.getInstance().get(enumEntryClass);
    String[] codeStrs=value.split(",");
    for(String codeStr : codeStrs)
    {
      int code=NumericTools.parseInt(codeStr,0);
      T entry=lotroEnum.getEntry(code);
      if (entry!=null)
      {
        ret.add(entry);
      }
      else
      {
        System.out.println("null entry: code="+code+" for class: "+enumEntryClass);
      }
    }
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
      Effect2 effect=_loadedEffects.get(Integer.valueOf(effectID));
      effectGenerator.setEffect(effect);
    }
    for(EffectAndProbability effectProb : _toUpdate2)
    {
      int effectID=effectProb.getEffect().getIdentifier();
      Effect2 effect=_loadedEffects.get(Integer.valueOf(effectID));
      effectProb.setEffect(effect);
    }
    for(Proxy<Effect2> proxy : _toUpdate3)
    {
      int effectID=proxy.getId();
      Effect2 effect=_loadedEffects.get(Integer.valueOf(effectID));
      proxy.setObject(effect);
      proxy.setName(effect.getName());
    }
  }
}
