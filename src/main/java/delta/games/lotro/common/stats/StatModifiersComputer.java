package delta.games.lotro.common.stats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.common.properties.ModPropertyList;

/**
 * Computes stat modifiers.
 * @author DAM
 */
public class StatModifiersComputer
{
  private static final Logger LOGGER=LoggerFactory.getLogger(StatModifiersComputer.class); 

  private StatValueProvider _statValueProvider;

  /**
   * Constructor.
   * @param statValueProvider Stat value provider.
   */
  public StatModifiersComputer(StatValueProvider statValueProvider)
  {
    _statValueProvider=statValueProvider;
  }

  /**
   * Compute the value of modifier properties.
   * @param mods Modifiers.
   * @return A value to add.
   */
  public float computeAdditiveModifiers(ModPropertyList mods)
  {
    if (mods==null)
    {
      return 0;
    }
    float ret=0;
    LOGGER.debug("Computing additive modifiers: {}",mods);
    for(Integer id : mods.getIDs())
    {
      float statValue=getStatValue(id.intValue());
      ret+=statValue;
    }
    LOGGER.debug("\tTotal: {}",Float.valueOf(ret));
    return ret;
  }

  /**
   * Compute the value of an modifier property.
   * @param modifier Modifier property (may be <code>null</code>).
   * @return A value to add.
   */
  public float computeAdditiveModifier(Integer modifier)
  {
    if (modifier==null)
    {
      return 0;
    }
    LOGGER.debug("Computing additive modifier: {}",modifier);
    float ret=getStatValue(modifier.intValue());
    LOGGER.debug("\tTotal: {}",Float.valueOf(ret));
    return ret;
  }

  private float getStatValue(int statID)
  {
    StatDescription stat=StatsRegistry.getInstance().getById(statID);
    if (stat==null)
    {
      return 0;
    }
    float statValue=_statValueProvider.getStat(stat);
    if (stat.isPercentage())
    {
      statValue/=100;
    }
    LOGGER.debug("\tStat {} => {}",stat.getPersistenceKey(),Float.valueOf(statValue));
    return statValue;
  }

  /**
   * Compute the value of modifier properties.
   * @param mods Modifiers.
   * @return A value to add.
   */
  public float computeMultiplicativeModifiers(ModPropertyList mods)
  {
    if (mods==null)
    {
      return 1;
    }
    float ret=1;
    LOGGER.debug("Computing multiplicative modifiers: {}",mods);
    for(Integer id : mods.getIDs())
    {
      StatDescription stat=StatsRegistry.getInstance().getById(id.intValue());
      float statValue=_statValueProvider.getStat(stat);
      if (stat.isPercentage())
      {
        statValue/=100;
      }
      LOGGER.debug("\tStat {} => {}",stat.getPersistenceKey(),Float.valueOf(statValue));
      ret*=(1+statValue);
    }
    LOGGER.debug("\tTotal: {}",Float.valueOf(ret));
    return ret;
  }
}
