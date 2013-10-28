package com.hardsoft.asyncsubtitles;


import org.apache.xmlrpc.common.TypeFactoryImpl;
import org.apache.xmlrpc.common.XmlRpcController;
import org.apache.xmlrpc.common.XmlRpcStreamConfig;
import org.apache.xmlrpc.serializer.TypeSerializer;
import org.xml.sax.SAXException;

/**
 *
 * @author daniele.belletti@gmail.com
 */
public class TypeFactory extends TypeFactoryImpl {

  public TypeFactory(XmlRpcController pController) {
    super(pController);
  }

  @Override
  public TypeSerializer getSerializer(XmlRpcStreamConfig pConfig, Object pObject) throws SAXException {
    if (pObject instanceof Double) {
      return new DoubleSerializer();
    } else if (pObject instanceof Integer) {
      return new IntegerSerializer();
    } else {
      return super.getSerializer(pConfig, pObject);
    }
  }

}
