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
  private int _maxUiRank;
  private Map<ItemQuality,QualityBasedData> _data;

  /**
   * Constructor.
   */
  public LegendaryData()
  {
    _maxUiRank=9;
    _data=new HashMap<ItemQuality,QualityBasedData>();
  }

  /**
   * Get the maximum legacy rank in UI.
   * @return A value.
   */
  public int getMaxUiRank()
  {
    return _maxUiRank;
  }

  /**
   * Set the maximum legacy rank in UI.
   * @param maxUiRank Value to set.
   */
  public void setMaxUiRank(int maxUiRank)
  {
    _maxUiRank=maxUiRank;
  }

  /**
   * Get the data for a quality.
   * @param quality Quality to use.
   * @param createIfNeeded Indicates if the quality data shall be created if not found.
   * @return the data for this quality or <code>null</code> if not found and not created.
   */
  public QualityBasedData getQualityData(ItemQuality quality, boolean createIfNeeded)
  {
    QualityBasedData data=_data.get(quality);
    if ((data==null) && (createIfNeeded))
    {
      data=new QualityBasedData(quality);
      _data.put(quality,data);
    }
    return data;
  }
}
