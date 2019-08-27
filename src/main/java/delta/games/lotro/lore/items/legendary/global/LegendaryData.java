package delta.games.lotro.lore.items.legendary.global;

import java.util.HashMap;
import java.util.Map;

import delta.games.lotro.lore.items.ItemQuality;

/**
 * Facade to access to legendary system data.
 * @author DAM
 */
public class LegendaryData
{
  private Map<ItemQuality,QualityBasedData> _data;

  /**
   * Constructor.
   */
  public LegendaryData()
  {
    _data=new HashMap<ItemQuality,QualityBasedData>();
  }

  /**
   * Get the data for a quality.
   * @param quality Quality to use.
   * @return the data for this quality.
   */
  public QualityBasedData getQualityData(ItemQuality quality)
  {
    QualityBasedData data=_data.get(quality);
    if (data==null)
    {
      data=new QualityBasedData(quality);
      _data.put(quality,data);
    }
    return data;
  }
}
