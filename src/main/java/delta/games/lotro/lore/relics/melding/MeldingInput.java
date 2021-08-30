package delta.games.lotro.lore.relics.melding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.relics.CountedRelic;

/**
 * Input of a relic melding.
 * @author DAM
 */
public class MeldingInput
{
  private Map<Integer,Integer> _tier2Count;
  private List<CountedRelic> _relics;

  /**
   * Constructor.
   */
  public MeldingInput()
  {
    _tier2Count=new HashMap<Integer,Integer>();
    _relics=new ArrayList<CountedRelic>();
  }

  /**
   * Set the number of relics needed for a tier.
   * @param tier Relic tier.
   * @param count Relic count.
   */
  public void setTierCount(int tier, int count)
  {
    _tier2Count.put(Integer.valueOf(tier),Integer.valueOf(count));
  }

  /**
   * Get the list of needed tiers.
   * @return A sorted list of relic tiers.
   */
  public List<Integer> getNeededTiers()
  {
    List<Integer> ret=new ArrayList<Integer>(_tier2Count.keySet());
    Collections.sort(ret);
    return ret;
  }

  /**
   * Get the count of needed relics of a given tier.
   * @param tier Tier to use.
   * @return A count (may be 0).
   */
  public int getCountForTier(int tier)
  {
    Integer ret=_tier2Count.get(Integer.valueOf(tier));
    return (ret!=null)?ret.intValue():0;
  }

  /**
   * Add a needed relic.
   * @param relic Needed relic.
   * @param count Count.
   */
  public void addNeededRelic(Relic relic, int count)
  {
    CountedRelic newRelic=new CountedRelic(relic,count);
    _relics.add(newRelic);
  }

  /**
   * Get the list of needed relics.
   * @return A list of counted relics.
   */
  public List<CountedRelic> getNeededRelics()
  {
    return new ArrayList<CountedRelic>(_relics);
  }
}
