package delta.games.lotro.common.rewards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.lore.reputation.Faction;

/**
 * Global reputation.
 * @author DAM
 */
public class Reputation
{
  private HashMap<String,ReputationReward> _reputations;

  /**
   * Constructor.
   */
  public Reputation()
  {
    _reputations=new HashMap<String,ReputationReward>();
  }

  /**
   * Indicates if this reputation is void.
   * @return <code>true</code> if it is.
   */
  public boolean isEmpty()
  {
    boolean ret=true;
    if (_reputations.size()>0)
    {
      for(ReputationReward item : _reputations.values())
      {
        if (item.getAmount()!=0)
        {
          ret=false;
          break;
        }
      }
    }
    return ret;
  }

  /**
   * Get the reputation for a given faction.
   * @param faction Faction to use.
   * @param createIfNeeded Indicates if a reputation item shall be created
   * if it does not exist. 
   * @return A reputation item or <code>null</code>.
   */
  public ReputationReward getReputation(Faction faction, boolean createIfNeeded)
  {
    ReputationReward ret=null;
    if (faction!=null)
    {
      String name=faction.getName();
      ret=_reputations.get(name);
      if ((ret==null) && (createIfNeeded))
      {
        ret=new ReputationReward(faction);
        _reputations.put(name,ret);
      }
    }
    return ret;
  }

  /**
   * Add a reputation item.
   * @param item Reputation item to add.
   */
  public void add(ReputationReward item)
  {
    if (item!=null)
    {
      Faction faction=item.getFaction();
      ReputationReward localItem=getReputation(faction,true);
      localItem.setAmount(localItem.getAmount()+item.getAmount());
    }
  }

  /**
   * Get an array of all reputation items.
   * @return A possibly empty array or reputation items.
   */
  public ReputationReward[] getItems()
  {
    int nb=_reputations.size();
    ReputationReward[] ret=new ReputationReward[nb];
    ret=_reputations.values().toArray(ret);
    return ret;
  }

  @Override
  public String toString()
  {
    String ret="";
    if (_reputations.size()>0)
    {
      StringBuilder sb=new StringBuilder();
      List<String> factions=new ArrayList<String>(_reputations.keySet());
      Collections.sort(factions);
      for(String factionName : factions)
      {
        ReputationReward reputation=_reputations.get(factionName);
        if (reputation!=null)
        {
          sb.append(reputation);
          sb.append(EndOfLine.NATIVE_EOL);
        }
      }
      ret=sb.toString().trim();
    }
    return ret;
  }
}
