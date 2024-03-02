package delta.games.lotro.character.status.traits.shared;

/**
 * Status of slotted traits.
 * @author DAM
 */
public class SlottedTraitsStatus extends TraitsStatus
{
  private int[] _traitIDs;

  /**
   * Constructor.
   */
  public SlottedTraitsStatus()
  {
    super();
    _traitIDs=new int[0];
  }

  /**
   * Set the traits.
   * @param traitIDs Trait IDs (0=none slotted).
   */
  public void setTraits(int[] traitIDs)
  {
    _traitIDs=traitIDs;
  }

  /**
   * Get the number of slots.
   * @return A slots count.
   */
  public int getSlotsCount()
  {
    return _traitIDs.length;
  }

  /**
   * Get the trait at the given index.
   * @param index Index, between 0 and getSlotsCount()-1.
   * @return A trait ID or 0 if not slotted.
   */
  public int getTraitAt(int index)
  {
    return _traitIDs[index];
  }
}
