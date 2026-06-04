package delta.games.lotro.lore.quests.loots;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.lore.agents.mobs.MobDescription;
import delta.games.lotro.lore.agents.mobs.MobSelection;
import delta.games.lotro.lore.items.Item;

/**
 * Elementary loot specification for an achievable.
 * @author DAM
 */
public class AchievableLoot
{
  private Item _item;
  private int[] _probabilities;
  // Single mob
  private MobDescription _mob;
  // Or mob specs
  private List<MobSelection> _monsterSpecs;
  private List<MobSelection> _excludedMonsterSpecs;

  /**
   * Constructor.
   * @param item Targeted item.
   */
  public AchievableLoot(Item item)
  {
    _item=item;
    _monsterSpecs=null;
    _mob=null;
    _excludedMonsterSpecs=null;
  }

  /**
   * Get the targeted item.
   * @return an item.
   */
  public Item getItem()
  {
    return _item;
  }

  /**
   * Get the loot probabilities.
   * @return an array of probabilities.
   */
  public int[] getProbabilities()
  {
    return _probabilities;
  }

  /**
   * Set the loot probabilities.
   * @param probabilities Probabilities to set.
   */
  public void setProbabilities(int[] probabilities)
  {
    _probabilities=probabilities;
  }

  /**
   * Get the targeted mob.
   * @return a mob or <code>null</code>.
   */
  public MobDescription getMob()
  {
    return _mob;
  }

  /**
   * Set the targeted mob.
   * @param mob Mob to set.
   */
  public void setMob(MobDescription mob)
  {
    _mob=mob;
  }

  /**
   * Get the monster specifications.
   * @return A non-empty list of such specifications, or <code>null</code>
   */
  public List<MobSelection> getMonsterSpecs()
  {
    return _monsterSpecs;
  }

  /**
   * Add a monster specification.
   * @param monsterSpec Specification to add.
   */
  public void addMonsterSpec(MobSelection monsterSpec)
  {
    if (_monsterSpecs==null)
    {
      _monsterSpecs=new ArrayList<MobSelection>();
    }
    _monsterSpecs.add(monsterSpec);
  }

  /**
   * Get the monster specifications to exclude.
   * @return A non-empty list of such specifications, or <code>null</code>
   */
  public List<MobSelection> getExcludedMonsterSpecs()
  {
    return _excludedMonsterSpecs;
  }

  /**
   * Add a monster specification to exclude.
   * @param excludedMonsterSpec Specification to add.
   */
  public void addExcludedMonsterSpec(MobSelection excludedMonsterSpec)
  {
    if (_excludedMonsterSpecs==null)
    {
      _excludedMonsterSpecs=new ArrayList<MobSelection>();
    }
    _excludedMonsterSpecs.add(excludedMonsterSpec);
  }
}
