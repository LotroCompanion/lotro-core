package delta.games.lotro.character.status.portraitFrames;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.lore.portraitFrames.PortraitFrameDescription;

/**
 * Status of portrait frames for a single character:
 * <ul>
 * <li>selected portrait frame,
 * <li>unlocked portrait frames.
 * </ul>
 * @author DAM
 */
public class PortraitFramesStatus
{
  private PortraitFrameDescription _current;
  private List<PortraitFrameDescription> _unlocked;

  /**
   * Constructor.
   */
  public PortraitFramesStatus()
  {
    _current=null;
    _unlocked=new ArrayList<PortraitFrameDescription>();
  }

  /**
   * Add an unlocked portrait frame.
   * @param portraitFrame Portrait frame to add.
   */
  public void addUnlockPortraitFrame(PortraitFrameDescription portraitFrame)
  {
    _unlocked.add(portraitFrame);
  }

  /**
   * Indicates if the given portrait frame is unlocked or not.
   * @param portraitFrame Portrait frame to use.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isUnlocked(PortraitFrameDescription portraitFrame)
  {
    return _unlocked.contains(portraitFrame);
  }

  /**
   * Get the unlocked portrait frames.
   * @return A list of portrait frames.
   */
  public List<PortraitFrameDescription> getUnlockedPortraitFrames()
  {
    return _unlocked;
  }

  /**
   * Get the current portrait frame.
   * @return A portrait frame or <code>null</code> if no current portrait frame.
   */
  public PortraitFrameDescription getCurrentPortraitFrame()
  {
    return _current;
  }

  /**
   * Set the current portrait frame.
   * @param current Portrait frame to set as current (may be <code>null</code>).
   */
  public void setCurrentPortraitFrame(PortraitFrameDescription current)
  {
    _current=current;
  }
}
