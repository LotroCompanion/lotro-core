package delta.games.lotro.character.skills.geometry;

import delta.games.lotro.common.enums.AreaEffectAnchorType;
import delta.games.lotro.common.properties.ModPropertyList;

/**
 * Geometry for a skill.
 * @author dm
 */
public class SkillGeometry
{
  /**
   * Shape (box, arc or sphere ; may be <code>null</code>).
   */
  private Shape _shape;
  /**
   * Anchor type (may be <code>null</code>).
   */
  private AreaEffectAnchorType _detectionAnchor;
  /**
   * Minimum range (if any) (meters).
   */
  private Float _minRange;
  /**
   * Maximum range (meters).
   */
  private float _maxRange;
  /**
   * Maximum range modifiers.
   */
  private ModPropertyList _maxRangeMods;

  /**
   * Constructor.
   */
  public SkillGeometry()
  {
    _shape=null;
    _detectionAnchor=null;
    _minRange=null;
    _maxRange=0;
    _maxRangeMods=null;
  }

  /**
   * @return the shape
   */
  public Shape getShape()
  {
    return _shape;
  }

  /**
   * @param shape the shape to set
   */
  public void setShape(Shape shape)
  {
    _shape=shape;
  }

  /**
   * @return the detectionAnchor
   */
  public AreaEffectAnchorType getDetectionAnchor()
  {
    return _detectionAnchor;
  }

  /**
   * @param detectionAnchor the detectionAnchor to set
   */
  public void setDetectionAnchor(AreaEffectAnchorType detectionAnchor)
  {
    _detectionAnchor=detectionAnchor;
  }

  /**
   * @return the minRange
   */
  public Float getMinRange()
  {
    return _minRange;
  }

  /**
   * @param minRange the minRange to set
   */
  public void setMinRange(Float minRange)
  {
    _minRange=minRange;
  }

  /**
   * @return the maxRange
   */
  public float getMaxRange()
  {
    return _maxRange;
  }

  /**
   * @param maxRange the maxRange to set
   */
  public void setMaxRange(float maxRange)
  {
    _maxRange=maxRange;
  }

  /**
   * @return the maxRangeMods
   */
  public ModPropertyList getMaxRangeMods()
  {
    return _maxRangeMods;
  }

  /**
   * @param maxRangeMods the maxRangeMods to set
   */
  public void setMaxRangeMods(ModPropertyList maxRangeMods)
  {
    _maxRangeMods=maxRangeMods;
  }

  /**
   * Indicates if this geometry has a meaningful value or not.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean hasValues()
  {
    return ((_shape!=null) || (_detectionAnchor!=null) || (_minRange!=null) || (_maxRange>0) || (_maxRangeMods!=null));
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    if (_shape!=null)
    {
      sb.append(" Shape=").append(_shape);
    }
    if (_detectionAnchor!=null)
    {
      sb.append(" Anchor=").append(_detectionAnchor);
    }
    if (_minRange!=null)
    {
      sb.append(" MinRange=").append(_minRange);
    }
    if (_maxRange>0)
    {
      sb.append(" MaxRange=").append(_maxRange);
      if (_maxRangeMods!=null)
      {
        sb.append(" (mods=").append(_maxRangeMods).append(')');
      }
    }
    return sb.toString().trim();
  }
}
