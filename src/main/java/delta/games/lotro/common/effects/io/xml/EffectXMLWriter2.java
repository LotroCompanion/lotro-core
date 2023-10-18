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
import delta.games.lotro.common.effects.ApplicationProbability;
import delta.games.lotro.common.effects.DispelByResistEffect;
import delta.games.lotro.common.effects.Effect2;
import delta.games.lotro.common.effects.EffectDuration;
import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.effects.GenesisEffect;
import delta.games.lotro.common.effects.Hotspot;
import delta.games.lotro.common.effects.InduceCombatStateEffect;
import delta.games.lotro.common.effects.InstantFellowshipEffect;
import delta.games.lotro.common.effects.InstantVitalEffect;
import delta.games.lotro.common.effects.ProcEffect;
import delta.games.lotro.common.effects.PropertyModificationEffect;
import delta.games.lotro.common.effects.ReactiveVitalEffect;
import delta.games.lotro.common.effects.VitalOverTimeEffect;
import delta.games.lotro.common.enums.CombatState;
import delta.games.lotro.common.enums.LotroEnumEntry;
import delta.games.lotro.common.enums.ResistCategory;
import delta.games.lotro.common.math.LinearFunction;

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
    
  }
  private void writeProcEffectAttributes(AttributesImpl attrs, ProcEffect procEffect)
  {
    
  }
  private void writeReactiveVitalEffectAttributes(AttributesImpl attrs, ReactiveVitalEffect reactiveVitalEffect)
  {
    
  }
  private void writePropertyModificationEffectAttributes(AttributesImpl attrs, PropertyModificationEffect propertyModificationEffect)
  {
    
  }
  private void writeVitalOverTimeEffectAttributes(AttributesImpl attrs, VitalOverTimeEffect vitalOverTimeEffect)
  {
    
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
    Interactable interactable=genesis.getInteractable();
    if (interactable!=null)
    {
      AttributesImpl attrs=new AttributesImpl();
      int id=interactable.getIdentifier();
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

  private void writeEffectGenerator(TransformerHandler hd, EffectGenerator generator) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    Effect2 effect=generator.getEffect();
    int id=effect.getIdentifier();
    attrs.addAttribute("","",EffectXMLConstants2.EFFECT_GENERATOR_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    String name=effect.getName();
    attrs.addAttribute("","",EffectXMLConstants2.EFFECT_GENERATOR_NAME_ATTR,XmlWriter.CDATA,name);
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
      sb.append(enumList.get(i).getCode());
    }
    return sb.toString();
  }

  /*
  private void toto()
  {
    hd.startElement("","",EffectXMLConstants.EFFECT_TAG,attrs);
    // Stats
    StatsProvider statsProvider=effect.getStatsProvider();
    if (statsProvider!=null)
    {
      StatsProviderXMLWriter.writeXml(hd,statsProvider);
    }
    hd.endElement("","",EffectXMLConstants.EFFECT_TAG);
  }
  */
}
