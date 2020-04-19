package delta.games.lotro.character.utils;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.character.stats.buffs.BuffInstance;
import delta.games.lotro.character.stats.buffs.BuffsManager;
import delta.games.lotro.common.CharacterClass;

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
    CharacterClass characterClass=data.getCharacterClass();
    ClassDescription classDescription=ClassesManager.getInstance().getClassDescription(characterClass);
    if (classDescription!=null)
    {
      BuffsManager buffsManager=data.getBuffs();
      for(BuffInstance buff : classDescription.getDefaultBuffs())
      {
        BuffInstance clone=new BuffInstance(buff);
        buffsManager.addBuff(clone);
      }
    }
  }
}
