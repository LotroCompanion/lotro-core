package delta.games.lotro.character;

import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.traitTree.TraitTree;
import delta.games.lotro.character.status.traitTree.TraitTreeStatus;

/**
 * Factory method related to character data.
 * @author DAM
 */
public class CharacterFactory
{
  /**
   * Build a character data for a character.
   * @param toonSummary Parent character summary.
   * @return the new character data.
   */
  public static CharacterData buildNewData(CharacterSummary toonSummary)
  {
    CharacterData newInfos=new CharacterData();
    CharacterDataSummary dataSummary=newInfos.getSummary();
    dataSummary.setName(toonSummary.getName());
    dataSummary.setLevel(toonSummary.getLevel());
    dataSummary.setSummary(toonSummary);
    newInfos.setDate(Long.valueOf(System.currentTimeMillis()));
    // Trait tree
    ClassDescription classDescription=toonSummary.getCharacterClass();
    TraitTree traitTree=classDescription.getTraitTree();
    TraitTreeStatus status=new TraitTreeStatus(traitTree);
    newInfos.getTraits().setTraitTreeStatus(status);
    return newInfos;
  }
}
