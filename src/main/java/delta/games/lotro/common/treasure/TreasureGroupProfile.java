package delta.games.lotro.common.treasure;


/**
 * Treasure group profile:
 * <ul>
 * <li>items table, OR
 * <li>treasure list
 * </ul>
 * @author DAM
 */
public abstract class TreasureGroupProfile extends LootTable
{
  /**
   * Constructor.
   * @param identifier Identifier.
   */
  public TreasureGroupProfile(int identifier)
  {
    super(identifier);
  }
}
