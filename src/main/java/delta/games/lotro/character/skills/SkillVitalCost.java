package delta.games.lotro.character.skills;

import delta.common.utils.l10n.L10n;
import delta.games.lotro.common.enums.VitalType;
import delta.games.lotro.common.properties.ModPropertyList;
import delta.games.lotro.utils.maths.Progression;

/**
 * Vital cost for skills.
 * @author DAM
 */
public class SkillVitalCost
{
  private VitalType _type;
  private boolean _consumesAll;
  private Float _percentage;
  private Float _points;
  private Progression _pointsProgression;
  private ModPropertyList _vitalMods;

  /**
   * Constructor.
   * @param type Type.
   */
  public SkillVitalCost(VitalType type)
  {
    _type=type;
    _consumesAll=false;
    _percentage=null;
    _points=null;
    _pointsProgression=null;
    _vitalMods=null;
  }

  /**
   * Get the managed vital.
   * @return A vital type.
   */
  public VitalType getVitalType()
  {
    return _type;
  }

  /**
   * Indicates if the skill consumes all the managed vital.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean isConsumesAll()
  {
    return _consumesAll;
  }

  /**
   * Set the 'consume all' flag.
   * @param consumesAll the value to set.
   */
  public void setConsumesAll(boolean consumesAll)
  {
    _consumesAll=consumesAll;
  }

  /**
   * Get the percentage of vital consumed.
   * @return A percentage (0..1) or <code>null</code>.
   */
  public Float getPercentage()
  {
    return _percentage;
  }

  /**
   * Set the percentage of vital consumed.
   * @param percentage the percentage to set (may be <code>null</code>).
   */
  public void setPercentage(Float percentage)
  {
    _percentage=percentage;
  }

  /**
   * Get the consumed points for the managed vital.
   * @return A points count or <code>null</code>.
   */
  public Float getPoints()
  {
    return _points;
  }

  /**
   * Set the consumed points.
   * @param points points count to set (may be <code>null</code>).
   */
  public void setPoints(Float points)
  {
    _points=points;
  }

  /**
   * Get the points progression.
   * @return the points progression (may be <code>null</code>).
   */
  public Progression getPointsProgression()
  {
    return _pointsProgression;
  }

  /**
   * Set the points progression.
   * @param pointsProgression the points progression to set.
   */
  public void setPointsProgression(Progression pointsProgression)
  {
    _pointsProgression=pointsProgression;
  }

  /**
   * Get the property modifiers.
   * @return the property modifiers (may be <code>null</code>).
   */
  public ModPropertyList getVitalMods()
  {
    return _vitalMods;
  }

  /**
   * Set the property modifiers.
   * @param vitalMods the modifiers to set.
   */
  public void setVitalMods(ModPropertyList vitalMods)
  {
    _vitalMods=vitalMods;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Cost: ");
    if (_consumesAll)
    {
      sb.append("(all)");
    }
    else if (_percentage!=null)
    {
      sb.append(L10n.getString(_percentage.floatValue()*100,1)).append('%');
    }
    else if (_points!=null)
    {
      sb.append(L10n.getString(_points.floatValue(),1));
    }
    else if (_pointsProgression!=null)
    {
      sb.append("progression:"+_pointsProgression.getIdentifier());
    }
    sb.append(' ');
    sb.append(_type);
    if (_vitalMods!=null)
    {
      sb.append(" (modifiers: ");
      sb.append(_vitalMods);
      sb.append(')');
    }
    return sb.toString();
  }
}
