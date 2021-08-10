package delta.games.lotro.lore.tasks.deeds;

import java.util.List;

/**
 * Test class for the task deeds manager.
 * @author DAM
 */
public class MainTestTaskDeedsManager
{
  private static void test(TaskDeedsManager manager, int tasksCount, int expectedCurrentDeedIndex, int expectedAchievedDeedIndex)
  {
    List<TaskDeed> taskDeeds=manager.getTaskDeeds();
    TaskDeed inProgress=manager.getTaskDeedInProgress(tasksCount);
    int inProgressIndex=taskDeeds.indexOf(inProgress);
    TaskDeed achieved=manager.getAchievedTaskDeed(tasksCount);
    int achievedIndex=taskDeeds.indexOf(achieved);
    if ((inProgressIndex==expectedCurrentDeedIndex) && (achievedIndex==expectedAchievedDeedIndex))
    {
      System.out.println("Tasks count OK: "+tasksCount);
    }
    else
    {
      System.out.println("Mismatch for tasks count: "+tasksCount);
    }
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    TaskDeedsBuilder builder=new TaskDeedsBuilder();
    TaskDeedsManager manager=builder.build();
    test(manager,0,0,-1);
    test(manager,99,0,-1);
    test(manager,100,1,0);
    test(manager,199,1,0);
    test(manager,200,2,1);
    test(manager,299,2,1);
    test(manager,300,3,2);
    test(manager,399,3,2);
    test(manager,400,4,3);
    test(manager,499,4,3);
    test(manager,500,-1,4);
    test(manager,501,-1,4);
  }
}
