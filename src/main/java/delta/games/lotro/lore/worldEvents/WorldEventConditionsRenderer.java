package delta.games.lotro.lore.worldEvents;

import org.apache.log4j.Logger;

import delta.games.lotro.common.utils.ComparisonOperator;
import delta.games.lotro.utils.Proxy;

/**
 * Renderer for world event conditions.
 * @author DAM
 */
public class WorldEventConditionsRenderer
{
  private static final Logger LOGGER=Logger.getLogger(WorldEventConditionsRenderer.class);

  /**
   * World condition label: farmer's faire active".
   */
  private static final String FARMERS_FAIRE_ACTIVE="Farmers Faire: active";
  /**
   * Error label "Unmanaged operator".
   */
  private static final String UNMANAGED_OPERATOR="Unmanaged operator: ";
  /**
   * Error label "Unmanaged value".
   */
  private static final String UNMANAGED_VALUE="Unmanaged value: ";

  /**
   * Render a simple world event condition.
   * @param condition Condition to use.
   * @return A displayable label for this condition, or <code>null</code> if not supported.
   */
  public String renderSimpleWorldEventCondition(SimpleWorldEventCondition condition)
  {
    String label=null;
    try
    {
      Proxy<WorldEvent> weProxy=condition.getWorldEvent();
      WorldEvent we=weProxy.getObject();
      ComparisonOperator operator=condition.getOperator();
      Integer value=condition.getValue();
      if (value!=null)
      {
        // Simple value
        label=renderSimpleWorldEventConditionWithValue(we,operator,value);
      }
      else
      {
        // Complex value
        label=renderSimpleWorldEventConditionWithComplexValue(condition);
      }
    }
    catch(Exception e)
    {
      LOGGER.error("Caught exception during rendering of "+condition, e);
      label=null;
    }
    return label;
  }

  private String renderSimpleWorldEventConditionWithValue(WorldEvent we, ComparisonOperator operator, Integer value)
  {
    String weProperty=we.getPropertyName();
    if ("WE_server_legendary_active".equals(weProperty))
    {
      return handleLegendaryServerCondition(operator,value);
    }
    if ("WE_Player_Level_Cap".equals(weProperty))
    {
      return handlePlayerLevelCapCondition(operator,value);
    }
    if ("ze_skirmish_level".equals(weProperty))
    {
      return handleInstanceLevel(operator,value);
    }
    if ("ze_skirmish_group_size".equals(weProperty))
    {
      return handleInstanceGroupSize(operator,value);
    }
    if ("ze_skirmish_difficulty".equals(weProperty))
    {
      return handleInstanceDifficulty(operator,value);
    }
    if ("WE_dwarfholds_eredmithrin_instance_leading_charge_lock".equals(weProperty))
    {
      return handleInstanceChallengePeriod(operator,value,"the Ered Mithrin instances");
    }
    if ("WE_vales_anduin_kidzul_kalah_instance_leading_charge_lock".equals(weProperty))
    {
      return handleInstanceChallengePeriod(operator,value,"the Vales of Anduin 3-men");
    }
    if ("WE_minas_morgul_instance_leading_charge_lock".equals(weProperty))
    {
      return handleInstanceChallengePeriod(operator,value,"the Minas Morgul 3-men");
    }
    if ("WE_minas_morgul_instance_6man_leading_charge_lock".equals(weProperty))
    {
      return handleInstanceChallengePeriod(operator,value,"the Minas Morgul 6-men");
    }
    if ("WE_minas_morgul_instance_raid_leading_charge_lock".equals(weProperty))
    {
      return handleInstanceChallengePeriod(operator,value,"the Minas Morgul raid");
    }
    if ("WE_Elderslade_6man_Aotc".equals(weProperty))
    {
      return handleInstanceChallengePeriod(operator,value,"the Elderslade 6-men");
    }
    if ("WE_Elderslade_Raid_Aotc".equals(weProperty))
    {
      return handleInstanceChallengePeriod(operator,value,"the Elderslade raid");
    }
    if ("WE_Wildwood_Instances_Aotc_Gate".equals(weProperty))
    {
      return handleInstanceChallengePeriod(operator,value,"the Wildwood instances");
    }
    if ("WE_Azanulbizar_Raid_Aotc_Gate".equals(weProperty))
    {
      return handleInstanceChallengePeriod(operator,value,"the Azanulbizar raid");
    }
    if ("WE_gundabad_3man_instance_leading_charge_lock".equals(weProperty))
    {
      return handleInstanceChallengePeriod(operator,value,"the Gundabad 3-men");
    }
    if ("WE_gundabad_6man_instance_leading_charge_lock".equals(weProperty))
    {
      return handleInstanceChallengePeriod(operator,value,"the Gundabad 6-men");
    }
    if ("WE_gundabad_12man_raid_leading_charge_lock".equals(weProperty))
    {
      return handleInstanceChallengePeriod(operator,value,"the Gundabad raid");
    }
    if (("WE_Event_DurinsDay_Counter".equals(weProperty))
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
      return null;
    }
    String label=handleMissions(we,operator,value);
    if (label!=null) return label;
    label=handleBooleanConditions(we,operator,value);
    if (label!=null) return label;
    label=handleEventParts(we,operator,value);
    if (label!=null) return label;
    LOGGER.debug("Unmanaged property: "+weProperty);
    return null;
  }

