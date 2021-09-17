package delta.games.lotro.lore.agents.mobs;

import delta.games.lotro.common.treasure.TreasureList;
import delta.games.lotro.common.treasure.TrophyList;

/**
 * @author dmorcellet
 */
public class MobLoot
{
  private TrophyList _barterTrophy;
  private boolean _generatesTrophy;
  private TrophyList _reputationTrophy;
  private TreasureList _treasureListOverride;
  private TrophyList _trophyListOverride;
  private boolean _isRemoteLootable;

  /**
   * Constructor.
   */
  public MobLoot()
  {
    _barterTrophy=null;
    _generatesTrophy=false;
    _reputationTrophy=null;
    _treasureListOverride=null;
    _trophyListOverride=null;
    _isRemoteLootable=true;
  }

  /**
   * Get the barter trophy. 
   * @return a barter trophy or <code>null</code>.
   */
  public TrophyList getBarterTrophy()
  {
    return _barterTrophy;
  }

  /**
   * Set the barter trophy.
   * @param barterTrophy the barter trophy to set.
   */
  public void setBarterTrophy(TrophyList barterTrophy)
  {
    _barterTrophy=barterTrophy;
  }

  /**
   * Get the 'generates trophy' flag.
   * @return a boolean value.
   */
  public boolean isGeneratesTrophy()
  {
    return _generatesTrophy;
  }

  /**
   * Set the 'generates trophy' flag.
   * @param generatesTrophy the value to set.
   */
  public void setGeneratesTrophy(boolean generatesTrophy)
  {
    _generatesTrophy=generatesTrophy;
  }

  /**
   * Get the reputation trophy.
   * @return a reputation trophy or <code>null</code>.
   */
  public TrophyList getReputationTrophy()
  {
    return _reputationTrophy;
  }

  /**
   * Set the reputation trophy.
   * @param reputationTrophy the reputation trophy to set.
   */
  public void setReputationTrophy(TrophyList reputationTrophy)
  {
    _reputationTrophy=reputationTrophy;
  }

  /**
   * Get the treasure list override.
   * @return a treasure list or <code>null</code>.
   */
  public TreasureList getTreasureListOverride()
  {
    return _treasureListOverride;
  }

  /**
   * Set the treasure list override.
   * @param treasureListOverride the treasure list override to set.
   */
  public void setTreasureListOverride(TreasureList treasureListOverride)
  {
    _treasureListOverride=treasureListOverride;
  }

  /**
   * Get the trophy list override.
   * @return a trophyListOverride or <code>null</code>.
   */
  public TrophyList getTrophyListOverride()
  {
    return _trophyListOverride;
  }

  /**
   * Set the trophy list override.
   * @param trophyListOverride the trophy list override to set.
   */
  public void setTrophyListOverride(TrophyList trophyListOverride)
  {
    _trophyListOverride=trophyListOverride;
  }

  /**
   * Get the 'remote lootable' flag.
   * @return a boolean value.
   */
  public boolean isRemoteLootable()
  {
    return _isRemoteLootable;
  }

  /**
   * Set the 'remote lootable' flag.
   * @param isRemoteLootable the value to set.
   */
  public void setRemoteLootable(boolean isRemoteLootable)
  {
    _isRemoteLootable=isRemoteLootable;
  }
}
