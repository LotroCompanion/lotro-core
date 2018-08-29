package delta.games.lotro.plugins;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import delta.common.utils.NumericTools;
import delta.common.utils.io.FileIO;
import delta.common.utils.misc.IntegerHolder;
import delta.common.utils.text.EncodingNames;

/**
 * LUA data structure parser.
 * @author DAM
 */
public class LuaParser
{
  private String _body;
  private int _index;
  private Stack<Integer> _marks;

  /**
   * Constructor.
   */
  public LuaParser()
  {
    _marks=new Stack<Integer>();
  }

  /**
   * Reset.
   */
  public void reset()
  {
    _marks.clear();
    _index=0;
  }

  /**
   * Store a mark.
   */
  private void pushMark()
  {
    _marks.add(Integer.valueOf(_index));
  }

  /**
   * Re-use mark.
   */
  private void popMark()
  {
    _index=_marks.pop().intValue();
  }

  /**
   * Read data from the given string.
   * @param body String to read.
   * @return Some data.
   */
  public Object read(String body)
  {
    if (body.startsWith("return")) body=body.substring(6);
    _body=body;
    reset();
    Object value=readValue();
    //System.out.println("Read: "+value);
    return value;
  }

  /**
   * Read data from the given file.
   * @param file File to read.
   * @return A map of data.
   * @throws Exception if an error occurs.
   */
  @SuppressWarnings("unchecked")
  public Map<String,Object> read(File file) throws Exception
  {
    Map<String,Object> ret=null;
    if (file.canRead())
    {
      byte[] b=FileIO.readFile(file);
      String body=new String(b,EncodingNames.UTF_8);
      Object data=read(body);
      if (data instanceof Map)
      {
        ret=(Map<String,Object>)data;
      }
    }
    return ret;
  }

  /**
   * Read data from the given file.
   * @param file File to read.
   * @return Some data.
   * @throws Exception if an error occurs.
   */
  public Object readObject(File file) throws Exception
  {
    byte[] b=FileIO.readFile(file);
    String body=new String(b,EncodingNames.UTF_8);
    return read(body);
  }

  private void skipBlanks()
  {
    while(true)
    {
      char c=readNextChar();
      if ((c!=' ') && (c!='\r') && (c!='\n') && (c!='\t'))
      {
        pushBackChar();
        break;
      }
    }
  }
  private void pushBackChar()
  {
    _index--;
  }

  private char readNextChar()
  {
    char c=_body.charAt(_index);
    _index++;
    return c;
  }

  private List<Object> readArray()
  {
    List<Object> ret=new ArrayList<Object>();
    skipBlanks();
    char c=readNextChar();
    if (c!='{') throwException("'{' expected");
    skipBlanks();
    c=readNextChar();
    if (c=='}')
    {
      return ret;
    }
    pushBackChar();
    Object value=readValue();
    ret.add(value);
    while (true)
    {
      skipBlanks();
      c=readNextChar();
      if (c=='}')
      {
        break;
      }
      if (c==',')
      {
        value=readValue();
        ret.add(value);
      }
      else
      {
        throwException("Unexpected char '" + c + '\'');
      }
    }
    return ret;
  }

  private Map<String,Object> readPropertiesMap()
  {
    Map<String,Object> ret=new HashMap<String,Object>();
    skipBlanks();
    char c=readNextChar();
    if (c!='{') throwException("'{' expected");
    skipBlanks();
    c=readNextChar();
    if (c=='}')
    {
      return ret;
    }
    pushBackChar();
    Object[] property=readProperty();
    ret.put((String)property[0],property[1]);
    while (true)
    {
      skipBlanks();
      c=readNextChar();
      if (c=='}')
      {
        break;
      }
      if (c==',')
      {
        property=readProperty();
        ret.put((String)property[0],property[1]);
      }
      else
      {
        throwException("Unexpected char '" + c + '\'');
      }
    }
    return ret;
  }

  private Object[] readProperty()
  {
    String name=readPropertyName();
    skipBlanks();
    char c=readNextChar();
    if (c!='=') throwException("'=' expected");
    Object value=readValue();
    Object[] ret=new Object[2];
    ret[0]=name;
    ret[1]=value;
    return ret;
  }

  private Object readValue()
  {
    Object ret=null;
    skipBlanks();
    char c=readNextChar();
    if (c=='{')
    {
      pushBackChar();
      ret=readCompound();
      //System.out.println("Read: "+ret);
    }
    else if (c=='\"')
    {
      pushBackChar();
      ret=readString();
    }
    else
    {
      pushBackChar();
      ret=readBasicValue();
      if ("true".equals(ret)) ret=Boolean.TRUE;
      if ("false".equals(ret)) ret=Boolean.FALSE;
    }
    //System.out.println("Read value: "+ret);
    return ret;
  }

