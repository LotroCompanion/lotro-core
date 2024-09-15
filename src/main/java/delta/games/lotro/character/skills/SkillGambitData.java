package delta.games.lotro.character.skills;

import java.util.List;

import delta.games.lotro.common.enums.GambitIconType;

/**
 * Skill gambit data.
 * @author DAM
 */
public class SkillGambitData
{
  private List<GambitIconType> _required;
  private List<GambitIconType> _toAdd;
  private int _toRemove;

  /**
   * Constructor.
   */
  public SkillGambitData()
  {
    _required=null;
    _toAdd=null;
    _toRemove=0;
  }

  /**
   * Get the required gambits.
   * @return a (possibly empty) list of gambits or <code>null</code>.
   */
  public List<GambitIconType> getRequired()
  {
    return _required;
  }

  /**
   * Set the required gambits.
   * @param required the required gambits to set.
   */
  public void setRequired(List<GambitIconType> required)
  {
    _required=required;
  }

  /**
   * Get the gambits to add.
   * @return a non empty list of gambits or <code>null</code>.
   */
  public List<GambitIconType> getToAdd()
  {
    return _toAdd;
  }

  /**
   * Set the gambits to add.
   * @param toAdd the gambits to add.
   */
  public void setToAdd(List<GambitIconType> toAdd)
  {
    _toAdd=toAdd;
  }

  /**
   * Get the number of gambits to remove.
   * @return a gambits count.
   */
  public int getToRemove()
  {
    return _toRemove;
  }

  /**
   * Set the number of gambits to remove.
   * @param toRemove the number of gambits to set
   */
  public void setToRemove(int toRemove)
  {
    _toRemove=toRemove;
  }

  /**
   * Indicates if skill clears all gambits.
   * @return <code>true</code> if it does, <code>false</code> otherwise..
   */
  public boolean isClearAllGambits()
  {
    return _toRemove>=100;
  }

  /**
   * Set the 'clear all gambits' flag.
   */
  public void setClearAllGambits()
  {
    _toRemove=100;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    if (_required!=null)
    {
      if (_required.isEmpty())
      {
        sb.append(" Requires: an active Gambit");
      }
      else
      {
        sb.append(" Requires: "+_required);
      }
    }
    if (_toAdd!=null)
    {
      sb.append(" Add: "+_toAdd);
    }
    if (isClearAllGambits())
    {
      sb.append(" Clears All Gambits");
    }
    else if (getToRemove()>0)
    {
      sb.append(" Clears "+getToRemove()+" gambit(s)");
    }
    return sb.toString().trim();
  }
}
