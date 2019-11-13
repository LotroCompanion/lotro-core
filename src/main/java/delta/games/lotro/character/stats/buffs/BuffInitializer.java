package delta.games.lotro.character.stats.buffs;

import java.io.File;
import java.util.List;

import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.character.classes.TraitTree;
import delta.games.lotro.character.classes.TraitTreeBranch;
import delta.games.lotro.character.classes.TraitTreeProgression;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.character.races.RacesManager;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.WellKnownStat;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.buffs.EffectBuff;
import delta.games.lotro.lore.buffs.io.xml.EffectBuffXMLParser;
import delta.games.lotro.lore.consumables.Consumable;
import delta.games.lotro.lore.consumables.io.xml.ConsumableXMLParser;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Initializes a buff registry.
 * @author DAM
 */
public class BuffInitializer
{
  /**
   * Generic buffs (for all characters).
   */
  private static final String GENERIC="Generic";
  /**
   * Racial buffs.
   */
  private static final String RACIAL="Racial";
  /**
   * Class buffs.
   */
  private static final String CLASS="Class";

  /**
   * Init buffs.
   * @param registry Registry to use.
   */
  public void initBuffs(BuffRegistry registry)
  {
    initSharedBuffs(registry);
    initRacialBuffs(registry);
    initClassBuffs(registry);
    initCaptainBuffs(registry);
    initEffectBasedBuffs(registry);
    initConsumableBuffs(registry);
  }

  private void initSharedBuffs(BuffRegistry registry)
  {
    // - Hope
    {
      Buff hope=new Buff("HOPE", GENERIC, "Hope");
      hope.setIcon("Hope-icon");
      SimpleTieredBuff buff=new SimpleTieredBuff();
      for(int tier=1;tier<=10;tier++)
      {
        buff.addTier(tier,buildBasicSet(WellKnownStat.HOPE,tier));
      }
      hope.setImpl(buff);
      registry.registerBuff(hope);
    }
    // - Hope (House of Celeborn)
    {
      Buff hope=new Buff("HOPE_CELEBORN", GENERIC, "Hope House of Celeborn (Tier 10)");
      hope.setIcon("Hope_4-icon");
      BasicStatsSet stats=buildBasicSet(WellKnownStat.HOPE,10);
      SimpleStatsBuff buff=new SimpleStatsBuff(stats);
      hope.setImpl(buff);
      registry.registerBuff(hope);
    }
  }

  private void initRacialBuffs(BuffRegistry registry)
  {
    RacesManager racesManager=RacesManager.getInstance();
    for(Race race : Race.ALL_RACES)
    {
      RaceDescription description=racesManager.getRaceDescription(race);
      List<TraitDescription> traits=description.getEarnableTraits();
      for(TraitDescription trait : traits)
      {
        initRaceBuff(race,registry,trait);
      }
    }
  }

  private void initRaceBuff(Race race, BuffRegistry registry, TraitDescription trait)
  {
    if (useTrait(trait))
    {
      int identifier=trait.getIdentifier();
      String name=trait.getName();
      Buff buff=new Buff(String.valueOf(identifier),RACIAL,name);
      int iconId=trait.getIconId();
      buff.setIcon("/traitIcons/"+iconId+".png");
      buff.setRequiredRace(race);
      StatsProviderBuffImpl buffImpl=new StatsProviderBuffImpl(trait.getStatsProvider(),trait.getTiersCount());
      buff.setImpl(buffImpl);
      registry.registerBuff(buff);
      String key=trait.getKey();
      if (key.length()>0)
      {
        registry.registerBuff(key,buff);
      }
    }
  }

  private void initClassBuffs(BuffRegistry registry)
  {
    ClassesManager classesManager=ClassesManager.getInstance();
    for(CharacterClass characterClass : CharacterClass.ALL_CLASSES)
    {
      ClassDescription description=classesManager.getClassDescription(characterClass);
      TraitTree tree=description.getTraitTree();
      if (tree!=null)
      {
        List<TraitTreeBranch> branches=tree.getBranches();
        for(TraitTreeBranch branch : branches)
        {
          String name=branch.getName();
          // Progression
          TraitTreeProgression progression=branch.getProgression();
          for(TraitDescription trait : progression.getTraits())
          {
            initClassBuff(characterClass,registry,trait,name);
          }
          // Cells
          List<String> cellIds=branch.getCells();
          for(String cellId : cellIds)
          {
            TraitDescription trait=branch.getTraitForCell(cellId);
            initClassBuff(characterClass,registry,trait,name);
          }
        }
      }
    }
  }

