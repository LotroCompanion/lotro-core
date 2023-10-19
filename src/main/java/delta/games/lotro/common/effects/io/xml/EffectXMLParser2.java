package delta.games.lotro.common.effects.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.NumericTools;
import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.xml.DOMParsingTools;
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
import delta.games.lotro.common.effects.VitalChangeDescription;
import delta.games.lotro.common.effects.VitalOverTimeEffect;
import delta.games.lotro.common.enums.CombatState;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumEntry;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.ResistCategory;
import delta.games.lotro.common.math.LinearFunction;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.lore.items.DamageType;

/**
 * Parser for effect descriptions stored in XML.
 * @author DAM
 */
public class EffectXMLParser2
{
  private SingleLocaleLabelsManager _labelsMgr;

  /**
   * Constructor.
   * @param labelsMgr Labels manager.
   */
  public EffectXMLParser2(SingleLocaleLabelsManager labelsMgr)
  {
    _labelsMgr=labelsMgr;
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
    return effects;
  }

  /**
   * Build an effect from an XML tag.
   * @param root Root XML tag.
   * @param labelsMgr Labels manager.
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
    else
    {
      ret=new Effect2();
    }
    readSharedAttributes(root,ret);
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
    String resistCategories=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants2.DISPEL_BY_RESIST_MAX_DISPELCOUNT_ATTR,null);
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
    // Hotspot
    Element hotspotTag=DOMParsingTools.getChildTagByName(root,EffectXMLConstants2.HOTSPOT_TAG);
    if (hotspotTag!=null)
    {
      NamedNodeMap hotspotAttrs=hotspotTag.getAttributes();
      // Identifier
      int id=DOMParsingTools.getIntAttribute(hotspotAttrs,EffectXMLConstants2.HOTSPOT_ID_ATTR,0);
      Hotspot hotspot=new Hotspot(id);
      // Name
      String name=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants2.HOTSPOT_NAME_ATTR,null);
      hotspot.setName(name);
      // Generators
      for(EffectGenerator effectGenerator : hotspot.getEffects())
      {
        hotspot.
        writeEffectGenerator(hd,effectGenerator);
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
      String name=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants2.SUMMONED_NAME_ATTR,null);
      // TODO Resolve interactable!
    }
  }

  private InduceCombatStateEffect parseInduceCombatStateEffect(Element root)
  {
    private CombatState _state;
    private float _duration;
    private LinearFunction _durationFunction;
  }

  private InstantFellowshipEffect parseInstantFellowshipEffect(Element root)
  {
    private List<EffectGenerator> _effects;
    private boolean _applyToRaidGroups;
    private boolean _applyToPets;
    private boolean _applyToTarget;
    private Float _range;

  }
  
  private InstantVitalEffect parseInstantVitalEffect(Element root)
  {
    // Morale, Power, ...
    private StatDescription _stat;
    private boolean _multiplicative;
    private VitalChangeDescription _instantChange;

  }

  private ProcEffect parseProcEffect(Element root)
  {
    PropertyMod+
    // Class 3686
    /**
     * Types of skills that may trigger effects.
     */
    private List<SkillType> _skillTypes;
    /**
     * Probability to trigger effects (0..1).
     */
    private Float _procProbability;
    /**
     * Triggered effects.
     */
    private List<EffectGenerator> _procedEffects;
    /**
     * Minimum time between triggers (s).
     */
    private Float _cooldown;

  }

  private ReactiveVitalEffect parseReactiveVitalEffect(Element root)
  {
    PropertyMod+
    // Incoming damage types
    private List<DamageType> _damageTypes;
    // Damage type override: type of damage received by the attacker (reflect), if different from source damage type
    // Usually <code>null</code>.
    private DamageType _attackerDamageTypeOverride;
    // Attacker reactive change (may be <code>null</code>).
    private ReactiveChange _attacker;
    // Defender reactive change (may be <code>null</code>).
    private ReactiveChange _defender;
    // Indicates if the effect is removed once it is triggered
    private boolean _removeOnProc;
    
  }

  private PropertyModificationEffect parsePropertyModificationEffect(Element root)
  {
    //PropertyMod+
  }

  private VitalOverTimeEffect parseVitalOverTimeEffect(Element root)
  {
    // Morale, Power, ...
    private StatDescription _stat;
    // Damage type (if harmful, null otherwise)
    private DamageType _damageType;
    // Initial change
    private VitalChangeDescription _initialChange;
    // Over-time change
    private VitalChangeDescription _overTimeChange;

  }

  private void readSharedAttributes(Element root, Effect2 effect)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants2.EFFECT_ID_ATTR,0);
    effect.setId(id);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants2.EFFECT_NAME_ATTR,null);
    effect.setName(name);
    // Description
    String description=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants2.EFFECT_DESCRIPTION_ATTR,"");
    effect.setDescription(description);
    // Description override
    String descriptionOverride=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants2.EFFECT_DESCRIPTION_OVERRIDE_ATTR,"");
    effect.setDescriptionOverride(descriptionOverride);
    // Applied description
    String appliedDescription=DOMParsingTools.getStringAttribute(attrs,EffectXMLConstants2.EFFECT_APPLIED_DESCRIPTION_ATTR,"");
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

  private void readEffectGenerator(Element generatorTag) throws SAXException
  {
    NamedNodeMap attrs=generatorTag.getAttributes();
    int effectId=DOMParsingTools.getIntAttribute(attrs,EffectXMLConstants2.EFFECT_GENERATOR_ID_ATTR,0);
    float spellcraftValue=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants2.EFFECT_GENERATOR_SPELLCRAFT_ATTR,-1);
    Float spellcraft=null;
    if (spellcraftValue>0)
    {
      spellcraft=Float.valueOf(spellcraftValue);
    }
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
      ret.add(entry);
    }
    return ret;
  }

  private void toto()
  {
    /*
    Effect effect;
    // Duration
    float duration=DOMParsingTools.getFloatAttribute(attrs,EffectXMLConstants.EFFECT_DURATION_ATTR,-1);
    if (duration>=0)
    {
      effect.setDuration(Float.valueOf(duration));
    }
    // Stats
    StatsProvider statsProvider=StatsProviderXMLParser.parseStatsProvider(root,labelsMgr);
    effect.setStatsProvider(statsProvider);
    */
  }
}
