package delta.games.lotro.common.money;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.lore.items.ItemQuality;

/**
 * Quality-based value table.
 * @author DAM
 */
public class QualityBasedValueLookupTable implements Identifiable
{
  private int _id;
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

  @Override
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * Set the identifier of this table.
   * @param id Identifier to set.
   */
  public void setIdentifier(int id)
  {
    _id=id;
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
   * Get the factor for the given quality.
   * @param quality Quality to use.
   * @return A factor or <code>null</code> if not found.
   */
  public Float getQualityFactor(ItemQuality quality)
  {
    return _qualityFactors.get(quality);
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
   * Get the supported levels.
   * @return a sorted list of levels.
   */
  public List<Integer> getLevels()
  {
    List<Integer> ret=new ArrayList<Integer>();
    ret.addAll(_baseValues.keySet());
    Collections.sort(ret);
    return ret;
  }

  /**
   * Get the base value for the given level.
   * @param level Level to use.
   * @return A raw value or <code>null</code> if not found.
   */
  public Float getBaseValue(int level)
  {
    return _baseValues.get(Integer.valueOf(level));
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
