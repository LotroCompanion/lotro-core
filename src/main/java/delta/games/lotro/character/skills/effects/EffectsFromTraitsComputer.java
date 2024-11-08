package delta.games.lotro.character.skills.effects;

import java.util.List;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.classes.traitTree.TraitTree;
import delta.games.lotro.character.skills.SkillEffectGenerator;
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
import delta.games.lotro.values.ArrayValue;
import delta.games.lotro.values.StructValue;

/**
 * Find properties that give effects.
 * @author DAM
 */
public class EffectsFromTraitsComputer
{
  private EffectProperties _storage;

  /**
   * Inspect the traits of a character to find properties that given effects.
   * @param data Character data.
   * @return the loaded data.
   */
  public EffectProperties inspectTraits(CharacterData data)
  {
    _storage=new EffectProperties();
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
    return _storage;
  }

  private void handleTrait(TraitDescription trait, int traitRank)
  {
    //System.out.println("trait: "+trait);
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

  private void handleEffect(Effect effect)
  {
    //System.out.println("\tUsing effect: "+effect);
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
    //System.out.println("\t\t'"+stat.getName()+"' => "+effect);
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
