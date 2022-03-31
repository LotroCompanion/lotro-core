package delta.games.lotro;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A place to play with date formats.
 * @author DAM
 */
public class MainPlayingWithDateFormats
{
  private static void showDates(Date d)
  {
    System.out.println(Locale.getDefault().getDisplayName());
    /*
    DateFormat format= DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.SHORT);
    System.out.println(format.format(d));
    format= DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.SHORT);
    System.out.println(format.format(d));
    format= DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.SHORT);
    System.out.println(format.format(d));
    */
    DateFormat format= DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
    System.out.println(format.format(d));
    SimpleDateFormat us=new SimpleDateFormat("dd/MM/yyyyy HH:mm");
    System.out.println(us.format(d));
  }

  /**
   * Method method for this game.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    /*
    SimpleDateFormat format=new SimpleDateFormat();
    System.out.println(format.toLocalizedPattern());
    System.out.println(format.toPattern());
    */

    Date d=new Date();
    showDates(d);
    Locale.setDefault(Locale.US);
    showDates(d);
  }
}
