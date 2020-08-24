package com.vss.daoimpl;

import java.sql.Connection;
import java.sql.ResultSet;

import com.vss.dao.VerificationDAO;
import com.vss.util.DBConnection;

public class VerificationDAOImpl implements VerificationDAO
{

   @Override
   public void verify(String email) throws Exception
   {
      Connection con = null;
      try
      {
         con = DBConnection.connect();
         con.createStatement().execute("insert into verification values ('" + email + "', 'VERIFIED') ");
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         con.close();
      }

   }

   @Override
   public boolean isVerified(String email) throws Exception
   {
      Connection con = null;
      boolean result = false;
      try
      {
         con = DBConnection.connect();
         ResultSet rs = con.createStatement().executeQuery("select count(*) from verification where email='" + email + "' and status='VERIFIED'");
         rs.next();
         result = rs.getInt(1) > 0 ? true : false;
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         con.close();
      }
      return result;
   }

}
