package delta.games.lotro.common.money;

import java.util.HashMap;
import java.util.Map;

import delta.games.lotro.lore.items.ItemQuality;

/**
 * Quality-based value table.
 * @author DAM
 */
public class QualityBasedValueLookupTable
{
  private Map<ItemQuality,Float> _qualityFactors;
  private Map<Integer,Float> _baseValues;

  /**
   * Constructor.
   */
  public QualityBasedValueLookupTable()
  {
    _qualityFactors=new HashMap<ItemQuality,Float>();
    _baseValues=new HashMap<Integer,Float>();
  }

  /**
   * Add a quality factor.
   * @param quality Item quality.
   * @param factor Factor to use.
   */
  public void addQualityFactor(ItemQuality quality, float factor)
  {
    _qualityFactors.put(quality,Float.valueOf(factor));
  }

  /**
   * Define the base value for a level.
   * @param level Level to use.
   * @param value Value to use.
   */
  public void addBaseValue(int level, float value)
  {
    _baseValues.put(Integer.valueOf(level),Float.valueOf(value));
  }

  /**
   * Get the value for a given quality and level.
   * @param quality Quality to use.
   * @param level Level to use.
   * @return the computed value or <code>null</code> if an error occurred.
   */
  public Integer getValue(ItemQuality quality, int level)
  {
    Integer ret=null;
    Float baseValue=_baseValues.get(Integer.valueOf(level));
    if (baseValue!=null)
    {
      Float factor=_qualityFactors.get(quality);
      float value=baseValue.floatValue()*factor.floatValue();
      ret=Integer.valueOf(Math.round(value));
    }
    return ret;
  }
}
