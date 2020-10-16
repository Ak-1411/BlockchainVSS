package com.vss.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection
{

   public static Connection connect() throws Exception
   {
      Class.forName("org.sqlite.JDBC");
      return DriverManager.getConnection("jdbc:sqlite:C:\\VSS\\VSS_Final\\vss.db");
   }

   public static void main(String arg[]) throws Exception
   {
      connect();

   }
}
