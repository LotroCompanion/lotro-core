package delta.games.lotro.common.owner;

import java.util.ArrayList;
import java.util.List;

/**
 * Compound storage owner.
 * @author DAM
 */
public class CompoundOwner extends Owner
{
  private List<Owner> _owners;

  /**
   * Constructor.
   */
  public CompoundOwner()
  {
    _owners=new ArrayList<Owner>();
  }

  /**
   * Add a owner.
   * @param owner Owner to add.
   */
  public void add(Owner owner)
  {
    if (!_owners.contains(owner))
    {
      _owners.add(owner);
    }
  }

  @Override
  public String getLabel()
  {
    StringBuilder sb=new StringBuilder();
    for(Owner owner : _owners)
    {
      if (sb.length()>0)
      {
        sb.append(" / ");
      }
      sb.append(owner.getLabel());
    }
    return sb.toString();
  }

  @Override
  public boolean equals(Object object)
  {
    if (this == object)
    {
      return true;
    }
    if (!(object instanceof CompoundOwner))
    {
      return false;
    }
    CompoundOwner other=(CompoundOwner)object;
    int nbItems=_owners.size();
    if (nbItems != other._owners.size())
    {
      return false;
    }
    for(Owner owner : _owners)
    {
      if (!other._owners.contains(owner))
      {
        return false;
      }
    }
    return true;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    for(Owner owner : _owners)
    {
      result = prime * result + owner.hashCode();
    }
    return result;
  }
}
