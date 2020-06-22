package delta.games.lotro.character.classes;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import delta.common.utils.NumericTools;
import delta.games.lotro.character.stats.buffs.Buff;
import delta.games.lotro.character.stats.buffs.BuffInstance;
import delta.games.lotro.character.stats.buffs.BuffsManager;
import delta.games.lotro.character.traits.TraitDescription;

/**
 * Method to load/save a trait tree status from/to a buffs manager.
 * @author DAM
 */
public class BuffsManagerToTraitTreeStatus
{
  private static final Logger LOGGER=Logger.getLogger(BuffsManagerToTraitTreeStatus.class);

  /**
   * Initialize the given trait tree status from a buffs manager.
   * @param status Trait tree status to use.
   * @param buffs Buffs manager to use.
   */
  public static void initFromBuffs(TraitTreeStatus status, BuffsManager buffs)
  {
    status.reset();
    int nbBuffs=buffs.getBuffsCount();
    for(int i=0;i<nbBuffs;i++)
    {
      BuffInstance buffInstance=buffs.getBuffAt(i);
      Buff buff=buffInstance.getBuff();
      String buffId=buff.getId();
      Integer traitId=NumericTools.parseInteger(buffId,false);
      if (traitId!=null)
      {
        Integer rank=status.getRankForTrait(traitId.intValue());
        if (rank!=null)
        {
          Integer newRank=buffInstance.getTier();
          int value=newRank!=null?newRank.intValue():1;
          status.setRankForTrait(traitId.intValue(),value);
        }
      }
    }
    guessSelectedBranch(status,buffs);
    if (LOGGER.isDebugEnabled())
    {
      LOGGER.debug("Loaded tree from buffs: "+status);
    }
  }

  private static void guessSelectedBranch(TraitTreeStatus status, BuffsManager buffs)
  {
    Set<String> buffIds=buffs.getBuffIds();
    List<TraitTreeBranch> branches=status.getTraitTree().getBranches();
    TraitTreeBranch selectedBranch=null;
    for(TraitTreeBranch branch : branches)
    {
      List<TraitDescription> traits=branch.getProgression().getTraits();
      for(TraitDescription trait : traits)
      {
        String key=String.valueOf(trait.getIdentifier());
        if (buffIds.contains(key))
        {
          status.setSelectedBranch(branch);
          selectedBranch=branch;
          break;
        }
      }
    }
    if (selectedBranch==null)
    {
      // Use first branch as default
      LOGGER.warn("Could not guess selected branch. Using first one!");
      selectedBranch=branches.get(0);
    }
    status.setSelectedBranch(selectedBranch);
  }
}
