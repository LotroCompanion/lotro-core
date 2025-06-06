package delta.games.lotro.character.status.traitTree;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.common.utils.NumericTools;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.traitTree.TraitTree;
import delta.games.lotro.character.classes.traitTree.TraitTreeBranch;
import delta.games.lotro.character.stats.buffs.io.xml.RawBuffStorage;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;

/**
 * Method to load a trait tree status from buffs.
 * @author DAM
 */
public class BuffsManagerToTraitTreeStatus
{
  private static final Logger LOGGER=LoggerFactory.getLogger(BuffsManagerToTraitTreeStatus.class);

  /**
   * Initialize a trait tree status from buffs.
   * @param characterClass Character class to use.
   * @param buffs Buffs to use.
   * @return the loaded trait tree.
   */
  public static TraitTreeStatus initFromBuffs(ClassDescription characterClass, RawBuffStorage buffs)
  {
    TraitTree traitTree=characterClass.getTraitTree();
    TraitTreeStatus status=new TraitTreeStatus(traitTree);

    TraitsManager traitsMgr=TraitsManager.getInstance();
    int nbBuffs=buffs.getSize();
    for(int i=0;i<nbBuffs;i++)
    {
      String buffID=buffs.getBuffID(i);
      TraitDescription trait=null;
      Integer traitId=NumericTools.parseInteger(buffID,false);
      if (traitId!=null)
      {
        trait=traitsMgr.getTrait(traitId.intValue());
      }
      else
      {
        trait=traitsMgr.getTraitByKey(buffID);
      }
      if (trait!=null)
      {
        boolean known=status.isKnownCell(trait.getIdentifier());
        if (known)
        {
          Integer newRank=buffs.getTier(i);
          int newRankValue=(newRank!=null)?newRank.intValue():0;
          status.setRankForTrait(trait.getIdentifier(),newRankValue);
        }
      }
    }
    if (nbBuffs>0)
    {
      guessSelectedBranch(status,buffs);
    }
    for(TraitDescription trait : status.getTraitTree().getAllTraits())
    {
      buffs.removeBuff(String.valueOf(trait.getIdentifier()));
      String key=trait.getKey();
      if (key!=null)
      {
        buffs.removeBuff(key);
      }
    }

    LOGGER.debug("Loaded tree from buffs: {}",status);
    return status;
  }

  private static void guessSelectedBranch(TraitTreeStatus status, RawBuffStorage buffs)
  {
    List<TraitTreeBranch> branches=status.getTraitTree().getBranches();
    TraitTreeBranch selectedBranch=null;
    for(TraitTreeBranch branch : branches)
    {
      List<TraitDescription> traits=branch.getProgression().getTraits();
      for(TraitDescription trait : traits)
      {
        String key=String.valueOf(trait.getIdentifier());
        if (buffs.contains(key))
        {
          status.setSelectedBranch(branch);
          selectedBranch=branch;
          break;
        }
        key=trait.getKey();
        if ((key!=null) && (buffs.contains(key)))
        {
          status.setSelectedBranch(branch);
          selectedBranch=branch;
          break;
        }
      }
    }
    if (selectedBranch==null)
    {
      LOGGER.info("Could not guess selected branch! Buffs={}",buffs);
    }
    status.setSelectedBranch(selectedBranch);
  }
}
