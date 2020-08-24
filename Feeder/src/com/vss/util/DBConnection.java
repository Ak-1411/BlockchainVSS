package com.vss.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection
{

   public static Connection connect() throws Exception
   {
      Class.forName("org.sqlite.JDBC");
      return DriverManager.getConnection("jdbc:sqlite:C:\\vss\\vss_final\\feeder.db");
   }

   public static void main(String arg[]) throws Exception
   {
      connect();

   }
}
