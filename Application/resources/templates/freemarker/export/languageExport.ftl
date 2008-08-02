<@pp.renameOutputFile extension="xls" />
<?xml version="1.0"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
          xmlns:o="urn:schemas-microsoft-com:office:office"
          xmlns:x="urn:schemas-microsoft-com:office:excel"
          xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
          xmlns:html="http://www.w3.org/TR/REC-html40">
    <DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
        <Author>TongueTied</Author>
        <LastAuthor>TongueTied</LastAuthor>
        <Created>${pp.now?string("yyyy-MM-dd")}T${pp.now?string("HH:mm:ss")}Z</Created>
        <LastSaved>${pp.now?string("yyyy-MM-dd")}T${pp.now?string("HH:mm:ss")}Z</LastSaved>
        <Company>TongueTied</Company>
        <Version>10.3501</Version>
    </DocumentProperties>
    <OfficeDocumentSettings xmlns="urn:schemas-microsoft-com:office:office">
        <DownloadComponents/>
        <LocationOfComponents HRef="file:///D:\"/>
    </OfficeDocumentSettings>
    <ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
        <WindowHeight>12270</WindowHeight>
        <WindowWidth>15240</WindowWidth>
        <WindowTopX>360</WindowTopX>
        <WindowTopY>60</WindowTopY>
        <ProtectStructure>False</ProtectStructure>
        <ProtectWindows>False</ProtectWindows>
    </ExcelWorkbook>
    <Styles>
        <Style ss:ID="Default" ss:Name="Normal">
            <Alignment ss:Vertical="Bottom"/>
            <Borders/>
            <Font ss:FontName="Futura Lt BT"/>
            <Interior/>
            <NumberFormat/>
            <Protection/>
        </Style>
        <Style ss:ID="s21">
            <Alignment ss:Vertical="Bottom" ss:WrapText="1"/>
        </Style>
        <Style ss:ID="s24">
            <Borders>
                <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
                <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
                <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
                <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
            </Borders>
            <Font ss:FontName="Futura Lt BT" x:Family="Swiss" ss:Bold="1"/>
            <Interior ss:Color="#C0C0C0" ss:Pattern="Solid"/>
        </Style>
    </Styles>
    <Worksheet ss:Name="Sheet1">
        <Table ss:ExpandedColumnCount="${rows.headers?size + 1}" ss:ExpandedRowCount="${rows?size + 1}" x:FullColumns="1" x:FullRows="1">
            <Column ss:Width="226.5"/>
            <Column ss:Width="100"/>
            <Column ss:StyleID="s21" ss:AutoFitWidth="0" ss:Width="149.25"/>
            <Column ss:StyleID="s21" ss:AutoFitWidth="0" ss:Width="250.75"/>
            <Column ss:StyleID="s21" ss:AutoFitWidth="0" ss:Width="250.75"/>
            <Row>
                <#assign newTranslation="${rows.headers?last}"/>
                <#list rows.headers as header>
                    <#if header != newTranslation>
                <Cell ss:StyleID="s24"><Data ss:Type="String">${header}</Data></Cell>
                    <#else>
                <Cell ss:StyleID="s24"><Data ss:Type="String">NEW TRANSLATION: ${newTranslation}</Data></Cell>
                    </#if>
                </#list>
            </Row>
            <#list rows as row>
            <Row>
                <Cell><Data ss:Type="String">${row.KEYWORDS}</Data></Cell>
                <Cell><Data ss:Type="String"><#switch row.BUNDLE><#case 1>Browser<#break><#case 3>PDA<#break><#case 4>Therapybook Diabetes<#break></#switch></Data></Cell>
                <#if row.CONTEXT?has_content>
                <Cell><Data ss:Type="String">${row.CONTEXT?xml}</Data></Cell>
                <Cell><Data ss:Type="String">${row.EN?xml}</Data></Cell>
                    <#if row[newTranslation]?has_content>
                <Cell><Data ss:Type="String">${row[newTranslation]?xml}</Data></Cell>
                    </#if>
                <#else>
                <Cell ss:Index="${rows.headers?seq_index_of("EN") + 1}"><Data ss:Type="String">${row.EN?xml}</Data></Cell>
                    <#if row[newTranslation]?has_content>
                <Cell ss:Index="${rows.headers?seq_index_of(newTranslation) + 1}"><Data ss:Type="String">${row[newTranslation]?xml}</Data></Cell>
                    </#if>
                </#if>
            </Row>
            </#list>
        </Table>
        <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
            <Selected/>
            <FreezePanes/>
            <FrozenNoSplit/>
            <SplitHorizontal>1</SplitHorizontal>
            <TopRowBottomPane>1</TopRowBottomPane>
            <ActivePane>2</ActivePane>
            <Panes>
                <Pane>
                    <Number>3</Number>
                    <ActiveRow>1</ActiveRow>
                    <ActiveCol>1</ActiveCol>
                </Pane>
            </Panes>
            <ProtectObjects>False</ProtectObjects>
            <ProtectScenarios>False</ProtectScenarios>
        </WorksheetOptions>
    </Worksheet>
    <Worksheet ss:Name="Sheet2">
        <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
            <ProtectObjects>False</ProtectObjects>
            <ProtectScenarios>False</ProtectScenarios>
        </WorksheetOptions>
    </Worksheet>
    <Worksheet ss:Name="Sheet3">
        <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
            <ProtectObjects>False</ProtectObjects>
            <ProtectScenarios>False</ProtectScenarios>
        </WorksheetOptions>
    </Worksheet>
</Workbook>
