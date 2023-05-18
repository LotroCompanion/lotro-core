package delta.games.lotro.common;

import delta.games.lotro.common.enums.LotroEnumsRegistry;

/**
 * Well-known genders.
 * @author DAM
 */
public class Genders
{
  /**
   * Male.
   */
  public static final CharacterSex MALE=LotroEnumsRegistry.getInstance().get(CharacterSex.class).getEntry(100);
  /**
   * Female.
   */
  public static final CharacterSex FEMALE=LotroEnumsRegistry.getInstance().get(CharacterSex.class).getEntry(101);
}
