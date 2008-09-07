<#if (pp.outputFileName=="resx.resx")>
    <@pp.dropOutputFile/>
</#if>
<#assign fileNames = pp.newWritableSequence()/>

<#macro processChinese countryCode languageCode>
    <#assign lang = ".${languageCode?lower_case}">
    <#assign country = "${countryCode}">
    <#assign isCountryProcessed = true>
</#macro>

<#macro header>
<?xml version="1.0" encoding="UTF-8"?>
<root>
    <xsd:schema id="root" xmlns="" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:msdata="urn:schemas-microsoft-com:xml-msdata">
        <xsd:element name="data">
            <xsd:complexType>
                <xsd:sequence>
                    <xsd:element name="value" type="xsd:string" minOccurs="0" msdata:Ordinal="2" />
                </xsd:sequence>
                <xsd:attribute name="name" type="xsd:string" />
                <xsd:attribute name="type" type="xsd:string" />
                <xsd:attribute name="mimetype" type="xsd:string" />
            </xsd:complexType>
        </xsd:element>
    </xsd:schema>
    <resheader name="resmimetype">
        <value>text/microsoft-resx</value>
    </resheader>
    <resheader name="version">
        <value>2.0</value>
    </resheader>
    <resheader name="reader">
        <value>System.Resources.ResXResourceReader, System.Windows.Forms, Version=2.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089</value>
    </resheader>
    <resheader name="writer">
        <value>System.Resources.ResXResourceWriter, System.Windows.Forms, Version=2.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089</value>
    </resheader>
</#macro>
<#list translations as translation>
    <#assign isCountryProcessed = false>
    <#switch translation.language.code>
        <#case "DEFAULT">
            <#assign lang = "">
            <#break/>
        <#case "zh">
            <@processChinese countryCode="-CHS" languageCode="${translation.language.code}"/>
            <#break/>
        <#case "zht">
            <@processChinese countryCode="-CHT" languageCode="${translation.language.code}"/>
            <#break/>
        <#default>
            <#assign lang = ".${translation.language.code?lower_case}">
            <#break/>
    </#switch>
    <#if !isCountryProcessed>
        <#switch translation.country.code>
            <#case "DEFAULT">
                <#assign country = "">
                <#break/>
            <#default>
                <#assign country = "-${translation.country.code?upper_case}">
                <#break/>
        </#switch>
    </#if>
    <#assign fileName="${translation.bundle.resourceName}${lang?default('')}${country?default('')}.resx"/>
    <#if !pp.outputFileExists(fileName)>
        <@pp.add seq=fileNames value={"name":fileName}/>
        <@pp.changeOutputFile name="${fileName}" append=true/>
        <@header></@>
    </#if>
    <@pp.changeOutputFile name="${fileName}" append=true/>
    <data name="${translation.keyword.keyword}">
        <value>${translation.value?xml}</value>
    </data>
</#list>
<#list fileNames as fileName>
<@pp.changeOutputFile name="${fileName.name}" append=true/>
</root>
</#list>
