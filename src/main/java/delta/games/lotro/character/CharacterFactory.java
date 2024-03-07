package delta.games.lotro.character;

import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.traitTree.TraitTree;
import delta.games.lotro.character.status.traitTree.TraitTreeStatus;
import delta.games.lotro.character.status.traits.TraitsStatus;
import delta.games.lotro.character.status.traits.shared.TraitSlotsStatus;

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
    // Init traits
    TraitsStatus traitsStatus=newInfos.getTraits();
    // - Trait tree
    ClassDescription classDescription=toonSummary.getCharacterClass();
    TraitTree traitTree=classDescription.getTraitTree();
    TraitTreeStatus traitTreeStatus=new TraitTreeStatus(traitTree);
    traitsStatus.setTraitTreeStatus(traitTreeStatus);
    // - Racial traits
    TraitSlotsStatus racialTraitsStatus=new TraitSlotsStatus();
    traitsStatus.setRacialTraitsStatus(racialTraitsStatus);
    return newInfos;
  }
}
