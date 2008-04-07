<@pp.renameOutputFile extension="csv" />
<#list rows.headers as header>${header}<#if header_has_next>,</#if></#list>
<#list rows as row>
${row.KEYWORDS},<#switch row.BUNDLE><#case 1>Browser<#break><#case 3>PDA<#break><#case 4>Therapybook Diabetes<#break></#switch>,"${row.CONTEXT?xml?replace('\"','\"\"','r')}","${row.EN?replace('\"','\"\"','r')}","${row[rows.headers?last]?replace('\"','\"\"','r')}"
</#list>
