package delta.games.lotro.lore.worldEvents.filter;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.lore.worldEvents.AbstractWorldEventCondition;
import delta.games.lotro.lore.worldEvents.CompoundWorldEventCondition;
import delta.games.lotro.lore.worldEvents.SimpleWorldEventCondition;

/**
 * Filter on world event conditions, using the label of the condition.
 * @author DAM
 */
public class WorldEventConditionFilter implements Filter<AbstractWorldEventCondition>
{
  private String _label;

  @Override
  public boolean accept(AbstractWorldEventCondition item)
  {
    if (_label==null)
    {
      return true;
    }
    if (item instanceof CompoundWorldEventCondition)
    {
      CompoundWorldEventCondition compound=(CompoundWorldEventCondition)item;
      for(AbstractWorldEventCondition child : compound.getItems())
      {
        if (accept(child))
        {
          return true;
        }
      }
    }
    else if (item instanceof SimpleWorldEventCondition)
    {
      SimpleWorldEventCondition simple=(SimpleWorldEventCondition)item;
      return _label.equals(simple.getLabel());
    }
    return false;
  }

  /**
   * Get the managed label.
   * @return the managed label.
   */
  public String getLabel()
  {
    return _label;
  }

  /**
   * Set the managed label.
   * @param label Label to use.
   */
  public void setLabel(String label)
  {
    _label=label;
  }
}
