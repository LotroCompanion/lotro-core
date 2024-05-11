package delta.games.lotro.character.status.summary;

/**
 * Achievements summary for a single character.
 * @author DAM
 */
public class AchievementsSummary
{
  private int _deeds;
  private int _quests;
  private int _titles;

  /**
   * Constructor.
   */
  public AchievementsSummary()
  {
    _deeds=0;
    _quests=0;
    _titles=0;
  }

  /**
   * Get the deeds count.
   * @return a deeds count.
   */
  public int getDeedsCount()
  {
    return _deeds;
  }

  /**
   * Set the deeds count.
   * @param deeds Deeds count.
   */
  public void setDeedsCount(int deeds)
  {
    _deeds=deeds; 
  }

  /**
   * Get the quests count.
   * @return a quests count.
   */
  public int getQuestsCount()
  {
    return _quests;
  }

  /**
   * Set the quests count.
   * @param quests Quests count.
   */
  public void setQuestsCount(int quests)
  {
    _quests=quests; 
  }

  /**
   * Get the titles count.
   * @return a titles count.
   */
  public int getTitlesCount()
  {
    return _titles;
  }

  /**
   * Set the titles count.
   * @param titles Titles count.
   */
  public void setTitlesCount(int titles)
  {
    _titles=titles; 
  }

  @Override
  public String toString()
  {
    return "Deeds: "+_deeds+", quests: "+_quests+", titles: "+_titles;
  }
}
