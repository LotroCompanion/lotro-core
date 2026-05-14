package delta.games.lotro.lore.items.details;

import delta.games.lotro.lore.portraitFrames.PortraitFrameDescription;

/**
 * 'Provides portrait frame' aspect/detail of an item.
 * @author DAM
 */
public class ProvidesPortraitFrame extends ItemDetail
{
  private PortraitFrameDescription _portraitFrame;

  /**
   * Constructor.
   * @param portraitFrame Provided portrait frame.
   */
  public ProvidesPortraitFrame(PortraitFrameDescription portraitFrame)
  {
    _portraitFrame=portraitFrame;
  }

  /**
   * Get the provided portrait frame.
   * @return the provided portrait frame.
   */
  public PortraitFrameDescription getPortraitFrame()
  {
    return _portraitFrame;
  }

  @Override
  public String toString()
  {
    return _portraitFrame.getName();
  }
}