  private String handleLegendaryServerCondition(ComparisonOperator operator, Integer value)
  {
    if (((operator==ComparisonOperator.EQUAL) && (value.intValue()==0))
        || ((operator==ComparisonOperator.NOT_EQUAL) && (value.intValue()==1)))
    {
      return "Legendary server: no";
    }
    else if (((operator==ComparisonOperator.EQUAL) && (value.intValue()==1))
        || ((operator==ComparisonOperator.NOT_EQUAL) && (value.intValue()==0)))
    {
      return "Legendary server: yes";
    }
    else
    {
      LOGGER.debug("Unmanaged case: operator="+operator+", value="+value);
    }
    return null;
  }

  private String handlePlayerLevelCapCondition(ComparisonOperator operator, Integer value)
  {
    String operatorStr;
    if (operator==ComparisonOperator.EQUAL) operatorStr=" is ";
    else if (operator==ComparisonOperator.GREATER_OR_EQUAL) operatorStr=">=";
    else if (operator==ComparisonOperator.LESS_OR_EQUAL) operatorStr="=<";
    else
    {
      LOGGER.debug(UNMANAGED_OPERATOR+operator);
      return null;
    }
    return "Level cap"+operatorStr+value;
  }

  private String handleInstanceDifficulty(ComparisonOperator operator, Integer value)
  {
    String operatorStr;
    if (operator==ComparisonOperator.EQUAL) operatorStr=" is ";
    else if (operator==ComparisonOperator.GREATER_OR_EQUAL) operatorStr=">=";
    else
    {
      LOGGER.debug(UNMANAGED_OPERATOR+operator);
      return null;
    }
    String valueStr="Tier "+value;
    return "Instance difficulty"+operatorStr+valueStr;
  }

  private String handleInstanceLevel(ComparisonOperator operator, Integer value)
  {
    String operatorStr="?";
    if (operator==ComparisonOperator.EQUAL) operatorStr=" is ";
    else if (operator==ComparisonOperator.GREATER_OR_EQUAL) operatorStr=">=";
    else
    {
      LOGGER.debug(UNMANAGED_OPERATOR+operator);
    }
    return "Instance level"+operatorStr+value;
  }

  private String handleInstanceGroupSize(ComparisonOperator operator, Integer value)
  {
    if (operator!=ComparisonOperator.EQUAL)
    {
      LOGGER.debug(UNMANAGED_OPERATOR+operator);
      return null;
    }
    return "Instance size is "+value;
  }

