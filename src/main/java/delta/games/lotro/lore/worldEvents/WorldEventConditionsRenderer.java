package delta.games.lotro.lore.worldEvents;

import delta.common.utils.collections.filters.Operator;
import delta.games.lotro.common.utils.ComparisonOperator;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.utils.Proxy;

/**
 * Renderer for world event conditions.
 * @author DAM
 */
public class WorldEventConditionsRenderer
{
  public void renderWorldEventCondition(Achievable achievable, AbstractWorldEventCondition condition)
  {
    if (condition==null)
    {
      return;
    }
    System.out.println("Achievable: "+achievable.getName());
    if (condition instanceof SimpleWorldEventCondition)
    {
      SimpleWorldEventCondition simpleWECondition=(SimpleWorldEventCondition)condition;
      renderSimpleWorldEventCondition(achievable,simpleWECondition);
    }
    else if (condition instanceof CompoundWorldEventCondition)
    {
      CompoundWorldEventCondition compoundWECondition=(CompoundWorldEventCondition)condition;
      renderCompoundWorldEventCondition(achievable,compoundWECondition);
    }
    else
    {
      System.out.println("Complex condition: "+condition);
    }
  }

  private void renderCompoundWorldEventCondition(Achievable achievable, CompoundWorldEventCondition condition)
  {
    Operator operator=condition.getOperator();
    System.out.println(operator);
    for(AbstractWorldEventCondition childCondition : condition.getItems())
    {
      renderWorldEventCondition(achievable,childCondition);
    }
  }

  private void renderSimpleWorldEventCondition(Achievable achievable, SimpleWorldEventCondition condition)
  {
    Proxy<WorldEvent> weProxy=condition.getWorldEvent();
    Integer value=condition.getValue();
    WorldEvent we=weProxy.getObject();
    ComparisonOperator operator=condition.getOperator();
    System.out.println(we+" "+operator+" "+value);
    if (value!=null)
    {
      renderSimpleWorldEventConditionWithValue(we,operator,value);
    }
    else
    {
      boolean ok=renderSimpleWorldEventConditionWithComplexValue(we,operator,condition.getCompareToWorldEvent().getObject());
      if (!ok)
      {
        System.out.println("Unmanaged condition with complex value!");
      }
    }
  }

