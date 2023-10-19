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
import delta.games.lotro.common.effects.AbstractVitalChange;
import delta.games.lotro.common.effects.ApplicationProbability;
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
import delta.games.lotro.common.effects.VitalChangeDescription;
import delta.games.lotro.common.effects.VitalOverTimeEffect;
import delta.games.lotro.common.enums.CombatState;
import delta.games.lotro.common.enums.LotroEnumEntry;
import delta.games.lotro.common.enums.ResistCategory;
import delta.games.lotro.common.enums.SkillType;
import delta.games.lotro.common.math.LinearFunction;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLWriter;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.utils.Proxy;
import delta.games.lotro.utils.maths.Progression;

/**
 * Writes effects to XML documents.
 * @author DAM
 */
public class EffectXMLWriter2
{
  /**
   * Write some effects to a XML file.
   * @param toFile File to write to.
   * @param effects Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File toFile, final List<Effect2> effects)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",EffectXMLConstants2.EFFECTS_TAG,new AttributesImpl());
        for(Effect2 effect : effects)
        {
          writeEffect(hd,effect);
        }
        hd.endElement("","",EffectXMLConstants2.EFFECTS_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private void writeEffect(TransformerHandler hd, Effect2 effect) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    String tagName=getTagName(effect);
    writeSharedEffectAttributes(attrs,effect);
    writeSpecificAttributes(attrs,effect);
    hd.startElement("","",tagName,attrs);
    writeChildTags(hd,effect);
    hd.endElement("","",tagName);
  }

  private String getTagName(Effect2 effect)
  {
    if (effect instanceof DispelByResistEffect) return EffectXMLConstants2.DISPEL_BY_RESIST_TAG;
    if (effect instanceof GenesisEffect) return EffectXMLConstants2.GENESIS_TAG;
    if (effect instanceof InduceCombatStateEffect) return EffectXMLConstants2.INDUCE_COMBAT_STATE_TAG;
    if (effect instanceof InstantFellowshipEffect) return EffectXMLConstants2.FELLOWSHIP_EFFECT_TAG;
    if (effect instanceof InstantVitalEffect) return EffectXMLConstants2.INSTANT_VITAL_EFFECT_TAG;
    if (effect instanceof ProcEffect) return EffectXMLConstants2.PROC_TAG;
    if (effect instanceof ReactiveVitalEffect) return EffectXMLConstants2.REACTIVE_VITAL_EFFECT_TAG;
    if (effect instanceof PropertyModificationEffect) return EffectXMLConstants2.PROPERTY_MOD_EFFECT_TAG;
    if (effect instanceof VitalOverTimeEffect) return EffectXMLConstants2.VITAL_OVER_TIME_EFFECT_TAG;
    return EffectXMLConstants2.EFFECT_TAG;
  }

  private void writeSharedEffectAttributes(AttributesImpl attrs, Effect2 effect)
  {
    // Identifier
    int id=effect.getIdentifier();
    attrs.addAttribute("","",EffectXMLConstants2.EFFECT_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=effect.getName();
    if (name!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants2.EFFECT_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Description
    String description=effect.getDescription();
    if (description.length()>0)
    {
      attrs.addAttribute("","",EffectXMLConstants2.EFFECT_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    }
    // Description override
    String descriptionOverride=effect.getDescriptionOverride();
    if (descriptionOverride.length()>0)
    {
      attrs.addAttribute("","",EffectXMLConstants2.EFFECT_DESCRIPTION_OVERRIDE_ATTR,XmlWriter.CDATA,descriptionOverride);
    }
    // Applied description
    String appliedDescription=effect.getAppliedDescription();
    if (appliedDescription.length()>0)
    {
      attrs.addAttribute("","",EffectXMLConstants2.EFFECT_APPLIED_DESCRIPTION_ATTR,XmlWriter.CDATA,appliedDescription);
    }
    // Icon
    Integer iconId=effect.getIconId();
    if (iconId!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants2.EFFECT_ICON_ID_ATTR,XmlWriter.CDATA,iconId.toString());
    }
    // Effect Duration
    EffectDuration effectDuration=effect.getEffectDuration();
    if (effectDuration!=null)
    {
      // Duration
      Float duration=effectDuration.getDuration();
      if (duration!=null)
      {
        attrs.addAttribute("","",EffectXMLConstants2.EFFECT_DURATION_ATTR,XmlWriter.CDATA,duration.toString());
      }
      // Pulse count
      int pulseCount=effectDuration.getPulseCount();
      if (pulseCount>0)
      {
        attrs.addAttribute("","",EffectXMLConstants2.EFFECT_PULSE_COUNT_ATTR,XmlWriter.CDATA,String.valueOf(pulseCount));
      }
      // Expires in real time
      boolean expiresInRealTime=effectDuration.expiresInRealTime();
      if (expiresInRealTime)
      {
        attrs.addAttribute("","",EffectXMLConstants2.EFFECT_EXPIRES_IN_REAL_TIME_ATTR,XmlWriter.CDATA,String.valueOf(expiresInRealTime));
      }
    }
    // Probability
    ApplicationProbability applicationProbability=effect.getApplicationProbability();
    if (applicationProbability!=ApplicationProbability.ALWAYS)
    {
      float probability=applicationProbability.getProbability();
      if (probability>0)
      {
        attrs.addAttribute("","",EffectXMLConstants2.EFFECT_PROBABILITY_ATTR,XmlWriter.CDATA,String.valueOf(probability));
      }
      float variance=applicationProbability.getProbabilityVariance();
      if (variance>0)
      {
        attrs.addAttribute("","",EffectXMLConstants2.EFFECT_PROBABILITY_VARIANCE_ATTR,XmlWriter.CDATA,String.valueOf(variance));
      }
      Integer modProperty=applicationProbability.getModProperty();
      if ((modProperty!=null) && (modProperty.intValue()!=0))
      {
        attrs.addAttribute("","",EffectXMLConstants2.EFFECT_PROBABILITY_MOD_PROPERTY_ATTR,XmlWriter.CDATA,modProperty.toString());
      }
    }
  }

  private void writeSpecificAttributes(AttributesImpl attrs, Effect2 effect)
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
    else if (effect instanceof PropertyModificationEffect)
    {
      PropertyModificationEffect propertyModificationEffect=(PropertyModificationEffect)effect;
      writePropertyModificationEffectAttributes(attrs,propertyModificationEffect);
    }
    else if (effect instanceof VitalOverTimeEffect)
    {
      VitalOverTimeEffect vitalOverTimeEffect=(VitalOverTimeEffect)effect;
      writeVitalOverTimeEffectAttributes(attrs,vitalOverTimeEffect);
    }
  }

  private void writeDispelByResistAttributes(AttributesImpl attrs, DispelByResistEffect dispelByResistEffect)
  {
    // Max dispel count
    int maxDispelCount=dispelByResistEffect.getMaxDispelCount();
    attrs.addAttribute("","",EffectXMLConstants2.DISPEL_BY_RESIST_MAX_DISPELCOUNT_ATTR,XmlWriter.CDATA,String.valueOf(maxDispelCount));
    // Resist categories
    List<ResistCategory> resistCategories=dispelByResistEffect.getResistCategories();
    String resistCategoriesStr=serializeEnumList(resistCategories);
    if (!resistCategoriesStr.isEmpty())
    {
      attrs.addAttribute("","",EffectXMLConstants2.DISPEL_BY_RESIST_CATEGORIES_ATTR,XmlWriter.CDATA,String.valueOf(resistCategoriesStr));
    }
    // Use strength restriction
    boolean useStrengthRestriction=dispelByResistEffect.useStrengthRestriction();
    if (useStrengthRestriction)
    {
      attrs.addAttribute("","",EffectXMLConstants2.DISPEL_BY_RESIST_USE_STRENGTH_RESTRICTION_ATTR,XmlWriter.CDATA,String.valueOf(useStrengthRestriction));
      // Strength offset
      Integer offset=dispelByResistEffect.getStrengthOffset();
      if (offset!=null)
      {
        attrs.addAttribute("","",EffectXMLConstants2.DISPEL_BY_RESIST_STRENGTH_OFFSET_ATTR,XmlWriter.CDATA,offset.toString());
      }
    }
  }

  private void writeGenesisAttributes(AttributesImpl attrs, GenesisEffect genesisEffect)
  {
    // Summon duration
    float summonDuration=genesisEffect.getSummonDuration();
    if (summonDuration>0)
    {
      attrs.addAttribute("","",EffectXMLConstants2.GENESIS_SUMMON_DURATION_ATTR,XmlWriter.CDATA,String.valueOf(summonDuration));
    }
    // Permanent
    boolean permanent=genesisEffect.isPermanent();
    if (permanent)
    {
      attrs.addAttribute("","",EffectXMLConstants2.GENESIS_PERMANENT_ATTR,XmlWriter.CDATA,String.valueOf(permanent));
    }
  }

  private void writeInduceCombatStateAttributes(AttributesImpl attrs, InduceCombatStateEffect induceCombatState)
  {
    // Combat state
    CombatState combatState=induceCombatState.getCombatState();
    if (combatState!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants2.INDUCE_COMBAT_STATE_STATE_ATTR,XmlWriter.CDATA,String.valueOf(combatState.getCode()));
    }
    // Duration
    float duration=induceCombatState.getDuration();
    if (duration>0)
    {
      attrs.addAttribute("","",EffectXMLConstants2.INDUCE_COMBAT_STATE_DURATION_ATTR,XmlWriter.CDATA,String.valueOf(duration));
    }
  }

  private void writeInstantFellowshipEffectAttributes(AttributesImpl attrs, InstantFellowshipEffect instantFellowshipEffect)
  {
    // Apply to raid groups
    boolean raidGroups=instantFellowshipEffect.appliesToRaidGroups();
    if (raidGroups)
    {
      attrs.addAttribute("","",EffectXMLConstants2.FELLOWSHIP_EFFECT_APPLY_TO_RAID_GROUPS_ATTR,XmlWriter.CDATA,String.valueOf(raidGroups));
    }
    // Apply to pets
    boolean pets=instantFellowshipEffect.appliesToPets();
    if (pets)
    {
      attrs.addAttribute("","",EffectXMLConstants2.FELLOWSHIP_EFFECT_APPLY_TO_PETS_ATTR,XmlWriter.CDATA,String.valueOf(pets));
    }
    // Apply to target
    boolean target=instantFellowshipEffect.appliesToTarget();
    if (target)
    {
      attrs.addAttribute("","",EffectXMLConstants2.FELLOWSHIP_EFFECT_APPLY_TARGET_ATTR,XmlWriter.CDATA,String.valueOf(target));
    }
    // Range
    Float range=instantFellowshipEffect.getRange();
    if (range!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants2.FELLOWSHIP_EFFECT_RANGE_ATTR,XmlWriter.CDATA,String.valueOf(range.floatValue()));
    }
  }

  private void writeInstantVitalEffectAttributes(AttributesImpl attrs, InstantVitalEffect instantVitalEffect)
  {
    // Stat
    StatDescription stat=instantVitalEffect.getStat();
    attrs.addAttribute("","",EffectXMLConstants2.INSTANT_VITAL_EFFECT_STAT_ATTR,XmlWriter.CDATA,String.valueOf(stat.getKey()));
    // Multiplicative
    boolean multiplicative=instantVitalEffect.isMultiplicative();
    if (multiplicative)
    {
      attrs.addAttribute("","",EffectXMLConstants2.INSTANT_VITAL_EFFECT_MULTIPLICATIVE_ATTR,XmlWriter.CDATA,String.valueOf(multiplicative));
    }
  }

  private void writeProcEffectAttributes(AttributesImpl attrs, ProcEffect procEffect)
  {
    // Skill types
    List<SkillType> skillTypes=procEffect.getSkillTypes();
    String skillTypesStr=serializeEnumList(skillTypes);
    attrs.addAttribute("","",EffectXMLConstants2.PROC_SKILL_TYPES_ATTR,XmlWriter.CDATA,skillTypesStr);
    // Probability
    Float probability=procEffect.getProcProbability();
    if (probability!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants2.PROC_PROBABILITY_ATTR,XmlWriter.CDATA,probability.toString());
    }
    // Cooldown
    Float cooldown=procEffect.getCooldown();
    if (cooldown!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants2.PROC_COOLDOWN_ATTR,XmlWriter.CDATA,cooldown.toString());
    }
  }

  private void writeReactiveVitalEffectAttributes(AttributesImpl attrs, ReactiveVitalEffect reactiveVitalEffect)
  {
    // Incoming damage types
    List<DamageType> damageTypes=reactiveVitalEffect.getDamageTypes();
    String damageTypesStr=serializeEnumList(damageTypes);
    attrs.addAttribute("","",EffectXMLConstants2.REACTIVE_VITAL_DAMAGE_TYPES_ATTR,XmlWriter.CDATA,damageTypesStr);
    // Damage type override
    DamageType overrideDamageType=reactiveVitalEffect.getAttackerDamageTypeOverride();
    if (overrideDamageType!=null)
    {
      String damageTypeOverrideStr=String.valueOf(overrideDamageType.getCode());
      attrs.addAttribute("","",EffectXMLConstants2.REACTIVE_VITAL_DAMAGE_TYPE_OVERRIDE_ATTR,XmlWriter.CDATA,damageTypeOverrideStr);
    }
    // Remove on proc
    boolean removeOnProc=reactiveVitalEffect.isRemoveOnProc();
    if (removeOnProc)
    {
      attrs.addAttribute("","",EffectXMLConstants2.REACTIVE_VITAL_REMOVE_ON_PROC_ATTR,XmlWriter.CDATA,String.valueOf(removeOnProc));
    }
  }

  private void writePropertyModificationEffectAttributes(AttributesImpl attrs, PropertyModificationEffect propertyModificationEffect)
  {
    // Nothing!
  }

  private void writeVitalOverTimeEffectAttributes(AttributesImpl attrs, VitalOverTimeEffect vitalOverTimeEffect)
  {
    // Stat
    StatDescription stat=vitalOverTimeEffect.getStat();
    attrs.addAttribute("","",EffectXMLConstants2.VITAL_OVER_TIME_EFFECT_STAT_ATTR,XmlWriter.CDATA,String.valueOf(stat.getKey()));
    // Damage type (if harmful, null otherwise)
    DamageType damageType=vitalOverTimeEffect.getDamageType();
    if (damageType!=null)
    {
      String damageTypeStr=String.valueOf(damageType.getCode());
      attrs.addAttribute("","",EffectXMLConstants2.VITAL_OVER_TIME_EFFECT_DAMAGE_TYPE_ATTR,XmlWriter.CDATA,damageTypeStr);
    }
  }

  private void writeChildTags(TransformerHandler hd, Effect2 effect) throws SAXException
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
  }

  private void writeGenesisTags(TransformerHandler hd, GenesisEffect genesis) throws SAXException
  {
    Hotspot hotspot=genesis.getHotspot();
    if (hotspot!=null)
    {
      AttributesImpl attrs=new AttributesImpl();
      int id=hotspot.getIdentifier();
      attrs.addAttribute("","",EffectXMLConstants2.HOTSPOT_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      String hotspotName=hotspot.getName();
      attrs.addAttribute("","",EffectXMLConstants2.HOTSPOT_NAME_ATTR,XmlWriter.CDATA,hotspotName);
      hd.startElement("","",EffectXMLConstants2.HOTSPOT_TAG,attrs);
      for(EffectGenerator effectGenerator : hotspot.getEffects())
      {
        writeEffectGenerator(hd,effectGenerator);
      }
      hd.endElement("","",EffectXMLConstants2.HOTSPOT_TAG);
    }
    Proxy<Interactable> interactable=genesis.getInteractable();
    if (interactable!=null)
    {
      AttributesImpl attrs=new AttributesImpl();
      int id=interactable.getId();
      attrs.addAttribute("","",EffectXMLConstants2.SUMMONED_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      String name=interactable.getName();
      attrs.addAttribute("","",EffectXMLConstants2.SUMMONED_NAME_ATTR,XmlWriter.CDATA,name);
      hd.startElement("","",EffectXMLConstants2.SUMMONED_TAG,attrs);
      hd.endElement("","",EffectXMLConstants2.SUMMONED_TAG);
    }
  }

  private void writeInduceCombatStateTags(TransformerHandler hd, InduceCombatStateEffect induceCombatState) throws SAXException
  {
    // Function
    LinearFunction function=induceCombatState.getDurationFunction();
    if (function!=null)
    {
      AttributesImpl attrs=new AttributesImpl();
      attrs.addAttribute("","",EffectXMLConstants2.FUNCTION_MIN_X_ATTR,XmlWriter.CDATA,String.valueOf(function.getMinX()));
      attrs.addAttribute("","",EffectXMLConstants2.FUNCTION_MAX_X_ATTR,XmlWriter.CDATA,String.valueOf(function.getMaxX()));
      attrs.addAttribute("","",EffectXMLConstants2.FUNCTION_MIN_Y_ATTR,XmlWriter.CDATA,String.valueOf(function.getMinY()));
      attrs.addAttribute("","",EffectXMLConstants2.FUNCTION_MAX_Y_ATTR,XmlWriter.CDATA,String.valueOf(function.getMaxY()));
      hd.startElement("","",EffectXMLConstants2.FUNCTION_TAG,attrs);
      hd.endElement("","",EffectXMLConstants2.FUNCTION_TAG);
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
    writeVitalChangeTag(hd,instantVitalEffect.getInstantChangeDescription(),EffectXMLConstants2.VITAL_CHANGE_TAG);
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
        attrs.addAttribute("","",EffectXMLConstants2.VITAL_CHANGE_MIN_ATTR,XmlWriter.CDATA,min.toString());
      }
      // Max
      Float max=change.getMinValue();
      if (max!=null)
      {
        attrs.addAttribute("","",EffectXMLConstants2.VITAL_CHANGE_MAX_ATTR,XmlWriter.CDATA,max.toString());
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
      attrs.addAttribute("","",EffectXMLConstants2.VITAL_CHANGE_CONSTANT_ATTR,XmlWriter.CDATA,constant.toString());
    }
    // Progression
    Progression progression=change.getProgression();
    if (progression!=null)
    {
      int id=progression.getIdentifier();
      attrs.addAttribute("","",EffectXMLConstants2.VITAL_CHANGE_PROGRESSION_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    // Variance
    Float variance=change.getVariance();
    if ((variance!=null) && (variance.floatValue()>0))
    {
      attrs.addAttribute("","",EffectXMLConstants2.VITAL_CHANGE_VARIANCE_ATTR,XmlWriter.CDATA,variance.toString());
    }
  }

  private void writeProcEffectTags(TransformerHandler hd, ProcEffect procEffect) throws SAXException
  {
    writePropertyModificationEffectTags(hd,procEffect);
    for(EffectGenerator effectGenerator : procEffect.getProcedEffects())
    {
      writeEffectGenerator(hd,effectGenerator);
    }
  }

  private void writeReactiveVitalEffectTags(TransformerHandler hd, ReactiveVitalEffect reactiveVitalEffect) throws SAXException
  {
    writePropertyModificationEffectTags(hd,reactiveVitalEffect);
    ReactiveChange attacker=reactiveVitalEffect.getAttackerReactiveChange();
    writeReactiveChangeTag(hd,attacker,EffectXMLConstants2.ATTACKER_TAG);
    ReactiveChange defender=reactiveVitalEffect.getDefenderReactiveChange();
    writeReactiveChangeTag(hd,defender,EffectXMLConstants2.DEFENDER_TAG);
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
      attrs.addAttribute("","",EffectXMLConstants2.REACTIVE_VITAL_PROBABILITY_ATTR,XmlWriter.CDATA,String.valueOf(probability));
      // Multiplicative
      boolean multiplicative=vitalChange.isMultiplicative();
      if (multiplicative)
      {
        attrs.addAttribute("","",EffectXMLConstants2.REACTIVE_VITAL_MULTIPLICATIVE_ATTR,XmlWriter.CDATA,String.valueOf(multiplicative));
      }
      hd.startElement("","",EffectXMLConstants2.REACTIVE_VITAL_TAG,attrs);
      hd.endElement("","",EffectXMLConstants2.REACTIVE_VITAL_TAG);
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
      attrs.addAttribute("","",EffectXMLConstants2.REACTIVE_EFFECT_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      // Name
      String name=effectProb.getEffect().getName();
      if (name!=null)
      {
        attrs.addAttribute("","",EffectXMLConstants2.REACTIVE_EFFECT_NAME_ATTR,XmlWriter.CDATA,name);
      }
      // Probability
      float probability=effectProb.getProbability();
      attrs.addAttribute("","",EffectXMLConstants2.REACTIVE_EFFECT_PROBABILITY_ATTR,XmlWriter.CDATA,String.valueOf(probability));
      hd.startElement("","",EffectXMLConstants2.REACTIVE_EFFECT_TAG,attrs);
      hd.endElement("","",EffectXMLConstants2.REACTIVE_EFFECT_TAG);
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
    writeVitalChangeTag(hd,initialChange,EffectXMLConstants2.INITIAL_CHANGE_TAG);
    // Over-time change
    VitalChangeDescription overTimeChange=vitalOverTimeEffect.getInitialChangeDescription();
    writeVitalChangeTag(hd,overTimeChange,EffectXMLConstants2.OVER_TIME_CHANGE_TAG);

  }

  private void writeEffectGenerator(TransformerHandler hd, EffectGenerator generator) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    Effect2 effect=generator.getEffect();
    int id=effect.getIdentifier();
    attrs.addAttribute("","",EffectXMLConstants2.EFFECT_GENERATOR_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    String name=effect.getName();
    if (name!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants2.EFFECT_GENERATOR_NAME_ATTR,XmlWriter.CDATA,name);
    }
    Float spellcraft=generator.getSpellcraft();
    if (spellcraft!=null)
    {
      attrs.addAttribute("","",EffectXMLConstants2.EFFECT_GENERATOR_SPELLCRAFT_ATTR,XmlWriter.CDATA,String.valueOf(spellcraft.floatValue()));
    }
    hd.startElement("","",EffectXMLConstants2.EFFECT_GENERATOR_TAG,attrs);
    hd.endElement("","",EffectXMLConstants2.EFFECT_GENERATOR_TAG);
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
