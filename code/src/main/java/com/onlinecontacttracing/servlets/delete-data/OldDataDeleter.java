package com.onlinecontacttracing.servlets;

/**
* This class stores the information needed to query old data from a given class and delete it.
*/
abstract class OldDataDeleter {
  public abstract Class getType();
  public abstract String getQueryString();
  public abstract long getMaxAge();
} 