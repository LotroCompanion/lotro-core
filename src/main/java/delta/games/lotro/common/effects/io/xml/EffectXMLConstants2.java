package delta.games.lotro.common.effects.io.xml;

/**
 * Constants for tags and attribute names used in XML I/O of effects.
 * @author DAM
 */
public class EffectXMLConstants2
{
  /**
   * Tag 'effects'.
   */
  public static final String EFFECTS_TAG="effects";
  /**
   * Tag 'effect'.
   */
  public static final String EFFECT_TAG="effect";
  /**
   * Tag 'effect', attribute 'id'.
   */
  public static final String EFFECT_ID_ATTR="id";
  /**
   * Tag 'effect', attribute 'name'.
   */
  public static final String EFFECT_NAME_ATTR="name";
  /**
   * Tag 'effect', attribute 'description'.
   */
  public static final String EFFECT_DESCRIPTION_ATTR="description";
  /**
   * Tag 'effect', attribute 'descriptionOverride'.
   */
  public static final String EFFECT_DESCRIPTION_OVERRIDE_ATTR="descriptionOverride";
  /**
   * Tag 'effect', attribute 'appliedDescription'.
   */
  public static final String EFFECT_APPLIED_DESCRIPTION_ATTR="appliedDescription";
  /**
   * Tag 'effect', attribute 'iconId'.
   */
  public static final String EFFECT_ICON_ID_ATTR="iconId";

  /**
   * Tag 'effect', attribute 'duration'.
   */
  public static final String EFFECT_DURATION_ATTR="duration";
  /**
   * Tag 'effect', attribute 'pulseCount'.
   */
  public static final String EFFECT_PULSE_COUNT_ATTR="pulseCount";
  /**
   * Tag 'effect', attribute 'expiresInRealTime'.
   */
  public static final String EFFECT_EXPIRES_IN_REAL_TIME_ATTR="expiresInRealTime";
  /**
   * Tag 'effect', attribute 'probability'.
   */
  public static final String EFFECT_PROBABILITY_ATTR="probability";
  /**
   * Tag 'effect', attribute 'probabilityVariance'.
   */
  public static final String EFFECT_PROBABILITY_VARIANCE_ATTR="probabilityVariance";
  /**
   * Tag 'effect', attribute 'probabilityModProperty'.
   */
  public static final String EFFECT_PROBABILITY_MOD_PROPERTY_ATTR="probabilityModProperty";

  /**
   * Tag 'dispelByResist'.
   */
  public static final String DISPEL_BY_RESIST_TAG="dispelByResist";
  /**
   * Tag 'dispelByResist', attribute 'maxDispelCount'.
   */
  public static final String DISPEL_BY_RESIST_MAX_DISPELCOUNT_ATTR="maxDispelCount";
  /**
   * Tag 'dispelByResist', attribute 'resistCategories'.
   */
  public static final String DISPEL_BY_RESIST_CATEGORIES_ATTR="resistCategories";
  /**
   * Tag 'dispelByResist', attribute 'useStrengthRestriction'.
   */
  public static final String DISPEL_BY_RESIST_USE_STRENGTH_RESTRICTION_ATTR="useStrengthRestriction";
  /**
   * Tag 'dispelByResist', attribute 'strengthOffset'.
   */
  public static final String DISPEL_BY_RESIST_STRENGTH_OFFSET_ATTR="strengthOffset";

  /**
   * Tag 'genesis'.
   */
  public static final String GENESIS_TAG="genesis";
  /**
   * Tag 'genesis', attribute 'summonDuration'.
   */
  public static final String GENESIS_SUMMON_DURATION_ATTR="summonDuration";
  /**
   * Tag 'genesis', attribute 'permanent'.
   */
  public static final String GENESIS_PERMANENT_ATTR="permanent";

  /**
   * Tag 'hotspot'.
   */
  public static final String HOTSPOT_TAG="hotspot";
  /**
   * Tag 'hotspot', attribute 'id'.
   */
  public static final String HOTSPOT_ID_ATTR="id";
  /**
   * Tag 'hotspot', attribute 'name'.
   */
  public static final String HOTSPOT_NAME_ATTR="name";
  /**
   * Tag 'summoned'.
   */
  public static final String SUMMONED_TAG="summoned";
  /**
   * Tag 'summoned', attribute 'id'.
   */
  public static final String SUMMONED_ID_ATTR="id";
  /**
   * Tag 'summoned', attribute 'name'.
   */
  public static final String SUMMONED_NAME_ATTR="name";

