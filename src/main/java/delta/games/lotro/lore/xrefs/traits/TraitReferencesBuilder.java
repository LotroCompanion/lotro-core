package delta.games.lotro.lore.xrefs.traits;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.classes.AbstractClassDescription;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassTrait;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.character.classes.traitTree.TraitTree;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.character.races.RaceTrait;
import delta.games.lotro.character.races.RacesManager;
import delta.games.lotro.character.traits.TraitDescription;
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
    for(RaceDescription race : RacesManager.getInstance().getAll())
    {
      findInRace(race, traitID);
    }
  }

  private void findInRace(RaceDescription race, int traitID)
  {
    List<RaceTrait> raceTraits=race.getTraits();
    for(RaceTrait raceTrait : raceTraits)
    {
      TraitDescription trait=raceTrait.getTrait();
      if (trait.getIdentifier()==traitID)
      {
        _storage.add(new TraitReference<RaceDescription>(race,TraitRole.RACE_TRAIT));
      }
    }
    for(TraitDescription trait : race.getEarnableTraits())
    {
      if (trait.getIdentifier()==traitID)
      {
        _storage.add(new TraitReference<RaceDescription>(race,TraitRole.RACE_TRAIT));
      }
    }
  }

  private void findInClasses(int traitID)
  {
    for(AbstractClassDescription classDescription : ClassesManager.getInstance().getAllClasses())
    {
      findInClass(classDescription,traitID);
    }
  }

  private void findInClass(AbstractClassDescription classDescription, int traitID)
  {
    // Regular traits
    List<ClassTrait> classTraits=classDescription.getTraits();
    for(ClassTrait classTrait : classTraits)
    {
      TraitDescription trait=classTrait.getTrait();
      if (trait.getIdentifier()==traitID)
      {
        _storage.add(new TraitReference<AbstractClassDescription>(classDescription,TraitRole.CLASS_TRAIT));
      }
    }
    // Traits tree
    if (classDescription instanceof ClassDescription)
    {
      ClassDescription characterClass=(ClassDescription)classDescription;
      TraitTree tree=characterClass.getTraitTree();
      if (tree==null)
      {
        return;
      }
      for(TraitDescription trait : tree.getAllTraits())
      {
        if (trait.getIdentifier()==traitID)
        {
          _storage.add(new TraitReference<ClassDescription>(characterClass,TraitRole.CLASS_TRAIT));
        }
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
        int traitRewardID=traitReward.getTrait().getIdentifier();
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
