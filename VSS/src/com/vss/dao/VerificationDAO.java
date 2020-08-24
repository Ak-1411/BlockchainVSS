package com.vss.dao;

public interface VerificationDAO
{

   public void verify(String email) throws Exception;

   public boolean isVerified(String email) throws Exception;
}
