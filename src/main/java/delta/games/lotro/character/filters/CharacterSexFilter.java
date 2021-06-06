package delta.games.lotro.character.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.character.BaseCharacterSummary;
import delta.games.lotro.common.CharacterSex;

/**
 * Filter for characters of a given sex.
 * @author DAM
 */
public class CharacterSexFilter implements Filter<BaseCharacterSummary>
{
  private CharacterSex _sex;

  /**
   * Constructor.
   * @param sex Sex to select (may be <code>null</code>).
   */
  public CharacterSexFilter(CharacterSex sex)
  {
    _sex=sex;
  }

  /**
   * Get the sex to use.
   * @return A sex or <code>null</code>.
   */
  public CharacterSex getSex()
  {
    return _sex;
  }

  /**
   * Set the sex to select.
   * @param sex Sex to use, may be <code>null</code>.
   */
  public void setSex(CharacterSex sex)
  {
    _sex=sex;
  }

  @Override
  public boolean accept(BaseCharacterSummary summary)
  {
    if (_sex==null)
    {
      return true;
    }
    return summary.getCharacterSex()==_sex;
  }
}