  private Object readCompound()
  {
    pushMark();
    Object ret=null;
    try
    {
      ret=readPropertiesMap();
    }
    catch(Exception e)
    {
      popMark();
    }
    if (ret==null)
    {
      try
      {
        ret=readArray();
      }
      catch(Exception e)
      {
        popMark();
      }
    }
    return ret;
  }

  /**
   * Read numeric or string value.
   * @return a value.
   */
  private Object readBasicValue()
  {
    Object ret=null;
    char c=readNextChar();
    pushBackChar();
    if ((Character.isDigit(c)) || (c=='-'))
    {
      ret=readNumeric();
    }
    else
    {
      ret=readBasicString();
    }
    return ret;
  }

  private String readPropertyName()
  {
    skipBlanks();
    char c=readNextChar();
    if (c=='{')
    {
      throwException("'{' not expected");
    }
    boolean useBrackets=false;
    if (c=='[') useBrackets=true; else pushBackChar();
    skipBlanks();
    Object name=readValue();
    skipBlanks();
    if (useBrackets)
    {
      c=readNextChar();
      if (c!=']') throwException("'[' expected");
    }
    //System.out.println("Read: "+name);
    return name.toString();
  }

  private String readString()
  {
    char c=readNextChar();
    if (c!='\"') throwException("'\"' expected");
    StringBuilder sb=new StringBuilder();
    while (true)
    {
      c=readNextChar();
      if (c=='\\')
      {
        // Escape
        c=readNextChar();
        if (c=='n') sb.append('\n');
        else sb.append(c);
      }
      else if (c!='\"')
      {
        sb.append(c);
      }
      else
      {
        break;
      }
    }
    String ret=sb.toString();
    ret=resolveEscapes(ret);
    return ret;
  }

  private String readBasicString()
  {
    StringBuilder sb=new StringBuilder();
    while (true)
    {
      char c=readNextChar();
      if (((c>='a') && (c<='z')) || ((c>='A') && (c<='Z')))
      {
        sb.append(c);
      }
      else
      {
        pushBackChar();
        break;
      }
    }
    String ret=sb.toString();
    return ret;
  }

  private String resolveEscapes(String s)
  {
    StringBuilder sb=new StringBuilder();
    int length=s.length();
    int index=0;
    while(index<length)
    {
      char c=s.charAt(index);
      if (c!='#')
      {
        sb.append(c);
        index++;
      }
      else
      {
        IntegerHolder holder=new IntegerHolder(index);
        try
        {
          byte[] escaped=readEscapes(s,holder);
          sb.append(new String(escaped,"UTF-8"));
        }
        catch(Exception e)
        {
          throw new IllegalArgumentException("Bad escaped value", e);
        }
        index=holder.getInt();
      }
    }
    return sb.toString();
  }

  private byte[] readEscapes(String s, IntegerHolder indexHolder) throws IOException
  {
    ByteArrayOutputStream bos=new ByteArrayOutputStream();
    int index=indexHolder.getInt();
    while(true)
    {
      int index2=s.indexOf('#',index+1);
      if (index2==index+1)
      {
        bos.write("#".getBytes());
        indexHolder.setInt(index2+1);
        index=index2+1;
        break;
      }
      if (index2!=-1)
      {
        Integer escapedValue=NumericTools.parseInteger(s.substring(index+1,index2));
        if (escapedValue!=null)
        {
          bos.write(escapedValue.byteValue());
        }
        index=index2+1;
        indexHolder.setInt(index);
      }
      else
      {
        bos.write(s.substring(index).getBytes());
        indexHolder.setInt(s.length());
        break;
      }
      if ((s.length()==index) || (s.charAt(index)!='#'))
      {
        break;
      }
    }
    byte[] byteBuffer=bos.toByteArray();
    return byteBuffer;
  }

  private Double readNumeric()
  {
    // Read numeric value
    StringBuilder sb=new StringBuilder();
    while(true)
    {
      char c=readNextChar();
      if (((c>='0') && (c<='9')) || (c=='.') || (c=='-'))
      {
        sb.append(c);
      }
      else
      {
        pushBackChar();
        break;
      }
    }
    double ret=NumericTools.parseDouble(sb.toString(),0);
    return Double.valueOf(ret);
  }

  private void throwException(String msg)
  {
    throw new IllegalStateException(msg);
  }
}
