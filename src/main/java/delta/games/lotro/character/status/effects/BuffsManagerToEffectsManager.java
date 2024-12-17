package delta.games.lotro.character.status.effects;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.common.utils.NumericTools;
import delta.games.lotro.character.stats.buffs.io.xml.RawBuffStorage;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectsManager;

/**
 * Method to load character effects status from buffs.
 * @author DAM
 */
public class BuffsManagerToEffectsManager
{
  private static final Logger LOGGER=LoggerFactory.getLogger(BuffsManagerToEffectsManager.class);

  /**
   * Fill an effects manager from raw buffs.
   * @param characterEffects Effects storage.
   * @param buffs Buffs to use.
   */
  public static void fillFromBuffs(RawBuffStorage buffs, CharacterEffectsManager characterEffects)
  {
    EffectsManager effectsMgr=EffectsManager.getInstance();
    Set<String> toRemove=new HashSet<String>();
    int nbBuffs=buffs.getSize();
    for(int i=0;i<nbBuffs;i++)
    {
      String buffID=buffs.getBuffID(i);
      Effect effect=null;
      Integer effectID=NumericTools.parseInteger(buffID,false);
      if (effectID!=null)
      {
        effect=effectsMgr.getEffectById(effectID.intValue());
      }
      if (effect!=null)
      {
        EffectInstance effectInstance=new EffectInstance(effect);
        characterEffects.addEffect(effectInstance);
        toRemove.add(buffID);
        LOGGER.debug("Loaded character effect from buffs: {}",effectInstance);
      }
    }
    for(String buffID : toRemove)
    {
      buffs.removeBuff(buffID);
    }
  }
}
