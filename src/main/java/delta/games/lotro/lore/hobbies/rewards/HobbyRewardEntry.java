package delta.games.lotro.lore.hobbies.rewards;

import delta.games.lotro.lore.items.Item;

/**
 * Entry of a hobby rewards profile.
 * @author DAM
 */
public class HobbyRewardEntry
{
  private Item _item;
  private int _minProficiency;
  private int _maxProficiency;
  private int _weight;

  /**
   * Constructor.
   * @param item Item to reward.
   * @param minProficiency Minimum proficiency.
   * @param maxProficiency Maximum proficiency.
   * @param weight Weight (chances to get this entry).
   */
  public HobbyRewardEntry(Item item, int minProficiency, int maxProficiency, int weight)
  {
    _item=item;
    _minProficiency=minProficiency;
    _maxProficiency=maxProficiency;
    _weight=weight;
  }

  /**
   * Get the item to reward.
   * @return the item to reward.
   */
  public Item getItem()
  {
    return _item;
  }

  /**
   * Get the minimum proficiency.
   * @return the minimum proficiency.
   */
  public int getMinProficiency()
  {
    return _minProficiency;
  }

  /**
   * Get the maximum proficiency.
   * @return the maximum proficiency.
   */
  public int getMaxProficiency()
  {
    return _maxProficiency;
  }

  /**
   * Get the weight.
   * @return the weight.
   */
  public int getWeight()
  {
    return _weight;
  }
}
