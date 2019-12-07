package delta.games.lotro.lore.trade.barter;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.requirements.UsageRequirement;

/**
 * Barter data for a NPC.
 * @author DAM
 */
public class BarterNpc implements Identifiable
{
  private int _npcId;
  private String _name;
  private String _title;
  // Requirements
  private UsageRequirement _requirements;
  // Profile(s)
  private List<BarterProfile> _profiles;

  /**
   * Constructor.
   * @param npcId NPC identifier.
   */
  public BarterNpc(int npcId)
  {
    _npcId=npcId;
    _requirements=new UsageRequirement();
    _profiles=new ArrayList<BarterProfile>();
  }

  /**
   * Get the identifier of the parent NPC.
   * @return a NPC identifier.
   */
  public int getIdentifier()
  {
    return _npcId;
  }

  /**
   * Get the NPC name.
   * @return a NPC name.
   */
  public String getNpcName()
  {
    return _name;
  }

  /**
   * Set the NPC name.
   * @param name Name to set.
   */
  public void setNpcName(String name)
  {
    _name=name;
  }

  /**
   * Get the NPC title.
   * @return title to set.
   */
  public String getNpcTitle()
  {
    return _title;
  }

  /**
   * Set the NPC title.
   * @param title Title to set.
   */
  public void setNpcTitle(String title)
  {
    _title=title;
  }

  /**
   * Get the usage requirements for this barterer.
   * @return some requirements.
   */
  public UsageRequirement getRequirements()
  {
    return _requirements;
  }

  /**
   * Add a barter profile.
   * @param profile Profile to add.
   */
  public void addBarterProfile(BarterProfile profile)
  {
    _profiles.add(profile);
  }

  /**
   * Get the barter profiles.
   * @return a list of barter profiles.
   */
  public List<BarterProfile> getBarterProfiles()
  {
    return _profiles;
  }

  /**
   * Dump the contents of this NPC barter data as a readable string.
   * @return A displayable string.
   */
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("NPC #").append(_npcId).append(": ").append(_name);
    if (_title!=null)
    {
      sb.append(" (").append(_title).append(')');
    }
    sb.append(EndOfLine.NATIVE_EOL);
    for(BarterProfile profile : _profiles)
    {
      sb.append("\tProfile: ").append(profile.getName()).append(EndOfLine.NATIVE_EOL);
      for(BarterEntry entry : profile.getEntries())
      {
        sb.append("\t\t").append(entry).append(EndOfLine.NATIVE_EOL);
      }
    }
    return sb.toString().trim();
  }
}
