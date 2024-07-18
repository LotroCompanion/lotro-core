package delta.games.lotro.lore.pvp;

import java.util.ArrayList;
import java.util.List;

/**
 * Keys for rank scales.
 * @author DAM
 */
public class RankScaleKeys
{
  /**
   * Renown.
   */
  private RankScaleKeys() {
  }
  
  public static final String RENOWN="renown";
  /**
   * Infamy.
   */
  public static final String INFAMY="infamy";
  /**
   * Prestige.
   */
  public static final String PRESTIGE="prestige";

  /**
   * Get the known keys.
   * @return A list of keys.
   */
  public static List<String> getKeys()
  {
    List<String> ret=new ArrayList<String>();
    ret.add(RENOWN);
    ret.add(INFAMY);
    ret.add(PRESTIGE);
    return ret;
  }
}
