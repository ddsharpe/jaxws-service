# Simple Application with JAX-WS Web Service

## Application
This project produces a SOAP web service using JAX-WS. 
After deployment, the service should be available at http://{host}:{port}/SimpleWebService/user

## Build
Use `mvn clean package` from the project root folder.

### Prerequisites
Java 8 and Maven 3.6.0+ are required to build this project.
   
### Maven
Using Maven, the build does not require WebLogic Server to be installed.  The necessary components will be 
downloaded by Maven.  The Oracle Maven repository can take a very long time to download the first time it is used.
Since the published artifacts for a given WebLogic release never/seldom change, it is recommended to set the 
`updatePolicy` for the Oracle Maven repository to `never` in the POM or settings.xml.

Since the Oracle Maven repository requires credentials (and an acceptance).  This can be done with either a new or an 
existing OTN user account by accessing the http://maven.oracle.com site and clicking the registration link.  Configuring
the WebLogic Maven plug-in is documented here, https://docs.oracle.com/middleware/12213/wls/WLPRG/maven.htm#WLPRG587.

### settings.xml
You will need to add the `maven.oracle.com` repository to your `settings.xml` so that the WebLogic dependencies are 
resolved during the build.  Both the repository definition and the server definition are required.  For example:
```xml
    <profile>
      <id>oracle-weblogic</id>
      <repositories>
        <repository>
          <id>maven.oracle.com</id>
          <releases>
            <enabled>true</enabled>
            <updatePolicy>never</updatePolicy>
            <checksumPolicy>warn</checksumPolicy>
          </releases>
          <snapshots>
            <enabled>true</enabled>
            <updatePolicy>never</updatePolicy>
            <checksumPolicy>fail</checksumPolicy>
          </snapshots>
          <url>https://maven.oracle.com</url>
          <layout>default</layout>
        </repository>
      </repositories>
      <pluginRepositories>
        <pluginRepository>
          <id>maven.oracle.com</id>
          <url>https://maven.oracle.com</url>
        </pluginRepository>
      </pluginRepositories>
    </profile>

  <servers>
    <server>
      <id>maven.oracle.com</id>
      <username>your OTN credential</username>
      <password>and password</password>
      <configuration>
        <basicAuthScope>
          <host>ANY</host>
          <port>ANY</port>
          <realm>OAM 11g</realm>
        </basicAuthScope>
        <httpConfiguration>
          <all>
            <params>
              <property>
                <name>http.protocol.allow-circular-redirects</name>
                <value>%b,true</value>
              </property>
            </params>
          </all>
        </httpConfiguration>
      </configuration>
    </server>
  </servers>
```
## Service WSDL
In this example, the WSDL is generated on the fly and available to the client 
at http://localhost:7001/SimpleWebService/user?WSDL), assuming the service was deployed to a server listening to port
7001 on localhost.
 
```xml
<?xml version='1.0' encoding='UTF-8'?>
<definitions xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" 
xmlns:wsp="http://www.w3.org/ns/ws-policy" xmlns:wsp1_2="http://schemas.xmlsoap.org/ws/2004/09/policy" 
xmlns:wsam="http://www.w3.org/2007/05/addressing/metadata" 
xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" 
xmlns:tns="http://ddsharpe.net" 
xmlns:xsd="http://www.w3.org/2001/XMLSchema" 
xmlns="http://schemas.xmlsoap.org/wsdl/" 
targetNamespace="http://ddsharpe.net" name="MyService">
  <types>
    <xsd:schema>
      <xsd:import namespace="http://ddsharpe.net" schemaLocation="http://localhost:7001/SimpleWebService/user?xsd=1"/>
    </xsd:schema>
  </types>
  <message name="getData">
    <part name="parameters" element="tns:getData"/>
  </message>
  <message name="getDataResponse">
    <part name="parameters" element="tns:getDataResponse"/>
  </message>
  <portType name="MyServiceType">
    <operation name="getData">
      <input wsam:Action="http://ddsharpe.net/MyServiceType/getDataRequest" message="tns:getData"/>
      <output wsam:Action="http://ddsharpe.net/MyServiceType/getDataResponse" message="tns:getDataResponse"/>
    </operation>
  </portType>
  <binding name="MyServicePortBinding" type="tns:MyServiceType">
    <soap12:binding transport="http://schemas.xmlsoap.org/soap/http" style="document"/>
    <operation name="getData">
      <soap12:operation soapAction=""/>
      <input>
        <soap12:body use="literal"/>
      </input>
      <output>
        <soap12:body use="literal"/>
      </output>
    </operation>
  </binding>
  <service name="MyService">
    <port name="MyServicePort" binding="tns:MyServicePortBinding">
      <soap12:address location="http://localhost:7001/SimpleWebService/user"/>
    </port>
  </service>
</definitions>
```


