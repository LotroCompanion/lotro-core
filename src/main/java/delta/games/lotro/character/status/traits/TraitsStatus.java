package delta.games.lotro.character.status.traits;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.stats.TraitTreeStatsComputer;
import delta.games.lotro.character.stats.contribs.StatsContribution;
import delta.games.lotro.character.status.traitTree.TraitTreeStatus;
import delta.games.lotro.character.status.traits.racial.RacialTraitsStatus;

/**
 * Traits status, as stored in character data.
 * @author DAM
 */
public class TraitsStatus
{
  private TraitTreeStatus _traitTree;
  private RacialTraitsStatus _racialTraits;
  // Later or never: tomes status, VirtuesSet
  // Note: SkirmishTraitsStatus, Mounted combat trait tree and BB trait tree are stored separately

  /**
   * Constructor.
   */
  public TraitsStatus()
  {
    _traitTree=null;
    _racialTraits=null;
  }

  /**
   * Set the contents of this object from a given source.
   * @param source Source to copy.
   */
  public void copyFrom(TraitsStatus source)
  {
    _traitTree=null;
    if (source._traitTree!=null)
    {
      _traitTree=new TraitTreeStatus(source._traitTree);
    }
    _racialTraits=null;
  }

  /**
   * Get the managed trait tree status.
   * @return the managed trait tree status.
   */
  public TraitTreeStatus getTraitTreeStatus()
  {
    return _traitTree;
  }

  /**
   * Set the trait tree status.
   * @param traitTree Trait tree status to set.
   */
  public void setTraitTreeStatus(TraitTreeStatus traitTree)
  {
    _traitTree=traitTree;
  }

  /**
   * Get the racial traits status.
   * @return the racial traits status.
   */
  public RacialTraitsStatus getRacialTraitsStatus()
  {
    return _racialTraits;
  }

  /**
   * Set the racial traits status.
   * @param racialTraits Racial traits status to set.
   */
  public void setRacialTraitsStatus(RacialTraitsStatus racialTraits)
  {
    _racialTraits=racialTraits;
  }

  /**
   * Get stats contributions for the traits on the given character.
   * @param c Targeted character.
   * @return A possibly empty but not <code>null</code> list of contributions.
   */
  public List<StatsContribution> getContributions(CharacterData c)
  {
    List<StatsContribution> ret=new ArrayList<StatsContribution>();
    // Trait tree
    if (_traitTree!=null)
    {
      ret.addAll(TraitTreeStatsComputer.getContributions(_traitTree,c));
    }
    // Racial traits
    // TODO
    return ret;
  }

  /**
   * Reset contents.
   */
  public void clear()
  {
    _traitTree=null;
    _racialTraits=null;
  }
}
