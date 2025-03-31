package delta.games.lotro.character.status.traits.shared;

import java.util.Arrays;

/**
 * Status of slotted traits.
 * @author DAM
 */
public class TraitSlotsStatus
{
  private int[] _traitIDs;

  /**
   * Constructor.
   */
  public TraitSlotsStatus()
  {
    super();
    _traitIDs=new int[0];
  }

  /**
   * Copy constructor.
   * @param status Source status.
   */
  public TraitSlotsStatus(TraitSlotsStatus status)
  {
    super();
    _traitIDs=new int[status._traitIDs.length];
    System.arraycopy(status._traitIDs,0,_traitIDs,0,status.getSlotsCount());
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
    if (index<_traitIDs.length)
    {
      return _traitIDs[index];
    }
    return 0;
  }

  /**
   * Set the trait at the given slot.
   * @param index Index of slot.
   * @param traitID ID of the trait to set (0 for no trait).
   */
  public void setTraitAt(int index, int traitID)
  {
    if (index>=_traitIDs.length)
    {
      int[] newTraitIDs=new int[index+1];
      System.arraycopy(_traitIDs,0,newTraitIDs,0,_traitIDs.length);
      _traitIDs=newTraitIDs;
    }
    _traitIDs[index]=traitID;
  }

  @Override
  public String toString()
  {
    return Arrays.toString(_traitIDs);
  }
}
