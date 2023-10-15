package delta.games.lotro.lore.items.sets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.lore.items.Item;

/**
 * Set of items.
 * <p>
 * Wearing a number of items of a set may give extra bonuses (stats).
 * @author DAM
 */
public class ItemsSet implements Identifiable
{
  /**
   * Set type.
   * @author DAM
   */
  public enum SetType
  {
    /**
     * Items.
     */
    ITEMS,
    /**
     * Traceries.
     */
    TRACERIES
  }

  private int _id;
  private String _name;
  private SetType _type;
  private String _description;
  private int _setLevel;
  private boolean _useAverageItemLevelForSetLevel;
  private int _requiredMinLevel;
  private Integer _requiredMaxLevel;
  private List<Item> _members;
  private Map<Integer,SetBonus> _bonuses;

  /**
   * Constructor.
   */
  public ItemsSet()
  {
    _id=0;
    _name="";
    _type=SetType.ITEMS;
    _description="";
    _setLevel=1;
    _useAverageItemLevelForSetLevel=false;
    _requiredMinLevel=1;
    _requiredMaxLevel=null;
    _members=new ArrayList<Item>();
    _bonuses=new HashMap<Integer,SetBonus>();
  }

  /**
   * Get the identifier of this set.
   * @return An identifier.
   */
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * Set the identifier of this set.
   * @param id Identifier to set.
   */
  public void setIdentifier(int id)
  {
    _id=id;
  }

  /**
   * Get the name of this set.
   * @return A name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this set.
   * @param name Name to set.
   */
  public void setName(String name)
  {
    if (name==null)
    {
      name="";
    }
    _name=name;
  }

  /**
   * Get the set type.
   * @return a set type.
   */
  public SetType getSetType()
  {
    return _type;
  }

  /**
   * Set the set type.
   * @param type Type to set.
   */
  public void setSetType(SetType type)
  {
    _type=type;
  }

  /**
   * Get the description of this set.
   * @return A description.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the description of this set.
   * @param description Description to set.
   */
  public void setDescription(String description)
  {
    if (description==null)
    {
      description="";
    }
    _description=description;
  }

  /**
   * Get the level of this set.
   * @return A level.
   */
  public int getSetLevel()
  {
    return _setLevel;
  }

  /**
   * Set the level of this set.
   * @param level Level to set.
   */
  public void setSetLevel(int level)
  {
    _setLevel=level;
  }

  /**
   * Indicates if we shall use the average item level of set members
   * or the set item level.
   * @return <code>true</code> to use the average of item levels, <code>false</code> otherwise.
   */
  public boolean useAverageItemLevelForSetLevel()
  {
    return _useAverageItemLevelForSetLevel;
  }

  /**
   * Set the value of the 'Use average item level for set level' flag.
   * @param useAverageItemLevelForSetLevel Value to set.
   */
  public void setUseAverageItemLevelForSetLevel(boolean useAverageItemLevelForSetLevel)
  {
    _useAverageItemLevelForSetLevel=useAverageItemLevelForSetLevel;
  }

  /**
   * Get the required character level for this set.
   * @return A level>=1.
   */
  public int getRequiredMinLevel()
  {
    return _requiredMinLevel;
  }

  /**
   * Set the required character level for this set.
   * @param requiredMinLevel Required level to set.
   */
  public void setRequiredMinLevel(int requiredMinLevel)
  {
    _requiredMinLevel=requiredMinLevel;
  }

  /**
   * Get the required maximum character level for this set.
   * @return A character level or <code>null</code>.
   */
  public Integer getRequiredMaxLevel()
  {
    return _requiredMaxLevel;
  }

  /**
   * Set the required maximum character level for this set.
   * @param requiredMaxLevel Level to set.
   */
  public void setRequiredMaxLevel(Integer requiredMaxLevel)
  {
    _requiredMaxLevel=requiredMaxLevel;
  }

  /**
   * Get the members of this set.
   * @return a list of items.
   */
  public List<Item> getMembers()
  {
    return _members;
  }

  /**
   * Add a member to this set.
   * @param item Item to add.
   */
  public void addMember(Item item)
  {
    _members.add(item);
  }

  /**
   * Indicates if this set contains the give item.
   * @param itemId Item to use.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean hasMember(int itemId)
  {
    for(Item member : _members)
    {
      if (member.getIdentifier()==itemId)
      {
        return true;
      }
    }
    return false;
  }

  /**
   * Get all possible bonuses, ordered by number of pieces.
   * @return A list of bonuses.
   */
  public List<SetBonus> getBonuses()
  {
    List<Integer> nbPieces=new ArrayList<Integer>(_bonuses.keySet());
    Collections.sort(nbPieces);
    List<SetBonus> ret=new ArrayList<SetBonus>();
    for(Integer count : nbPieces)
    {
      ret.add(_bonuses.get(count));
    }
    return ret;
  }

  /**
   * Get the bonus for the given number of pieces.
   * @param nbPieces Number of pieces.
   * @return A set bonus or <code>null</code>.
   */
  public SetBonus getBonus(int nbPieces)
  {
    return _bonuses.get(Integer.valueOf(nbPieces));
  }

  /**
   * Add a bonus.
   * @param bonus Bonus to add.
   */
  public void addBonus(SetBonus bonus)
  {
    int nbPieces=bonus.getPiecesCount();
    _bonuses.put(Integer.valueOf(nbPieces),bonus);
  }

  /**
   * Dump the contents of this set of items as a string.
   * @return A readable string.
   */
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Set ID=").append(_id);
    sb.append(", name=").append(_name);
    sb.append(", type=").append(_type);
    if (_useAverageItemLevelForSetLevel)
    {
      sb.append(", use average item level for set level");
    }
    else
    {
      sb.append(", level=").append(_setLevel);
    }
    if (_requiredMinLevel!=1)
    {
      sb.append(", required character level=").append(_requiredMinLevel);
    }
    if (_requiredMaxLevel!=null)
    {
      sb.append(", max character level=").append(_requiredMaxLevel);
    }
    sb.append(EndOfLine.NATIVE_EOL);
    for(Item item : _members)
    {
      sb.append('\t').append(item);
      sb.append(EndOfLine.NATIVE_EOL);
    }
    List<SetBonus> bonuses=getBonuses();
    for(SetBonus bonus : bonuses)
    {
      int nb=bonus.getPiecesCount();
      sb.append('\t').append(nb).append(" items: ");
      StatsProvider stats=bonus.getStatsProvider();
      sb.append(stats);
      sb.append(EndOfLine.NATIVE_EOL);
    }
    return sb.toString().trim();
  }

  @Override
  public String toString()
  {
    return _id+": "+_name+" ("+_setLevel+")";
  }
}
