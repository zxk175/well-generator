package ${package.Controller};

<#assign comment = table.comment?replace("表", "")>
<#assign pojo = entity?substring(0,1)?lower_case + entity?substring(1)>
import ${package.Service}.${table.serviceName};
import lombok.AllArgsConstructor;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
import org.springframework.web.bind.annotation.RequestMapping;
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${cfg.since}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@AllArgsConstructor
@RequestMapping("/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
@Api(tags = "${entity}", description = "${comment}")
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    private ${table.serviceName} ${pojo}Service;

}
</#if>
