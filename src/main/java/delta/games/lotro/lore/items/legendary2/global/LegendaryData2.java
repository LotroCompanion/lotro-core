package delta.games.lotro.lore.items.legendary2.global;

import delta.games.lotro.utils.maths.Progression;

/**
 * Facade to access to legendary system (reloaded) data.
 * @author DAM
 */
public class LegendaryData2
{
  private Progression _charLevel2ItemLevelProgression;

  /**
   * Constructor.
   */
  public LegendaryData2()
  {
    _charLevel2ItemLevelProgression=null;
  }

  /**
   * Get the progression from character level to item level.
   * @return A progression.
   */
  public Progression getCharacterLevel2ItemLevelProgression()
  {
    return _charLevel2ItemLevelProgression;
  }

  /**
   * Set the progression from character level to item level.
   * @param charLevel2ItemLevelProgression Progression to set.
   */
  public void setCharacterLevel2ItemLevelProgression(Progression charLevel2ItemLevelProgression)
  {
    _charLevel2ItemLevelProgression=charLevel2ItemLevelProgression;
  }
}
