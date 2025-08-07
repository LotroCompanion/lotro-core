package delta.games.lotro.common.effects.io.xml;

/**
 * Constants for tags and attribute names used in XML I/O of effects.
 * @author DAM
 */
public class EffectXMLConstants
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
   * Tag 'effect', attribute 'durationMods'.
   */
  public static final String EFFECT_DURATION_MODIFIERS_ATTR="durationMods";
  /**
   * Tag 'effect', attribute 'pulseCount'.
   */
  public static final String EFFECT_PULSE_COUNT_ATTR="pulseCount";
  /**
   * Tag 'effect', attribute 'pulseCountMods'.
   */
  public static final String EFFECT_PULSE_COUNT_MODIFIERS_ATTR="pulseCountMods";
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
   * Tag 'effect', attribute 'baseFlags'.
   */
  public static final String BASE_FLAGS_ATTR="baseFlags";

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
   * Tag 'induceCombatState', attribute 'stateDurationMods'.
   */
  public static final String INDUCE_COMBAT_STATE_DURATION_MODS_ATTR="stateDurationMods";
  /**
   * Tag 'induceCombatState', attribute 'breakOnHarmfullSkill'.
   */
  public static final String INDUCE_COMBAT_STATE_BREAK_ON_HARMFULL_SKILL_ATTR="breakOnHarmfullSkill";
  /**
   * Tag 'induceCombatState', attribute 'breakOnVitalLoss'.
   */
  public static final String INDUCE_COMBAT_STATE_BREAK_ON_VITAL_LOSS_ATTR="breakOnVitalLoss";
  /**
   * Tag 'induceCombatState', attribute 'breakOnVitalLossMods'.
   */
  public static final String INDUCE_COMBAT_STATE_BREAK_ON_VITAL_LOSS_MODS_ATTR="breakOnVitalLossMods";
  /**
   * Tag 'induceCombatState', attribute 'gracePeriod'.
   */
  public static final String INDUCE_COMBAT_STATE_GRACE_PERIOD_ATTR="gracePeriod";
  /**
   * Tag 'induceCombatState', attribute 'gracePeriodMods'.
   */
  public static final String INDUCE_COMBAT_STATE_GRACE_PERIOD_MODS_ATTR="gracePeriodMods";
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
   * Tag 'fellowshipEffect', attribute 'stringOverride'.
   */
  public static final String FELLOWSHIP_EFFECT_STRING_OVERRIDE_ATTR="stringOverride";
  /**
   * Tag 'fellowshipEffect', attribute 'range'.
   */
  public static final String FELLOWSHIP_EFFECT_RANGE_ATTR="range";

  /**
   * Tag 'instantVitalEffect'.
   */
  public static final String INSTANT_VITAL_EFFECT_TAG="instantVitalEffect";
  /**
   * Tag 'instantVitalEffect'/'vitalOverTimeEffect', attribute 'stat'.
   */
  public static final String BASE_VITAL_EFFECT_STAT_ATTR="stat";
  /**
   * Tag 'instantVitalEffect'/'vitalOverTimeEffect', attribute 'damageType'.
   */
  public static final String BASE_VITAL_EFFECT_DAMAGE_TYPE_ATTR="damageType";
  /**
   * Tag 'instantVitalEffect', attribute 'multiplicative'.
   */
  public static final String INSTANT_VITAL_EFFECT_MULTIPLICATIVE_ATTR="multiplicative";
  /**
   * Tag 'instantVitalEffect', attribute 'multiplier'.
   */
  public static final String INSTANT_VITAL_EFFECT_MULTIPLIER_ATTR="multiplier";

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
   * Tag 'vitalChange'/'initialChange'/'overTimeChange', attribute 'modifiers'.
   */
  public static final String VITAL_CHANGE_MODIFIERS_ATTR="modifiers";
  /**
   * Tag 'vitalChange'/'initialChange'/'overTimeChange', attribute 'vpsMultiplier'.
   */
  public static final String VITAL_CHANGE_VPS_MULTIPLIER_ATTR="vpsMultiplier";
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
   * Proc effect generator tags, attribute 'target' (true=target/false=user).
   */
  public static final String PROC_TARGET_ATTR="target";

  /**
   * Tag 'reactiveVitalEffect'.
   */
  public static final String REACTIVE_VITAL_EFFECT_TAG="reactiveVitalEffect";
  /**
   * Tag 'reactiveVitalEffect', attribute 'damageTypes'.
   */
  public static final String REACTIVE_VITAL_DAMAGE_TYPES_ATTR="damageTypes";
  /**
   * Tag 'reactiveVitalEffect', attribute 'damageQualifiers'.
   */
  public static final String REACTIVE_VITAL_DAMAGE_QUALIFIERS_ATTR="damageQualifiers";
  /**
   * Tag 'reactiveVitalEffect', attribute 'damageTypeOverride'.
   */
  public static final String REACTIVE_VITAL_DAMAGE_TYPE_OVERRIDE_ATTR="damageTypeOverride";
  /**
   * Tag 'reactiveVitalEffect', attribute 'removeOnProc'.
   */
  public static final String REACTIVE_VITAL_REMOVE_ON_PROC_ATTR="removeOnProc";
  /**
   * Tag 'reactiveVitalEffect', attribute 'vitalTypes'.
   */
  public static final String REACTIVE_VITAL_VITAL_TYPES_ATTR="vitalTypes";
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
   * Tag 'travelEffect'.
   */
  public static final String TRAVEL_EFFECT_TAG="travelEffect";
  /**
   * Tag 'travelEffect', attribute 'sceneID'.
   */
  public static final String TRAVEL_EFFECT_SCENE_ID="sceneID";
  /**
   * Tag 'travelEffect', attribute 'privateEncounterID'.
   */
  public static final String TRAVEL_EFFECT_PRIVATE_ENCOUNTER_ID="privateEncounterID";
  /**
   * Tag 'travelEffect', attribute 'removeFromInstance'.
   */
  public static final String TRAVEL_EFFECT_REMOVE_FROM_INSTANCE="removeFromInstance";
  /**
   * Tag 'comboEffect'.
   */
  public static final String COMBO_EFFECT_TAG="comboEffect";
  /**
   * Tag 'present'.
   */
  public static final String COMBO_PRESENT_EFFECT_TAG="present";
  /**
   * Tag 'toAddIfNotPresent'.
   */
  public static final String COMBO_TO_ADD_IF_NOT_PRESENT_TAG="toAddIfNotPresent";
  /**
   * Tag 'toAddIfPresent'.
   */
  public static final String COMBO_TO_ADD_IF_PRESENT_TAG="toAddIfPresent";
  /**
   * Tag 'toGiveBackIfNotPresent'.
   */
  public static final String COMBO_TO_GIVE_BACK_IF_NOT_PRESENT_TAG="toGiveBackIfNotPresent";
  /**
   * Tag 'toGiveBackIfPresent'.
   */
  public static final String COMBO_TO_GIVE_BACK_IF_PRESENT_TAG="toGiveBackIfPresent";
  /**
   * Tag 'toExamine'.
   */
  public static final String COMBO_TO_EXAMINE_TAG="toExamine";
  /**
   * Tag 'tieredEffect'.
   */
  public static final String TIERED_EFFECT_TAG="tieredEffect";
  /**
   * Tag 'tieredEffect', attribute 'showInExamination'.
   */
  public static final String TIERED_SHOW_IN_EXAMINATION_ATTR="showInExamination";
  /**
   * Tag 'tierUp'.
   */
  public static final String TIERED_TIER_UP_TAG="tierUp";
  /**
   * Tag 'finalTier'.
   */
  public static final String TIERED_FINAL_TIER_TAG="finalTier";
  /**
   * Tag 'areaEffect'.
   */
  public static final String AREA_EFFECT_TAG="areaEffect";
  /**
   * Tag 'areaEffect', attribute 'flags'.
   */
  public static final String AREA_EFFECT_FLAGS_ATTR="flags";
  /**
   * Tag 'areaEffect', attribute 'range'.
   */
  public static final String AREA_EFFECT_RANGE_ATTR="range";
  /**
   * Tag 'areaEffect', attribute 'detectionBuffer'.
   */
  public static final String AREA_EFFECT_DETECTION_BUFFER_ATTR="detectionBuffer";
  /**
   * Tag 'areaEffect', attribute 'maxTargets'.
   */
  public static final String AREA_EFFECT_MAX_TARGETS_ATTR="maxTargets";
  /**
   * Tag 'areaEffect', attribute 'maxTargetsMods'.
   */
  public static final String AREA_EFFECT_MAX_TARGETS_MODS_ATTR="maxTargetsMods";
  /**
   * Tag 'countdownEffect'.
   */
  public static final String COUNTDOWN_EFFECT_TAG="countdownEffect";
  /**
   * Tag 'onExpire'.
   */
  public static final String ON_EXPIRE_TAG="onExpire";
  /**
   * Tag 'onRemoval'.
   */
  public static final String ON_REMOVAL_TAG="onRemoval";
  /**
   * Tag 'bubbleEffect'.
   */
  public static final String BUBBLE_EFFECT_TAG="bubbleEffect";
  /**
   * Tag 'bubbleEffect', attribute 'vital'.
   */
  public static final String BUBBLE_VITAL_ATTR="vital";
  /**
   * Tag 'bubbleEffect', attribute 'value'.
   */
  public static final String BUBBLE_VALUE_ATTR="value";
  /**
   * Tag 'bubbleEffect', attribute 'percentage'.
   */
  public static final String BUBBLE_PERCENTAGE_ATTR="percentage";
  /**
   * Tag 'bubbleEffect', attribute 'progression'.
   */
  public static final String BUBBLE_PROGRESSION_ATTR="progression";
  /**
   * Tag 'bubbleEffect', attribute 'modifiers'.
   */
  public static final String BUBBLE_MODS_ATTR="modifiers";
  /**
   * Tag 'applyOverTimeEffect'.
   */
  public static final String APPLY_OVER_TIME_EFFECT_TAG="applyOverTimeEffect";
  /**
   * Tag 'initiallyApplied'.
   */
  public static final String INITIALLY_APPLIED_TAG="initiallyApplied";
  /**
   * Tag 'applied'.
   */
  public static final String APPLIED_TAG="applied";

  /**
   * Tag 'reviveEffect'.
   */
  public static final String REVIVE_EFFECT_TAG="reviveEffect";
  /**
   * Tag 'reviveVital'.
   */
  public static final String REVIVE_VITAL_TAG="reviveVital";
  /**
   * Tag 'reviveVital', attribute 'vital'.
   */
  public static final String REVIVE_VITAL_ATTR="vital";
  /**
   * Tag 'reviveVital', attribute 'percentage'.
   */
  public static final String REVIVE_PERCENTAGE_ATTR="percentage";
  /**
   * Tag 'reviveVital', attribute 'modifiers'.
   */
  public static final String REVIVE_MODIFIERS_ATTR="modifiers";

  /**
   * Tag 'pipEffect'.
   */
  public static final String PIP_EFFECT_TAG="pipEffect";
  /**
   * Tag 'pipEffect', attribute 'type'.
   */
  public static final String PIP_TYPE_ATTR="type";
  /**
   * Tag 'pipEffect', attribute 'adjustmentType'.
   */
  public static final String PIP_ADJUSTMENT_TYPE_ATTR="adjustmentType";
  /**
   * Tag 'pipEffect', attribute 'reset'.
   */
  public static final String PIP_RESET_ATTR="reset";
  /**
   * Tag 'pipEffect', attribute 'amount'.
   */
  public static final String PIP_AMOUNT_ATTR="amount";
  /**
   * Tag 'pipEffect', attribute 'amountMods'.
   */
  public static final String PIP_AMOUNT_MODIFIERS_ATTR="amountMods";

  /**
   * Tag 'auraEffect'.
   */
  public static final String AURA_EFFECT_TAG="auraEffect";
  /**
   * Tag 'auraEffect', attribute 'type'.
   */
  public static final String AURA_EFFECT_TYPE_ATTR="type";
  /**
   * Tag 'auraEffect', attribute 'shouldAffectCaster'.
   */
  public static final String AURA_SHOULD_AFFECT_CASTER_ATTR="shouldAffectCaster";
  /**
   * Tag 'dispelEffect'.
   */
  public static final String DISPEL_EFFECT_TAG="dispelEffect";
  /**
   * Tag 'dispelEffect', attribute 'dispelCasters'.
   */
  public static final String DISPEL_EFFECT_DISPEL_CASTERS_ATTR="dispelCasters";
  /**
   * Tag 'randomEffect'.
   */
  public static final String RANDOM_EFFECT_TAG="randomEffect";
  /**
   * Tag 'effect' (generator), attribute 'weight'.
   */
  public static final String EFFECT_GENERATOR_WEIGHT_ATTR="weight";
  /**
   * Tag 'effect' (generator), attribute 'doCaster'.
   */
  public static final String EFFECT_GENERATOR_TO_CASTER_ATTR="doCaster";
  /**
   * Tag 'flagEffect'.
   */
  public static final String FLAG_EFFECT_TAG="flagEffect";
  /**
   * Tag 'aiPetEffect'.
   */
  public static final String AI_PET_EFFECT_TAG="aiPetEffect";
  /**
   * Tag 'applyToMasterEffect' (generator).
   */
  public static final String APPLY_TO_MASTER_EFFECT_GENERATOR_TAG="applyToMasterEffect";
  /**
   * Tag 'cooldownEffect'.
   */
  public static final String COOLDOWN_EFFECT_TAG="cooldownEffect";
  /**
   * Tag 'cooldownEffect', attribute "durationModifiers".
   */
  public static final String COOLDOWN_EFFECT_DURATION_MODIFIERS_ATTR="durationModifiers";
  /**
   * Tag 'skill' (from cooldown effect).
   */
  public static final String SKILL_TAG="skill";
  /**
   * Tag 'skill', attribute 'id'.
   */
  public static final String SKILL_ID_ATTR="id";
  /**
   * Tag 'skill', attribute 'name'.
   */
  public static final String SKILL_NAME_ATTR="name";
  /**
   * Tag 'cooldownChannel' (from cooldown effect).
   */
  public static final String COOLDOWN_CHANNEL_TAG="cooldownChannel";
  /**
   * Tag 'cooldownChannel', attribute 'code'.
   */
  public static final String COOLDOWN_CHANNEL_CODE_ATTR="code";
  /**
   * Tag 'cooldownChannel', attribute 'name'.
   */
  public static final String COOLDOWN_CHANNEL_NAME_ATTR="name";

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
