<#list suffixes as suffix>
    <#if "DEFAULT" != suffix.LANGUAGE_CODE>
        <#assign lang = ".${suffix.LANGUAGE_CODE?lower_case}">
    <#else>
        <#assign lang = "">
    </#if>
    <#if "TW" == suffix.COUNTRY_CODE>
        <#assign country = "-CHT">
    <#elseif "DEFAULT" != suffix.COUNTRY_CODE>
        <#assign country = "-${suffix.COUNTRY_CODE?upper_case}">
    <#else>
        <#if "zh" == suffix.LANGUAGE_CODE>
            <#assign country = "-CHS">
        <#else>
            <#assign country = "">
        </#if>
    </#if>
    <@pp.changeOutputFile name="${fileNamePrefix}${lang?default('')}${country?default('')}.resx" append=false/>
<?xml version="1.0" encoding="utf-8"?>
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
</#list>

<#list props as prop>
    <#if "DEFAULT" != prop.LANGUAGE_CODE>
        <#assign lang = ".${prop.LANGUAGE_CODE?lower_case}">
    <#else>
        <#assign lang = "">
    </#if>
    <#if "TW" == prop.COUNTRY_CODE>
        <#assign country = "-CHT">
    <#elseif "DEFAULT" != prop.COUNTRY_CODE>
        <#assign country = "-${prop.COUNTRY_CODE?upper_case}">
    <#else>
        <#if "zh" == prop.LANGUAGE_CODE>
            <#assign country = "-CHS">
        <#else>
            <#assign country = "">
        </#if>
    </#if>
    <@pp.changeOutputFile name="${fileNamePrefix}${lang?default('')}${country?default('')}.resx" append=true/>
    <data name="${prop.KEYWORD}">
        <value>${prop.VALUE}</value>
    </data>
</#list>

<#list suffixes as suffix>
    <#if "DEFAULT" != suffix.LANGUAGE_CODE>
        <#assign lang = ".${suffix.LANGUAGE_CODE?lower_case}">
    <#else>
        <#assign lang = "">
    </#if>
    <#if "TW" == suffix.COUNTRY_CODE>
        <#assign country = "-CHT">
    <#elseif "DEFAULT" != suffix.COUNTRY_CODE>
        <#assign country = "-${suffix.COUNTRY_CODE?upper_case}">
    <#else>
        <#if "zh" == suffix.LANGUAGE_CODE>
            <#assign country = "-CHS">
        <#else>
            <#assign country = "">
        </#if>
    </#if>
    <@pp.changeOutputFile name="${fileNamePrefix}${lang?default('')}${country?default('')}.resx" append=true/>
</root>
</#list>