package delta.games.lotro.common.effects;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;

/**
 * Effect.
 * @author DAM
 */
public class Effect2 implements Identifiable,Named
{
  // Identifier
  private int _id;
  // Name
  private String _name;
  private String _description;
  private String _descriptionOverride;
  private String _appliedDescription;
  private ApplicationProbability _prob;
  private EffectDuration _duration;
  private EffectCriticalChance _critChance;
  // Icon
  private Integer _iconId;
  // Aspects
  private List<EffectAspect> _aspects;

  /**
   * Constructor.
   */
  public Effect2()
  {
    _description="";
    _descriptionOverride="";
    _appliedDescription="";
    _aspects=new ArrayList<EffectAspect>();
  }

  /**
   * Get the identifier of this effect.
   * @return an identifier.
   */
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * Set the identifier of this effect.
   * @param id the identifier to set.
   */
  public void setId(int id)
  {
    _id=id;
  }

  /**
   * Get the effect name.
   * @return a name or <code>null</code>.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the effect name.
   * @param name Name to set (may be <code>null</code>).
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the effect description.
   * @return a description (may be empty but not <code>null</code>).
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the effect description.
   * @param description Description to set.
   */
  public void setDescription(String description)
  {
    if (description==null)
    {
      description="";
    }
    _description=description;
  }

  /**
   * Get the effect description override.
   * @return a description (may be empty but not <code>null</code>).
   */
  public String getDescriptionOverride()
  {
    return _descriptionOverride;
  }

  /**
   * Set the effect description override.
   * @param descriptionOverride Description to set.
   */
  public void setDescriptionOverride(String descriptionOverride)
  {
    if (descriptionOverride==null)
    {
      descriptionOverride="";
    }
    _descriptionOverride=descriptionOverride;
  }

  /**
   * Get the effect 'applied' description.
   * @return a description (may be empty but not <code>null</code>).
   */
  public String getAppliedDescription()
  {
    return _appliedDescription;
  }

  /**
   * Set the effect 'applied' description.
   * @param appliedDescription Description to set.
   */
  public void setAppliedDescription(String appliedDescription)
  {
    if (appliedDescription==null)
    {
      appliedDescription="";
    }
    _appliedDescription=appliedDescription;
  }

  /**
   * Get the application probability.
   * @return the application probability.
   */
  public ApplicationProbability getApplicationProbability()
  {
    return _prob;
  }

  /**
   * Set the application probability.
   * @param probability Probability to set.
   */
  public void setApplicationProbability(ApplicationProbability probability)
  {
    _prob=probability;
  }

  /**
   * Get the effect duration.
   * @return the effect duration.
   */
  public EffectDuration getDuration()
  {
    return _duration;
  }

  /**
   * Set the effect duration.
   * @param duration Duration to set.
   */
  public void setApplicationProbability(EffectDuration duration)
  {
    _duration=duration;
  }

  /**
   * Get the icon ID.
   * @return an icon ID or <code>null</code> if none.
   */
  public Integer getIconId()
  {
    return _iconId;
  }

  /**
   * Set the icon ID.
   * @param iconId Icon ID to set.
   */
  public void setIconId(Integer iconId)
  {
    _iconId=iconId;
  }

  /**
   * Add an aspect.
   * @param aspect Aspect to add.
   */
  public void addAspect(EffectAspect aspect)
  {
    _aspects.add(aspect);
  }

  /**
   * Get the aspects.
   * @return a list of aspects.
   */
  public List<EffectAspect> getAspects()
  {
    return _aspects;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Effect: ID=").append(_id);
    if (_name!=null)
    {
      sb.append(", (name=").append(_name).append(')');
    }
    if (_iconId!=null)
    {
      sb.append(", (icon=").append(_iconId).append(')');
    }
    return sb.toString();
  }
}
