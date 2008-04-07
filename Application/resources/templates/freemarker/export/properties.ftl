<#assign currentFileName = "">
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
    <#assign newFileName = "${translation.bundle.resourceName}${lang?default('')}${country?default('')}.rawproperties">
    <#if newFileName != currentFileName>
        <#assign currentFileName = "${newFileName}">
        <@pp.changeOutputFile name="${currentFileName}" append=true/>
    </#if>
    <#if (translation.value!)?length gt 0>
${translation.keyword.keyword}=${translation.value}
    </#if>
</#list>
