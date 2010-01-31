<#if pp.outputFileName="properties.properties">
    <@pp.dropOutputFile/>
</#if>
<#list translations as translation>
    <#switch translation.language.code>
        <#case "he">
            <#assign lang = "_iw">
            <#break/>
        <#case "DEFAULT">
            <#assign lang = "">
            <#break/>
        <#default>
            <#assign lang = "_${translation.language.code?lower_case}">
    </#switch>
    <#if "DEFAULT" != translation.country.code>
        <#assign country = "_${translation.country.code?upper_case}">
    <#else>
        <#assign country = "">
    </#if>
    <#assign newFileName = "${translation.bundle.resourceName}${lang?default('')}${country?default('')}.properties">
    <#if newFileName != pp.outputFileName>
        <@pp.changeOutputFile name="${newFileName}" append=true/>
    </#if>
    <#if (translation.value!)?length gt 0>
<@native2ascii iskey=true>${translation.keyword.keyword}</@native2ascii>=<@native2ascii>${translation.value}</@native2ascii>
    </#if>
</#list>