  private void initClassBuff(CharacterClass characterClass, BuffRegistry registry, TraitDescription trait, String category)
  {
    if (useTrait(trait))
    {
      int identifier=trait.getIdentifier();
      String name=trait.getName();
      Buff buff=new Buff(String.valueOf(identifier),category,name);
      int iconId=trait.getIconId();
      buff.setIcon("/traitIcons/"+iconId+".png");
      buff.setRequiredClass(characterClass);
      StatsProviderBuffImpl buffImpl=new StatsProviderBuffImpl(trait.getStatsProvider(),trait.getTiersCount());
      buff.setImpl(buffImpl);
      registry.registerBuff(buff);
      String key=trait.getKey();
      if (key.length()>0)
      {
        registry.registerBuff(key,buff);
      }
    }
  }

  private boolean useTrait(TraitDescription trait)
  {
    StatsProvider statsProvider=trait.getStatsProvider();
    int nbStats=statsProvider.getNumberOfStatProviders();
    return nbStats>0;
  }

  private void initCaptainBuffs(BuffRegistry registry)
  {
    // Captain buffs
    // - Motivated (OK Update 23)
    {
      Buff motivated=new Buff("MOTIVATED", CLASS, "Motivated");
      motivated.setIcon("Motivating_Speech-icon");
      // This buff is fellowship-wide... allow it for all classes
      //motivated.setRequiredClass(CharacterClass.CAPTAIN);
      motivated.setImpl(new Motivated());
      registry.registerBuff(motivated);
    }
  }

  private void initConsumableBuffs(BuffRegistry registry)
  {
    File consumablesFile=LotroCoreConfig.getInstance().getFile(DataFiles.CONSUMABLES);
    List<Consumable> consumables=ConsumableXMLParser.parseConsumablesFile(consumablesFile);
    for(Consumable consumable : consumables)
    {
      String id=String.valueOf(consumable.getIdentifier());
      String category="Consumable: "+consumable.getCategory();
      String name=consumable.getName();
      Buff buff=new Buff(id,category,name);
      String icon="/icons/"+consumable.getIcon()+".png";
      buff.setIcon(icon);
      StatsProvider statsProvider=consumable.getProvider();
      StatsProviderBuffImpl impl=new StatsProviderBuffImpl(statsProvider,1);
      buff.setImpl(impl);
      registry.registerBuff(buff);
    }
  }

  private void initEffectBasedBuffs(BuffRegistry registry)
  {
    File effectsFile=LotroCoreConfig.getInstance().getFile(DataFiles.BUFFS);
    List<EffectBuff> effectBuffs=EffectBuffXMLParser.parseEffectsFile(effectsFile);
    for(EffectBuff effectBuff : effectBuffs)
    {
      String id=String.valueOf(effectBuff.getIdentifier());
      String category="Misc";
      Effect effect=effectBuff.getEffect();
      String name=effect.getName();
      Buff buff=new Buff(id,category,name);
      String icon="/effectIcons/"+effect.getIconId()+".png";
      buff.setIcon(icon);
      StatsProvider statsProvider=effect.getStatsProvider();
      StatsProviderBuffImpl impl=new StatsProviderBuffImpl(statsProvider,1);
      buff.setImpl(impl);
      registry.registerBuff(buff);
      String key=effectBuff.getKey();
      if (key.length()>0)
      {
        registry.registerBuff(key,buff);
      }
    }
  }

  private BasicStatsSet buildBasicSet(StatDescription stat, float value)
  {
    BasicStatsSet ret=new BasicStatsSet();
    ret.addStat(stat,new FixedDecimalsInteger(value));
    return ret;
  }
}
