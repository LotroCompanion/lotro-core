package delta.games.lotro.common.global;

import delta.games.lotro.character.stats.ratings.RatingsMgr;

/**
 * Facade to access to combat system data.
 * @author DAM
 */
public class CombatData
{
  private RatingsMgr _ratingsMgr;
  private WeaponStrikeModifiersManager _weaponStrikeModifiersMgr;

  /**
   * Constructor.
   */
  public CombatData()
  {
    _ratingsMgr=new RatingsMgr();
    _weaponStrikeModifiersMgr=new WeaponStrikeModifiersManager();
  }

  /**
   * Get the ratings manager
   * @return the ratings manager.
   */
  public RatingsMgr getRatingsMgr()
  {
    return _ratingsMgr;
  }

  /**
   * Get the weapon strike modifiers manager.
   * @return the weapon strike modifiers manager.
   */
  public WeaponStrikeModifiersManager getWeaponStrikeModifiersMgr()
  {
    return _weaponStrikeModifiersMgr;
  }
}
