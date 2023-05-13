package delta.games.lotro.common;

import delta.games.lotro.common.enums.LotroEnumEntry;
import delta.games.lotro.common.enums.LotroEnumsRegistry;

/**
 * Character sex.
 * @author DAM
 */
public final class CharacterSex extends LotroEnumEntry
{
  /**
   * Male.
   */
  public static final CharacterSex MALE=LotroEnumsRegistry.getInstance().get(CharacterSex.class).getEntry(100);
  /**
   * Female.
   */
  public static final CharacterSex FEMALE=LotroEnumsRegistry.getInstance().get(CharacterSex.class).getEntry(101);

  @Override
  public String toString()
  {
    return getLabel();
  }
}
