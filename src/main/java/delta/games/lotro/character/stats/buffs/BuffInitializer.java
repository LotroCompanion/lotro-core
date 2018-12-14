package delta.games.lotro.character.stats.buffs;

import java.util.List;

import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.character.classes.TraitTree;
import delta.games.lotro.character.classes.TraitTreeBranch;
import delta.games.lotro.character.classes.TraitTreeProgression;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.character.races.RacesManager;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;
import delta.games.lotro.common.stats.StatsProvider;
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
        buff.addTier(tier,buildBasicSet(STAT.HOPE,tier));
      }
      hope.setImpl(buff);
      registry.registerBuff(hope);
    }
    // - Hope (House of Celeborn)
    {
      Buff hope=new Buff("HOPE_CELEBORN", GENERIC, "Hope House of Celeborn (Tier 10)");
      hope.setIcon("Hope_4-icon");
      BasicStatsSet stats=buildBasicSet(STAT.HOPE,10);
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
      TraitBuff buffImpl=new TraitBuff(trait);
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
      TraitBuff buffImpl=new TraitBuff(trait);
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
    // - In Defence of Middle Earth (OK Update 23)
    {
      Buff idome=new Buff("IN_DEFENCE_OF_MIDDLE_EARTH", CLASS, "In Defence of Middle-Earth");
      idome.setIcon("In_Defence_of_Middle-earth-icon");
      idome.setRequiredClass(CharacterClass.CAPTAIN);
      idome.setImpl(new InDefenceOfMiddleEarth());
      registry.registerBuff(idome);
    }
    // - Motivated (OK Update 23)
    {
      Buff motivated=new Buff("MOTIVATED", CLASS, "Motivated");
      motivated.setIcon("Motivating_Speech-icon");
      motivated.setRequiredClass(CharacterClass.CAPTAIN);
      motivated.setImpl(new Motivated());
      registry.registerBuff(motivated);
    }
  }

  private BasicStatsSet buildBasicSet(STAT stat, float value)
  {
    BasicStatsSet ret=new BasicStatsSet();
    ret.addStat(stat,new FixedDecimalsInteger(value));
    return ret;
  }
}
