package com.vss.daoimpl;

import java.sql.Connection;
import java.sql.ResultSet;

import com.vss.dao.VerificationDAO;
import com.vss.util.DBConnection;

public class VerificationDAOImpl implements VerificationDAO
{

   @Override
   public void verify(String mobile) throws Exception
   {
      Connection con = null;
      try
      {
         con = DBConnection.connect();
         con.createStatement().execute("insert into verification values ('" + mobile + "', 'VERIFIED') ");
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
   public boolean isVerified(String mobile) throws Exception
   {
      Connection con = null;
      boolean result = false;
      try
      {
         con = DBConnection.connect();
         ResultSet rs = con.createStatement().executeQuery("select count(*) from verification where mobile='" + mobile + "' and status='VERIFIED'");
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
