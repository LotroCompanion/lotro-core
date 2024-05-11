package delta.games.lotro.character.status.summary;

/**
 * Achievements summary for a single character.
 * @author DAM
 */
public class AchievementsSummary
{
  private Integer _deeds;
  private Integer _quests;
  private Integer _titles;

  /**
   * Constructor.
   */
  public AchievementsSummary()
  {
    _deeds=null;
    _quests=null;
    _titles=null;
  }

  /**
   * Get the deeds count.
   * @return a deeds count.
   */
  public Integer getDeedsCount()
  {
    return _deeds;
  }

  /**
   * Set the deeds count.
   * @param deeds Deeds count.
   */
  public void setDeedsCount(Integer deeds)
  {
    _deeds=deeds; 
  }

  /**
   * Get the quests count.
   * @return a quests count.
   */
  public Integer getQuestsCount()
  {
    return _quests;
  }

  /**
   * Set the quests count.
   * @param quests Quests count.
   */
  public void setQuestsCount(Integer quests)
  {
    _quests=quests; 
  }

  /**
   * Get the titles count.
   * @return a titles count.
   */
  public Integer getTitlesCount()
  {
    return _titles;
  }

  /**
   * Set the titles count.
   * @param titles Titles count.
   */
  public void setTitlesCount(Integer titles)
  {
    _titles=titles; 
  }

  @Override
  public String toString()
  {
    return "Deeds: "+_deeds+", quests: "+_quests+", titles: "+_titles;
  }
}
