package ddsharpe.samples.jaxws.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.ws.BindingType;
import javax.xml.ws.Holder;

import ddsharpe.samples.jaxws.data.UserCredential;

@WebService(
    name = "MyServiceType",
    portName = "MyServicePort",
    serviceName = "MyService",
    targetNamespace = "http://ddsharpe.net"
)
@SOAPBinding(
    style = SOAPBinding.Style.DOCUMENT,
    use = SOAPBinding.Use.LITERAL,
    parameterStyle = SOAPBinding.ParameterStyle.WRAPPED
)
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
public class MyService {

  public MyService() {
  }

  @WebMethod()
  public void getData(
      @XmlElement(required = true)
      @WebParam(mode = WebParam.Mode.IN, name = "userId")
          String userId,
      @WebParam(mode = WebParam.Mode.OUT, name = "returnData")
          Holder<String> returnData
  ) {
    returnData.value = UserCredential.getUserCredential(userId).toString();
  }
}
