package delta.games.lotro.character.status.traits.shared;

/**
 * Status of slotted traits.
 * @author DAM
 */
public class SlottedTraitsStatus
{
  private TraitSlotsStatus _slotsStatus;
  private AvailableTraitsStatus _availableTraits;

  /**
   * Constructor.
   */
  public SlottedTraitsStatus()
  {
    _slotsStatus=new TraitSlotsStatus();
    _availableTraits=new AvailableTraitsStatus();
  }

  /**
   * Get the slots status.
   * @return the slots status.
   */
  public TraitSlotsStatus getSlotsStatus()
  {
    return _slotsStatus;
  }

  /**
   * Get the available traits status.
   * @return the available traits status.
   */
  public AvailableTraitsStatus getAvailableTraitsStatus()
  {
    return _availableTraits;
  }
}
