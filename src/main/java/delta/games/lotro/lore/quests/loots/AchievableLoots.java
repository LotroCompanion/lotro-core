package delta.games.lotro.lore.quests.loots;

import java.util.ArrayList;
import java.util.List;

/**
 * Set of loots for an achievable.
 * @author DAM
 */
public class AchievableLoots
{
  private List<AchievableLoot> _loots;

  /**
   * Constructor.
   */
  public AchievableLoots()
  {
    _loots=new ArrayList<AchievableLoot>();
  }

  /**
   * Add a new loot.
   * @param loot Loot to add.
   */
  public void addLoot(AchievableLoot loot)
  {
    _loots.add(loot);
  }

  /**
   * Get the managed loots.
   * @return A list of loots.
   */
  public List<AchievableLoot> getLoots()
  {
    return _loots;
  }
}
