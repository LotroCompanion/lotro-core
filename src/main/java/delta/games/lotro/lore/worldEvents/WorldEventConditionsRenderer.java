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
      boolean ok=handleMissions(we,operator,value);
      if (ok)
      {
        return;
      }
      ok=handleBooleanConditions(we,operator,value);
      if (ok)
      {
        return;
      }
      ok=handleEventParts(we,operator,value);
      if (ok)
      {
        return;
      }
      System.out.println("Unmanaged property: "+weProperty);
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

  private boolean handleEventParts(WorldEvent we, ComparisonOperator operator, Integer value)
  {
    String weProperty=we.getPropertyName();
    if ("WE_fall_festival_maze_driver".equals(weProperty)) handleEventPart(operator,value,"Active Maze of Harvest Festival is #VALUE");
    else if ("WE_ev_skirmish_driver".equals(weProperty)) handleEventPart(operator,value,"Active during Ill Omens day VALUE");
    else if ("WE_Bingo_Boffin_Current_Week".equals(weProperty)) handleEventPart(operator,value,"Bingo Boffin week OPERATOR VALUE");
    else if ("WE_Anniversary_Event_Current_Week".equals(weProperty)) handleEventPart(operator,value,"Anniversary Event Scavenger Hunt week OPERATOR VALUE");
    else if ("WE_Episodic_LRR_Current_Week".equals(weProperty)) handleEventPart(operator,value, "Ballad of Bingo Boffin week OPERATOR VALUE");
    else
    {
      return false;
    }
    return true;
  }

  private boolean handleBooleanConditions(WorldEvent we, ComparisonOperator operator, Integer value)
  {
    String weProperty=we.getPropertyName();
    // Festivals
    if ("WE_SummerFestival_FestivalActive".equals(weProperty)) handleBooleanCondition(operator,value,"Active during the Summer Festival");
    // - yule
    else if ("WE_WinterFestival_FestivalActive".equals(weProperty)) handleBooleanCondition(operator,value,"Active during the Yule Festival");
    else if ("we_winterfestival_town_active".equals(weProperty)) handleBooleanCondition(operator,value,"Yule Festival: town quests active");
    else if ("we_winterfestival_eatingcontest_active".equals(weProperty)) handleBooleanCondition(operator,value,"Yule Festival: eating contest active");
    else if ("we_winterfestival_snowballfight_active".equals(weProperty)) handleBooleanCondition(operator,value,"Yule Festival: snowball fight active");
    else if ("we_winterfestival_eatingcontest_status".equals(weProperty)) handleBooleanCondition(operator,value,null,"Yule Festival: eating contest not started");
    else if ("we_winterfestival_snowballfight_status".equals(weProperty)) handleBooleanCondition(operator,value,null,"Yule Festival: snowball fight not started");
    else if ("WE_FarmersFair_FestivalActive".equals(weProperty)) handleBooleanCondition(operator,value,"Active during the Farmers Faire");
    else if ("WE_SpringFestival_FestivalActive".equals(weProperty)) handleBooleanCondition(operator,value,"Active during the Farmers Faire"); // ?
    else if ("WE_FallFestival_FestivalActive".equals(weProperty)) handleBooleanCondition(operator,value,"Active during the Harvest Festival");
    else if ("WE_Anniversary_Event_Active".equals(weProperty)) handleBooleanCondition(operator,value,"Active during the Anniversary Festival");
    else if ("WE_WeddingFestival_FestivalActive".equals(weProperty)) handleBooleanCondition(operator,value,"Active during the Wedding Festival");
    else if ("WE_Anniversary_Event_Active".equals(weProperty)) handleBooleanCondition(operator,value,"Active during the Anniversary Festival");
    else if ("WE_Real_SpringFestival_FestivalActive".equals(weProperty)) handleBooleanCondition(operator,value,"Active during the Spring Festival");
    else if ("WE_GenericFestival_FestivalActive".equals(weProperty)) handleBooleanCondition(operator,value,"Active during any Festival");
    // Dailies
    // - Mordor
    else if ("WE_Gorgoroth_Endgame_Event_1_Udun_DorAmarth".equals(weProperty)) handleBooleanCondition(operator,value,"Mordor dailies: Udûn and Dor Amarth featured");
    else if ("WE_Gorgoroth_Endgame_Event_2_Lhingris_TalathUrui".equals(weProperty)) handleBooleanCondition(operator,value,"Mordor dailies: Lhingris and Talath Úrui featured");
    else if ("WE_Gorgoroth_Endgame_Event_3_DorAmarth_Agarnaith".equals(weProperty)) handleBooleanCondition(operator,value,"Mordor dailies: Dor Amarth and Agarnaith featured");
    else if ("WE_Gorgoroth_Endgame_Event_4_Udun_TalathUrui".equals(weProperty)) handleBooleanCondition(operator,value,"Mordor dailies: Udûn and Talath Úrui featured");
    else if ("WE_Gorgoroth_Endgame_Event_5_Agarnaith_Lhingris".equals(weProperty)) handleBooleanCondition(operator,value,"Mordor dailies: Agarnaith and Lhingris featured");
    // - Strongholds of the North
    else if ("WE_Mirkwood_North_Endgame_Driven_Daily_Set_1".equals(weProperty)) handleBooleanCondition(operator,value,"Set 1 of Stronghold of the North Dailies");
    else if ("WE_Mirkwood_North_Endgame_Driven_Daily_Set_2".equals(weProperty)) handleBooleanCondition(operator,value,"Set 2 of Stronghold of the North Dailies");
    else if ("WE_Mirkwood_North_Endgame_Driven_Daily_Set_3".equals(weProperty)) handleBooleanCondition(operator,value,"Set 3 of Stronghold of the North Dailies");
    // Events
    else if ("WE_Trollshaws_FrodoBilboBirthday_active".equals(weProperty)) handleBooleanCondition(operator,value,"Baggins' Birthday is active");
    else if ("WE_EV_Skirmish_Active".equals(weProperty)) handleBooleanCondition(operator,value,"Ill Omens event is active");
    else if ("WE_Bingo_Boffin_Active".equals(weProperty)) handleBooleanCondition(operator,value,"Bingo Boffin event is active");
    
    // PVP
    else if ("World_MPControl_Ettenmoors_WestTower".equals(weProperty)) handleBooleanCondition(operator,value,"Creeps control Lugazag","Freeps control Lugazag");
    else if ("World_MPControl_Ettenmoors_EastTower".equals(weProperty)) handleBooleanCondition(operator,value,"Creeps control Tirith Rhaw","Freeps control Tirith Rhaw");
    else if ("World_MPControl_Ettenmoors_CenterKeep".equals(weProperty)) handleBooleanCondition(operator,value,"Creeps control Tol Ascarnen","Freeps control Tol Ascarnen");
    // World_MPControl_Ettenmoors_Lumberyard
    // World_MPControl_Ettenmoors_Mine
    // Time of Day
    else if ("World_IsNight".equals(weProperty)) handleBooleanCondition(operator,value,"At Night","During daytime");
    // Maps state
    // - Forochel
    else if ("WE_Forochel_ControlPOI_Glacier".equals(weProperty)) handleBooleanCondition(operator,value,"Hylje-leiri is ?");
    else if ("WE_Forochel_ControlPOI_Icebay".equals(weProperty)) handleBooleanCondition(operator,value,"Karhu-leiri is ?");
    else if ("WE_Forochel_ControlPOI_TundraEast".equals(weProperty)) handleBooleanCondition(operator,value,"Pynti-leiri is ?");
    else if ("WE_Forochel_ControlPOI_TundraWest".equals(weProperty)) handleBooleanCondition(operator,value,"Norsu-leiri is ?");
    // - Annuminas
    else if ("WE_Annuminas_Camp1_Control".equals(weProperty)) handleBooleanCondition(operator,value,"Gwaelband is ?");
    else if ("WE_Annuminas_Camp2_Control".equals(weProperty)) handleBooleanCondition(operator,value,"Clorhir is ?");
    else if ("WE_Annuminas_Camp3_Control".equals(weProperty)) handleBooleanCondition(operator,value,"Tirband is ?","Tirband is ??");
    else
    {
      return false;
    }
    return true;

    // WE_Bingo_Boffin_Active Should be FALSE until we are ready to begin the Ballad of Bingo Boffin event.
    // WE_Minas_Tirith_Active Should be FALSE until we are ready to begin the Ballad of Bingo Boffin event. EQUAL 1
    // WE_Minas_Tirith_Current_Week : Controls the current week of the Minas Tirith After Battle quest chain. 1-7
    // we_rohan_west_endgame_bestowal
    // WE_Gondor_West_Endgame_Unlock_1
    // WE_Gondor_West_Endgame_Unlock_2
    // WE_Elderslade_Missions_Active
    
    
    /*
     * Ignore:
     55 Unmanaged property: ze_skirmish_controlpoint_*

     52 Unmanaged property: WE_Bingo_Boffin_Active
     42 Unmanaged property: ze_skirmish_player_groupsizechoice
     21 Unmanaged property: we_monsterplay_invasion_forochel
     21 Unmanaged property: we_monsterplay_invasion_eregion
     21 Unmanaged property: we_monsterplay_invasion_angmar
     21 Unmanaged property: WE_Minas_Tirith_Current_Week
     21 Unmanaged property: WE_Minas_Tirith_Active
     17 Unmanaged property: we_rohanpreorder_active
     16 Unmanaged property: we_liveops_strangehappenings_phase
     13 Unmanaged property: we_anniversary_promotion_vendors
     11 Unmanaged property: WE_dwarfholds_endgame_optional_objective
     10 Unmanaged property: ze_skirmish_optional_1_complete
     10 Unmanaged property: we_rohan_west_endgame_bestowal
      9 Unmanaged property: World_MPControl_Ettenmoors_Mine
      9 Unmanaged property: WE_Gondor_West_Endgame_Unlock_2
      9 Unmanaged property: WE_Gondor_West_Endgame_Unlock_1
      8 Unmanaged property: ze_skirmish_optional_1
      8 Unmanaged property: we_int_daily_quest_normal_moria
      8 Unmanaged property: WE_Elderslade_Missions_Active
     */
  }

  private void handleBooleanCondition(ComparisonOperator operator, Integer value, String label)
  {
    handleBooleanCondition(operator,value,label,null);
  }

  private void handleBooleanCondition(ComparisonOperator operator, Integer value, String labelTrue, String labelFalse)
  {
    if (operator==ComparisonOperator.EQUAL)
    {
      if (value.intValue()==1)
      {
        if (labelTrue!=null)
        {
          System.out.println(labelTrue);
        }
        else
        {
          System.out.println("Unmanaged value: "+value);
        }
      }
      else if (value.intValue()==0)
      {
        if (labelFalse!=null)
        {
          System.out.println(labelFalse);
        }
        else
        {
          System.out.println("Unmanaged value: "+value);
        }
      }
    }
    else
    {
      System.out.println("Unmanaged operator: "+operator);
    }

  }

  private void handleEventPart(ComparisonOperator operator, Integer value, String pattern)
  {
    String label=pattern.replace("VALUE",value.toString());
    String operatorStr=convertOperator(operator);
    label=label.replace("OPERATOR",operatorStr);
    System.out.println(label);
  }

  private String convertOperator(ComparisonOperator operator)
  {
    if (operator==ComparisonOperator.EQUAL) return "is";
    if (operator==ComparisonOperator.GREATER_OR_EQUAL) return ">=";
    System.out.println("Unmanaged operator: "+operator);
    return "?";
  }

  private boolean handleMissions(WorldEvent we, ComparisonOperator operator, Integer value)
  {
    String weProperty=we.getPropertyName();
    if ("WE_Integer_Elderslade_Mission_Config".equals(weProperty))
    {
      handleMissionDay(operator,value,"War of the Three Peaks"); // 1-10
    }
    else if ("WE_Integer_Gundabad_Mission_Config".equals(weProperty))
    {
      handleMissionDay(operator,value,"Gundabad"); // 1-6
    }
    else
    {
      return false;
    }
    return true;
  }

  private void handleMissionDay(ComparisonOperator operator, Integer value, String which)
  {
    if (operator==ComparisonOperator.EQUAL)
    {
      System.out.println("Day "+value+" of "+which+" missions");
    }
    else
    {
      System.out.println("Unmanaged operator: "+operator);
    }
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
