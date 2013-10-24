package com.hardsoft.asyncsubtitles.demo;

/*
 *  (c) Copyright (c) 2012 Mridang Agarwalla
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
import java.io.*;
import java.util.*;


/**
 * Class to read the application data for the application
 */
public class ApplicationData {

   /** The properties instance that will be used to read and write settings
    */
    private static Properties cfgProperties = new Properties();

   /**
    * This loads the list of properties from the file
    */
    public static void loadData() {

        try {
        	FileInputStream fisFileInputStream = new FileInputStream("properties.ini");
            cfgProperties.load(fisFileInputStream);
            fisFileInputStream.close();

        } catch (IOException e) {
        } catch (IllegalArgumentException e) {
        }

   }


   /**
    * Fetches a configuration value from the application data file
    *
    * @param   strName  the name of the configuration to fetch
    * @return           the value of the configuration
    */
    public static String getProperty(String strName) {

        try {

            return cfgProperties.getProperty(strName);

        } catch (Exception e) {
            return null;
        }

    }


   /**
    * This saves the list of properties to the file
    */
    public static void saveData() {

        try  {
        	FileOutputStream fisFileOutputStream = new FileOutputStream("properties.ini");
            cfgProperties.store(fisFileOutputStream, null);
            fisFileOutputStream.close();

        } catch (IOException e) {
        } catch (ClassCastException e) {
        }

   }

}