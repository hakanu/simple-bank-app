package com.banka.accessControl;

import java.util.ArrayList;
import java.util.regex.Pattern;
 
/**
 * An inclusion/exclusion filterset, similar to ant's fileset but does not support directories in the same style(**,
 * etc).
 * 
 * For example:
 * <ul>
 * <li>/servlet/* matches all urls starting with "/servlet/" e.g. /servlet/this.html
 * <li>*.do matches all urls that end in ".do" - e.g. mypage.do
 * <li>/servlet/*.do matches all urls starting with "/servlet/" and end in ".do"  - e.g. /servlet/mypage.do
 * </ul>
 * 
 * @author Chris Watts 2009
 * 
 */
public class UrlFilter
{
  private ArrayList<Pattern> include = new ArrayList<Pattern>();
  private ArrayList<Pattern> exclude = new ArrayList<Pattern>();
 
  public UrlFilter()
  {
 
  }
 
  /**
   * Include the wildcard(*) built pattern.
   * 
   * @param pattern
   * @return
   */
  public UrlFilter include(String pattern)
  {
    include.add(generateExpression(pattern));
    return this;
  }
 
  /**
   * Exclude the wildcard(*) built pattern.
   * 
   * @param pattern
   * @return
   */
  public UrlFilter exclude(String pattern)
  {
    exclude.add(generateExpression(pattern));
    return this;
  }
 
  /**
   * Checks to see if uri matches at least ONE inclusion filter and doesn't match ANY exclusion filters.
   * 
   * @param uri
   * @return
   */
  public boolean matches(String uri)
  {
    boolean match = false;
 
    //check inclusions
    for (Pattern pattern : include)
    {
      match = match || pattern.matcher(uri).matches();
    }
 
    if (!match)
      return false;
 
    //check exclusions
    for (Pattern pattern : exclude)
    {
      match = match && !pattern.matcher(uri).matches();
    }
    return match;
  }
   
  /** regular expression special character */
  private static char[] specialChars = { '[', '\\', '^', '$', '.', '|', '?', '*', '+', '(', ')' };
 
  /**
   * 
   * @param input
   * @return
   */
  private static Pattern generateExpression(String input)
  {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < input.length(); i++)
    {
      char letter = input.charAt(i);
      if (letter == '*')
      {
        sb.append(".*");
      }
      else if (contains(specialChars, letter))
      {
        sb.append("\\" + letter);
      }
      else
      {
        sb.append(letter);
      }
    }
    return Pattern.compile(sb.toString());
  }
 
  private static boolean contains(char[] array, char value)
  {
    if (array == null || array.length == 0)
    {
      return false;
    }
 
    for (int i = 0; i < array.length; i++)
    {
      char o = array[i];
      if (o == value)
      {
        return true;
      }
    }
 
    return false;
  }
}