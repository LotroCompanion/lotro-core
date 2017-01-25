package delta.games.lotro.lore.items.essences;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.lore.items.Item;

/**
 * Set of essences.
 * @author DAM
 */
public class EssencesSet
{
  private List<Item> _essences;

  /**
   * Constructor.
   * @param size Maximum number of essences in this set.
   */
  public EssencesSet(int size)
  {
    _essences=new ArrayList<Item>();
    for(int i=0;i<size;i++)
    {
      _essences.add(null);
    }
  }

  /**
   * Get the maximum number of essences in this set.
   * @return a number of essences.
   */
  public int getSize()
  {
    return _essences.size();
  }

  /**
   * Get the essence stored at the given index.
   * @param index Index, starting at 0.
   * @return An essence or <code>null</code> if none set.
   */
  public Item getEssence(int index)
  {
    return _essences.get(index);
  }

  /**
   * Set the essence at the given index.
   * @param index Index, starting at 0.
   * @param essence Essence to set.
   */
  public void setEssence(int index, Item essence)
  {
    _essences.set(index,essence);
  }

  /**
   * Add an essence.
   * @param essence Essence to add (may be <code>null</code>).
   */
  public void addEssence(Item essence)
  {
    _essences.add(essence);
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    for(Item essence : _essences)
    {
      if (essence!=null)
      {
        sb.append(essence);
      }
      else
      {
        sb.append('-');
      }
      sb.append(EndOfLine.NATIVE_EOL);
    }
    return sb.toString().trim();
  }
}