  private String handleInstanceChallengePeriod(ComparisonOperator operator, Integer value, String what)
  {
    if (operator==ComparisonOperator.EQUAL)
    {
      if (value.intValue()==1)
      {
        return "In the period for initial completion of challenge mode of "+what;
      }
      LOGGER.debug(UNMANAGED_VALUE+value);
    }
    else
    {
      LOGGER.debug(UNMANAGED_OPERATOR+operator);
    }
    return null;
  }

  private String handleEventParts(WorldEvent we, ComparisonOperator operator, Integer value)
  {
    String weProperty=we.getPropertyName();
    if ("WE_fall_festival_maze_driver".equals(weProperty)) return handleEventPart(operator,value,"Harvest Festival: Active Maze is #VALUE");
    if ("WE_ev_skirmish_driver".equals(weProperty)) return handleEventPart(operator,value,"Ill Omens: day VALUE");
    if ("WE_Bingo_Boffin_Current_Week".equals(weProperty)) return handleEventPart(operator,value,"The Lay of Rust and Rime: week OPERATOR VALUE");
    if ("WE_Anniversary_Event_Current_Week".equals(weProperty)) return handleEventPart(operator,value,"Anniversary Event Scavenger Hunt: week OPERATOR VALUE");
    if ("WE_Episodic_LRR_Current_Week".equals(weProperty)) return handleEventPart(operator,value, "Ballad of Bingo Boffin: week OPERATOR VALUE");
    return null;
  }

