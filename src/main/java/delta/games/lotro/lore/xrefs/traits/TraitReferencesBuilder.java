package delta.games.lotro.lore.xrefs.traits;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassTrait;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.character.classes.traitTree.TraitTree;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.character.races.RaceTrait;
import delta.games.lotro.character.races.RacesManager;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;
import delta.games.lotro.common.rewards.RewardElement;
import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.common.rewards.SelectableRewardElement;
import delta.games.lotro.common.rewards.TraitReward;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.DeedsManager;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.quests.QuestsManager;

/**
 * Finds references to traits.
 * @author DAM
 */
public class TraitReferencesBuilder
{
  private List<TraitReference<?>> _storage;

  /**
   * Constructor.
   */
  public TraitReferencesBuilder()
  {
    _storage=new ArrayList<TraitReference<?>>();
  }

  /**
   * Search for a trait.
   * @param traitID Trait identifier.
   * @return the found references.
   */
  public List<TraitReference<?>> inspectTrait(int traitID)
  {
    _storage.clear();
    findInRaces(traitID);
    findInClasses(traitID);
    findInRewards(traitID);
    List<TraitReference<?>> ret=new ArrayList<TraitReference<?>>(_storage);
    _storage.clear();
    return ret;
  }

  private void findInRaces(int traitID)
  {
    for(Race race : Race.ALL_RACES)
    {
      findInRace(race, traitID);
    }
  }

  private void findInRace(Race race, int traitID)
  {
    RacesManager mgr=RacesManager.getInstance();
    RaceDescription raceDescription=mgr.getRaceDescription(race);
    List<RaceTrait> raceTraits=raceDescription.getTraits();
    for(RaceTrait raceTrait : raceTraits)
    {
      TraitDescription trait=raceTrait.getTrait();
      if (trait.getIdentifier()==traitID)
      {
        _storage.add(new TraitReference<Race>(race,TraitRole.RACE_TRAIT));
      }
    }
    for(TraitDescription trait : raceDescription.getEarnableTraits())
    {
      if (trait.getIdentifier()==traitID)
      {
        _storage.add(new TraitReference<Race>(race,TraitRole.RACE_TRAIT));
      }
    }
  }

  private void findInClasses(int traitID)
  {
    for(CharacterClass cClass : CharacterClass.ALL_CLASSES)
    {
      findInClass(cClass, traitID);
    }
  }

  private void findInClass(CharacterClass cClass, int traitID)
  {
    ClassesManager mgr=ClassesManager.getInstance();
    ClassDescription classDescription=mgr.getClassDescription(cClass);
    // Regular traits
    List<ClassTrait> classTraits=classDescription.getTraits();
    for(ClassTrait classTrait : classTraits)
    {
      TraitDescription trait=classTrait.getTrait();
      if (trait.getIdentifier()==traitID)
      {
        _storage.add(new TraitReference<CharacterClass>(cClass,TraitRole.CLASS_TRAIT));
      }
    }
    // Traits tree
    TraitTree tree=classDescription.getTraitTree();
    for(TraitDescription trait : tree.getAllTraits())
    {
      if (trait.getIdentifier()==traitID)
      {
        _storage.add(new TraitReference<CharacterClass>(cClass,TraitRole.CLASS_TRAIT));
      }
    }
  }

  private void findInRewards(int traitID)
  {
    // Quests
    QuestsManager questsManager=QuestsManager.getInstance();
    List<QuestDescription> quests=questsManager.getAll();
    for(QuestDescription quest : quests)
    {
      findInRewards(quest,quest.getRewards(),traitID);
    }
    // Deeds
    DeedsManager deedsManager=DeedsManager.getInstance();
    List<DeedDescription> deeds=deedsManager.getAll();
    for(DeedDescription deed : deeds)
    {
      findInRewards(deed,deed.getRewards(),traitID);
    }
  }

  private void findInRewards(Achievable context, Rewards rewards, int traitID)
  {
    List<RewardElement> elements=rewards.getRewardElements();
    findInRewardsElements(context,elements,traitID);
  }

  private void findInRewardsElements(Achievable context, List<RewardElement> elements, int traitID)
  {
    for(RewardElement element : elements)
    {
      if (element instanceof TraitReward)
      {
        TraitReward traitReward=(TraitReward)element;
        int traitRewardID=traitReward.getTraitProxy().getId();
        if (traitRewardID==traitID)
        {
          TraitRole role=TraitRole.REWARD;
          _storage.add(new TraitReference<Achievable>(context,role));
        }
      }
      else if (element instanceof SelectableRewardElement)
      {
        SelectableRewardElement selectableReward=(SelectableRewardElement)element;
        findInRewardsElements(context,selectableReward.getElements(),traitID);
      }
    }
  }
}
