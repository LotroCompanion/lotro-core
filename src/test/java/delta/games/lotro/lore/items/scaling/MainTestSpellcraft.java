package delta.games.lotro.lore.items.scaling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.CharactersManager;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.comparators.NamedComparator;
import delta.games.lotro.common.stats.StatUtils;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.utils.maths.Progression;

/**
 * Test item spellcraft computations.
 * @author DAM
 */
public class MainTestSpellcraft
{
  private List<Item> getItemsWithSpellcraft()
  {
    List<Item> ret=new ArrayList<Item>();
    for(Item item : ItemsManager.getInstance().getAllItems())
    {
      ItemSpellcraft spellcraft=item.getSpellcraft();
      if (spellcraft!=null)
      {
        ret.add(item);
      }
    }
    return ret;
  }

  private Map<String,List<Item>> sortByPropertyName(List<Item> items)
  {
    Map<String,List<Item>> ret=new HashMap<String,List<Item>>();
    for(Item item : items)
    {
      String property=item.getSpellcraft().getPropertyName();
      List<Item> list=ret.get(property);
      if (list==null)
      {
        list=new ArrayList<Item>();
        ret.put(property,list);
      }
      list.add(item);
    }
    return ret;
  }

  private SpellcraftResolver buildResolver()
  {
    CharacterFile file=CharactersManager.getInstance().getToonById("Landroval","Utharr");
    CharacterData data=file.getInfosManager().getCurrentData();
    // Overrides
    data.getSummary().setLevel(150);
    file.getSummary().setRankCode(Integer.valueOf(10));
    SpellcraftResolver resolver=new SpellcraftResolver(file,data);
    return resolver;
  }

  private void showStats(String meaning, BasicStatsSet stats)
  {
    System.out.println("\t\t*** "+meaning);
    String[] lines=StatUtils.getStatsDisplayLines(stats);
    for(String line : lines)
    {
      System.out.println("\t\t"+line);
    }
  }

  private void handleItem(Item item, SpellcraftResolver resolver)
  {
    System.out.println("\t"+item);
    Progression progression=item.getSpellcraft().getProgression();
    if (progression!=null)
    {
      System.out.println("\t"+progression);
    }
    BasicStatsSet stats=item.getStats();
    showStats("current",stats);
    Integer itemLevel=resolver.getEffectiveSpellcraft(item);
    if (itemLevel==null)
    {
      itemLevel=item.getItemLevelForStats();
    }
    StatsProvider statsProvider=item.getStatsProvider();
    if (statsProvider!=null)
    {
      BasicStatsSet newStats=statsProvider.getStats(1,itemLevel.intValue());
      showStats("scaled",newStats);
    }
  }

  private void doIt()
  {
    List<Item> items=getItemsWithSpellcraft();
    Map<String,List<Item>> sortedItems=sortByPropertyName(items);
    List<String> propertyNames=new ArrayList<String>(sortedItems.keySet());
    Collections.sort(propertyNames);
    SpellcraftResolver resolver=buildResolver();
    for(String propertyName : propertyNames)
    {
      System.out.println("Property: "+propertyName);
      List<Item> itemsForProperty=sortedItems.get(propertyName);
      Collections.sort(itemsForProperty,new NamedComparator());
      for(Item item : itemsForProperty)
      {
        handleItem(item,resolver);
      }
    }
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new MainTestSpellcraft().doIt();
  }
}
