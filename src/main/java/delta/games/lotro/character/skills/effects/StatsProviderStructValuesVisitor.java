package delta.games.lotro.character.skills.effects;

import java.util.function.BiFunction;

import delta.games.lotro.common.stats.GenericConstantStatProvider;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatProvider;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.values.ArrayValue;
import delta.games.lotro.values.StructValue;

/**
 * Visitor for struct values inside stats providers.
 * @author DAM
 */
public class StatsProviderStructValuesVisitor
{
  private BiFunction<StatDescription,StructValue,Void> _callback;

  /**
   * Constructor.
   * @param callback Callback.
   */
  public StatsProviderStructValuesVisitor(BiFunction<StatDescription,StructValue,Void> callback)
  {
    _callback=callback;
  }

  /**
   * Inspect a stats provider.
   * @param statsProvider Input stats provider.
   */
  public void inspectStatsProvider(StatsProvider statsProvider)
  {
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
      _callback.apply(stat,structValue);
    }
  }
}
