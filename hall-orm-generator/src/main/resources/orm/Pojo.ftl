${pojo.getPackageDeclaration()}
// Generated ${date} by hall orm generator ${version}

<#assign classbody>
<#include "PojoTypeDeclaration.ftl"/> {

<#if !pojo.isInterface()>
<#include "PojoFields.ftl"/>

<#include "PojoConstructors.ftl"/>
   

<#include "PojoToString.ftl"/>

<#include "PojoEqualsHashcode.ftl"/>

<#else>
<#include "PojoInterfacePropertyAccessors.ftl"/>

</#if>
<#include "PojoExtraClassCode.ftl"/>

}
</#assign>

${pojo.generateImports()}
${classbody}

