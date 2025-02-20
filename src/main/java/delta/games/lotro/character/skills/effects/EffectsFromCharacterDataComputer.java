package delta.games.lotro.character.skills.effects;

import java.util.List;

import delta.common.utils.NumericTools;
import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.traitTree.TraitTree;
import delta.games.lotro.character.gear.CharacterGear;
import delta.games.lotro.character.gear.GearSlot;
import delta.games.lotro.character.gear.GearSlotContents;
import delta.games.lotro.character.gear.GearSlots;
import delta.games.lotro.character.skills.SkillEffectGenerator;
import delta.games.lotro.character.stats.buffs.BuffInstance;
import delta.games.lotro.character.stats.buffs.BuffsManager;
import delta.games.lotro.character.status.traitTree.TraitTreeStatus;
import delta.games.lotro.character.traits.EffectAtRank;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.EffectGenerator;
import delta.games.lotro.common.effects.EffectsManager;
import delta.games.lotro.common.effects.PropertyModificationEffect;
import delta.games.lotro.common.enums.ImplementUsageType;
import delta.games.lotro.common.enums.ImplementUsageTypes;
import delta.games.lotro.common.stats.GenericConstantStatProvider;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatProvider;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.effects.ItemEffectsManager;
import delta.games.lotro.lore.items.effects.ItemEffectsManager.Type;
import delta.games.lotro.values.ArrayValue;
import delta.games.lotro.values.StructValue;

/**
 * Find properties that give effects.
 * @author DAM
 */
public class EffectsFromCharacterDataComputer
{
  private EffectProperties _storage;

  /**
   * Inspect a character to find properties that give effects.
   * @param data Character data.
   * @return the loaded data.
   */
  public EffectProperties inspect(CharacterData data)
  {
    _storage=new EffectProperties();
    inspectTraits(data);
    inspectBuffs(data);
    inspectEquipment(data);
    // TODO inspect the character effects manager?
    return _storage;
  }

  private void inspectEquipment(CharacterData data)
  {
    CharacterGear gear=data.getEquipment();
    // Iterate on slots
    for(GearSlot slot : GearSlots.getSlotsForStatsComputation())
    {
      GearSlotContents slotContents=gear.getSlotContents(slot,false);
      if (slotContents!=null)
      {
        ItemInstance<?> item=slotContents.getItem();
        if (item!=null)
        {
          inspectItem(item.getReference());
        }
      }
    }
  }

  private void inspectItem(Item item)
  {
    ItemEffectsManager mgr=item.getEffects();
    if (mgr!=null)
    {
      EffectGenerator[] effects=mgr.getEffects(Type.ON_EQUIP);
      for(EffectGenerator effect : effects)
      {
        handleEffect(effect.getEffect());
      }
    }
  }

  /**
   * Inspect the traits of a character to find properties that give effects.
   * @param data Character data.
   */
  private void inspectTraits(CharacterData data)
  {
    inspectTraitTree(data);
    inspectTraitsFromClass(data);
  }

  private void inspectTraitTree(CharacterData data)
  {
    TraitTreeStatus status=data.getTraits().getTraitTreeStatus();
    TraitTree tree=status.getTraitTree();
    for(TraitDescription trait : tree.getAllTraits())
    {
      Integer rank=status.getRankForTrait(trait.getIdentifier());
      if ((rank!=null) && (rank.intValue()>0))
      {
        handleTrait(trait,rank.intValue());
      }
    }
    // Handle unlocked traits
    List<TraitDescription> unlockedTraits=status.getUnlockedTraits();
    for(TraitDescription trait : unlockedTraits)
    {
      handleTrait(trait,1);
    }
  }

  private void inspectTraitsFromClass(CharacterData data)
  {
    ClassDescription characterClass=data.getCharacterClass();
    if (characterClass==null)
    {
      return;
    }
    int level=data.getLevel();
    List<TraitDescription> traits=characterClass.getTraitsForLevel(level);
    for(TraitDescription trait : traits)
    {
      handleTrait(trait,1);
    }
  }

  private void handleTrait(TraitDescription trait, int traitRank)
  {
    for(EffectGenerator generator : trait.getEffectGenerators())
    {
      Effect effect=generator.getEffect();
      handleEffect(effect);
    }
    for(EffectAtRank effectAtRank : trait.getEffects())
    {
      int rank=effectAtRank.getRank();
      if (rank==traitRank)
      {
        Effect effect=effectAtRank.getEffect();
        handleEffect(effect);
      }
    }
  }

  /**
   * Inspect the traits of a character to find properties that given effects.
   * @param data Character data.
   */
  private void inspectBuffs(CharacterData data)
  {
    BuffsManager buffs=data.getBuffs();
    for(int i=0;i<buffs.getBuffsCount();i++)
    {
      BuffInstance buff=buffs.getBuffAt(i);
      String idStr=buff.getBuff().getId();
      Integer id=NumericTools.parseInteger(idStr);
      if (id!=null)
      {
        Effect effect=EffectsManager.getInstance().getEffectById(id.intValue());
        if (effect!=null)
        {
          handleEffect(effect);
        }
      }
    }
  }

  private void handleEffect(Effect effect)
  {
    if (effect instanceof PropertyModificationEffect)
    {
      PropertyModificationEffect propModEffect=(PropertyModificationEffect)effect;
      StatsProvider statsProvider=propModEffect.getStatsProvider();
      if (statsProvider!=null)
      {
        for(StatProvider statProvider : statsProvider.getStatProviders())
        {
          if (statProvider instanceof GenericConstantStatProvider)
          {
            @SuppressWarnings("unchecked")
            GenericConstantStatProvider<Object> genericProvider=(GenericConstantStatProvider<Object>)statProvider;
            handlePropertyValue(genericProvider.getStat(),genericProvider.getRawValue());
          }
        }
      }
    }
  }

  private void handlePropertyValue(StatDescription stat, Object value)
  {
    if (value instanceof ArrayValue)
    {
      ArrayValue arrayValue=(ArrayValue)value;
      int size=arrayValue.getSize();
      for(int i=0;i<size;i++)
      {
        Object childValue=arrayValue.getValueAt(i);
        handlePropertyValue(stat,childValue);
      }
    }
    if (value instanceof StructValue)
    {
      StructValue structValue=(StructValue)value;
      Object skillEffectID=structValue.getValue("Skill_Effect");
      if (skillEffectID!=null)
      {
        handleStruct(stat,structValue);
      }
    }
  }

  private void handleStruct(StatDescription stat, StructValue structValue)
  {
    int skillEffectID=((Integer)structValue.getValue("Skill_Effect")).intValue();
    if (skillEffectID==0)
    {
      return;
    }
    Effect effect=EffectsManager.getInstance().getEffectById(skillEffectID);
    int propertyID=stat.getIdentifier();
    // Spellcraft
    Float spellcraft=null;
    SkillEffectGenerator generator=new SkillEffectGenerator(effect,spellcraft,null);
    // Implement usage
    Integer implementUsageCode=(Integer)structValue.getValue("Skill_EffectImplementUsage");
    if ((implementUsageCode!=null) && (implementUsageCode.intValue()>0))
    {
      ImplementUsageType implementUsage=ImplementUsageTypes.getByCode(implementUsageCode.intValue());
      generator.setImplementUsage(implementUsage);
    }
    _storage.addEffectToProperty(propertyID,generator);
  }

}