  private void renderSimpleWorldEventConditionWithValue(WorldEvent we, ComparisonOperator operator, Integer value)
  {
    String weProperty=we.getPropertyName();
    if ("WE_server_legendary_active".equals(weProperty))
    {
      handleLegendaryServerCondition(operator,value);
    }
    else if ("WE_Player_Level_Cap".equals(weProperty))
    {
      handlePlayerLevelCapCondition(operator,value);
    }
    else if ("ze_skirmish_level".equals(weProperty))
    {
      handleInstanceLevel(operator,value);
    }
    else if ("ze_skirmish_group_size".equals(weProperty))
    {
      handleInstanceGroupSize(operator,value);
    }
    else if ("ze_skirmish_difficulty".equals(weProperty))
    {
      handleInstanceDifficulty(operator,value);
    }
    else if ("WE_dwarfholds_eredmithrin_instance_leading_charge_lock".equals(weProperty))
    {
      handleInstanceChallengePeriod(operator,value,"the Ered Mithrin instances");
    }
    else if ("WE_vales_anduin_kidzul_kalah_instance_leading_charge_lock".equals(weProperty))
    {
      handleInstanceChallengePeriod(operator,value,"the Vales of Anduin 3-men");
    }
    else if ("WE_minas_morgul_instance_leading_charge_lock".equals(weProperty))
    {
      handleInstanceChallengePeriod(operator,value,"the Minas Morgul 3-men");
    }
    else if ("WE_minas_morgul_instance_6man_leading_charge_lock".equals(weProperty))
    {
      handleInstanceChallengePeriod(operator,value,"the Minas Morgul 6-men");
    }
    else if ("WE_minas_morgul_instance_raid_leading_charge_lock".equals(weProperty))
    {
      handleInstanceChallengePeriod(operator,value,"the Minas Morgul raid");
    }
    else if ("WE_Elderslade_6man_Aotc".equals(weProperty))
    {
      handleInstanceChallengePeriod(operator,value,"the Elderslade 6-men");
    }
    else if ("WE_Elderslade_Raid_Aotc".equals(weProperty))
    {
      handleInstanceChallengePeriod(operator,value,"the Elderslade raid");
    }
    else if ("WE_Wildwood_Instances_Aotc_Gate".equals(weProperty))
    {
      handleInstanceChallengePeriod(operator,value,"the Wildwood instances");
    }
    else if ("WE_Azanulbizar_Raid_Aotc_Gate".equals(weProperty))
    {
      handleInstanceChallengePeriod(operator,value,"the Azanulbizar raid");
    }
    else if ("WE_gundabad_3man_instance_leading_charge_lock".equals(weProperty))
    {
      handleInstanceChallengePeriod(operator,value,"the Gundabad 3-men");
    }
    else if ("WE_gundabad_6man_instance_leading_charge_lock".equals(weProperty))
    {
      handleInstanceChallengePeriod(operator,value,"the Gundabad 6-men");
    }
    else if ("WE_gundabad_12man_raid_leading_charge_lock".equals(weProperty))
    {
      handleInstanceChallengePeriod(operator,value,"the Gundabad raid");
    }
    else if (("WE_Event_DurinsDay_Counter".equals(weProperty))
        || ("WE_vales_anduin_instance_tier_gate".equals(weProperty))
        || ("WE_Vales_Anduin_Shades_Swamp_Active".equals(weProperty))
        || ("WE_dwarfholds_eredmithrin_instance_tier_3_lock".equals(weProperty))
        || ("WE_minas_morgul_instance_tier_gate".equals(weProperty))
        || ("WE_minas_morgul_instance_6man_tier_gate".equals(weProperty))
        || ("WE_minas_morgul_instance_raid_tier_gate".equals(weProperty))
        || ("WE_secret_stone_2_active".equals(weProperty))
        || ("WE_secret_stone_2_raid_boss_active".equals(weProperty))
        || ("WE_secret_stone_2_puzzle_leading_the_charge_lock".equals(weProperty))
        || ("WE_Integer_Elderslade_6man_Tier_Gate".equals(weProperty))
        || ("WE_Integer_Elderslade_Raid_Tier_Gate".equals(weProperty))
        || ("WE_Integer_Wildwood_Instance_Tier_Gate".equals(weProperty))
        || ("WE_Integer_Azanulbizar_Raid_Tier_Gate".equals(weProperty))
        || ("WE_gundabad_3man_instance_tier_gate".equals(weProperty))
        || ("WE_gundabad_6man_instance_tier_gate".equals(weProperty))
        )
    {
      // Ignore
    }
    else
    {
      System.out.println("Unmanaged property: "+weProperty);
      // WE_Elderslade_Raid_Aotc
      // WE_Azanulbizar_Raid_Aotc_Gate
      // WE_Integer_Azanulbizar_Raid_Tier_Gate
      // WE_gundabad_12man_raid_leading_charge_lock
    }
  }

  private void handleLegendaryServerCondition(ComparisonOperator operator, Integer value)
  {
    if (((operator==ComparisonOperator.EQUAL) && (value.intValue()==0))
        || ((operator==ComparisonOperator.NOT_EQUAL) && (value.intValue()==1)))
    {
      System.out.println("Not on a legendary server");
    }
    else if (((operator==ComparisonOperator.EQUAL) && (value.intValue()==1))
        || ((operator==ComparisonOperator.NOT_EQUAL) && (value.intValue()==0)))
    {
      System.out.println("On a legendary server");
    }
    else
    {
      System.out.println("Unmanaged case: operator="+operator+", value="+value);
    }
  }

  private void handlePlayerLevelCapCondition(ComparisonOperator operator, Integer value)
  {
    String operatorStr="?";
    if (operator==ComparisonOperator.EQUAL) operatorStr=" is ";
    else if (operator==ComparisonOperator.GREATER_OR_EQUAL) operatorStr=">=";
    else if (operator==ComparisonOperator.LESS_OR_EQUAL) operatorStr="=<";
    else
    {
      System.out.println("Unmanaged operator: "+operator);
    }
    System.out.println("Level cap"+operatorStr+value);
  }

  private void handleInstanceDifficulty(ComparisonOperator operator, Integer value)
  {
    String operatorStr="?";
    if (operator==ComparisonOperator.EQUAL) operatorStr=" is ";
    else if (operator==ComparisonOperator.GREATER_OR_EQUAL) operatorStr=">=";
    else
    {
      System.out.println("Unmanaged operator: "+operator);
    }
    String valueStr="Tier "+value;
    System.out.println("Instance difficulty"+operatorStr+valueStr);
  }

