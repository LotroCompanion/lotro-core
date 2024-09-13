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
   * Shape (box, arc or sphere).
   */
  private Shape _shape;
  /**
   * Anchor type.
   */
  private AreaEffectAnchorType _detectionAnchor; // (enum) From Skill_AreaEffectDetectionAnchor
  /**
   * Minimum range (if any) (meters).
   */
  private Float _minRange; // From Skill_MinRange
  /**
   * Maximum range (meters).
   */
  private float _maxRange; // From Skill_MaxRange
  /**
   * Maximum range modifiers.
   */
  private ModPropertyList _maxRangeMods; // From Skill_MaxRange_ModifierArray

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
}
