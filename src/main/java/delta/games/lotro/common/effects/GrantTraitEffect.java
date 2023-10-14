package delta.games.lotro.common.effects;

/**
 * Effect to grant a trait.
 * @author DAM
 */
public class GrantTraitEffect extends Effect2
{
  private int _grantedTraitID;

  /**
   * Constructor.
   * @param grantedTraitID Trait identifier.
   */
  public GrantTraitEffect(int grantedTraitID)
  {
    super();
    _grantedTraitID=grantedTraitID;
  }

  /**
   * Get the identifier of the granted trait.
   * @return A trait identifier.
   */
  public int getGrantedTraitID()
  {
    return _grantedTraitID;
  }
}
