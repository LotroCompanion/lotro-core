package delta.games.lotro.common.treasure;

import delta.games.lotro.common.Identifiable;

/**
 * Treasure group profile:
 * <ul>
 * <li>items table, OR
 * <li>treasure list
 * </ul>
 * @author DAM
 */
public abstract class TreasureGroupProfile implements Identifiable
{
  private int _id;

  /**
   * Constructor.
   * @param identifier Identifier.
   */
  public TreasureGroupProfile(int identifier)
  {
    _id=identifier;
  }

  @Override
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * Dump contents.
   * @param sb Output.
   * @param level Indentation level.
   */
  public abstract void dump(StringBuilder sb, int level);
}
