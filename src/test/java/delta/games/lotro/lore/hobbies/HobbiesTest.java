package delta.games.lotro.lore.hobbies;

import delta.games.lotro.lore.hobbies.rewards.HobbyRewardEntry;
import delta.games.lotro.lore.hobbies.rewards.HobbyRewards;
import delta.games.lotro.lore.hobbies.rewards.HobbyRewardsProfile;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.maps.GeoAreasManager;
import delta.games.lotro.lore.maps.Territory;
import delta.games.lotro.lore.titles.TitleDescription;
import junit.framework.TestCase;

/**
 * Test class for hobbies.
 * @author DAM
 */
public class HobbiesTest extends TestCase
{
  /**
   * Test hobbies loading. Dump the results.
   */
  public void testHobbiesLoading()
  {
    HobbiesManager hobbiesMgr=HobbiesManager.getInstance();
    for(HobbyDescription hobby : hobbiesMgr.getAll())
    {
      // Attributes
      int id=hobby.getIdentifier();
      String name=hobby.getName();
      System.out.println("***** ID="+id+", name="+name+" ********");
      int hobbyType=hobby.getHobbyType();
      System.out.println("Hobby type: "+hobbyType);
      int iconID=hobby.getIconId();
      System.out.println("Icon ID: "+iconID);
      int minLevel=hobby.getMinLevel();
      System.out.println("Min level: "+minLevel);
      int dailyProficiencyGainLimit=hobby.getDailyProficiencyGainLimit();
      System.out.println("Daily proficiency gain limit: "+dailyProficiencyGainLimit);
      String description=hobby.getDescription();
      System.out.println("Description: "+description);
      String trainerInfo=hobby.getTrainerDisplayInfo();
      System.out.println("Trainer info: "+trainerInfo);
      String proficiencyPropertyName=hobby.getProficiencyPropertyName();
      System.out.println("Proficiency property name: "+proficiencyPropertyName);
      String proficiencyModifierPropertyName=hobby.getProficiencyModifierPropertyName();
      System.out.println("Proficiency modifier property name: "+proficiencyModifierPropertyName);
      String treasureProfileOverridePropertName=hobby.getTreasureProfileOverridePropertyName();
      System.out.println("Treasure profile override property name: "+treasureProfileOverridePropertName);
      // Items
      for(Item item : hobby.getItems())
      {
        System.out.println("Item: "+item);
      }
      // Titles
      for(HobbyTitleEntry titleEntry : hobby.getTitles())
      {
        TitleDescription title=titleEntry.getTitle();
        int proficiency=titleEntry.getProficiency();
        System.out.println("Title: "+proficiency+" => "+title);
      }
      // Rewards
      HobbyRewards rewards=hobby.getRewards();
      for(Integer terrorityID : rewards.getKnownTerritories())
      {
        Territory territory=GeoAreasManager.getInstance().getTerritoryById(terrorityID.intValue()); 
        System.out.println(territory);
        HobbyRewardsProfile profile=rewards.getProfile(terrorityID.intValue());
        if (profile!=null)
        {
          for(HobbyRewardEntry entry : profile.getEntries())
          {
            Item item=entry.getItem();
            int minProficiency=entry.getMinProficiency();
            int maxProficiency=entry.getMaxProficiency();
            int weight=entry.getWeight();
            System.out.println("Proficiency: "+minProficiency+"/"+maxProficiency+", weight="+weight+" => "+item);
          }
        }
      }
    }
  }
}
