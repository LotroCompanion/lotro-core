package delta.games.lotro.lore.reputation;

import java.util.ArrayList;
import java.util.List;

/**
 * Description of a reputation deed.
 * @author DAM
 */
public class ReputationDeed
{
  private String _name;
  private List<Faction> _factions;
  private int _lotroPoints;

  /**
   * Constructor.
   * @param name Name of the deed.
   */
  public ReputationDeed(String name)
  {
    _name=name;
    _factions=new ArrayList<Faction>();
  }

  /**
   * Get the name of this deed.
   * @return a reputation deed name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Add a faction to this deed.
   * @param faction Faction to add.
   */
  public void addFaction(Faction faction)
  {
    if (faction!=null)
    {
      _factions.add(faction);
    }
  }

  /**
   * Get the factions in this deed.
   * @return a list of factions.
   */
  public List<Faction> getFactions()
  {
    return _factions;
  }

  /**
   * Get the LOTRO points given by this deed.
   * @return A LOTRO points count.
   */
  public int getLotroPoints()
  {
    return _lotroPoints;
  }

  /**
   * Set the LOTRO points given by this deed.
   * @param lotroPoints A LOTRO points count.
   */
  public void setLotroPoints(int lotroPoints)
  {
    _lotroPoints=lotroPoints;
  }
}
