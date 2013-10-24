/*
 *  Copyright 2011 daniele.belletti@gmail.com.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package com.hardsoft.asyncsubtitles;

import org.apache.xmlrpc.serializer.TypeSerializerImpl;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 *
 * @author daniele.belletti@gmail.com
 */
public class DoubleSerializer extends TypeSerializerImpl {

  public DoubleSerializer() {
    super();
  }

  public void write(ContentHandler pHandler, Object pObject) throws SAXException {
    Double d = (Double) pObject;
    Long l = new Long(d.longValue());
    write(pHandler, "double", l.toString());
  }
}
