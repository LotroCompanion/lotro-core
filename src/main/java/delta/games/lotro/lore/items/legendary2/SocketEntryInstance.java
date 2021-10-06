package delta.games.lotro.lore.items.legendary2;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.lore.items.Item;

/**
 * Socket entry instance.
 * @author DAM
 */
public class SocketEntryInstance
{
  private SocketEntry _template;
  private Tracery _tracery;
  private int _itemLevel;

  /**
   * Constructor.
   * @param template Entry template.
   */
  public SocketEntryInstance(SocketEntry template)
  {
    _template=template;
    _tracery=null;
    _itemLevel=0;
  }

  /**
   * Copy constructor.
   * @param source Source instance.
   */
  public SocketEntryInstance(SocketEntryInstance source)
  {
    _template=source._template;
    _tracery=source._tracery;
    _itemLevel=source._itemLevel;
  }

  /**
   * Get the associated socket entry template.
   * @return a socket entry.
   */
  public SocketEntry getTemplate()
  {
    return _template;
  }

  /**
   * Get the slotted tracery.
   * @return A tracery or <code>null</code>.
   */
  public Tracery getTracery()
  {
    return _tracery;
  }

  /**
   * Set the slotted tracery.
   * @param tracery the tracery to set.
   */
  public void setTracery(Tracery tracery)
  {
    _tracery=tracery;
  }

  /**
   * Get the item level.
   * @return an item level.
   */
  public int getItemLevel()
  {
    return _itemLevel;
  }

  /**
   * Set the item level.
   * @param itemLevel the item level to set.
   */
  public void setItemLevel(int itemLevel)
  {
    _itemLevel=itemLevel;
  }

  /**
   * Get the stats for this socket entry.
   * @return some stats.
   */
  public BasicStatsSet getStats()
  {
    BasicStatsSet ret=null;
    if (_tracery!=null)
    {
      Item item=_tracery.getItem();
      StatsProvider statsProvider=item.getStatsProvider();
      if (statsProvider!=null)
      {
        ret=statsProvider.getStats(1,_itemLevel);
      }
    }
    if (ret==null)
    {
      ret=new BasicStatsSet();
    }
    return ret;
  }

  /**
   * Dump the contents of this socket entry as a string.
   * @return A readable string.
   */
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    if (_tracery!=null)
    {
      String name=_tracery.getName();
      sb.append(name).append(" (").append(_itemLevel).append(')').append(EndOfLine.NATIVE_EOL);
      BasicStatsSet stats=getStats();
      sb.append(stats).append(EndOfLine.NATIVE_EOL);
    }
    else
    {
      String type=_template.getType().getLabel();
      sb.append(type).append(EndOfLine.NATIVE_EOL);
    }
    return sb.toString().trim();
  }

  @Override
  public String toString()
  {
    return dump();
  }
}
