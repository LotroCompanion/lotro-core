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
import delta.games.lotro.utils.Proxy;

/**
 * Set of items.
 * <p>
 * Wearing a number of items of a set may give extra bonuses (stats).
 * @author DAM
 */
public class ItemsSet implements Identifiable
{
  private int _id;
  private String _name;
  private String _description;
  private int _level;
  private int _requiredLevel;
  private List<Proxy<Item>> _members;
  private Map<Integer,ItemsSetBonus> _bonuses;

  /**
   * Constructor.
   */
  public ItemsSet()
  {
    _id=0;
    _name="";
    _description="";
    _level=1;
    _requiredLevel=1;
    _members=new ArrayList<Proxy<Item>>();
    _bonuses=new HashMap<Integer,ItemsSetBonus>();
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
  public int getLevel()
  {
    return _level;
  }

  /**
   * Set the level of this set.
   * @param level Level to set.
   */
  public void setLevel(int level)
  {
    _level=level;
  }

  /**
   * Get the required level for this set.
   * @return A level.
   */
  public int getRequiredLevel()
  {
    return _requiredLevel;
  }

  /**
   * Set the required level for this set.
   * @param requiredLevel Required level to set.
   */
  public void setRequiredLevel(int requiredLevel)
  {
    _requiredLevel=requiredLevel;
  }

  /**
   * Get the members of this set.
   * @return a list of item proxies.
   */
  public List<Proxy<Item>> getMembers()
  {
    return _members;
  }

  /**
   * Add a member to this set.
   * @param proxy Item proxy to add.
   */
  public void addMember(Proxy<Item> proxy)
  {
    _members.add(proxy);
  }

  /**
   * Indicates if this set contains the give item.
   * @param itemId Item to use.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean hasMember(int itemId)
  {
    for(Proxy<Item> member : _members)
    {
      if (member.getId()==itemId)
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
  public List<ItemsSetBonus> getBonuses()
  {
    List<Integer> nbPieces=new ArrayList<Integer>(_bonuses.keySet());
    Collections.sort(nbPieces);
    List<ItemsSetBonus> ret=new ArrayList<ItemsSetBonus>();
    for(Integer count : nbPieces)
    {
      ret.add(_bonuses.get(count));
    }
    return ret;
  }

  /**
   * Add a bonus.
   * @param bonus Bonus to add.
   */
  public void addBonus(ItemsSetBonus bonus)
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
    sb.append(", level=").append(_level);
    if (_requiredLevel!=1)
    {
      sb.append(", required level=").append(_requiredLevel);
    }
    sb.append(EndOfLine.NATIVE_EOL);
    for(Proxy<Item> itemProxy : _members)
    {
      sb.append('\t').append(itemProxy);
      sb.append(EndOfLine.NATIVE_EOL);
    }
    List<ItemsSetBonus> bonuses=getBonuses();
    for(ItemsSetBonus bonus : bonuses)
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
    return _name;
  }
}
