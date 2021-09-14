package delta.games.lotro.lore.instances.loot;

import delta.common.utils.math.Range;

/**
 * Parameters for an instance loot table.
 * @author DAM
 */
public class InstanceLootParameters
{
  private Range _difficultyTier;
  private Range _groupSize;
  private Range _level;

  /**
   * Constructor.
   * @param difficultyTier Range of difficulty tiers.
   * @param groupSize Range of group sizes.
   * @param level Range of instance level.
   */
  public InstanceLootParameters(Range difficultyTier, Range groupSize, Range level)
  {
    _difficultyTier=difficultyTier;
    _groupSize=groupSize;
    _level=level;
  }

  /**
   * Get the range of difficulty tiers.
   * @return a range of difficulty tiers.
   */
  public Range getDifficultyTier()
  {
    return _difficultyTier;
  }

  /**
   * Get the range of group sizes.
   * @return a range of group sizes.
   */
  public Range getGroupSize()
  {
    return _groupSize;
  }

  /**
   * Get the range of instance levels.
   * @return a range of instance levels.
   */
  public Range getLevel()
  {
    return _level;
  }

  /**
   * Indicates if the given values do match this parameters.
   * @param difficulty Difficulty value.
   * @param groupSize Group size.
   * @param level Level.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean accept(int difficulty, int groupSize, int level)
  {
    return _difficultyTier.contains(difficulty) && _groupSize.contains(groupSize) && _level.contains(level);
  }

  @Override
  public String toString()
  {
    return "Parameters: difficulty="+_difficultyTier+", group size="+_groupSize+", level="+_level;
  }
}
