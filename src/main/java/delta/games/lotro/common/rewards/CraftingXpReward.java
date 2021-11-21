package delta.games.lotro.common.rewards;

import delta.games.lotro.lore.crafting.Profession;

/**
 * Crafting XP reward.
 * @author DAM
 */
public class CraftingXpReward extends RewardElement
{
  private Profession _profession;
  private int _tier;
  private int _xp;

  /**
   * Constructor.
   * @param profession Profession.
   * @param tier Tier.
   * @param xp XP value.
   */
  public CraftingXpReward(Profession profession, int tier, int xp)
  {
    _profession=profession;
    _tier=tier;
    _xp=xp;
  }

  /**
   * Get the targeted profession.
   * @return a profession.
   */
  public Profession getProfession()
  {
    return _profession;
  }

  /**
   * Get the targeted tier.
   * @return a tier index (starting at 1).
   */
  public int getTier()
  {
    return _tier;
  }

  /**
   * Get the reward XP amount.
   * @return an XP amount.
   */
  public int getXp()
  {
    return _xp;
  }

  /**
   * Add XP.
   * @param xp XP to add.
   */
  public void addXp(int xp)
  {
    _xp+=xp;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Profession=").append(_profession);
    sb.append(", tier=").append(_tier);
    sb.append(", XP=").append(_xp);
    return sb.toString();
  }
}
