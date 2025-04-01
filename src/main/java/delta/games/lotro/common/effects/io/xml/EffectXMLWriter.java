package delta.games.lotro.common.effects.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.Interactable;
import delta.games.lotro.common.effects.AIPetEffect;
import delta.games.lotro.common.effects.AbstractVitalChange;
import delta.games.lotro.common.effects.ApplicationProbability;
import delta.games.lotro.common.effects.ApplyOverTimeEffect;
import delta.games.lotro.common.effects.AreaEffect;
import delta.games.lotro.common.effects.AuraEffect;
import delta.games.lotro.common.effects.BaseVitalEffect;
import delta.games.lotro.common.effects.BubbleEffect;
import delta.games.lotro.common.effects.ComboEffect;
import delta.games.lotro.common.effects.CountDownEffect;
import delta.games.lotro.common.effects.DispelByResistEffect;
import delta.games.lotro.common.effects.DispelEffect;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectAndProbability;
import delta.games.lotro.common.effects.EffectDuration;
import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.effects.FlagEffect;
import delta.games.lotro.common.effects.GenesisEffect;
import delta.games.lotro.common.effects.Hotspot;
import delta.games.lotro.common.effects.InduceCombatStateEffect;
import delta.games.lotro.common.effects.InstantFellowshipEffect;
import delta.games.lotro.common.effects.InstantVitalEffect;
import delta.games.lotro.common.effects.PipEffect;
import delta.games.lotro.common.effects.ProcEffect;
import delta.games.lotro.common.effects.ProcEffectGenerator;
import delta.games.lotro.common.effects.PropertyModificationEffect;
import delta.games.lotro.common.effects.RandomEffect;
import delta.games.lotro.common.effects.RandomEffectGenerator;
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
import delta.games.lotro.common.enums.EffectAuraType;
import delta.games.lotro.common.enums.LotroEnumEntry;
import delta.games.lotro.common.enums.PipAdjustmentType;
import delta.games.lotro.common.enums.PipType;
import delta.games.lotro.common.enums.ResistCategory;
import delta.games.lotro.common.enums.SkillType;
import delta.games.lotro.common.enums.VitalType;
import delta.games.lotro.common.geo.Position;
import delta.games.lotro.common.geo.io.xml.PositionXMLWriter;
import delta.games.lotro.common.math.LinearFunction;
import delta.games.lotro.common.properties.ModPropertyList;
import delta.games.lotro.common.properties.io.ModPropertyListIO;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLWriter;
import delta.games.lotro.lore.agents.AgentDescription;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.utils.Proxy;
import delta.games.lotro.utils.maths.Progression;

/**
 * Writes effects to XML documents.
 * @author DAM
 */
