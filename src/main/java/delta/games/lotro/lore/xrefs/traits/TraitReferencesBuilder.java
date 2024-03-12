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
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.details.GrantType;
import delta.games.lotro.lore.items.details.GrantedElement;
import delta.games.lotro.lore.items.details.ItemDetailsManager;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.quests.QuestsManager;
import delta.games.lotro.lore.xrefs.Reference;

/**
 * Finds references to traits.
 * @author DAM
 */
public class TraitReferencesBuilder
{
  private List<Reference<?,TraitRole>> _storage;

  /**
   * Constructor.
   */
  public TraitReferencesBuilder()
  {
    _storage=new ArrayList<Reference<?,TraitRole>>();
  }

  /**
   * Search for a trait.
   * @param traitID Trait identifier.
   * @return the found references.
   */
  public List<Reference<?,TraitRole>> inspectTrait(int traitID)
  {
    _storage.clear();
    findInRaces(traitID);
    findInClasses(traitID);
    findInRewards(traitID);
    findInItems(traitID);
    List<Reference<?,TraitRole>> ret=new ArrayList<Reference<?,TraitRole>>(_storage);
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
        _storage.add(new Reference<RaceDescription,TraitRole>(race,TraitRole.RACE_TRAIT));
      }
    }
    for(TraitDescription trait : race.getEarnableTraits())
    {
      if (trait.getIdentifier()==traitID)
      {
        _storage.add(new Reference<RaceDescription,TraitRole>(race,TraitRole.RACE_TRAIT));
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
        _storage.add(new Reference<AbstractClassDescription,TraitRole>(classDescription,TraitRole.CLASS_TRAIT));
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
          _storage.add(new Reference<ClassDescription,TraitRole>(characterClass,TraitRole.CLASS_TRAIT));
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
          _storage.add(new Reference<Achievable,TraitRole>(context,role));
        }
      }
      else if (element instanceof SelectableRewardElement)
      {
        SelectableRewardElement selectableReward=(SelectableRewardElement)element;
        findInRewardsElements(context,selectableReward.getElements(),traitID);
      }
    }
  }

  private void findInItems(int traitID)
  {
    for(Item item : ItemsManager.getInstance().getAllItems())
    {
      ItemDetailsManager details=item.getDetails();
      if (details==null)
      {
        continue;
      }
      for(GrantedElement<?> granted : details.getItemDetails(GrantedElement.class))
      {
        if (granted.getType()==GrantType.TRAIT)
        {
          TraitDescription trait=(TraitDescription)granted.getGrantedElement();
          if (trait.getIdentifier()==traitID)
          {
            _storage.add(new Reference<Item,TraitRole>(item,TraitRole.ITEM_TRAIT));
          }
        }
      }
    }
  }

}
