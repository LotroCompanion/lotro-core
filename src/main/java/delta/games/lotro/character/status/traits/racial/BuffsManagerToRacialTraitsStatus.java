package delta.games.lotro.character.status.traits.racial;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import delta.common.utils.NumericTools;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.character.stats.buffs.io.xml.RawBuffStorage;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;

/**
 * Method to load a racial traits status from buffs.
 * @author DAM
 */
public class BuffsManagerToRacialTraitsStatus
{
  private static final Logger LOGGER=Logger.getLogger(BuffsManagerToRacialTraitsStatus.class);

  /**
   * Initialize a racial traits status from buffs.
   * @param race Character race to use.
   * @param buffs Buffs to use.
   * @return the loaded racial traits status.
   */
  public static RacialTraitsStatus initFromBuffs(RaceDescription race, RawBuffStorage buffs)
  {
    RacialTraitsStatus status=new RacialTraitsStatus();
    Set<Integer> availableRacialTraitIDs=new HashSet<Integer>();
    for(TraitDescription trait : race.getEarnableTraits())
    {
      availableRacialTraitIDs.add(Integer.valueOf(trait.getIdentifier()));
    }
    List<Integer> slottedTraits=new ArrayList<Integer>();
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
        Integer key=Integer.valueOf(trait.getIdentifier());
        boolean isRacialTrait=availableRacialTraitIDs.contains(key);
        if (isRacialTrait)
        {
          slottedTraits.add(key);
        }
      }
    }
    int nbSlottedTraits=slottedTraits.size();
    int[] traitIDs=new int[nbSlottedTraits];
    for(int i=0;i<nbSlottedTraits;i++)
    {
      traitIDs[i]=slottedTraits.get(i).intValue();
    }
    status.setTraits(traitIDs);
    for(TraitDescription trait : race.getEarnableTraits())
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
      LOGGER.debug("Loaded racial traits status from buffs: "+status);
    }
    return status;
  }
}
