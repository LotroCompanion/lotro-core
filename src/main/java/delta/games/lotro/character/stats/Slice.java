package delta.games.lotro.character.stats;

/**
 * Computes stat values using level/slices based formulas.
 * @author DAM (thanks LotroPlan for all formulas)
 */
public class Slice
{
  /**
   * Get a base stat value.
   * @param level Level.
   * @param sliceCount Slice count.
   * @return A base stat value.
   */
  public static double getBaseStat(int level,float sliceCount)
  {
    double ret;
    if (level <= 10) {
      ret = Math.floor((0.291*level+0.275)*sliceCount+0.4+0.500000000001);
    } else if (level <=20) {
      ret = Math.floor((0.291*level+0.35)*sliceCount+0.25+0.500000000001);
    } else if (level <=30) {
      ret = Math.floor((0.294*level+0.4)*sliceCount+0.500000000001);
    } else if (level <=40) {
      ret = Math.floor((0.29565*level+0.45)*sliceCount-0.275+0.500000000001);
    } else if (level <=49) {
      ret = Math.floor((0.296*level+0.475)*sliceCount-0.4+0.500000000001);
    } else if (level <=59) {
      ret = Math.floor((0.3*level+1.5)*sliceCount-0.5+0.500000000001);
    } else {
      // Works:
      ret = Math.round((level-21)*(sliceCount/2)-0.49);
      // Original:
      //ret = Math.floor((0.5*level-10.5)*sliceCount-0.5+0.500000000001);
      // Equivalent to:
      //ret = Math.round(((0.5*level-10.5)*sliceCount)-0.5);
      // returns 216 instead of 217 when given 176 and 2.8
    }
    return ret;
  }

  /**
   * Get a morale value.
   * @param level Level.
   * @param sliceCount Slice count.
   * @return A morale value.
   */
  public static float getMorale(int level,float sliceCount)
  {
    float ret;
    if (level<=49)
    {
      ret=((43/48)*level+53/48)*sliceCount;
    }
    else
    {
      ret = (3.5078741609f*level-126.88497f)*sliceCount;
    }
    return ret;
  }

  /**
   * Get a power value.
   * @param level Level.
   * @param sliceCount Slice count.
   * @return A power value.
   */
  public static float getPower(int level,float sliceCount)
  {
    float ret;
    if (level<=49)
    {
      ret=((43/48)*level+53/48)*sliceCount;
    }
    else
    {
      ret = (2.9842521321f*level-101.2285f)*sliceCount;
    }
    return ret;
  }

  /**
   * Get a finesse value.
   * @param level Level.
   * @param sliceCount Slice count.
   * @return A finesse value. 
   */
  public static float getFinesse(int level,float sliceCount)
  {
    float ret = (11.951937108f*level+48.04765f)*sliceCount;
    return ret;
  }

  /**
   * Get a physical mastery value.
   * @param level Level.
   * @param sliceCount Slice count.
   * @return A physical mastery value. 
   */
  public static float getPhysicalMastery(int level,float sliceCount)
  {
    float ret = 4*level*sliceCount;
    return ret;
  }

  /**
   * Get a tactical mastery value.
   * @param level Level.
   * @param sliceCount Slice count.
   * @return A tactical mastery value. 
   */
  public static float getTacticalMastery(int level,float sliceCount)
  {
    float ret = 4*level*sliceCount;
    return ret;
  }

  /**
   * Get a critical rating value.
   * @param level Level.
   * @param sliceCount Slice count.
   * @return A critical rating value. 
   */
  public static float getCriticalRating(int level,float sliceCount)
  {
    float ret = 4*level*sliceCount;
    return ret;
  }

  /**
   * Get an incoming healing value.
   * @param level Level.
   * @param sliceCount Slice count.
   * @return An incoming healing value. 
   */
  public static float getIncomingHealing(int level,float sliceCount)
  {
    float ret = 8*level*sliceCount;
    return ret;
  }

  /**
   * Get a physical mitigation value.
   * @param level Level.
   * @param sliceCount Slice count.
   * @return A physical mitigation value. 
   */
  public static float getPhysicalMitigation(int level,float sliceCount)
  {
    float ret = (9*level+0.5f)*sliceCount;
    return ret;
  }

  /**
   * Get a tactical mitigation value.
   * @param level Level.
   * @param sliceCount Slice count.
   * @return A tactical mitigation value. 
   */
  public static float getTacticalMitigation(int level,float sliceCount)
  {
    float ret = (9*level+0.5f)*sliceCount;
    return ret;
  }

  /**
   * Get a resist rating slice.
   * @param level Level.
   * @param sliceCount Slice count.
   * @return A resist rating value. 
   */
  public static float getResist(int level,float sliceCount)
  {
    float ret = (6*level+18)*sliceCount;
    return ret;
  }

  /**
   * Get BPE rating value.
   * @param level Level.
   * @param sliceCount Slice count.
   * @return A BPE rating value. 
   */
  public static float getBPE(int level,float sliceCount)
  {
    float ret = 9*level*sliceCount;
    return ret;
  }

  /**
   * Get a critical defence value.
   * @param level Level.
   * @param sliceCount Slice count.
   * @return A critical defence value. 
   */
  public static float getCriticalDefence(int level,float sliceCount)
  {
    float ret = (10*level+10)*sliceCount;
    return ret;
  }
}
