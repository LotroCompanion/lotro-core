package delta.games.lotro.common.treasure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.lore.items.Item;

/**
 * Trophy list.
 * @author DAM
 */
public class TrophyList extends LootTable
{
  private String _description;
  private Integer _imageId;
  private List<TrophyListEntry> _entries;

  /**
   * Constructor.
   * @param identifier Identifier.
   */
  public TrophyList(int identifier)
  {
    super(identifier);
    _description=null;
    _imageId=null;
    _entries=new ArrayList<TrophyListEntry>();
  }

  /**
   * Get the description.
   * @return a description (often <code>null</code>).
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set description.
   * @param description Description to set.
   */
  public void setDescription(String description)
  {
    _description=description;
  }

  /**
   * Get the identifier of the associated image, if any.
   * @return an image identifier or often <code>null</code.
   */
  public Integer getImageId()
  {
    return _imageId;
  }

  /**
   * Set the identifier of the associated image, if any.
   * @param imageId Identifier to set.
   */
  public void setImageId(Integer imageId)
  {
    _imageId=imageId;
  }

  /**
   * Add an entry.
   * @param entry Entry to add.
   */
  public void addEntry(TrophyListEntry entry)
  {
    _entries.add(entry);
  }

  /**
   * Get the entries.
   * @return a list of entries.
   */
  public List<TrophyListEntry> getEntries()
  {
    return _entries;
  }

  @Override
  public boolean contains(int itemId)
  {
    for(TrophyListEntry entry : _entries)
    {
      Item item=entry.getItem();
      if ((item!=null) && (item.getIdentifier()==itemId))
      {
        return true;
      }
      TreasureGroupProfile treasureGroupProfile=entry.getTreasureGroup();
      if ((treasureGroupProfile!=null) && (treasureGroupProfile.contains(itemId)))
      {
        return true;
      }
    }
    return false;
  }

  @Override
  public Set<Integer> getItemIds()
  {
    Set<Integer> ret=new HashSet<Integer>();
    for(TrophyListEntry entry : _entries)
    {
      Item item=entry.getItem();
      if (item!=null)
      {
        int itemId=item.getIdentifier();
        ret.add(Integer.valueOf(itemId));
      }
      TreasureGroupProfile treasureGroupProfile=entry.getTreasureGroup();
      if (treasureGroupProfile!=null)
      {
        ret.addAll(treasureGroupProfile.getItemIds());
      }
    }
    return ret;
  }

  @Override
  public void dump(StringBuilder sb, int level)
  {
    for(int i=0;i<level;i++) sb.append('\t');
    sb.append("Trophy list ID=").append(getIdentifier()).append(EndOfLine.NATIVE_EOL);
    if ((_description!=null) || (_imageId!=null))
    {
      sb.append("Description: ").append(_description).append(", image ID=").append(_imageId).append(EndOfLine.NATIVE_EOL);
    }
    for(TrophyListEntry entry : _entries)
    {
      entry.dump(sb,level+1);
    }
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    dump(sb,0);
    return sb.toString().trim();
  }
}
