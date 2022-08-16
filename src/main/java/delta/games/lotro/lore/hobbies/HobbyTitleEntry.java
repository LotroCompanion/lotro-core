package delta.games.lotro.lore.hobbies;

import delta.games.lotro.lore.titles.TitleDescription;

/**
 * Hobby title entry.
 * @author DAM
 */
public class HobbyTitleEntry
{
  private int _proficiency;
  private TitleDescription _title;

  /**
   * Constructor.
   * @param proficiency Needed proficiency.
   * @param title Rewarded title.
   */
  public HobbyTitleEntry(int proficiency, TitleDescription title)
  {
    _proficiency=proficiency;
    _title=title;
  }

  /**
   * Get the needed proficiency.
   * @return a proficiency value.
   */
  public int getProficiency()
  {
    return _proficiency;
  }

  /**
   * Get the rewarded title.
   * @return a title.
   */
  public TitleDescription getTitle()
  {
    return _title;
  }
}
