package delta.games.lotro.character.utils;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.stats.buffs.BuffInstance;
import delta.games.lotro.character.stats.buffs.BuffRegistry;
import delta.games.lotro.character.stats.buffs.BuffSpecification;
import delta.games.lotro.character.stats.buffs.BuffsManager;

/**
 * Utility methods related to character data.
 * @author DAM
 */
public class CharacterDataUtils
{
  /**
   * Add the default class buffs on the given character configuration.
   * @param data
   */
  public static void addDefaultBuffs(CharacterData data)
  {
    ClassDescription classDescription=data.getCharacterClass();
    if (classDescription!=null)
    {
      BuffRegistry buffsRegistry=BuffRegistry.getInstance();
      BuffsManager buffsManager=data.getBuffs();
      for(BuffSpecification buffSpec : classDescription.getDefaultBuffs())
      {
        BuffInstance buff=buffsRegistry.newBuffInstance(buffSpec.getBuffId());
        buff.setTier(buffSpec.getTier());
        buffsManager.addBuff(buff);
      }
    }
  }
}