  /**
   * Tag 'induceCombatState'.
   */
  public static final String INDUCE_COMBAT_STATE_TAG="induceCombatState";
  /**
   * Tag 'induceCombatState', attribute 'state'.
   */
  public static final String INDUCE_COMBAT_STATE_STATE_ATTR="state";
  /**
   * Tag 'induceCombatState', attribute 'stateDuration'.
   */
  public static final String INDUCE_COMBAT_STATE_DURATION_ATTR="stateDuration";

  /**
   * Tag 'fellowshipEffect'.
   */
  public static final String FELLOWSHIP_EFFECT_TAG="fellowshipEffect";
  /**
   * Tag 'fellowshipEffect', attribute 'applyToRaidGroups'.
   */
  public static final String FELLOWSHIP_EFFECT_APPLY_TO_RAID_GROUPS_ATTR="applyToRaidGroups";
  /**
   * Tag 'fellowshipEffect', attribute 'applyToPets'.
   */
  public static final String FELLOWSHIP_EFFECT_APPLY_TO_PETS_ATTR="applyToPets";
  /**
   * Tag 'fellowshipEffect', attribute 'applyToTarget'.
   */
  public static final String FELLOWSHIP_EFFECT_APPLY_TARGET_ATTR="applyToTarget";
  /**
   * Tag 'fellowshipEffect', attribute 'range'.
   */
  public static final String FELLOWSHIP_EFFECT_RANGE_ATTR="range";

  /**
   * Tag 'instantVitalEffect'.
   */
  public static final String INSTANT_VITAL_EFFECT_TAG="instantVitalEffect";
  /**
   * Tag 'instantVitalEffect', attribute 'stat'.
   */
  public static final String INSTANT_VITAL_EFFECT_STAT_ATTR="stat";
  /**
   * Tag 'instantVitalEffect', attribute 'multiplicative'.
   */
  public static final String INSTANT_VITAL_EFFECT_MULTIPLICATIVE_ATTR="multiplicative";

  /**
   * Tag 'vitalChange'.
   */
  public static final String VITAL_CHANGE_TAG="vitalChange";
  /**
   * Tag 'vitalChange'/'attacker'/'defender'/'initialChange'/'overTimeChange', attribute 'constant'.
   */
  public static final String VITAL_CHANGE_CONSTANT_ATTR="constant";
  /**
   * Tag 'vitalChange'/'attacker'/'defender'/'initialChange'/'overTimeChange', attribute 'progressionID'.
   */
  public static final String VITAL_CHANGE_PROGRESSION_ID_ATTR="progressionID";
  /**
   * Tag 'vitalChange'/'attacker'/'defender'/'initialChange'/'overTimeChange', attribute 'variance'.
   */
  public static final String VITAL_CHANGE_VARIANCE_ATTR="variance";
  /**
   * Tag 'vitalChange'/'initialChange'/'overTimeChange', attribute 'min'.
   */
  public static final String VITAL_CHANGE_MIN_ATTR="min";
  /**
   * Tag 'vitalChange'/'initialChange'/'overTimeChange', attribute 'max'.
   */
  public static final String VITAL_CHANGE_MAX_ATTR="max";

  /**
   * Tag 'propertyModEffect'.
   */
  public static final String PROPERTY_MOD_EFFECT_TAG="propertyModEffect";

  /**
   * Tag 'procEffect'.
   */
  public static final String PROC_TAG="procEffect";
  /**
   * Tag 'procEffect', attribute 'skillTypes'.
   */
  public static final String PROC_SKILL_TYPES_ATTR="skillTypes";
  /**
   * Tag 'procEffect', attribute 'procProbability'.
   */
  public static final String PROC_PROBABILITY_ATTR="procProbability";
  /**
   * Tag 'procEffect', attribute 'cooldown'.
   */
  public static final String PROC_COOLDOWN_ATTR="cooldown";

