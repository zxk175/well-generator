package ${package.Controller};

<#assign comment = table.comment?replace("表", "")>
<#assign pojo = entity?substring(0,1)?lower_case + entity?substring(1)>
import com.zxk175.notify.core.http.Response;
import ${package.Service}.${table.serviceName};
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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


    @ResponseBody
    @PostMapping("/save")
    @ApiOperation(value = "新增${comment}", notes = "新增${comment}")
    public Response save${entity}(@Validated @RequestBody Object param) {
        return ok();
    }

    @ResponseBody
    @PostMapping("/remove")
    @ApiOperation(value = "删除${comment}", notes = "删除${comment}")
    public Response remove${entity}(@Validated @RequestBody Object param) {
        return ok();
    }

    @ResponseBody
    @PostMapping("/modify")
    @ApiOperation(value = "修改${comment}", notes = "修改${comment}")
    public Response modify${entity}(@Validated @RequestBody Object param) {
        return ok();
    }

    @ResponseBody
    @PostMapping("/list")
    @ApiOperation(value = "${comment}分页列表", notes = "${comment}分页列表")
    public Response list${entity}Page(@Validated @RequestBody Object param) {
        return null;
    }

}
</#if>
