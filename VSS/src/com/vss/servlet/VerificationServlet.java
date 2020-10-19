package com.vss.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vss.mail.MailThread;
import com.vss.mobile.SendMessage;
import com.vss.dao.VerificationDAO;
import com.vss.daoimpl.VerificationDAOImpl;
import com.vss.model.User;
import com.vss.util.VerificationCode;

public class VerificationServlet extends HttpServlet
{
   private static final long serialVersionUID = 1L;

   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
   {
      doPost(req, resp);
   }

   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
   {

      try
      {
         User user = (User) req.getSession().getAttribute("user");
         VerificationDAO vDao = new VerificationDAOImpl();
         String reqType = req.getParameter("req_type");
         if (reqType == null)
         {
            resp.sendRedirect("verification.jsp?msg=Bad Request");
         }
         else
         {
            if (reqType.equals("generate_code"))
            {
               String mobileCode = VerificationCode.generateVerificationCodeForMobile();
               req.getSession().setAttribute("mobileCode", mobileCode);
               String mobileMsg = "Mobile verification code for VSS application is: " + mobileCode;
               SendMessage.sendSms(user.getMobile(), mobileMsg);

               resp.sendRedirect("verification.jsp?msg=Verification Code Sent");
            }
            else if (reqType.equals("verify"))
            {
               String actualMobileCode = (String) req.getSession().getAttribute("mobileCode");
               String mobileCode = req.getParameter("mobileCode");
               if (mobileCode.equals(actualMobileCode))
               {
                  vDao.verify(user.getMobile());
                  resp.sendRedirect("verification.jsp?msg=Verification Successful");
               }
               else
               {
                  resp.sendRedirect("verification.jsp?msg=Verification Failed");
               }
            }

         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
         resp.sendRedirect("verification.jsp?msg=" + e.getMessage());
      }

   }

}
