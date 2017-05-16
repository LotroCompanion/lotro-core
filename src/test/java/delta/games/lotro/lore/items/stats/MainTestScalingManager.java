package delta.games.lotro.lore.items.stats;

import java.util.List;

/**
 * Test for the scaling manager.
 * @author DAM
 */
public class MainTestScalingManager
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    ScalingManager mgr=new ScalingManager();
    for(String id : ScalingRulesNames.NAMES)
    {
      System.out.println("Rule: "+id);
      ScalingRule rule=mgr.getRule(id);
      List<Integer> requiredLevels=rule.getRequiredLevels();
      for(Integer requiredLevel : requiredLevels)
      {
        Integer itemLevel=rule.getItemLevel(requiredLevel.intValue());
        System.out.println(requiredLevel+"=>"+itemLevel);
      }
    }
  }
}
