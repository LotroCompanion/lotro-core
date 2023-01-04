package delta.games.lotro.character.status.traitTree;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import delta.common.utils.NumericTools;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.character.classes.traitTree.TraitTree;
import delta.games.lotro.character.classes.traitTree.TraitTreeBranch;
import delta.games.lotro.character.stats.buffs.io.xml.RawBuffStorage;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;
import delta.games.lotro.common.CharacterClass;

/**
 * Method to load a trait tree status from buffs.
 * @author DAM
 */
public class BuffsManagerToTraitTreeStatus
{
  private static final Logger LOGGER=Logger.getLogger(BuffsManagerToTraitTreeStatus.class);

  /**
   * Initialize a trait tree status from buffs.
   * @param characterClass Character class to use.
   * @param buffs Buffs to use.
   * @return the loaded trait tree.
   */
  public static TraitTreeStatus initFromBuffs(CharacterClass characterClass, RawBuffStorage buffs)
  {
    ClassDescription classDescription=ClassesManager.getInstance().getClassDescription(characterClass);
    TraitTree traitTree=classDescription.getTraitTree();
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
          int newRank=buffs.getTier(i);
          status.setRankForTrait(trait.getIdentifier(),newRank);
        }
      }
    }
    guessSelectedBranch(status,buffs);
    for(TraitDescription trait : status.getTraitTree().getAllTraits())
    {
      buffs.removeBuff(String.valueOf(trait.getIdentifier()));
      String key=trait.getKey();
      if (key!=null)
      {
        buffs.removeBuff(key);
      }
    }

    if (LOGGER.isDebugEnabled())
    {
      LOGGER.debug("Loaded tree from buffs: "+status);
    }
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
