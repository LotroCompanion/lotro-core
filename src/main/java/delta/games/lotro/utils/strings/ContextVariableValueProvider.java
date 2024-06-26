package delta.games.lotro.utils.strings;

import java.util.Map;

import org.apache.log4j.Logger;

import delta.common.utils.variables.VariableValueProvider;

/**
 * A variable value provider that handles the variables found in lore objects (quests, deeds, titles, ...).
 * @author DAM
 */
public class ContextVariableValueProvider implements VariableValueProvider
{
  private static final Logger LOGGER=Logger.getLogger(ContextVariableValueProvider.class);

  private Map<String,String> _context;

  /**
   * Constructor.
   * @param context Context to use.
   */
  public ContextVariableValueProvider(Map<String,String> context)
  {
    _context=context;
  }

  @Override
  public String getVariable(String variableName)
  {
    if (_context.containsKey(variableName))
    {
      return _context.get(variableName);
    }
    LOGGER.warn("Unmanaged variable: "+variableName);
    // TODO Unmanaged: MAX, CURRENT, NOS
    return null;
  }
}
