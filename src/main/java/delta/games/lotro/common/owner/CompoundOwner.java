package delta.games.lotro.common.owner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import delta.games.lotro.common.owner.comparators.OwnerComparator;

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

  /**
   * Sort owners.
   */
  public void sortOwners()
  {
    Collections.sort(_owners,new OwnerComparator());
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

  /**
   * Get the number of owners.
   * @return a count of owners.
   */
  public int getOwnersCount()
  {
    return _owners.size();
  }

  /**
   * Get the owner at the specified index.
   * @param index Index of the owner to get.
   * @return A owner.
   */
  public Owner getOwnerAt(int index)
  {
    return _owners.get(index);
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
