package delta.games.lotro.character.stats;

/**
 * Slices computations.
 * @author DAM (thanks LotroPlan)
 */
public class Slice
{
  /**
   * Get morale slice.
   * @param level
   * @param sliceCount
   * @return A morale value.
   */
  public static float getMorale(int level,float sliceCount)
  {
    float ret;
    if (level<=49)
    {
      ret=((43/48)*level+53/48)*sliceCount;
    }
    else //if (50 <= SliceILvl)
    {
      ret = (3.5078741609f*level-126.88497f)*sliceCount;
    }
    return ret;
  }

  /**
   * Get finesse slice.
   * @param level Level.
   * @param sliceCount Slice count.
   * @return A finesse value. 
   */
  public static float getFinesse(int level,float sliceCount)
  {
    float ret = (11.951937108f*level+48.04765f)*sliceCount;
    return ret;
  }
}
