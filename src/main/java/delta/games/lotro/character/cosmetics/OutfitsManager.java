package delta.games.lotro.character.cosmetics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manager for the outfits of a character.
 * @author DAM
 */
public class OutfitsManager
{
  private Map<Integer,Outfit> _outfits;

  /**
   * Current outfit.
   * 0 for equipment outfit, n>=1 for a cosmetic outfit. 
   */
  private int _currentOutFit;

  /**
   * Constructor.
   */
  public OutfitsManager()
  {
    _outfits=new HashMap<Integer,Outfit>();
    _currentOutFit=0;
  }

  /**
   * Add an outfit.
   * @param outfitId Outfit identifier.
   * @param outfit Outfit.
   */
  public void addOutfit(int outfitId, Outfit outfit)
  {
    _outfits.put(Integer.valueOf(outfitId),outfit);
  }

  /**
   * Get the index of the current outfit.
   * @return An index.
   */
  public int getCurrentOutfitIndex()
  {
    return _currentOutFit;
  }

  /**
   * Set the index of the current outfit.
   * @param index Index of the current outfit.
   */
  public void setCurrentOutfitIndex(int index)
  {
    _currentOutFit=index;
  }

  /**
   * Get the current outfit.
   * @return An outfit.
   */
  public Outfit getCurrentOutfit()
  {
    return _outfits.get(Integer.valueOf(_currentOutFit));
  }

  /**
   * Get the indexes of all the known outfits.
   * @return A sorted list of indexes.
   */
  public List<Integer> getOutfitIndexes()
  {
    List<Integer> ret=new ArrayList<Integer>(_outfits.keySet());
    Collections.sort(ret);
    return ret;
  }

  /**
   * Get an outfit using its index.
   * @param index Index of the outfit to get.
   * @return An outfit or <code>null</code> if not found.
   */
  public Outfit getOutfit(int index)
  {
    return _outfits.get(Integer.valueOf(index));
  }
}
