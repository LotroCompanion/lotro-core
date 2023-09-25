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
  private List<Essence> _essences;

  /**
   * Constructor.
   * @param size Maximum number of essences in this set.
   */
  public EssencesSet(int size)
  {
    _essences=new ArrayList<Essence>();
    for(int i=0;i<size;i++)
    {
      _essences.add(null);
    }
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public EssencesSet(EssencesSet source)
  {
    _essences=new ArrayList<Essence>(source._essences);
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
  public Essence getEssence(int index)
  {
    if (index<_essences.size())
    {
      return _essences.get(index);
    }
    return null;
  }

  /**
   * Set the essence at the given index.
   * @param index Index, starting at 0.
   * @param essence Essence to set.
   */
  public void setEssence(int index, Essence essence)
  {
    if ((index>=0) && (index<_essences.size()))
    {
      _essences.set(index,essence);
    }
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Essences:").append(EndOfLine.NATIVE_EOL);
    for(Item essence : _essences)
    {
      sb.append('\t');
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