public class EffectXMLWriter
{
  /**
   * Write some effects to a XML file.
   * @param toFile File to write to.
   * @param effects Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File toFile, final List<Effect> effects)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",EffectXMLConstants.EFFECTS_TAG,new AttributesImpl());
        for(Effect effect : effects)
        {
          writeEffect(hd,effect);
        }
        hd.endElement("","",EffectXMLConstants.EFFECTS_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private void writeEffect(TransformerHandler hd, Effect effect) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    String tagName=getTagName(effect);
    writeSharedEffectAttributes(attrs,effect);
    writeSpecificAttributes(attrs,effect);
    hd.startElement("","",tagName,attrs);
    writeChildTags(hd,effect);
    hd.endElement("","",tagName);
  }

  private String getTagName(Effect effect)
  {
    if (effect instanceof DispelByResistEffect) return EffectXMLConstants.DISPEL_BY_RESIST_TAG;
    if (effect instanceof GenesisEffect) return EffectXMLConstants.GENESIS_TAG;
    if (effect instanceof InduceCombatStateEffect) return EffectXMLConstants.INDUCE_COMBAT_STATE_TAG;
    if (effect instanceof InstantFellowshipEffect) return EffectXMLConstants.FELLOWSHIP_EFFECT_TAG;
    if (effect instanceof InstantVitalEffect) return EffectXMLConstants.INSTANT_VITAL_EFFECT_TAG;
    if (effect instanceof ProcEffect) return EffectXMLConstants.PROC_TAG;
    if (effect instanceof ReactiveVitalEffect) return EffectXMLConstants.REACTIVE_VITAL_EFFECT_TAG;
    if (effect instanceof BubbleEffect) return EffectXMLConstants.BUBBLE_EFFECT_TAG;
    if (effect instanceof CountDownEffect) return EffectXMLConstants.COUNTDOWN_EFFECT_TAG;
    if (effect instanceof PropertyModificationEffect) return EffectXMLConstants.PROPERTY_MOD_EFFECT_TAG;
    if (effect instanceof VitalOverTimeEffect) return EffectXMLConstants.VITAL_OVER_TIME_EFFECT_TAG;
    if (effect instanceof RecallEffect) return EffectXMLConstants.RECALL_EFFECT_TAG;
    if (effect instanceof TravelEffect) return EffectXMLConstants.TRAVEL_EFFECT_TAG;
    if (effect instanceof ComboEffect) return EffectXMLConstants.COMBO_EFFECT_TAG;
    if (effect instanceof TieredEffect) return EffectXMLConstants.TIERED_EFFECT_TAG;
    if (effect instanceof AreaEffect) return EffectXMLConstants.AREA_EFFECT_TAG;
    if (effect instanceof ApplyOverTimeEffect) return EffectXMLConstants.APPLY_OVER_TIME_EFFECT_TAG;
    if (effect instanceof ReviveEffect) return EffectXMLConstants.REVIVE_EFFECT_TAG;
    if (effect instanceof PipEffect) return EffectXMLConstants.PIP_EFFECT_TAG;
    if (effect instanceof AuraEffect) return EffectXMLConstants.AURA_EFFECT_TAG;
    if (effect instanceof DispelEffect) return EffectXMLConstants.DISPEL_EFFECT_TAG;
    if (effect instanceof RandomEffect) return EffectXMLConstants.RANDOM_EFFECT_TAG;
    if (effect instanceof FlagEffect) return EffectXMLConstants.FLAG_EFFECT_TAG;
    if (effect instanceof AIPetEffect) return EffectXMLConstants.AI_PET_EFFECT_TAG;
    return EffectXMLConstants.EFFECT_TAG;
  }

  private void writeSharedEffectAttributes(AttributesImpl attrs, Effect effect)
  {
    // Identifier
    int id=effect.getIdentifier();
    attrs.addAttribute("","",EffectXMLConstants.EFFECT_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=effect.getName();
    if (!name.isEmpty())
    {
      attrs.addAttribute("","",EffectXMLConstants.EFFECT_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Description
    String description=effect.getDescription();
    if (!description.isEmpty())
    {
      attrs.addAttribute("","",EffectXMLConstants.EFFECT_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    }
    // Description override
    String descriptionOverride=effect.getDescriptionOverride();
    if (!descriptionOverride.isEmpty())
    {
      attrs.addAttribute("","",EffectXMLConstants.EFFECT_DESCRIPTION_OVERRIDE_ATTR,XmlWriter.CDATA,descriptionOverride);
    }
    // Applied description
    String appliedDescription=effect.getAppliedDescription();
    if (!appliedDescription.isEmpty())
    {
      attrs.addAttribute("","",EffectXMLConstants.EFFECT_APPLIED_DESCRIPTION_ATTR,XmlWriter.CDATA,appliedDescription);
    }
    // Icon
    Integer iconId=effect.getIconId();
    if (iconId!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants.EFFECT_ICON_ID_ATTR,XmlWriter.CDATA,iconId.toString());
    }
    // Effect Duration
    EffectDuration effectDuration=effect.getEffectDuration();
    if (effectDuration!=null)
    {
      // Duration
      // - value
      Float duration=effectDuration.getDuration();
      if (duration!=null)
      {
        attrs.addAttribute("","",EffectXMLConstants.EFFECT_DURATION_ATTR,XmlWriter.CDATA,duration.toString());
      }
      // - modifiers
      ModPropertyList durationMods=effectDuration.getDurationModifiers();
      String durationModsStr=ModPropertyListIO.asPersistentString(durationMods);
      if (!durationModsStr.isEmpty())
      {
        attrs.addAttribute("","",EffectXMLConstants.EFFECT_DURATION_MODIFIERS_ATTR,XmlWriter.CDATA,durationModsStr);
      }
      // Pulse count
      // - value
      int pulseCount=effectDuration.getPulseCount();
      if (pulseCount>0)
      {
        attrs.addAttribute("","",EffectXMLConstants.EFFECT_PULSE_COUNT_ATTR,XmlWriter.CDATA,String.valueOf(pulseCount));
      }
      // - modifiers
      ModPropertyList pulseCountModifiers=effectDuration.getPulseCountModifiers();
      String pulseCountModifiersStr=ModPropertyListIO.asPersistentString(pulseCountModifiers);
      if (!pulseCountModifiersStr.isEmpty())
      {
        attrs.addAttribute("","",EffectXMLConstants.EFFECT_PULSE_COUNT_MODIFIERS_ATTR,XmlWriter.CDATA,pulseCountModifiersStr);
      }
    }
    // Probability
    writeProbability(attrs,effect);
    // Flags
    int flags=effect.getBaseFlags();
    if (flags!=0)
    {
      attrs.addAttribute("","",EffectXMLConstants.BASE_FLAGS_ATTR,XmlWriter.CDATA,String.valueOf(flags));
    }
  }

  private void writeProbability(AttributesImpl attrs, Effect effect)
  {
    ApplicationProbability applicationProbability=effect.getApplicationProbability();
    if (applicationProbability!=ApplicationProbability.ALWAYS)
    {
      float probability=applicationProbability.getProbability();
      if (probability!=1.0f)
      {
        attrs.addAttribute("","",EffectXMLConstants.EFFECT_PROBABILITY_ATTR,XmlWriter.CDATA,String.valueOf(probability));
      }
      float variance=applicationProbability.getProbabilityVariance();
      if (variance>0)
      {
        attrs.addAttribute("","",EffectXMLConstants.EFFECT_PROBABILITY_VARIANCE_ATTR,XmlWriter.CDATA,String.valueOf(variance));
      }
      Integer modProperty=applicationProbability.getModProperty();
      if ((modProperty!=null) && (modProperty.intValue()!=0))
      {
        attrs.addAttribute("","",EffectXMLConstants.EFFECT_PROBABILITY_MOD_PROPERTY_ATTR,XmlWriter.CDATA,modProperty.toString());
      }
    }
  }

  private void writeSpecificAttributes(AttributesImpl attrs, Effect effect)
  {
    if (effect instanceof DispelByResistEffect)
    {
      DispelByResistEffect dispelByResistEffect=(DispelByResistEffect)effect;
      writeDispelByResistAttributes(attrs,dispelByResistEffect);
    }
    else if (effect instanceof GenesisEffect)
    {
      GenesisEffect genesisEffect=(GenesisEffect)effect;
      writeGenesisAttributes(attrs,genesisEffect);
    }
    else if (effect instanceof InduceCombatStateEffect)
    {
      InduceCombatStateEffect induceCombatState=(InduceCombatStateEffect)effect;
      writeInduceCombatStateAttributes(attrs,induceCombatState);
    }
    else if (effect instanceof InstantFellowshipEffect)
    {
      InstantFellowshipEffect instantFellowshipEffect=(InstantFellowshipEffect)effect;
      writeInstantFellowshipEffectAttributes(attrs,instantFellowshipEffect);
    }
    else if (effect instanceof InstantVitalEffect)
    {
      InstantVitalEffect instantVitalEffect=(InstantVitalEffect)effect;
      writeInstantVitalEffectAttributes(attrs,instantVitalEffect);
    }
    else if (effect instanceof ProcEffect)
    {
      ProcEffect procEffect=(ProcEffect)effect;
      writeProcEffectAttributes(attrs,procEffect);
    }
    else if (effect instanceof ReactiveVitalEffect)
    {
      ReactiveVitalEffect reactiveVitalEffect=(ReactiveVitalEffect)effect;
      writeReactiveVitalEffectAttributes(attrs,reactiveVitalEffect);
    }
    else if (effect instanceof BubbleEffect)
    {
      BubbleEffect bubbleEffect=(BubbleEffect)effect;
      writeBubbleEffectAttributes(attrs,bubbleEffect);
    }
    else if (effect instanceof PropertyModificationEffect)
    {
      PropertyModificationEffect propertyModificationEffect=(PropertyModificationEffect)effect;
      writePropertyModificationEffectAttributes(attrs,propertyModificationEffect);
    }
    else if (effect instanceof TravelEffect)
    {
      TravelEffect travelEffect=(TravelEffect)effect;
      writeTravelEffectAttributes(attrs,travelEffect);
    }
    else if (effect instanceof VitalOverTimeEffect)
    {
      VitalOverTimeEffect vitalOverTimeEffect=(VitalOverTimeEffect)effect;
      writeBaseVitalEffectAttributes(attrs,vitalOverTimeEffect);
    }
    else if (effect instanceof TieredEffect)
    {
      TieredEffect tieredEffect=(TieredEffect)effect;
      writeTieredEffectAttributes(attrs,tieredEffect);
    }
    else if (effect instanceof AreaEffect)
    {
      AreaEffect areaEffect=(AreaEffect)effect;
      writeAreaEffectAttributes(attrs,areaEffect);
    }
    else if (effect instanceof PipEffect)
    {
      PipEffect pipEffect=(PipEffect)effect;
      writePipEffectAttributes(attrs,pipEffect);
    }
    else if (effect instanceof AuraEffect)
    {
      AuraEffect auraEffect=(AuraEffect)effect;
      writeAuraEffectAttributes(attrs,auraEffect);
    }
    else if (effect instanceof DispelEffect)
    {
      DispelEffect dispelEffect=(DispelEffect)effect;
      writeDispelEffectAttributes(attrs,dispelEffect);
    }
  }

  private void writeDispelByResistAttributes(AttributesImpl attrs, DispelByResistEffect dispelByResistEffect)
  {
    // Max dispel count
    int maxDispelCount=dispelByResistEffect.getMaxDispelCount();
    attrs.addAttribute("","",EffectXMLConstants.DISPEL_BY_RESIST_MAX_DISPELCOUNT_ATTR,XmlWriter.CDATA,String.valueOf(maxDispelCount));
    // Resist categories
    List<ResistCategory> resistCategories=dispelByResistEffect.getResistCategories();
    String resistCategoriesStr=serializeEnumList(resistCategories);
    if (!resistCategoriesStr.isEmpty())
    {
      attrs.addAttribute("","",EffectXMLConstants.DISPEL_BY_RESIST_CATEGORIES_ATTR,XmlWriter.CDATA,String.valueOf(resistCategoriesStr));
    }
    // Use strength restriction
    boolean useStrengthRestriction=dispelByResistEffect.useStrengthRestriction();
    if (useStrengthRestriction)
    {
      attrs.addAttribute("","",EffectXMLConstants.DISPEL_BY_RESIST_USE_STRENGTH_RESTRICTION_ATTR,XmlWriter.CDATA,String.valueOf(useStrengthRestriction));
      // Strength offset
      Integer offset=dispelByResistEffect.getStrengthOffset();
      if (offset!=null)
      {
        attrs.addAttribute("","",EffectXMLConstants.DISPEL_BY_RESIST_STRENGTH_OFFSET_ATTR,XmlWriter.CDATA,offset.toString());
      }
    }
  }

  private void writeGenesisAttributes(AttributesImpl attrs, GenesisEffect genesisEffect)
  {
    // Summon duration
    float summonDuration=genesisEffect.getSummonDuration();
    if (summonDuration>0)
    {
      attrs.addAttribute("","",EffectXMLConstants.GENESIS_SUMMON_DURATION_ATTR,XmlWriter.CDATA,String.valueOf(summonDuration));
    }
    // Permanent
    boolean permanent=genesisEffect.isPermanent();
    if (permanent)
    {
      attrs.addAttribute("","",EffectXMLConstants.GENESIS_PERMANENT_ATTR,XmlWriter.CDATA,String.valueOf(permanent));
    }
  }

  private void writeInduceCombatStateAttributes(AttributesImpl attrs, InduceCombatStateEffect induceCombatState)
  {
    // Combat state
    CombatState combatState=induceCombatState.getCombatState();
    if (combatState!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants.INDUCE_COMBAT_STATE_STATE_ATTR,XmlWriter.CDATA,String.valueOf(combatState.getCode()));
    }
    // Duration
    float duration=induceCombatState.getDuration();
    if (duration>0)
    {
      attrs.addAttribute("","",EffectXMLConstants.INDUCE_COMBAT_STATE_DURATION_ATTR,XmlWriter.CDATA,String.valueOf(duration));
    }
    // Duration modifiers
    String durationModsStr=ModPropertyListIO.asPersistentString(induceCombatState.getDurationModifiers());
    if (!durationModsStr.isEmpty())
    {
      attrs.addAttribute("","",EffectXMLConstants.INDUCE_COMBAT_STATE_DURATION_MODS_ATTR,XmlWriter.CDATA,durationModsStr);
    }
    // Break on harmfull skill
    Float breakOnHarmfullSkill=induceCombatState.getBreakOnHarmfullSkill();
    if (breakOnHarmfullSkill!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants.INDUCE_COMBAT_STATE_BREAK_ON_HARMFULL_SKILL_ATTR,XmlWriter.CDATA,breakOnHarmfullSkill.toString());
    }
    // Break on vital loss
    // - value
    Float breakOnVitalLoss=induceCombatState.getBreakOnVitalLossProbability();
    if (breakOnVitalLoss!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants.INDUCE_COMBAT_STATE_BREAK_ON_VITAL_LOSS_ATTR,XmlWriter.CDATA,breakOnVitalLoss.toString());
    }
    // - modifiers
    String breakOnVitalLossMods=ModPropertyListIO.asPersistentString(induceCombatState.getBreakOnVitalLossProbabilityModifiers());
    if (!breakOnVitalLossMods.isEmpty())
    {
      attrs.addAttribute("","",EffectXMLConstants.INDUCE_COMBAT_STATE_BREAK_ON_VITAL_LOSS_MODS_ATTR,XmlWriter.CDATA,breakOnVitalLossMods);
    }
    // Grace period
    Float gracePeriod=induceCombatState.getGracePeriod();
    if (gracePeriod!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants.INDUCE_COMBAT_STATE_GRACE_PERIOD_ATTR,XmlWriter.CDATA,gracePeriod.toString());
    }
    String gracePeriodModsStr=ModPropertyListIO.asPersistentString(induceCombatState.getGracePeriodModifiers());
    if (!gracePeriodModsStr.isEmpty())
    {
      attrs.addAttribute("","",EffectXMLConstants.INDUCE_COMBAT_STATE_GRACE_PERIOD_MODS_ATTR,XmlWriter.CDATA,gracePeriodModsStr);
    }
  }

  private void writeInstantFellowshipEffectAttributes(AttributesImpl attrs, InstantFellowshipEffect instantFellowshipEffect)
  {
    // Apply to raid groups
    boolean raidGroups=instantFellowshipEffect.appliesToRaidGroups();
    if (raidGroups)
    {
      attrs.addAttribute("","",EffectXMLConstants.FELLOWSHIP_EFFECT_APPLY_TO_RAID_GROUPS_ATTR,XmlWriter.CDATA,String.valueOf(raidGroups));
    }
    // Apply to pets
    boolean pets=instantFellowshipEffect.appliesToPets();
    if (pets)
    {
      attrs.addAttribute("","",EffectXMLConstants.FELLOWSHIP_EFFECT_APPLY_TO_PETS_ATTR,XmlWriter.CDATA,String.valueOf(pets));
    }
    // Apply to target
    boolean target=instantFellowshipEffect.appliesToTarget();
    if (target)
    {
      attrs.addAttribute("","",EffectXMLConstants.FELLOWSHIP_EFFECT_APPLY_TARGET_ATTR,XmlWriter.CDATA,String.valueOf(target));
    }
    // String override
    String override=instantFellowshipEffect.getFellowshipStringOverride();
    if (override!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants.FELLOWSHIP_EFFECT_STRING_OVERRIDE_ATTR,XmlWriter.CDATA,override);
    }
    // Range
    Float range=instantFellowshipEffect.getRange();
    if (range!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants.FELLOWSHIP_EFFECT_RANGE_ATTR,XmlWriter.CDATA,String.valueOf(range.floatValue()));
    }
  }

  private void writeInstantVitalEffectAttributes(AttributesImpl attrs, InstantVitalEffect instantVitalEffect)
  {
    writeBaseVitalEffectAttributes(attrs,instantVitalEffect);
    // Multiplicative
    boolean multiplicative=instantVitalEffect.isMultiplicative();
    if (multiplicative)
    {
      attrs.addAttribute("","",EffectXMLConstants.INSTANT_VITAL_EFFECT_MULTIPLICATIVE_ATTR,XmlWriter.CDATA,String.valueOf(multiplicative));
    }
    // Initial Change Multiplier
    Float initialChangeMultipplier=instantVitalEffect.getInitialChangeMultiplier();
    if (initialChangeMultipplier!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants.INSTANT_VITAL_EFFECT_MULTIPLIER_ATTR,XmlWriter.CDATA,initialChangeMultipplier.toString());
    }
  }

  private void writeProcEffectAttributes(AttributesImpl attrs, ProcEffect procEffect)
  {
    // Skill types
    List<SkillType> skillTypes=procEffect.getSkillTypes();
    String skillTypesStr=serializeEnumList(skillTypes);
    attrs.addAttribute("","",EffectXMLConstants.PROC_SKILL_TYPES_ATTR,XmlWriter.CDATA,skillTypesStr);
    // Probability
    Float probability=procEffect.getProcProbability();
    if (probability!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants.PROC_PROBABILITY_ATTR,XmlWriter.CDATA,probability.toString());
    }
    // Cooldown
    Float cooldown=procEffect.getCooldown();
    if (cooldown!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants.PROC_COOLDOWN_ATTR,XmlWriter.CDATA,cooldown.toString());
    }
  }

  private void writeReactiveVitalEffectAttributes(AttributesImpl attrs, ReactiveVitalEffect reactiveVitalEffect)
  {
    // Incoming damage types
    List<DamageType> damageTypes=reactiveVitalEffect.getDamageTypes();
    String damageTypesStr=serializeEnumList(damageTypes);
    attrs.addAttribute("","",EffectXMLConstants.REACTIVE_VITAL_DAMAGE_TYPES_ATTR,XmlWriter.CDATA,damageTypesStr);
    // Damage qualifiers
    List<DamageQualifier> damageQualifiers=reactiveVitalEffect.getDamageQualifiers();
    if (!damageQualifiers.isEmpty())
    {
      String damageQualifiersStr=serializeEnumList(damageQualifiers);
      attrs.addAttribute("","",EffectXMLConstants.REACTIVE_VITAL_DAMAGE_QUALIFIERS_ATTR,XmlWriter.CDATA,damageQualifiersStr);
    }
    // Damage type override
    DamageType overrideDamageType=reactiveVitalEffect.getAttackerDamageTypeOverride();
    if (overrideDamageType!=null)
    {
      String damageTypeOverrideStr=String.valueOf(overrideDamageType.getCode());
      attrs.addAttribute("","",EffectXMLConstants.REACTIVE_VITAL_DAMAGE_TYPE_OVERRIDE_ATTR,XmlWriter.CDATA,damageTypeOverrideStr);
    }
    // Remove on proc
    boolean removeOnProc=reactiveVitalEffect.isRemoveOnProc();
    if (removeOnProc)
    {
      attrs.addAttribute("","",EffectXMLConstants.REACTIVE_VITAL_REMOVE_ON_PROC_ATTR,XmlWriter.CDATA,String.valueOf(removeOnProc));
    }
    // Vital types
    List<VitalType> vitalTypes=reactiveVitalEffect.getVitalTypes();
    if (!vitalTypes.isEmpty())
    {
      String vitalTypesStr=serializeEnumList(vitalTypes);
      attrs.addAttribute("","",EffectXMLConstants.REACTIVE_VITAL_VITAL_TYPES_ATTR,XmlWriter.CDATA,vitalTypesStr);
    }
  }

  private void writePropertyModificationEffectAttributes(AttributesImpl attrs, PropertyModificationEffect propertyModificationEffect) // NOSONAR
  {
    // Nothing!
  }

  private void writeTravelEffectAttributes(AttributesImpl attrs, TravelEffect travelEffect)
  {
    // Scene ID
    int sceneID=travelEffect.getSceneID();
    if (sceneID>0)
    {
      attrs.addAttribute("","",EffectXMLConstants.TRAVEL_EFFECT_SCENE_ID,XmlWriter.CDATA,String.valueOf(sceneID));
    }
    // Remove from instance
    boolean removeFromInstance=travelEffect.isRemoveFromInstance();
    if (!removeFromInstance)
    {
      attrs.addAttribute("","",EffectXMLConstants.TRAVEL_EFFECT_REMOVE_FROM_INSTANCE,XmlWriter.CDATA,String.valueOf(removeFromInstance));
    }
    // Private encounter ID
    Integer privateEncounterID=travelEffect.getPrivateEncounterID();
    if (privateEncounterID!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants.TRAVEL_EFFECT_PRIVATE_ENCOUNTER_ID,XmlWriter.CDATA,privateEncounterID.toString());
    }
  }

  private void writeBaseVitalEffectAttributes(AttributesImpl attrs, BaseVitalEffect baseVitalEffect)
  {
    // Stat
    StatDescription stat=baseVitalEffect.getStat();
    attrs.addAttribute("","",EffectXMLConstants.BASE_VITAL_EFFECT_STAT_ATTR,XmlWriter.CDATA,String.valueOf(stat.getKey()));
    // Damage type (if harmful, null otherwise)
    DamageType damageType=baseVitalEffect.getDamageType();
    if (damageType!=null)
    {
      String damageTypeStr=String.valueOf(damageType.getCode());
      attrs.addAttribute("","",EffectXMLConstants.BASE_VITAL_EFFECT_DAMAGE_TYPE_ATTR,XmlWriter.CDATA,damageTypeStr);
    }
  }

  private void writeTieredEffectAttributes(AttributesImpl attrs, TieredEffect tieredEffect)
  {
    // Show in Examination
    boolean showInExamination=tieredEffect.isShowInExamination();
    if (showInExamination)
    {
      attrs.addAttribute("","",EffectXMLConstants.TIERED_SHOW_IN_EXAMINATION_ATTR,XmlWriter.CDATA,String.valueOf(showInExamination));
    }
  }

  private void writeAreaEffectAttributes(AttributesImpl attrs, AreaEffect areaEffect)
  {
    // Flags
    int flags=areaEffect.getFlags();
    if (flags>0)
    {
      attrs.addAttribute("","",EffectXMLConstants.AREA_EFFECT_FLAGS_ATTR,XmlWriter.CDATA,String.valueOf(flags));
    }
    // Range
    float range=areaEffect.getRange();
    if (range>0)
    {
      attrs.addAttribute("","",EffectXMLConstants.AREA_EFFECT_RANGE_ATTR,XmlWriter.CDATA,String.valueOf(range));
    }
    // Detection buffer
    float detectionBuffer=areaEffect.getDetectionBuffer();
    if (detectionBuffer>0)
    {
      attrs.addAttribute("","",EffectXMLConstants.AREA_EFFECT_DETECTION_BUFFER_ATTR,XmlWriter.CDATA,String.valueOf(detectionBuffer));
    }
    // Max targets
    int maxTargets=areaEffect.getMaxTargets();
    attrs.addAttribute("","",EffectXMLConstants.AREA_EFFECT_MAX_TARGETS_ATTR,XmlWriter.CDATA,String.valueOf(maxTargets));
    // - modifiers
    String maxTargetsMods=ModPropertyListIO.asPersistentString(areaEffect.getMaxTargetsModifiers());
    if (!maxTargetsMods.isEmpty())
    {
      attrs.addAttribute("","",EffectXMLConstants.AREA_EFFECT_MAX_TARGETS_MODS_ATTR,XmlWriter.CDATA,maxTargetsMods);
    }
  }

  private void writePipEffectAttributes(AttributesImpl attrs, PipEffect pipEffect)
  {
    // Type
    PipType type=pipEffect.getType();
    attrs.addAttribute("","",EffectXMLConstants.PIP_TYPE_ATTR,XmlWriter.CDATA,String.valueOf(type.getCode()));
    // Reset
    boolean reset=pipEffect.isReset();
    if (reset)
    {
      attrs.addAttribute("","",EffectXMLConstants.PIP_RESET_ATTR,XmlWriter.CDATA,String.valueOf(reset));
    }
    else
    {
      // Adjustment type
      PipAdjustmentType adjustmentType=pipEffect.getAdjustmentType();
      if (adjustmentType!=null)
      {
        attrs.addAttribute("","",EffectXMLConstants.PIP_ADJUSTMENT_TYPE_ATTR,XmlWriter.CDATA,String.valueOf(adjustmentType.getCode()));
      }
      // Amount
      int amount=pipEffect.getAmount();
      attrs.addAttribute("","",EffectXMLConstants.PIP_AMOUNT_ATTR,XmlWriter.CDATA,String.valueOf(amount));
      // Amount modifiers
      String amountModsStr=ModPropertyListIO.asPersistentString(pipEffect.getAmountModifiers());
      if (!amountModsStr.isEmpty())
      {
        attrs.addAttribute("","",EffectXMLConstants.PIP_AMOUNT_MODIFIERS_ATTR,XmlWriter.CDATA,amountModsStr);
      }
    }
  }

  private void writeAuraEffectAttributes(AttributesImpl attrs, AuraEffect auraEffect)
  {
    // Type
    EffectAuraType type=auraEffect.getType();
    attrs.addAttribute("","",EffectXMLConstants.AURA_EFFECT_TYPE_ATTR,XmlWriter.CDATA,String.valueOf(type.getCode()));
    // Should affect caster
    boolean shouldAffectCaster=auraEffect.shouldAffectCaster();
    if (!shouldAffectCaster)
    {
      attrs.addAttribute("","",EffectXMLConstants.AURA_SHOULD_AFFECT_CASTER_ATTR,XmlWriter.CDATA,String.valueOf(shouldAffectCaster));
    }
  }

  private void writeDispelEffectAttributes(AttributesImpl attrs, DispelEffect dispelEffect)
  {
    // Dispel casters
    boolean dispelCasters=dispelEffect.isDispelCasters();
    if (dispelCasters)
    {
      attrs.addAttribute("","",EffectXMLConstants.DISPEL_EFFECT_DISPEL_CASTERS_ATTR,XmlWriter.CDATA,String.valueOf(dispelCasters));
    }
  }

  private void writeChildTags(TransformerHandler hd, Effect effect) throws SAXException
  {
    if (effect instanceof GenesisEffect)
    {
      writeGenesisTags(hd,(GenesisEffect)effect);
    }
    else if (effect instanceof InduceCombatStateEffect)
    {
      writeInduceCombatStateTags(hd,(InduceCombatStateEffect)effect);
    }
    else if (effect instanceof InstantFellowshipEffect)
    {
      writeInstantFellowshipEffectTags(hd,(InstantFellowshipEffect)effect);
    }
    else if (effect instanceof InstantVitalEffect)
    {
      InstantVitalEffect instantVitalEffect=(InstantVitalEffect)effect;
      writeInstantVitalEffectTags(hd,instantVitalEffect);
    }
    else if (effect instanceof ProcEffect)
    {
      ProcEffect procEffect=(ProcEffect)effect;
      writeProcEffectTags(hd,procEffect);
    }
    else if (effect instanceof ReactiveVitalEffect)
    {
      ReactiveVitalEffect reactiveVitalEffect=(ReactiveVitalEffect)effect;
      writeReactiveVitalEffectTags(hd,reactiveVitalEffect);
    }
    else if (effect instanceof CountDownEffect)
    {
      CountDownEffect countDownEffect=(CountDownEffect)effect;
      writeCountDownEffectTags(hd,countDownEffect);
    }
    else if (effect instanceof ApplyOverTimeEffect)
    {
      ApplyOverTimeEffect applyOverTimeEffect=(ApplyOverTimeEffect)effect;
      writeApplyOverTimeEffectTags(hd,applyOverTimeEffect);
    }
    else if (effect instanceof PropertyModificationEffect)
    {
      PropertyModificationEffect propertyModificationEffect=(PropertyModificationEffect)effect;
      writePropertyModificationEffectTags(hd,propertyModificationEffect);
    }
    else if (effect instanceof VitalOverTimeEffect)
    {
      VitalOverTimeEffect vitalOverTimeEffect=(VitalOverTimeEffect)effect;
      writeVitalOverTimeEffectTags(hd,vitalOverTimeEffect);
    }
    else if (effect instanceof RecallEffect)
    {
      RecallEffect recallEffect=(RecallEffect)effect;
      writeRecallEffectTags(hd,recallEffect);
    }
    else if (effect instanceof TravelEffect)
    {
      TravelEffect travelEffect=(TravelEffect)effect;
      writeTravelEffectTags(hd,travelEffect);
    }
    else if (effect instanceof ComboEffect)
    {
      ComboEffect comboEffect=(ComboEffect)effect;
      writeComboEffectTags(hd,comboEffect);
    }
    else if (effect instanceof TieredEffect)
    {
      TieredEffect tieredEffect=(TieredEffect)effect;
      writeTieredEffectTags(hd,tieredEffect);
    }
    else if (effect instanceof AreaEffect)
    {
      AreaEffect areaEffect=(AreaEffect)effect;
      writeAreaEffectTags(hd,areaEffect);
    }
    else if (effect instanceof ReviveEffect)
    {
      ReviveEffect reviveEffect=(ReviveEffect)effect;
      writeReviveEffectTags(hd,reviveEffect);
    }
    else if (effect instanceof AuraEffect)
    {
      AuraEffect auraEffect=(AuraEffect)effect;
      writeAuraEffectTags(hd,auraEffect);
    }
    else if (effect instanceof DispelEffect)
    {
      DispelEffect dispelEffect=(DispelEffect)effect;
      writeDispelEffectTags(hd,dispelEffect);
    }
    else if (effect instanceof RandomEffect)
    {
      RandomEffect randomEffect=(RandomEffect)effect;
      writeRandomEffectTags(hd,randomEffect);
    }
    else if (effect instanceof AIPetEffect)
    {
      AIPetEffect aiPetEffect=(AIPetEffect)effect;
      writeAIPetEffectTags(hd,aiPetEffect);
    }
  }

  private void writeGenesisTags(TransformerHandler hd, GenesisEffect genesis) throws SAXException
  {
    Hotspot hotspot=genesis.getHotspot();
    if (hotspot!=null)
    {
      AttributesImpl attrs=new AttributesImpl();
      int id=hotspot.getIdentifier();
      attrs.addAttribute("","",EffectXMLConstants.HOTSPOT_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      String hotspotName=hotspot.getName();
      attrs.addAttribute("","",EffectXMLConstants.HOTSPOT_NAME_ATTR,XmlWriter.CDATA,hotspotName);
      hd.startElement("","",EffectXMLConstants.HOTSPOT_TAG,attrs);
      for(EffectGenerator effectGenerator : hotspot.getEffects())
      {
        writeEffectGenerator(hd,effectGenerator);
      }
      hd.endElement("","",EffectXMLConstants.HOTSPOT_TAG);
    }
    Proxy<Interactable> interactable=genesis.getInteractable();
    if (interactable!=null)
    {
      AttributesImpl attrs=new AttributesImpl();
      int id=interactable.getId();
      attrs.addAttribute("","",EffectXMLConstants.SUMMONED_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      String name=interactable.getName();
      attrs.addAttribute("","",EffectXMLConstants.SUMMONED_NAME_ATTR,XmlWriter.CDATA,name);
      hd.startElement("","",EffectXMLConstants.SUMMONED_TAG,attrs);
      hd.endElement("","",EffectXMLConstants.SUMMONED_TAG);
    }
  }

  private void writeInduceCombatStateTags(TransformerHandler hd, InduceCombatStateEffect induceCombatState) throws SAXException
  {
    // Function
    LinearFunction function=induceCombatState.getDurationFunction();
    if (function!=null)
    {
      AttributesImpl attrs=new AttributesImpl();
      attrs.addAttribute("","",EffectXMLConstants.FUNCTION_MIN_X_ATTR,XmlWriter.CDATA,String.valueOf(function.getMinX()));
      attrs.addAttribute("","",EffectXMLConstants.FUNCTION_MAX_X_ATTR,XmlWriter.CDATA,String.valueOf(function.getMaxX()));
      attrs.addAttribute("","",EffectXMLConstants.FUNCTION_MIN_Y_ATTR,XmlWriter.CDATA,String.valueOf(function.getMinY()));
      attrs.addAttribute("","",EffectXMLConstants.FUNCTION_MAX_Y_ATTR,XmlWriter.CDATA,String.valueOf(function.getMaxY()));
      hd.startElement("","",EffectXMLConstants.FUNCTION_TAG,attrs);
      hd.endElement("","",EffectXMLConstants.FUNCTION_TAG);
    }
  }

  private void writeInstantFellowshipEffectTags(TransformerHandler hd, InstantFellowshipEffect instantFellowshipEffect) throws SAXException
  {
    for(EffectGenerator effectGenerator : instantFellowshipEffect.getEffects())
    {
      writeEffectGenerator(hd,effectGenerator);
    }
  }

  private void writeInstantVitalEffectTags(TransformerHandler hd, InstantVitalEffect instantVitalEffect) throws SAXException
  {
    writeVitalChangeTag(hd,instantVitalEffect.getInstantChangeDescription(),EffectXMLConstants.VITAL_CHANGE_TAG);
  }

  private void writeVitalChangeTag(TransformerHandler hd, VitalChangeDescription change, String tagName) throws SAXException
  {
    if (change!=null)
    {
      AttributesImpl attrs=new AttributesImpl();
      writeAbstractVitalChangeAttributes(attrs,change);
      // Min
      Float min=change.getMinValue();
      if (min!=null)
      {
        attrs.addAttribute("","",EffectXMLConstants.VITAL_CHANGE_MIN_ATTR,XmlWriter.CDATA,min.toString());
      }
      // Max
      Float max=change.getMinValue();
      if (max!=null)
      {
        attrs.addAttribute("","",EffectXMLConstants.VITAL_CHANGE_MAX_ATTR,XmlWriter.CDATA,max.toString());
      }
      hd.startElement("","",tagName,attrs);
      hd.endElement("","",tagName);
    }
  }

  private void writeAbstractVitalChangeAttributes(AttributesImpl attrs, AbstractVitalChange change)
  {
    // Constant
    Float constant=change.getConstant();
    if (constant!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants.VITAL_CHANGE_CONSTANT_ATTR,XmlWriter.CDATA,constant.toString());
    }
    // Progression
    Progression progression=change.getProgression();
    if (progression!=null)
    {
      int id=progression.getIdentifier();
      attrs.addAttribute("","",EffectXMLConstants.VITAL_CHANGE_PROGRESSION_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    // Variance
    Float variance=change.getVariance();
    if ((variance!=null) && (variance.floatValue()>0))
    {
      attrs.addAttribute("","",EffectXMLConstants.VITAL_CHANGE_VARIANCE_ATTR,XmlWriter.CDATA,variance.toString());
    }
    // Modifiers
    ModPropertyList modifiers=change.getModifiers();
    String modifiersStr=ModPropertyListIO.asPersistentString(modifiers);
    if (!modifiersStr.isEmpty())
    {
      attrs.addAttribute("","",EffectXMLConstants.VITAL_CHANGE_MODIFIERS_ATTR,XmlWriter.CDATA,modifiersStr);
    }
    // VPS multiplier
    Float vpsMultiplier=change.getVPSMultiplier();
    if (vpsMultiplier!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants.VITAL_CHANGE_VPS_MULTIPLIER_ATTR,XmlWriter.CDATA,vpsMultiplier.toString());
    }
  }

  private void writeProcEffectTags(TransformerHandler hd, ProcEffect procEffect) throws SAXException
  {
    writePropertyModificationEffectTags(hd,procEffect);
    for(ProcEffectGenerator effectGenerator : procEffect.getProcedEffects())
    {
      writeProcEffectTag(hd,effectGenerator);
    }
  }

  private void writeProcEffectTag(TransformerHandler hd, ProcEffectGenerator generator) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    writeEffectGeneratorAttrs(attrs,generator);
    // User/target
    boolean target=generator.isOnTarget();
    if (target)
    {
      attrs.addAttribute("","",EffectXMLConstants.PROC_TARGET_ATTR,XmlWriter.CDATA,String.valueOf(target));
    }
    hd.startElement("","",EffectXMLConstants.EFFECT_GENERATOR_TAG,attrs);
    hd.endElement("","",EffectXMLConstants.EFFECT_GENERATOR_TAG);
  }

  private void writeReactiveVitalEffectTags(TransformerHandler hd, ReactiveVitalEffect reactiveVitalEffect) throws SAXException
  {
    writePropertyModificationEffectTags(hd,reactiveVitalEffect);
    ReactiveChange attacker=reactiveVitalEffect.getAttackerReactiveChange();
    writeReactiveChangeTag(hd,attacker,EffectXMLConstants.ATTACKER_TAG);
    ReactiveChange defender=reactiveVitalEffect.getDefenderReactiveChange();
    writeReactiveChangeTag(hd,defender,EffectXMLConstants.DEFENDER_TAG);
  }

  private void writeReactiveChangeTag(TransformerHandler hd, ReactiveChange change, String tagName) throws SAXException
  {
    if (change==null)
    {
      return;
    }
    hd.startElement("","",tagName,new AttributesImpl());
    ReactiveVitalChange vitalChange=change.getVitalChange();
    if (vitalChange!=null)
    {
      AttributesImpl attrs=new AttributesImpl();
      writeAbstractVitalChangeAttributes(attrs,vitalChange);
      // Probability
      float probability=vitalChange.getProbability();
      attrs.addAttribute("","",EffectXMLConstants.REACTIVE_VITAL_PROBABILITY_ATTR,XmlWriter.CDATA,String.valueOf(probability));
      // Multiplicative
      boolean multiplicative=vitalChange.isMultiplicative();
      if (multiplicative)
      {
        attrs.addAttribute("","",EffectXMLConstants.REACTIVE_VITAL_MULTIPLICATIVE_ATTR,XmlWriter.CDATA,String.valueOf(multiplicative));
      }
      hd.startElement("","",EffectXMLConstants.REACTIVE_VITAL_TAG,attrs);
      hd.endElement("","",EffectXMLConstants.REACTIVE_VITAL_TAG);
    }
    writeReactiveVitalEffectTag(hd,change.getEffect());
    hd.endElement("","",tagName);
  }

  private void writeReactiveVitalEffectTag(TransformerHandler hd, EffectAndProbability effectProb) throws SAXException
  {
    if (effectProb!=null)
    {
      AttributesImpl attrs=new AttributesImpl();
      // ID
      int id=effectProb.getEffect().getIdentifier();
      attrs.addAttribute("","",EffectXMLConstants.REACTIVE_EFFECT_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      // Name
      String name=effectProb.getEffect().getName();
      if (name!=null)
      {
        attrs.addAttribute("","",EffectXMLConstants.REACTIVE_EFFECT_NAME_ATTR,XmlWriter.CDATA,name);
      }
      // Probability
      float probability=effectProb.getProbability();
      attrs.addAttribute("","",EffectXMLConstants.REACTIVE_EFFECT_PROBABILITY_ATTR,XmlWriter.CDATA,String.valueOf(probability));
      hd.startElement("","",EffectXMLConstants.REACTIVE_EFFECT_TAG,attrs);
      hd.endElement("","",EffectXMLConstants.REACTIVE_EFFECT_TAG);
    }
  }

  private void writePropertyModificationEffectTags(TransformerHandler hd, PropertyModificationEffect propertyModificationEffect) throws SAXException
  {
    // Stats
    StatsProvider statsProvider=propertyModificationEffect.getStatsProvider();
    if (statsProvider!=null)
    {
      StatsProviderXMLWriter.writeXml(hd,statsProvider);
    }
  }

  private void writeVitalOverTimeEffectTags(TransformerHandler hd, VitalOverTimeEffect vitalOverTimeEffect) throws SAXException
  {
    // Initial change
    VitalChangeDescription initialChange=vitalOverTimeEffect.getInitialChangeDescription();
    writeVitalChangeTag(hd,initialChange,EffectXMLConstants.INITIAL_CHANGE_TAG);
    // Over-time change
    VitalChangeDescription overTimeChange=vitalOverTimeEffect.getOverTimeChangeDescription();
    writeVitalChangeTag(hd,overTimeChange,EffectXMLConstants.OVER_TIME_CHANGE_TAG);
  }

  private void writeRecallEffectTags(TransformerHandler hd, RecallEffect recallEffect) throws SAXException
  {
    // Position
    Position position=recallEffect.getPosition();
    if (position!=null)
    {
      PositionXMLWriter.writePosition(hd,position);
    }
  }

  private void writeTravelEffectTags(TransformerHandler hd, TravelEffect travelEffect) throws SAXException
  {
    PositionXMLWriter.writePosition(hd,travelEffect.getDestination());
  }

  private void writeComboEffectTags(TransformerHandler hd, ComboEffect comboEffect) throws SAXException
  {
    for(Proxy<Effect> proxy : comboEffect.getPresentEffects())
    {
      writeEffectProxyTag(hd,EffectXMLConstants.COMBO_PRESENT_EFFECT_TAG,proxy);
    }
    writeEffectProxyTag(hd,EffectXMLConstants.COMBO_TO_ADD_IF_NOT_PRESENT_TAG,comboEffect.getToAddIfNotPresent());
    writeEffectProxyTag(hd,EffectXMLConstants.COMBO_TO_ADD_IF_PRESENT_TAG,comboEffect.getToAddIfPresent());
    writeEffectProxyTag(hd,EffectXMLConstants.COMBO_TO_GIVE_BACK_IF_NOT_PRESENT_TAG,comboEffect.getToGiveBackIfNotPresent());
    writeEffectProxyTag(hd,EffectXMLConstants.COMBO_TO_GIVE_BACK_IF_PRESENT_TAG,comboEffect.getToGiveBackIfPresent());
    writeEffectProxyTag(hd,EffectXMLConstants.COMBO_TO_EXAMINE_TAG,comboEffect.getToExamine());
  }

  private void writeTieredEffectTags(TransformerHandler hd, TieredEffect tieredEffect) throws SAXException
  {
    for(EffectGenerator generator : tieredEffect.getTiers())
    {
      writeEffectGenerator(hd,generator, EffectXMLConstants.TIERED_TIER_UP_TAG);
    }
    EffectGenerator finalTier=tieredEffect.getFinalTier();
    if (finalTier!=null)
    {
      writeEffectGenerator(hd,finalTier, EffectXMLConstants.TIERED_FINAL_TIER_TAG);
    }
  }

  private void writeAreaEffectTags(TransformerHandler hd, AreaEffect areaEffect) throws SAXException
  {
    for(EffectGenerator generator : areaEffect.getEffects())
    {
      writeEffectGenerator(hd,generator);
    }
  }

  private void writeCountDownEffectTags(TransformerHandler hd, CountDownEffect countDownEffect) throws SAXException
  {
    // Stats
    writePropertyModificationEffectTags(hd,countDownEffect);
    // 'on expire' effects
    for(EffectGenerator onExpireEffect : countDownEffect.getOnExpireEffects())
    {
      writeEffectGenerator(hd,onExpireEffect,EffectXMLConstants.ON_EXPIRE_TAG);
    }
    // 'on removal' effect
    EffectGenerator onRemovalEffect=countDownEffect.getOnRemovalEffect();
    if (onRemovalEffect!=null)
    {
      writeEffectGenerator(hd,onRemovalEffect,EffectXMLConstants.ON_REMOVAL_TAG);
    }
  }

  private void writeBubbleEffectAttributes(AttributesImpl attrs, BubbleEffect bubbleEffect)
  {
    // Vital
    StatDescription vital=bubbleEffect.getVital();
    attrs.addAttribute("","",EffectXMLConstants.BUBBLE_VITAL_ATTR,XmlWriter.CDATA,String.valueOf(vital.getKey()));
    // Value
    Float value=bubbleEffect.getValue();
    if (value!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants.BUBBLE_VALUE_ATTR,XmlWriter.CDATA,value.toString());
    }
    // Percentage
    Float percentage=bubbleEffect.getPercentage();
    if (percentage!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants.BUBBLE_PERCENTAGE_ATTR,XmlWriter.CDATA,percentage.toString());
    }
    // Progression
    Progression progression=bubbleEffect.getProgression();
    if (progression!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants.BUBBLE_PROGRESSION_ATTR,XmlWriter.CDATA,String.valueOf(progression.getIdentifier()));
    }
    // Modifiers
    String modifiers=ModPropertyListIO.asPersistentString(bubbleEffect.getModifiers());
    if (!modifiers.isEmpty())
    {
      attrs.addAttribute("","",EffectXMLConstants.BUBBLE_MODS_ATTR,XmlWriter.CDATA,modifiers);
    }
  }

  private void writeApplyOverTimeEffectTags(TransformerHandler hd, ApplyOverTimeEffect applyOverTimeEffect) throws SAXException
  {
    // 'initially applied' effects
    for(EffectGenerator initiallyAppliedEffect : applyOverTimeEffect.getInitiallyAppliedEffects())
    {
      writeEffectGenerator(hd,initiallyAppliedEffect,EffectXMLConstants.INITIALLY_APPLIED_TAG);
    }
    // 'applied' effects
    for(EffectGenerator appliedEffect : applyOverTimeEffect.getAppliedEffects())
    {
      writeEffectGenerator(hd,appliedEffect,EffectXMLConstants.APPLIED_TAG);
    }
  }

  private void writeReviveEffectTags(TransformerHandler hd, ReviveEffect reviveEffect) throws SAXException
  {
    for(ReviveVitalData vitalData : reviveEffect.getReviveVitalData())
    {
      AttributesImpl attrs=new AttributesImpl();
      // Vital
      VitalType vital=vitalData.getVital();
      attrs.addAttribute("","",EffectXMLConstants.REVIVE_VITAL_ATTR,XmlWriter.CDATA,String.valueOf(vital.getCode()));
      // Percentage
      float percentage=vitalData.getPercentage();
      attrs.addAttribute("","",EffectXMLConstants.REVIVE_PERCENTAGE_ATTR,XmlWriter.CDATA,String.valueOf(percentage));
      // Modifiers
      String modifiersStr=ModPropertyListIO.asPersistentString(vitalData.getModifiers());
      if (!modifiersStr.isEmpty())
      {
        attrs.addAttribute("","",EffectXMLConstants.REVIVE_MODIFIERS_ATTR,XmlWriter.CDATA,modifiersStr);
      }
      hd.startElement("","",EffectXMLConstants.REVIVE_VITAL_TAG,attrs);
      hd.endElement("","",EffectXMLConstants.REVIVE_VITAL_TAG);
    }
    for(Proxy<Effect> proxy : reviveEffect.getReviveEffects())
    {
      writeEffectProxyTag(hd,EffectXMLConstants.EFFECT_TAG,proxy);
    }
  }

  private void writeAuraEffectTags(TransformerHandler hd, AuraEffect auraEffect) throws SAXException
  {
    for(EffectGenerator generator : auraEffect.getAppliedEffects())
    {
      writeEffectGenerator(hd,generator);
    }
  }

  private void writeDispelEffectTags(TransformerHandler hd, DispelEffect dispelEffect) throws SAXException
  {
    for(Proxy<Effect> proxy : dispelEffect.getEffects())
    {
      writeEffectProxyTag(hd,EffectXMLConstants.EFFECT_TAG,proxy);
    }
  }

  private void writeRandomEffectTags(TransformerHandler hd, RandomEffect randomEffect) throws SAXException
  {
    for(RandomEffectGenerator generator : randomEffect.getEffects())
    {
      AttributesImpl attrs=new AttributesImpl();
      writeEffectGeneratorAttrs(attrs,generator);
      // Weight
      float weight=generator.getWeight();
      attrs.addAttribute("","",EffectXMLConstants.EFFECT_GENERATOR_WEIGHT_ATTR,XmlWriter.CDATA,String.valueOf(weight));
      // To caster
      boolean toCaster=generator.isToCaster();
      if (toCaster)
      {
        attrs.addAttribute("","",EffectXMLConstants.EFFECT_GENERATOR_TO_CASTER_ATTR,XmlWriter.CDATA,String.valueOf(toCaster));
      }
      hd.startElement("","",EffectXMLConstants.EFFECT_TAG,attrs);
      hd.endElement("","",EffectXMLConstants.EFFECT_TAG);
    }
  }

  private void writeAIPetEffectTags(TransformerHandler hd, AIPetEffect aiPetEffect) throws SAXException
  {
    // Summoned agent
    Proxy<AgentDescription> agentProxy=aiPetEffect.getAgent();
    if (agentProxy!=null)
    {
      AttributesImpl attrs=new AttributesImpl();
      int id=agentProxy.getId();
      attrs.addAttribute("","",EffectXMLConstants.SUMMONED_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      String name=agentProxy.getName();
      attrs.addAttribute("","",EffectXMLConstants.SUMMONED_NAME_ATTR,XmlWriter.CDATA,name);
      hd.startElement("","",EffectXMLConstants.SUMMONED_TAG,attrs);
      hd.endElement("","",EffectXMLConstants.SUMMONED_TAG);
    }
    // Startup effects
    for(EffectGenerator generator : aiPetEffect.getStartupEffects())
    {
      writeEffectGenerator(hd,generator);
    }
    // 'Apply to master' effects
    for(EffectGenerator generator : aiPetEffect.getApplyToMasterEffects())
    {
      writeEffectGenerator(hd,generator,EffectXMLConstants.APPLY_TO_MASTER_EFFECT_GENERATOR_TAG);
    }
  }

  
  private void writeEffectProxyTag(TransformerHandler hd, String tagName, Proxy<Effect> proxy) throws SAXException
  {
    if (proxy==null)
    {
      return;
    }
    int id=proxy.getId();
    AttributesImpl attrs=new AttributesImpl();
    attrs.addAttribute("","",EffectXMLConstants.EFFECT_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    String name=proxy.getName();
    if (name!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants.EFFECT_NAME_ATTR,XmlWriter.CDATA,name);
    }
    hd.startElement("","",tagName,attrs);
    hd.endElement("","",tagName);
  }

  private void writeEffectGenerator(TransformerHandler hd, EffectGenerator generator) throws SAXException
  {
    writeEffectGenerator(hd,generator,EffectXMLConstants.EFFECT_GENERATOR_TAG);
  }

  /**
   * Write an effect generator.
   * @param hd Output stream.
   * @param generator Generator to write.
   * @param tag Tag to use.
   * @throws SAXException If an error occurs.
   */
  public static void writeEffectGenerator(TransformerHandler hd, EffectGenerator generator, String tag) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    writeEffectGeneratorAttrs(attrs,generator);
    hd.startElement("","",tag,attrs);
    hd.endElement("","",tag);
  }

  private static void writeEffectGeneratorAttrs(AttributesImpl attrs, EffectGenerator generator)
  {
    Effect effect=generator.getEffect();
    int id=effect.getIdentifier();
    attrs.addAttribute("","",EffectXMLConstants.EFFECT_GENERATOR_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    String name=effect.getName();
    if (name!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants.EFFECT_GENERATOR_NAME_ATTR,XmlWriter.CDATA,name);
    }
    Float spellcraft=generator.getSpellcraft();
    if (spellcraft!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants.EFFECT_GENERATOR_SPELLCRAFT_ATTR,XmlWriter.CDATA,String.valueOf(spellcraft.floatValue()));
    }
  }

  private <T extends LotroEnumEntry> String serializeEnumList(List<T> enumList)
  {
    if ((enumList==null) || (enumList.isEmpty()))
    {
      return "";
    }
    StringBuilder sb=new StringBuilder();
    int size=enumList.size();
    for(int i=0;i<size;i++)
    {
      if (i>0)
      {
        sb.append(',');
      }
      T entry=enumList.get(i);
      sb.append(entry.getCode());
    }
    return sb.toString();
  }
}
