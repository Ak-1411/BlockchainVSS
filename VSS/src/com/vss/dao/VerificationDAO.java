package com.vss.dao;

public interface VerificationDAO
{

   public void verify(String mobile) throws Exception;

   public boolean isVerified(String mobile) throws Exception;
}
