package com.zxk175.generator.custom;

import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author zxk175
 * @since 2019-08-29 14:13
 */
public class MyFreemarkerTemplateEngine extends FreemarkerTemplateEngine {

    @Override
    public AbstractTemplateEngine batchOutput() {
        try {
            List<TableInfo> tableInfoList = getConfigBuilder().getTableInfoList();
            for (TableInfo tableInfo : tableInfoList) {
                Map<String, Object> objectMap = getObjectMap(tableInfo);
                Map<String, String> pathInfo = getConfigBuilder().getPathInfo();
                TemplateConfig template = getConfigBuilder().getTemplate();
                // 自定义内容
                InjectionConfig injectionConfig = getConfigBuilder().getInjectionConfig();
                if (null != injectionConfig) {
                    injectionConfig.initMap();
                    objectMap.put("cfg", injectionConfig.getMap());
                    List<FileOutConfig> focList = injectionConfig.getFileOutConfigList();
                    for (FileOutConfig foc : focList) {
                        if (isCreate(FileType.OTHER, foc.outputFile(tableInfo))) {
                            writer(objectMap, foc.getTemplatePath(), foc.outputFile(tableInfo));
                        }
                    }
                } else {
                    // Mp.java
                    String entityName = tableInfo.getEntityName();
                    if (null != entityName && null != pathInfo.get(ConstVal.ENTITY_PATH)) {
                        String entityFile = String.format((pathInfo.get(ConstVal.ENTITY_PATH) + File.separator + "%s" + suffixJavaOrKt()), entityName);
                        if (isCreate(FileType.ENTITY, entityFile)) {
                            writer(objectMap, templateFilePath(template.getEntity(getConfigBuilder().getGlobalConfig().isKotlin())), entityFile);
                        }
                    }
                    // MpMapper.java
                    if (null != tableInfo.getMapperName() && null != pathInfo.get(ConstVal.MAPPER_PATH)) {
                        String mapperFile = String.format((pathInfo.get(ConstVal.MAPPER_PATH) + File.separator + tableInfo.getMapperName() + suffixJavaOrKt()), entityName);
                        if (isCreate(FileType.MAPPER, mapperFile)) {
                            writer(objectMap, templateFilePath(template.getMapper()), mapperFile);
                        }
                    }
                    // MpMapper.xml
                    if (null != tableInfo.getXmlName() && null != pathInfo.get(ConstVal.XML_PATH)) {
                        String xmlFile = String.format((pathInfo.get(ConstVal.XML_PATH) + File.separator + tableInfo.getXmlName() + ConstVal.XML_SUFFIX), entityName);
                        if (isCreate(FileType.XML, xmlFile)) {
                            writer(objectMap, templateFilePath(template.getXml()), xmlFile);
                        }
                    }
                    // IMpService.java
                    if (null != tableInfo.getServiceName() && null != pathInfo.get(ConstVal.SERVICE_PATH)) {
                        String serviceFile = String.format((pathInfo.get(ConstVal.SERVICE_PATH) + File.separator + tableInfo.getServiceName() + suffixJavaOrKt()), entityName);
                        if (isCreate(FileType.SERVICE, serviceFile)) {
                            writer(objectMap, templateFilePath(template.getService()), serviceFile);
                        }
                    }
                    // MpServiceImpl.java
                    if (null != tableInfo.getServiceImplName() && null != pathInfo.get(ConstVal.SERVICE_IMPL_PATH)) {
                        String implFile = String.format((pathInfo.get(ConstVal.SERVICE_IMPL_PATH) + File.separator + tableInfo.getServiceImplName() + suffixJavaOrKt()), entityName);
                        if (isCreate(FileType.SERVICE_IMPL, implFile)) {
                            writer(objectMap, templateFilePath(template.getServiceImpl()), implFile);
                        }
                    }
                    // MpController.java
                    if (null != tableInfo.getControllerName() && null != pathInfo.get(ConstVal.CONTROLLER_PATH)) {
                        String controllerFile = String.format((pathInfo.get(ConstVal.CONTROLLER_PATH) + File.separator + tableInfo.getControllerName() + suffixJavaOrKt()), entityName);
                        if (isCreate(FileType.CONTROLLER, controllerFile)) {
                            writer(objectMap, templateFilePath(template.getController()), controllerFile);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            AbstractTemplateEngine.logger.error("无法创建文件，请检查配置信息！", ex);
        }

        return this;
    }

    @Override
    public Map<String, Object> getObjectMap(TableInfo tableInfo) {
        return super.getObjectMap(tableInfo);
    }
}
