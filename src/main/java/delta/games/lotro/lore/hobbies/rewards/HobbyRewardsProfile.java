package delta.games.lotro.lore.hobbies.rewards;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.math.Range;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.lore.items.Item;

/**
 * Hobby rewards profile.
 * @author DAM
 */
public class HobbyRewardsProfile implements Identifiable
{
  private int _id;
  private List<HobbyRewardEntry> _entries;

  /**
   * Constructor.
   * @param id Identifier.
   */
  public HobbyRewardsProfile(int id)
  {
    _id=id;
    _entries=new ArrayList<HobbyRewardEntry>();
  }

  @Override
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * Add an entry.
   * @param entry Entry to add.
   */
  public void addEntry(HobbyRewardEntry entry)
  {
    _entries.add(entry);
  }

  /**
   * Get the managed entries.
   * @return the managed entries.
   */
  public List<HobbyRewardEntry> getEntries()
  {
    return _entries;
  }

  /**
   * Get the proficiency range.
   * @return A proficiency range.
   */
  public Range getProficiencyRange()
  {
    int min=1;
    int max=1;
    for(HobbyRewardEntry entry : _entries)
    {
      int currentMax=entry.getMaxProficiency();
      if (currentMax>max)
      {
        max=currentMax;
      }
    }
    return new Range(min,max);
  }

  /**
   * Build a drop table for the given proficiency.
   * @param proficiency Proficiency to use.
   * @return A drop table.
   */
  public HobbyDropTable buildDropTable(int proficiency)
  {
    HobbyDropTable table=new HobbyDropTable();
    List<HobbyRewardEntry> entries=findEntriesForProficiency(proficiency);
    if (!entries.isEmpty())
    {
      int totalWeight=0;
      for(HobbyRewardEntry entry : entries)
      {
        totalWeight+=entry.getWeight();
      }
      for(HobbyRewardEntry entry : entries)
      {
        Item item=entry.getItem();
        float dropPercentage=((float)entry.getWeight())/totalWeight;
        HobbyDropTableEntry dropTableEntry=new HobbyDropTableEntry(item,dropPercentage*100);
        table.addEntry(dropTableEntry);
      }
    }
    return table;
  }

  private List<HobbyRewardEntry> findEntriesForProficiency(int proficiency)
  {
    List<HobbyRewardEntry> ret=new ArrayList<HobbyRewardEntry>();
    for(HobbyRewardEntry entry : _entries)
    {
      int min=entry.getMinProficiency();
      int max=entry.getMaxProficiency();
      if ((min<=proficiency) && (max>=proficiency))
      {
        ret.add(entry);
      }
    }
    return ret;
  }
}
