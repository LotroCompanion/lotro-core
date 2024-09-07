package delta.games.lotro.lore.tasks;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.tasks.io.xml.TasksXMLParser;

/**
 * Tasks registry.
 * @author DAM
 */
public class TasksRegistry
{
  private static final Logger LOGGER=LoggerFactory.getLogger(TasksRegistry.class);

  private static TasksRegistry _instance=null;

  private List<Task> _tasks;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static TasksRegistry getInstance()
  {
    if (_instance==null)
    {
      _instance=new TasksRegistry(true);
    }
    return _instance;
  }

  /**
   * Private constructor.
   * @param load Indicates if the tasks database shall be loaded or not.
   */
  private TasksRegistry(boolean load)
  {
    _tasks=new ArrayList<Task>();
    if (load)
    {
      loadAll();
    }
  }

  /**
   * Load all tasks.
   */
  private void loadAll()
  {
    _tasks.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File tasksFile=cfg.getFile(DataFiles.TASKS);
    long now=System.currentTimeMillis();
    List<Task> tasks=TasksXMLParser.parseTasksFile(tasksFile);
    for(Task task : tasks)
    {
      _tasks.add(task);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_tasks.size()+" tasks in "+duration+"ms.");
  }

  /**
   * Add a task.
   * @param task Task to add.
   */
  public void addTask(Task task)
  {
    _tasks.add(task);
  }

  /**
   * Get the list of all known tasks.
   * @return a list of tasks.
   */
  public List<Task> getTasks()
  {
    return new ArrayList<Task>(_tasks);
  }
}
