package delta.games.lotro.character.stats.buffs;

/**
 * Formula.
 * @author DAM
 */
public interface Formula
{
  /**
   * Compute a value.
   * @param level Level.
   * @param sliceCount Slice count.
   * @return A value.
   */
  float compute(int level, float sliceCount);
}
