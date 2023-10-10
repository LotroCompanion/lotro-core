package delta.games.lotro.common.effects;

import java.util.List;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.Duration;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatUtils;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.lore.items.DamageType;
import delta.games.lotro.utils.maths.Progression;

/**
 * Display effects.
 * @author DAM
 */
public class EffectDisplay
{
  private int _level;
  private boolean _durationDisplayed;

  /**
   * Constructor.
   * @param level Level to use for computations.
   */
  public EffectDisplay(int level)
  {
    _level=level;
    _durationDisplayed=false;
  }

  /**
   * Display an effect.
   * @param effect Effect to show.
   */
  public void displayEffect(Effect2 effect)
  {
    String description=effect.getDescription();
    if (!description.isEmpty())
    {
      System.out.println(description);
    }
    for(EffectAspect aspect : effect.getAspects())
    {
      displayAspect(aspect);
    }
    if (!_durationDisplayed)
    {
      EffectDuration effectDuration=effect.getDuration();
      if (effectDuration!=null)
      {
        Float duration=effectDuration.getDuration();
        if (duration!=null)
        {
          String durationStr=Duration.getDurationString(duration.intValue());
          System.out.println("Duration: "+durationStr);
          _durationDisplayed=true;
        }
      }
    }
  }

  private void displayAspect(EffectAspect aspect)
  {
    if (aspect instanceof InstantVitalEffect)
    {
      showInstantVitalEffect((InstantVitalEffect)aspect);
    }
    else if (aspect instanceof StatsEffect)
    {
      showStatsEffect((StatsEffect)aspect);
    }
    else if (aspect instanceof ReactiveVitalEffect)
    {
      showReactiveVitalEffect((ReactiveVitalEffect)aspect);
    }
    else if (aspect instanceof InstantFellowshipEffect)
    {
      showInstantFellowshipEffect((InstantFellowshipEffect)aspect);
    }
    else if (aspect instanceof GenesisEffect)
    {
      showGenesisEffect((GenesisEffect)aspect);
    }
  }

  private void showInstantVitalEffect(InstantVitalEffect effect)
  {
    StatDescription stat=effect.getStat();
    VitalChangeDescription description=effect.getInstantChangeDescription();
    if (description==null)
    {
      return;
    }
    Float value=getValue(description);
    if (value!=null)
    {
      float maxValueFloat=value.floatValue();
      int maxValue=(int)maxValueFloat;
      Float variance=description.getVariance();
      if (variance!=null)
      {
        float minValueFloat=maxValueFloat*(1-variance.floatValue());
        int minValue=(int)minValueFloat;
        System.out.print(minValue+" - "+maxValue);
      }
      else
      {
        System.out.print(maxValue);
      }
    }
    else
    {
      Float minValue=description.getMinValue();
      Float maxValue=description.getMaxValue();
      System.out.println((int)minValue.floatValue()+" - "+(int)maxValue.floatValue());
    }
    System.out.println(" "+stat.getName());
  }

  private Float getValue(AbstractVitalChange change)
  {
    Float value=change.getConstant();
    if (value!=null)
    {
      return value;
    }
    Progression progression=change.getProgression();
    if (progression!=null)
    {
      value=progression.getValue(_level);
    }
    return value;
  }

  private void showStatsEffect(StatsEffect effect)
  {
    StatsProvider provider=effect.getStatsProvider();
    BasicStatsSet stats=provider.getStats(1,_level);
    String[] lines=StatUtils.getStatsDisplayLines(stats);
    for(String line : lines)
    {
      System.out.println(line);
    }
  }

  private void showReactiveVitalEffect(ReactiveVitalEffect aspect)
  {
    ReactiveVitalChange defender=aspect.getDefenderVitalChange();
    if (defender!=null)
    {
      Float value=getValue(defender);
      List<DamageType> damageTypes=aspect.getDamageTypes();
      float probability=defender.getProbability();
      boolean multiplicative=defender.isMultiplicative();
      System.out.println("On any "+damageTypes+" damage:");
      if (!multiplicative)
      {
        int percentage=(int)(probability*100);
        int damage=(int)value.floatValue();
        System.out.println(percentage+"% chance to Negate "+damage+" damage");
      }
    }
  }

  private void showInstantFellowshipEffect(InstantFellowshipEffect aspect)
  {
    Float range=aspect.getRange();
    List<EffectGenerator> effects=aspect.getEffects();
    //boolean appliesToTarget=aspect.appliesToTarget();
    int rangeInt=(int)range.floatValue();
    System.out.println("Effects applied to the Fellowship within "+rangeInt+" metres:");
    showEffectGenerators(effects);
  }

  private void showEffectGenerators(List<EffectGenerator> effects)
  {
    for(EffectGenerator effectGenerator : effects)
    {
      showEffectGenerator(effectGenerator);
    }
  }

  private void showEffectGenerator(EffectGenerator effectGenerator)
  {
    Effect2 childEffect=effectGenerator.getEffect();
    Float spellcraft=effectGenerator.getSpellcraft();
    int levelBackup=_level;
    if (spellcraft!=null)
    {
      _level=spellcraft.intValue();
    }
    displayEffect(childEffect);
    _level=levelBackup;
  }

  private void showGenesisEffect(GenesisEffect aspect)
  {
    boolean permanent=aspect.isPermanent();
    if (permanent)
    {
      System.out.println("Permanent");
    }
    else
    {
      float duration=aspect.getDuration();
      String durationStr=Duration.getDurationString((int)duration);
      System.out.println("Duration: "+durationStr);
    }
    _durationDisplayed=true;
    Hotspot hotspot=aspect.getHotspot();
    if (hotspot!=null)
    {
      showEffectGenerators(hotspot.getEffects());
    }
    // TODO Interactable
  }
}
