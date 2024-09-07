package delta.games.lotro.character.skills;

/**
 * @author dm
 */
public class SkillGeometry
{
  // 1) Shape
  // Arc
  float _radius; // From Skill_AEDetectionVolume_ArcRadius
  float _degrees; // From Skill_AEDetectionVolume_ArcDegrees
  // Box
  float _length; // From Skill_AEDetectionVolume_BoxLength
  float _width; // From Skill_AEDetectionVolume_BoxWidth
  float _headingOffset; // From Skill_AEDetectionVolume_HeadingOffset (may be also for arc)
  // Sphere
  float _Sphereadius; // From Skill_AEDetectionVolume_SphereRadius

  // 2) Anchor
  private int _detectionAnchor; // (enum) From Skill_AreaEffectDetectionAnchor

  // 3) Range
  Float _minRange; // From Skill_MinRange
  Float _maxRange; // From Skill_MaxRange
  // + Mod Array // From Skill_MaxRange_ModifierArray
}
