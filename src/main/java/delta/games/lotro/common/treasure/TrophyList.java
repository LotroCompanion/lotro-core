package delta.games.lotro.common.treasure;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.utils.Proxy;

/**
 * Trophy list.
 * @author DAM
 */
public class TrophyList implements Identifiable
{
  private int _id;
  private String _description;
  private Integer _imageId;
  private List<TrophyListEntry> _entries;

  /**
   * Constructor.
   * @param identifier Identifier.
   */
  public TrophyList(int identifier)
  {
    _id=identifier;
    _description=null;
    _imageId=null;
    _entries=new ArrayList<TrophyListEntry>();
  }

  @Override
  public int getIdentifier()
  {
    return _id;
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

  /**
   * Indicates if this loot table may contain the given item.
   * @param itemId Identifier of the item to search.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean contains(int itemId)
  {
    for(TrophyListEntry entry : _entries)
    {
      Proxy<Item> item=entry.getItem();
      if ((item!=null) && (item.getId()==itemId))
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

  /**
   * Dump contents.
   * @param sb Output.
   * @param level Indentation level.
   */
  public void dump(StringBuilder sb, int level)
  {
    for(int i=0;i<level;i++) sb.append('\t');
    sb.append("Trophy list ID=").append(_id).append(EndOfLine.NATIVE_EOL);
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
