package delta.games.lotro.character.storage.currencies;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Storage for a currency.
 * @author DAM
 */
public class CurrencyStorage
{
  private List<Long> _startTimes;
  private List<Integer> _values;

  /**
   * Constructor.
   */
  public CurrencyStorage()
  {
    _startTimes=new ArrayList<Long>();
    _values=new ArrayList<Integer>();
  }

  /**
   * Get the value at a given time.
   * @param time Time to use.
   * @return A value or <code>null</code> if out of bounds.
   */
  public Integer getValueAtTime(long time)
  {
    int index=getInsertPoint(time);
    if (index==-1)
    {
      return null;
    }
    int nbPoints=_startTimes.size();
    if (index==nbPoints-1)
    {
      if (time==_startTimes.get(index).longValue())
      {
        return _values.get(index);
      }
      return null;
    }
    if (index>=nbPoints)
    {
      return null;
    }
    return _values.get(index);
  }

  /**
   * Add/set a new time/value pair.
   * @param time Time to use.
   * @param value Value to use.
   */
  public void setValueAt(long time, int value)
  {
    int nbItems=_startTimes.size();
    if (nbItems==0)
    {
      _startTimes.add(Long.valueOf(time));
      _values.add(Integer.valueOf(value));
    }
    else
    {
      int insertionIndex=getInsertPoint(time);
      if (insertionIndex==-1)
      {
        // Time is before the beginning
        int firstValue=_values.get(0).intValue();
        if (value==firstValue)
        {
          if (nbItems>1)
          {
            int secondValue=_values.get(1).intValue();
            if (secondValue==firstValue)
            {
              _startTimes.set(0,Long.valueOf(time));
            }
            else
            {
              // Update first time
              _startTimes.set(0,Long.valueOf(time));
            }
          }
          else
          {
            // Insert first point
            _startTimes.add(0,Long.valueOf(time));
            _values.add(0,Integer.valueOf(value));
          }
        }
        else
        {
          // Insert first point
          _startTimes.add(0,Long.valueOf(time));
          _values.add(0,Integer.valueOf(value));
        }
      }
      else //if (insertionIndex==nbItems-1)
      {
        long lastTime=_startTimes.get(insertionIndex).longValue();
        if (time==lastTime)
        {
          _startTimes.set(insertionIndex,Long.valueOf(time));
        }
        else
        {
          // Time is after the one of the insertion index
          int lastValue=_values.get(insertionIndex).intValue();
          if (value==lastValue)
          {
            if (insertionIndex>0)
            {
              int valueBeforeLast=_values.get(insertionIndex-1).intValue();
              if (valueBeforeLast==lastValue)
              {
                // Just update the last time
                _startTimes.set(insertionIndex,Long.valueOf(time));
              }
              else
              {
                // Insert new point
                _startTimes.add(insertionIndex+1,Long.valueOf(time));
                _values.add(insertionIndex+1,Integer.valueOf(value));
              }
            }
            else
            {
              // Insert new point
              _startTimes.add(insertionIndex+1,Long.valueOf(time));
              _values.add(insertionIndex+1,Integer.valueOf(value));
            }
          }
          else
          {
            // Insert new point
            _startTimes.add(insertionIndex+1,Long.valueOf(time));
            _values.add(insertionIndex+1,Integer.valueOf(value));
          }
        }
      }
    }
  }

  private int getInsertPoint(long time)
  {
    if (_startTimes.size()==0)
    {
      return -1;
    }
    if (time<_startTimes.get(0).longValue())
    {
      return -1;
    }
    int index=0;
    int ret=0;
    for(Long timeSample : _startTimes)
    {
      if (time>=timeSample.longValue())
      {
        ret=index;
      }
      index++;
    }
    return ret;
  }

  /**
   * Get the number of recorded points.
   * @return A points count.
   */
  public int getPoints()
  {
    return _startTimes.size();
  }

  /**
   * Get the time at the given point.
   * @param index Index of targeted point.
   * @return A time value (not <code>null</code>).
   */
  public Long getTimeAtIndex(int index)
  {
    return _startTimes.get(index);
  }

  /**
   * Get the value at the given point.
   * @param index Index of targeted point.
   * @return A value (not <code>null</code>).
   */
  public Integer getValueAtIndex(int index)
  {
    return _values.get(index);
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    int nbPoints=getPoints();
    Date date=new Date(0);
    for(int i=0;i<nbPoints;i++)
    {
      if (i>0)
      {
        sb.append(", ");
      }
      long time=_startTimes.get(i).longValue();
      int value=_values.get(i).intValue();
      sb.append(time);
      date.setTime(time);
      sb.append(" (").append(date).append(')');
      sb.append(": ");
      sb.append(value);
    }
    return sb.toString();
  }
}
