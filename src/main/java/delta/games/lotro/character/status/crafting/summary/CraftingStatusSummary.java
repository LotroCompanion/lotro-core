package delta.games.lotro.character.status.crafting.summary;

import java.util.ArrayList;
import java.util.List;

/**
 * Summary of crafting status for a single character.
 * @author DAM
 */
public class CraftingStatusSummary
{
  private List<ProfessionStatusSummary> _professions;

  /**
   * Constructor.
   */
  public CraftingStatusSummary()
  {
    _professions=new ArrayList<ProfessionStatusSummary>();
  }

  /**
   * Add a profession status summary.
   * @param summary Summary to add.
   */
  public void addProfessionStatus(ProfessionStatusSummary summary)
  {
    _professions.add(summary);
  }

  /**
   * Get all the profession status summaries.
   * @return A list of profession status summaries.
   */
  public List<ProfessionStatusSummary> getProfessionStatuses()
  {
    return _professions;
  }
}
