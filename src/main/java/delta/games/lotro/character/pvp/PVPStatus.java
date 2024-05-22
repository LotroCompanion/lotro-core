package delta.games.lotro.character.pvp;

import delta.games.lotro.lore.pvp.Rank;

/**
 * PVP status.
 * @author DAM
 */
public class PVPStatus
{
  // Ranks
  // - glory=infamy orrenown
  private int _gloryPoints;
  private Rank _rank;
  // - prestige
  private float _rating;
  private Rank _prestige;
  // Stats
  private int _deaths;
  private int _kills;
  private int _killsAboveRating;
  private int _killsBelowRating;
  private float _kill2deathRatio;
  private float _highestRatingKilled;
  private int _deathBlows;

  /**
   * Constructor.
   */
  public PVPStatus()
  {
    _gloryPoints=0;
    _rank=null;
    _rating=0.0f;
    _prestige=null;
    _deaths=0;
    _kills=0;
    _killsAboveRating=0;
    _killsBelowRating=0;
    _kill2deathRatio=1.0f;
    _highestRatingKilled=0.0f;
    _deathBlows=0;
  }

  /**
   * Get the glory points (infamy or renown).
   * @return the glory points.
   */
  public int getGloryPoints()
  {
    return _gloryPoints;
  }

  /**
   * Set the glory points.
   * @param gloryPoints the points to set.
   */
  public void setGlory(int gloryPoints)
  {
    _gloryPoints=gloryPoints;
  }

  /**
   * Get the glory rank.
   * @return the glory rank.
   */
  public Rank getRank()
  {
    return _rank;
  }

  /**
   * Set the glory rank.
   * @param rank the rank to set.
   */
  public void setRank(Rank rank)
  {
    _rank=rank;
  }

  /**
   * Get the rating.
   * @return the rating.
   */
  public float getRating()
  {
    return _rating;
  }

  /**
   * Set the rating.
   * @param rating the rating to set.
   */
  public void setRating(float rating)
  {
    _rating=rating;
  }

  /**
   * Get the prestige.
   * @return the prestige.
   */
  public Rank getPrestige()
  {
    return _prestige;
  }

  /**
   * Set the prestige.
   * @param prestige the prestige to set.
   */
  public void setPrestige(Rank prestige)
  {
    _prestige=prestige;
  }

  /**
   * Get the deaths count.
   * @return a count.
   */
  public int getDeaths()
  {
    return _deaths;
  }

  /**
   * Set the deaths count.
   * @param deaths the count to set.
   */
  public void setDeaths(int deaths)
  {
    _deaths=deaths;
  }

  /**
   * Get the kills count.
   * @return a count.
   */
  public int getKills()
  {
    return _kills;
  }

  /**
   * Set the kills count.
   * @param kills the count to set.
   */
  public void setKills(int kills)
  {
    _kills=kills;
  }

  /**
   * Get the 'kills above rating' count.
   * @return a count.
   */
  public int getKillsAboveRating()
  {
    return _killsAboveRating;
  }

  /**
   * Set the 'kills above rating' count.
   * @param killsAboveRating the count to set.
   */
  public void setKillsAboveRating(int killsAboveRating)
  {
    _killsAboveRating=killsAboveRating;
  }

  /**
   * Get the 'kills below rating' count.
   * @return a count.
   */
  public int getKillsBelowRating()
  {
    return _killsBelowRating;
  }

  /**
   * Set the 'kills below rating' count.
   * @param killsBelowRating the count to set.
   */
  public void setKillsBelowRating(int killsBelowRating)
  {
    _killsBelowRating=killsBelowRating;
  }

  /**
   * Get the 'kills/deaths' ratio.
   * @return a ratio.
   */
  public float getKill2deathRatio()
  {
    return _kill2deathRatio;
  }

  /**
   * Set the 'kills/deaths' ratio.
   * @param kill2deathRatio the ratio to set.
   */
  public void setKill2deathRatio(float kill2deathRatio)
  {
    _kill2deathRatio=kill2deathRatio;
  }

  /**
   * Get the highest rating killed.
   * @return a rating.
   */
  public float getHighestRatingKilled()
  {
    return _highestRatingKilled;
  }

  /**
   * Set the highest rating killed.
   * @param highestRatingKilled the rating to set.
   */
  public void setHighestRatingKilled(float highestRatingKilled)
  {
    _highestRatingKilled=highestRatingKilled;
  }

  /**
   * Get the 'death blows' count.
   * @return a count.
   */
  public int getDeathBlows()
  {
    return _deathBlows;
  }

  /**
   * Set the 'death blows' count.
   * @param deathBlows the count to set.
   */
  public void setDeathBlows(int deathBlows)
  {
    _deathBlows=deathBlows;
  }
}
