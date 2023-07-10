package delta.games.lotro.character.stats.buffs;

import java.util.List;

import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.common.Named;

/**
 * Buff.
 * @author DAM
 */
public class Buff implements Named
{
  private String _id;
  private BuffType _type;
  private String _category;
  private String _label;
  private String _icon;
  private ClassDescription _requiredClass;
  private RaceDescription _requiredRace;
  private AbstractBuffImpl _impl;

  /**
   * Constructor.
   * @param id Buff identifier.
   * @param type Buff type.
   * @param category Category.
   * @param label Displayed label.
   */
  public Buff(String id, BuffType type, String category, String label)
  {
    _id=id;
    _type=type;
    _category=category;
    _label=label;
  }

  /**
   * Get the buff identifier.
   * @return a string identifier.
   */
  public String getId()
  {
    return _id;
  }

  /**
   * Get the buff type.
   * @return the buff type.
   */
  public BuffType getType()
  {
    return _type;
  }

  /**
   * Get the buff label.
   * @return a label.
   */
  public String getLabel()
  {
    return _label;
  }

  @Override
  public String getName()
  {
    return _label;
  }

  /**
   * Get the buff category.
   * @return the buff category.
   */
  public String getCategory()
  {
    return _category;
  }

  /**
   * Get the associated icon filename.
   * @return an icon filename.
   */
  public String getIcon()
  {
    return _icon;
  }

  /**
   * Set the icon filename.
   * @param icon filename to set.
   */
  public void setIcon(String icon)
  {
    _icon=icon;
  }

  /**
   * Get the class requirement for this buff.
   * @return A class or <code>null</code> if no class restriction.
   */
  public ClassDescription getRequiredClass()
  {
    return _requiredClass;
  }

  /**
   * Set the class requirement for this buff.
   * @param requiredClass Required class or <code>null</code>.
   */
  public void setRequiredClass(ClassDescription requiredClass)
  {
    _requiredClass=requiredClass;
  }

  /**
   * Get the race requirement for this buff.
   * @return A race or <code>null</code> if no race restriction.
   */
  public RaceDescription getRequiredRace()
  {
    return _requiredRace;
  }

  /**
   * Set the race requirement for this buff.
   * @param requiredRace Required race or <code>null</code>.
   */
  public void setRequiredRace(RaceDescription requiredRace)
  {
    _requiredRace=requiredRace;
  }

  /**
   * Get the implementation of this buff.
   * @return the implementation of this buff.
   */
  public AbstractBuffImpl getImpl()
  {
    return _impl;
  }

  /**
   * Set implementation.
   * @param impl Implementation to set.
   */
  public void setImpl(AbstractBuffImpl impl)
  {
    _impl=impl;
  }

  /**
   * Build a new instance of this buff.
   * @return A new buff instance.
   */
  public BuffInstance buildInstance()
  {
    BuffInstance buff=new BuffInstance(this);
    List<Integer> tiers=getImpl().getTiers();
    if ((tiers!=null) && (tiers.size()>0))
    {
      buff.setTier(tiers.get(0));
    }
    return buff;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder("Buff: ");
    sb.append(_id);
    sb.append(": ").append(_label);
    sb.append(" (").append(_icon).append(')');
    sb.append(" ; type=").append(_type);
    if ((_category!=null) && (_category.length()>0))
    {
      sb.append(" ; category=").append(_category);
    }
    if (_requiredRace!=null)
    {
      sb.append(" ; race=").append(_requiredRace);
    }
    if (_requiredClass!=null)
    {
      sb.append(" ; class=").append(_requiredClass);
    }
    return sb.toString();
  }
}
