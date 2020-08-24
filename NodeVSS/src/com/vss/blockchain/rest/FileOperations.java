package com.vss.blockchain.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataParam;

import com.vss.blockchain.util.Constants;

@Path("/operations")
public class FileOperations
{

   @POST
   @Path("/upload")
   @Consumes({ MediaType.MULTIPART_FORM_DATA })
   public Response upload(@FormDataParam("textFile") InputStream fileInputStream,
            @QueryParam("filename") String filename) throws Exception
   {
      System.out.println("Inside upload method");
      try
      {
         int read = 0;
         byte[] bytes = new byte[1024];

         OutputStream out = new FileOutputStream(
                  new File(Constants.STORAGE_PATH + File.separator + filename));
         while ((read = fileInputStream.read(bytes)) != -1)
         {
            out.write(bytes, 0, read);
         }
         out.flush();
         out.close();
      }
      catch (IOException e)
      {
         System.out.println("Error inside upload methid .. "+e.getMessage());
         throw new WebApplicationException("Error while uploading file. Please try again !!");
      }
      return Response.ok("Data uploaded successfully !!").build();
   }


}
