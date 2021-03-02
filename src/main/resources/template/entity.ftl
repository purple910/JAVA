package ${pack};

<#list importList as list>
${list}
</#list>

/**
 * @author: <#if user??>${user}</#if>
 * @description: <#if description??>${description}</#if>
 * @date: ${datetime}
 **/
@Data
public class ${className} {
<#list noteList as note >

    /**
     * ${note.desc}
     **/
    private ${note.type} ${note.filed};
</#list>

}