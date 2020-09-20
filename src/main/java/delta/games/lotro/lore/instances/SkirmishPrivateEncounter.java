package delta.games.lotro.lore.instances;

import java.util.List;

/**
 * Private encounter of type 'Skirmish/Big battle/Classic instance'.
 * @author DAM
 */
public class SkirmishPrivateEncounter extends PrivateEncounter
{
  // Rewards configuration
  // Quests to bestow (array)
  private List<Integer> _questsToBestow;
  // Difficulty tiers
  // Group sizes
  // Min/max level scale
  // Category (Defensive...) // WorldJoin_EncounterCategory
  // Type (Skirmish...) // WorldJoin_EncounterType
  // Skirmish_RestrictionDescription: #1: You must have completed the 'Helm's Dike' battle.
  private Integer _levelScaling; // Skirmish_Template_LevelScalingLevel: 100
}