  /**
   * Tag 'reactiveVitalEffect'.
   */
  public static final String REACTIVE_VITAL_EFFECT_TAG="reactiveVitalEffect";
  /**
   * Tag 'reactiveVitalEffect', attribute 'damageTypes'.
   */
  public static final String REACTIVE_VITAL_DAMAGE_TYPES_ATTR="damageTypes";
  /**
   * Tag 'reactiveVitalEffect', attribute 'damageTypes'.
   */
  public static final String REACTIVE_VITAL_DAMAGE_TYPE_OVERRIDE_ATTR="damageTypeOverride";
  /**
   * Tag 'reactiveVitalEffect', attribute 'removeOnProc'.
   */
  public static final String REACTIVE_VITAL_REMOVE_ON_PROC_ATTR="removeOnProc";
  /**
   * Tag 'attacker'.
   */
  public static final String ATTACKER_TAG="attacker";
  /**
   * Tag 'defender'.
   */
  public static final String DEFENDER_TAG="defender";
  /**
   * Tag 'reactiveVital'.
   */
  public static final String REACTIVE_VITAL_TAG="reactiveVital";
  /**
   * Tag 'reactiveVital', attribute 'probability'.
   */
  public static final String REACTIVE_VITAL_PROBABILITY_ATTR="probability";
  /**
   * Tag 'reactiveVital', attribute 'multiplicative'.
   */
  public static final String REACTIVE_VITAL_MULTIPLICATIVE_ATTR="multiplicative";
  /**
   * Tag 'effect'.
   */
  public static final String REACTIVE_EFFECT_TAG="effect";
  /**
   * Tag 'effect', attribute 'id'.
   */
  public static final String REACTIVE_EFFECT_ID_ATTR="id";
  /**
   * Tag 'effect', attribute 'name'.
   */
  public static final String REACTIVE_EFFECT_NAME_ATTR="name";
  /**
   * Tag 'effect', attribute 'probability'.
   */
  public static final String REACTIVE_EFFECT_PROBABILITY_ATTR="probability";

  /**
   * Tag 'vitalOverTimeEffect'.
   */
  public static final String VITAL_OVER_TIME_EFFECT_TAG="vitalOverTimeEffect";
  /**
   * Tag 'vitalOverTimeEffect', attribute 'stat'.
   */
  public static final String VITAL_OVER_TIME_EFFECT_STAT_ATTR="stat";
  /**
   * Tag 'vitalOverTimeEffect', attribute 'stat'.
   */
  public static final String VITAL_OVER_TIME_EFFECT_DAMAGE_TYPE_ATTR="damageType";
  /**
   * Tag 'initialChange'.
   */
  public static final String INITIAL_CHANGE_TAG="initialChange";
  /**
   * Tag 'overTimeChange'.
   */
  public static final String OVER_TIME_CHANGE_TAG="overTimeChange";
  /**
   * Tag 'recallEffect'.
   */
  public static final String RECALL_EFFECT_TAG="recallEffect";

  /**
   * Tag 'effect' (generator).
   */
  public static final String EFFECT_GENERATOR_TAG="effect";
  /**
   * Tag 'effect' (generator), attribute 'id'.
   */
  public static final String EFFECT_GENERATOR_ID_ATTR="id";
  /**
   * Tag 'effect' (generator), attribute 'name'.
   */
  public static final String EFFECT_GENERATOR_NAME_ATTR="name";
  /**
   * Tag 'effect' (generator), attribute 'spellcraft'.
   */
  public static final String EFFECT_GENERATOR_SPELLCRAFT_ATTR="spellcraft";

  /**
   * Tag 'function'.
   */
  public static final String FUNCTION_TAG="function";
  /**
   * Tag 'function', attribute 'minX'.
   */
  public static final String FUNCTION_MIN_X_ATTR="minX";
  /**
   * Tag 'function', attribute 'maxX'.
   */
  public static final String FUNCTION_MAX_X_ATTR="maxX";
  /**
   * Tag 'function', attribute 'minY'.
   */
  public static final String FUNCTION_MIN_Y_ATTR="minY";
  /**
   * Tag 'function', attribute 'maxY'.
   */
  public static final String FUNCTION_MAX_Y_ATTR="maxY";
}
