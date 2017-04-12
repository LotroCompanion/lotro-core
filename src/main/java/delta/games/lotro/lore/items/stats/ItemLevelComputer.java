package delta.games.lotro.lore.items.stats;

/**
 * Computes item levels from character level.
 * @author DAM
 */
public class ItemLevelComputer
{
  /**
   * Get an item level from a character level.
   * @param characterLevel Character level.
   * @return the computed item level.
   */
  public static int getItemLevel(int characterLevel)
  {
    int itemLevel=1;
    if (characterLevel>0)
    {
      itemLevel=characterLevel;
      if ((86<=characterLevel)&&(characterLevel<=95))
      {
        itemLevel=4*itemLevel-204;
      }
      else if ((96<=characterLevel)&&(characterLevel<=97))
      {
        itemLevel=Math.round(3.2f*itemLevel-128.4f);
      }
      else if (98<=characterLevel)
      {
        itemLevel=5*characterLevel-303;
      }
    }
    return itemLevel;
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    for(int i=1;i<=105;i++)
    {
      System.out.println("Char="+i+", item level="+getItemLevel(i-1));
    }
  }
}
