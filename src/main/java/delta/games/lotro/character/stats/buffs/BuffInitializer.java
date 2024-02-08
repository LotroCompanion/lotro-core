package delta.games.lotro.character.stats.buffs;

import java.io.File;
import java.util.List;

import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.character.races.RacesManager;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.effects.PropertyModificationEffect;
import delta.games.lotro.common.enums.ItemClass;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.WellKnownStat;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.buffs.EffectBuff;
import delta.games.lotro.lore.buffs.io.xml.EffectBuffXMLParser;
import delta.games.lotro.lore.consumables.Consumable;
import delta.games.lotro.lore.consumables.io.xml.ConsumableXMLParser;
import delta.games.lotro.utils.IconsUtils;

/**
 * Initializes the buff registry.
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
   * Init buffs.
   * @param registry Registry to use.
   */
  public void initBuffs(BuffRegistry registry)
  {
    initSharedBuffs(registry);
    initRacialBuffs(registry);
    initEffectBasedBuffs(registry);
    initConsumableBuffs(registry);
  }

  private void initSharedBuffs(BuffRegistry registry)
  {
    // - Hope
    {
      Buff hope=new Buff("HOPE", BuffType.OTHER, GENERIC, "Hope");
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
      Buff hope=new Buff("HOPE_CELEBORN", BuffType.OTHER, GENERIC, "Hope House of Celeborn (Tier 10)");
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
    for(RaceDescription race : racesManager.getAll())
    {
      List<TraitDescription> traits=race.getEarnableTraits();
      for(TraitDescription trait : traits)
      {
        initRaceBuff(race,registry,trait);
      }
    }
  }

  private void initRaceBuff(RaceDescription race, BuffRegistry registry, TraitDescription trait)
  {
    int identifier=trait.getIdentifier();
    String name=trait.getName();
    Buff buff=new Buff(String.valueOf(identifier),BuffType.RACE, RACIAL,name);
    int iconId=trait.getIconId();
    buff.setIcon("/traits/"+iconId+".png");
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

  private void initConsumableBuffs(BuffRegistry registry)
  {
    File consumablesFile=LotroCoreConfig.getInstance().getFile(DataFiles.CONSUMABLES);
    List<Consumable> consumables=new ConsumableXMLParser().parseConsumablesFile(consumablesFile);
    for(Consumable consumable : consumables)
    {
      String id=String.valueOf(consumable.getIdentifier());
      ItemClass itemClass=consumable.getItemClass();
      String category="Consumable: "+((itemClass!=null)?itemClass.getLabel():"?");
      String name=consumable.getName();
      Buff buff=new Buff(id,BuffType.CONSUMABLE,category,name);
      String icon=IconsUtils.ITEM_ICON_PREFIX+consumable.getIcon();
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
    List<EffectBuff> effectBuffs=new EffectBuffXMLParser().parseEffectsFile(effectsFile);
    for(EffectBuff effectBuff : effectBuffs)
    {
      String id=String.valueOf(effectBuff.getIdentifier());
      String category="Effect";
      Effect effect=effectBuff.getEffect();
      String name=effect.getName();
      Buff buff=new Buff(id,BuffType.EFFECT,category,name);
      String icon="/effects/"+effect.getIconId()+".png";
      buff.setIcon(icon);
      StatsProvider statsProvider;
      if (effect instanceof PropertyModificationEffect)
      {
        PropertyModificationEffect propertyModificationEffect=(PropertyModificationEffect)effect;
        statsProvider=propertyModificationEffect.getStatsProvider();
      }
      else
      {
        statsProvider=new StatsProvider();
      }
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
    ret.addStat(stat,Float.valueOf(value));
    return ret;
  }
}
