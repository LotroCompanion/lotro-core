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
   * Maximum range (if any) (meters).
   */
  private Float _maxRange;
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
    _maxRange=null;
    _maxRangeMods=null;
  }

  /**
   * Get the shape.
   * @return the shape (may be <code>null</code>).
   */
  public Shape getShape()
  {
    return _shape;
  }

  /**
   * Set the shape.
   * @param shape the shape to set.
   */
  public void setShape(Shape shape)
  {
    _shape=shape;
  }

  /**
   * Get the detection anchor.
   * @return the detection anchor (may be <code>null</code>).
   */
  public AreaEffectAnchorType getDetectionAnchor()
  {
    return _detectionAnchor;
  }

  /**
   * Set the detection anchor.
   * @param detectionAnchor the detection anchor to set.
   */
  public void setDetectionAnchor(AreaEffectAnchorType detectionAnchor)
  {
    _detectionAnchor=detectionAnchor;
  }

  /**
   * Get the minimum range.
   * @return A range (meters) or <code>null</code>.
   */
  public Float getMinRange()
  {
    return _minRange;
  }

  /**
   * Set the minimum range.
   * @param minRange the minimum range to set (may be <code>null</code>).
   */
  public void setMinRange(Float minRange)
  {
    _minRange=minRange;
  }

  /**
   * Get the maximum range.
   * @return A range (meters) or <code>null</code>.
   */
  public Float getMaxRange()
  {
    return _maxRange;
  }

  /**
   * Set the maximum range.
   * @param maxRange the maximum range to set (may be <code>null</code>).
   */
  public void setMaxRange(Float maxRange)
  {
    _maxRange=maxRange;
  }

  /**
   * Get the maximum range modifiers.
   * @return some modifiers or <code>null</code>.
   */
  public ModPropertyList getMaxRangeMods()
  {
    return _maxRangeMods;
  }

  /**
   * Set the maximum range modifiers.
   * @param maxRangeMods the modifiers to set (may be <code>null</code>).
   */
  public void setMaxRangeMods(ModPropertyList maxRangeMods)
  {
    _maxRangeMods=maxRangeMods;
  }

  /**
   * Get the radius of the skill.
   * @return A radius (meters).
   */
  public float getRadius()
  {
    if (_shape instanceof Arc)
    {
      Arc arc=(Arc)_shape;
      return arc.getRadius();
    }
    if (_shape instanceof Sphere)
    {
      Sphere sphere=(Sphere)_shape;
      return sphere.getRadius();
    }
    return 0.0f;
  }

  /**
   * Get the range of the skill.
   * @return A range (meters).
   */
  public float getRange()
  {
    if (_detectionAnchor!=null)
    {
      int anchorCode=_detectionAnchor.getCode();
      if ((anchorCode==2) || (anchorCode==3)) // Donut or Self: AOE from self
      {
        float radius=getRadius();
        float range=radius+0.2f;
        if ((_maxRange!=null) && (_maxRange.floatValue()>range))
        {
          range=_maxRange.floatValue();
        }
        return range;
      }
    }
    return (_maxRange!=null)?_maxRange.floatValue():0;
  }

  /**
   * Indicates if this geometry has a meaningful value or not.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean hasValues()
  {
    return ((_shape!=null) || (_detectionAnchor!=null) || (_minRange!=null) || (_maxRange!=null) || (_maxRangeMods!=null));
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
    if (_maxRange!=null)
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