  private String handleBooleanConditions(WorldEvent we, ComparisonOperator operator, Integer value)
  {
    String weProperty=we.getPropertyName();
    // - Yule
    if ("WE_WinterFestival_FestivalActive".equals(weProperty)) return handleBooleanCondition(operator,value,"Yule Festival: active");
    if ("we_winterfestival_town_active".equals(weProperty)) return handleBooleanCondition(operator,value,"Yule Festival: town quests are active");
    if ("we_winterfestival_eatingcontest_active".equals(weProperty)) return handleBooleanCondition(operator,value,"Yule Festival: eating contest is active");
    if ("we_winterfestival_snowballfight_active".equals(weProperty)) return handleBooleanCondition(operator,value,"Yule Festival: snowball fight is active");
    if ("we_winterfestival_eatingcontest_status".equals(weProperty)) return handleBooleanCondition(operator,value,null,"Yule Festival: eating contest is not started");
    if ("we_winterfestival_snowballfight_status".equals(weProperty)) return handleBooleanCondition(operator,value,null,"Yule Festival: snowball fight is not started");
    // - Farmers Faire
    if ("WE_FarmersFair_FestivalActive".equals(weProperty)) return handleBooleanCondition(operator,value,FARMERS_FAIRE_ACTIVE);
    if ("WE_SpringFestival_FestivalActive".equals(weProperty)) return handleBooleanCondition(operator,value,FARMERS_FAIRE_ACTIVE);
    if ("WE_SummerFestival_FestivalActive".equals(weProperty)) return handleBooleanCondition(operator,value,FARMERS_FAIRE_ACTIVE);
    // - Harvest Festival
    if ("WE_FallFestival_FestivalActive".equals(weProperty)) return handleBooleanCondition(operator,value,"Harvest Festival: active");
    if ("we_fallfestival_hauntedhouse_active".equals(weProperty)) return handleBooleanCondition(operator,value,"Harvest Festival: haunted house is active");
    // - LOTRO Anniversary Celebration
    if ("WE_Anniversary_Event_Active".equals(weProperty)) return handleBooleanCondition(operator,value,"LOTRO Anniversary Celebration: active");
    if ("we_fallfestival_barfight_active".equals(weProperty)) return handleBooleanCondition(operator,value,"LOTRO Anniversary Celebration: fight-arena is active");
    if ("we_fallfestival_barfight_status".equals(weProperty)) return handleBooleanCondition(operator,value,null,"LOTRO Anniversary Celebration: fight-arena not started");
    // - Midsummer Festival
    if ("WE_WeddingFestival_FestivalActive".equals(weProperty)) return handleBooleanCondition(operator,value,"Wedding Festival: active");
    // - Spring Festival
    if ("WE_Real_SpringFestival_FestivalActive".equals(weProperty)) return handleBooleanCondition(operator,value,"Spring Festival: active");
    if ("we_event_flowers_active".equals(weProperty)) return handleBooleanCondition(operator,value,"Spring Festival: active");
    // - International Talk Like a Pirate Day
    if ("WE_TalkLikeACorsairDay_Active".equals(weProperty)) return handleBooleanCondition(operator,value,"International Talk Like a Pirate Day");
    // All Festivals
    if ("WE_GenericFestival_FestivalActive".equals(weProperty)) return handleBooleanCondition(operator,value,"Any Festival: active");

    /*
    WE_SpringFestival_DanceDuillondActive
    WE_SpringFestival_DanceThorinsHallActive
    WE_SpringFestival_DanceShireActive
    WE_SpringFestival_DanceBreelandActive

    WE_Summerfestival_DwarfRace_Status
    WE_Summerfestival_HobbitRace_Status
    WE_summerfestival_gamble_shire_active
    WE_Real_SpringFestival_Shrew_Status
    WE_Festival_Egg_Farm
    WE_Festival_Mushroom_Farm
    WE_Summerfestival_DwarfRace_Status
    WE_Summerfestival_HobbitRace_Status
    we_anniversary_fireworks_shire_status
    we_anniversary_promotion_drops
    we_anniversary_promotion_vendors
    WE_Anniversary_Fireworks_Active
    */

    // Dailies
    // - Mordor
    if ("WE_Gorgoroth_Endgame_Event_1_Udun_DorAmarth".equals(weProperty)) return handleBooleanCondition(operator,value,"Mordor dailies: Udûn and Dor Amarth featured");
    if ("WE_Gorgoroth_Endgame_Event_2_Lhingris_TalathUrui".equals(weProperty)) return handleBooleanCondition(operator,value,"Mordor dailies: Lhingris and Talath Úrui featured");
    if ("WE_Gorgoroth_Endgame_Event_3_DorAmarth_Agarnaith".equals(weProperty)) return handleBooleanCondition(operator,value,"Mordor dailies: Dor Amarth and Agarnaith featured");
    if ("WE_Gorgoroth_Endgame_Event_4_Udun_TalathUrui".equals(weProperty)) return handleBooleanCondition(operator,value,"Mordor dailies: Udûn and Talath Úrui featured");
    if ("WE_Gorgoroth_Endgame_Event_5_Agarnaith_Lhingris".equals(weProperty)) return handleBooleanCondition(operator,value,"Mordor dailies: Agarnaith and Lhingris featured");
    // - Strongholds of the North
    if ("WE_Mirkwood_North_Endgame_Driven_Daily_Set_1".equals(weProperty)) return handleBooleanCondition(operator,value,"Stronghold of the North Dailies: set 1");
    if ("WE_Mirkwood_North_Endgame_Driven_Daily_Set_2".equals(weProperty)) return handleBooleanCondition(operator,value,"Stronghold of the North Dailies: set 2");
    if ("WE_Mirkwood_North_Endgame_Driven_Daily_Set_3".equals(weProperty)) return handleBooleanCondition(operator,value,"Stronghold of the North Dailies: set 3");
    // Events
    if ("WE_Trollshaws_FrodoBilboBirthday_active".equals(weProperty)) return handleBooleanCondition(operator,value,"Bilbo & Frodo's Birthday");
    if ("WE_EV_Skirmish_Active".equals(weProperty)) return handleBooleanCondition(operator,value,"Ill Omens: active");
    if ("WE_Bingo_Boffin_Active".equals(weProperty)) return handleBooleanCondition(operator,value,"Ballad of Bingo Boffin: active");
    if ("we_rohanpreorder_active".equals(weProperty)) return handleBooleanCondition(operator,value,"Riders of Rohan legendary expansion: owned");
    if ("WE_Treasure_Bugan_Active".equals(weProperty)) return handleBooleanCondition(operator,value,"Treasure Bugans event: active");
    // PVP
    if ("World_MPControl_Ettenmoors_WestTower".equals(weProperty)) return handleBooleanCondition(operator,value,"Ettenmoors: creeps control Lugazag","Ettenmoors: freeps control Lugazag");
    if ("World_MPControl_Ettenmoors_EastTower".equals(weProperty)) return handleBooleanCondition(operator,value,"Ettenmoors: creeps control Tirith Rhaw","Ettenmoors: freeps control Tirith Rhaw");
    if ("World_MPControl_Ettenmoors_CenterKeep".equals(weProperty)) return handleBooleanCondition(operator,value,"Ettenmoors: creeps control Tol Ascarnen","Ettenmoors: freeps control Tol Ascarnen");
    // World_MPControl_Ettenmoors_Lumberyard
    // World_MPControl_Ettenmoors_Mine
    // Time of Day
    if ("World_IsNight".equals(weProperty)) return handleBooleanCondition(operator,value,"Time of day: night","Time of day: day");
    // Maps state
    // - Forochel
    if ("WE_Forochel_ControlPOI_Glacier".equals(weProperty)) return handleBooleanCondition(operator,value,"Forochel: freeps control Hylje-leiri");
    if ("WE_Forochel_ControlPOI_Icebay".equals(weProperty)) return handleBooleanCondition(operator,value,"Forochel: freeps control Karhu-leiri");
    if ("WE_Forochel_ControlPOI_TundraEast".equals(weProperty)) return handleBooleanCondition(operator,value,"Forochel: freeps control Pynti-leiri");
    if ("WE_Forochel_ControlPOI_TundraWest".equals(weProperty)) return handleBooleanCondition(operator,value,"Forochel: freeps control Norsu-leiri");
    // - Annuminas
    if ("WE_Annuminas_Camp1_Control".equals(weProperty)) return handleBooleanCondition(operator,value,"Annuminas: freeps control Gwaelband");
    if ("WE_Annuminas_Camp2_Control".equals(weProperty)) return handleBooleanCondition(operator,value,"Annuminas: freeps control Clorhir");
    if ("WE_Annuminas_Camp3_Control".equals(weProperty)) return handleBooleanCondition(operator,value,"Annuminas: freeps control Tirband","Annuminas: Angmar holds Tirband");
    return null;

    /*
    // WE_Minas_Tirith_Active Should be FALSE until we are ready to begin the Ballad of Bingo Boffin event. EQUAL 1
    // WE_Minas_Tirith_Current_Week : Controls the current week of the Minas Tirith After Battle quest chain. 1-7
    // we_rohan_west_endgame_bestowal
    // WE_Gondor_West_Endgame_Unlock_1
    // WE_Gondor_West_Endgame_Unlock_2
    // WE_Elderslade_Missions_Active
     Ignore: Unmanaged property: ze_skirmish_controlpoint_*
     42 Unmanaged property: ze_skirmish_player_groupsizechoice
     21 Unmanaged property: we_monsterplay_invasion_forochel
     21 Unmanaged property: we_monsterplay_invasion_eregion
     21 Unmanaged property: we_monsterplay_invasion_angmar
     21 Unmanaged property: WE_Minas_Tirith_Current_Week
     21 Unmanaged property: WE_Minas_Tirith_Active
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

  private String handleBooleanCondition(ComparisonOperator operator, Integer value, String label)
  {
    return handleBooleanCondition(operator,value,label,null);
  }

  private String handleBooleanCondition(ComparisonOperator operator, Integer value, String labelTrue, String labelFalse)
  {
    if (operator==ComparisonOperator.EQUAL)
    {
      if (value.intValue()==1)
      {
        if (labelTrue!=null)
        {
          return labelTrue;
        }
        LOGGER.debug(UNMANAGED_VALUE+value);
      }
      else if (value.intValue()==0)
      {
        if (labelFalse!=null)
        {
          return labelFalse;
        }
        LOGGER.debug(UNMANAGED_VALUE+value);
      }
    }
    else
    {
      LOGGER.debug(UNMANAGED_OPERATOR+operator);
    }
    return null;
  }

  private String handleEventPart(ComparisonOperator operator, Integer value, String pattern)
  {
    String label=pattern.replace("VALUE",value.toString());
    String operatorStr=convertOperator(operator);
    label=label.replace("OPERATOR",operatorStr);
    return label;
  }

  private String convertOperator(ComparisonOperator operator)
  {
    if (operator==ComparisonOperator.EQUAL) return "is";
    if (operator==ComparisonOperator.GREATER_OR_EQUAL) return ">=";
    LOGGER.warn(UNMANAGED_OPERATOR+operator);
    return "?";
  }

  private String handleMissions(WorldEvent we, ComparisonOperator operator, Integer value)
  {
    String weProperty=we.getPropertyName();
    if ("WE_Integer_Elderslade_Mission_Config".equals(weProperty))
    {
      return handleMissionDay(operator,value,"War of the Three Peaks"); // 1-10
    }
    if ("WE_Integer_Gundabad_Mission_Config".equals(weProperty))
    {
      return handleMissionDay(operator,value,"Gundabad"); // 1-6
    }
    if ("WE_Integer_U34_BTS_Mission_Config".equals(weProperty))
    {
      return handleMissionDay(operator,value,"Before the Shadow"); // 1-3
    }
    return null;
  }

  private String handleMissionDay(ComparisonOperator operator, Integer value, String which)
  {
    if (operator==ComparisonOperator.EQUAL)
    {
      return "Missions: "+which+": day "+value;
    }
    LOGGER.debug(UNMANAGED_OPERATOR+operator);
    return null;
  }

  /*
   For PVP:
    WE_Ettenmoors_TA_Player_Oil => Number of completions for quest "Oil for Boiling"
    WE_Ettenmoors_Lug_Player_Oil => Number of completions for quest "Oil for the Cauldron of Lugazag"
    WE_Ettenmoors_TR_Player_Oil => Number of completions for quest "Oil for the Cauldron of Tirith Rhaw"
    WE_Ettenmoors_TA_Player_Cauldron => Number of completions for quest "A Cauldron of Iron"
    WE_Ettenmoors_Lug_Player_Cauldron => Number of completions for quest "Iron for the Cauldron of Lugazag"
    WE_Ettenmoors_TR_Player_Cauldron => Number of completions for quest "Iron on the Walls of Tirith Rhaw"
    WE_Ettenmoors_TA_Player_Rockwithers related to "Rockwithers' Horn" (hidden)
    WE_Ettenmoors_TA_Player_Stand related to "Pillar of Strength" (hidden)
    WE_Ettenmoors_TA_Player_Reinforce_West related to "Signal to the West" (hidden)
    WE_Ettenmoors_TA_Player_Reinforce_South related to "Signal to the South" (hidden)
    WE_Ettenmoors_TA_Player_Sinew related to "Strapped for Straps" (hidden)
   */

  private String renderSimpleWorldEventConditionWithComplexValue(SimpleWorldEventCondition condition)
  {
    WorldEvent we=condition.getWorldEvent().getObject();
    String weProperty=we.getPropertyName();
    if ("ze_skirmish_level".equals(weProperty))
    {
      WorldEvent compareTo=condition.getCompareToWorldEvent().getObject();
      String compareToWeProperty=compareTo.getPropertyName();
      ComparisonOperator operator=condition.getOperator();
      if ("WE_Player_Level_Cap".equals(compareToWeProperty))
      {
        if (operator==ComparisonOperator.EQUAL)
        {
          return "Instance level is player cap level";
        }
        LOGGER.debug("Unmanaged operator for skirmish level/player cap property: "+operator);
      }
      else
      {
        LOGGER.debug("Unmanaged skirmish level property: "+condition);
      }
    }
    else
    {
      LOGGER.debug("Unmanaged condition with complex value: "+condition);
    }
    return null;
  }
}