  private void handleInstanceLevel(ComparisonOperator operator, Integer value)
  {
    String operatorStr="?";
    if (operator==ComparisonOperator.EQUAL) operatorStr=" is ";
    else if (operator==ComparisonOperator.GREATER_OR_EQUAL) operatorStr=">=";
    else
    {
      System.out.println("Unmanaged operator: "+operator);
    }
    System.out.println("Instance level"+operatorStr+value);
    //sometimes: EQUAL WE_Player_Level_Cap
  }

  private void handleInstanceGroupSize(ComparisonOperator operator, Integer value)
  {
    String operatorStr="?";
    if (operator==ComparisonOperator.EQUAL) operatorStr=" is ";
    //else if (operator==ComparisonOperator.GREATER_OR_EQUAL) operatorStr=">=";
    else
    {
      System.out.println("Unmanaged operator: "+operator);
    }
    System.out.println("Instance size"+operatorStr+value);
  }

  private void handleInstanceChallengePeriod(ComparisonOperator operator, Integer value, String what)
  {
    if (operator==ComparisonOperator.EQUAL)
    {
      if (value.intValue()==1)
      {
        System.out.println("In the period for initial completion of challenge mode of "+what);
      }
      else
      {
        System.out.println("Unmanaged value: "+value);
      }
    }
    else
    {
      System.out.println("Unmanaged operator: "+operator);
    }
  }

  private void handleFestival(ComparisonOperator operator, Integer value, String which)
  {
    /*
    WE_SummerFestival_FestivalActive
    WE_WinterFestival_FestivalActive
    WE_FarmersFair_FestivalActive
    WE_SpringFestival_FestivalActive => Farmers Faire?
    WE_FallFestival_FestivalActive => Harvest Festival
        WE_fall_festival_maze_driver => Active Maze 1-5
    WE_Anniversary_Event_Active
    WE_GenericFestival_FestivalActive
    WE_WeddingFestival_FestivalActive
    WE_Real_SpringFestival_FestivalActive
    */
  }

  private void handleEvents()
  {
    /*
    WE_EV_Skirmish_Active => Ill Omens
    WE_ev_skirmish_driver => Ill Omens day 1-4
    */
  }

  private void handleMissions()
  {
    // WE_Integer_Elderslade_Mission_Config: 1-10 Elderslade's missions day N
    // WE_Integer_Gundabad_Mission_Config: 1-6
  }

  private void handlePVP()
  {
    /*
    World_MPControl_Ettenmoors_WestTower EQUAL 0 => Freeps control Lugazag (1 for Creeps)
    World_MPControl_Ettenmoors_EastTower EQUAL 0 => Freeps control Tirith Rhaw (1 for Creeps)
    World_MPControl_Ettenmoors_CenterKeep EQUAL 0 => Freeps control Tol Ascarnen (1 for Creeps)
    WE_Ettenmoors_TA_Player_Oil => Number of completions for quest "Oil for Boiling"
    WE_Ettenmoors_Lug_Player_Oil => Number of completions for quest "Oil for the Cauldron of Lugazag"
    WE_Ettenmoors_TR_Player_Oil => Number of completions for quest "Oil for the Cauldron of Tirith Rhaw"
    WE_Ettenmoors_TA_Player_Cauldron => Number of completions for quest "A Cauldron of Iron"
    WE_Ettenmoors_Lug_Player_Cauldron => Number of completions for quest "Iron for the Cauldron of Lugazag"
    WE_Ettenmoors_TR_Player_Cauldron => Number of completions for quest "Iron on the Walls of Tirith Rhaw"
    */

    // WE_Ettenmoors_TA_Player_Rockwithers related to "Rockwithers' Horn" (hidden)
    // WE_Ettenmoors_TA_Player_Stand related to "Pillar of Strength" (hidden)
    // WE_Ettenmoors_TA_Player_Reinforce_West related to "Signal to the West" (hidden)
    // WE_Ettenmoors_TA_Player_Reinforce_South related to "Signal to the South" (hidden)
    // WE_Ettenmoors_TA_Player_Sinew related to "Strapped for Straps" (hidden)
  }

  private boolean renderSimpleWorldEventConditionWithComplexValue(WorldEvent we, ComparisonOperator operator, WorldEvent compareTo)
  {
    String weProperty=we.getPropertyName();
    String compareToWeProperty=compareTo.getPropertyName();
    if ("ze_skirmish_level".equals(weProperty))
    {
      if ("WE_Player_Level_Cap".equals(compareToWeProperty))
      {
        if (operator==ComparisonOperator.EQUAL)
        {
          System.out.println("Instance level is player cap level");
          return true;
        }
        System.out.println("Unmanaged skirmish level/player cap property!");
      }
    }
    return false;
  }
}
