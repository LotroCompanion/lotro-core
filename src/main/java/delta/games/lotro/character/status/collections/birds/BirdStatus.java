package delta.games.lotro.character.status.collections.birds;

import delta.games.lotro.lore.collections.birds.BirdDescription;

/**
 * Bird status.
 * @author DAM
 */
public class BirdStatus
{
  private BirdDescription _bird;
  private boolean _known;

  /**
   * Constructor.
   * @param bird Associated bird.
   */
  public BirdStatus(BirdDescription bird)
  {
    if (bird==null)
    {
      throw new IllegalArgumentException("bird is null");
    }
    _bird=bird;
    _known=false;
  }

  /**
   * Get the associated bird.
   * @return the associated bird.
   */
  public BirdDescription getBird()
  {
    return _bird;
  }

  /**
   * Indicates if this bird is known or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isKnown()
  {
    return _known;
  }

  /**
   * Set the 'known' flag.
   * @param known Value to set.
   */
  public void setKnown(boolean known)
  {
    _known=known;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    int skillID=_bird.getIdentifier();
    sb.append("Bird ").append(_bird.getName()).append(" (").append(skillID).append("): ");
    if (_known)
    {
      sb.append("known");
    }
    else
    {
      sb.append("not known");
    }
    return sb.toString();
  }
}
