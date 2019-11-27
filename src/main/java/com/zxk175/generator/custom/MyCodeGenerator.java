package com.zxk175.generator.custom;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.zxk175.generator.enums.PathType;
import lombok.Data;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zxk175
 * @since 2019-08-29 14:01
 */
@Data
public class MyCodeGenerator {

    private DataSourceConfig dataSourceConfig;
    private MyPackageConfig myPackageConfig;
    private StrategyConfig strategyConfig;
    private GlobalConfig globalConfig;
    private String controllerBasePath;
    private String templateBasePath;
    private String javaBasePath;
    private String javaPath;
    private String xmlPath;
    private String[] include;


    public MyCodeGenerator(String basePath, String javaBasePath, String controllerBasePath, String templateBasePath, List<String> include, DataSourceConfig dataSourceConfig, MyPackageConfig myPackageConfig, StrategyConfig strategyConfig, GlobalConfig globalConfig) {
        this.dataSourceConfig = dataSourceConfig;
        this.myPackageConfig = myPackageConfig;
        this.strategyConfig = strategyConfig;
        this.globalConfig = globalConfig;
        this.controllerBasePath = controllerBasePath;
        this.templateBasePath = templateBasePath;
        this.javaBasePath = javaBasePath;
        this.javaPath = basePath + "/java/" + javaBasePath;
        this.xmlPath = basePath + "/resources/";
        this.include = CollUtil.isEmpty(include) ? null : include.toArray(new String[0]);
    }

    public static String getProjectPathByLevel(PathType pathType, boolean isJoinDir) {
        return getProjectPathByLevel(pathType) + (isJoinDir ? "/code" : "");
    }

    private static String getProjectPathByLevel(PathType pathType) {
        int level = pathType.value();
        if (level <= 0) {
            return getProjectBasePathFile().getPath();
        }

        File file = null;
        for (int i = 0; i < level; i++) {
            file = (file == null) ? getProjectBasePathFile().getParentFile() : file.getParentFile();
        }

        return file.getPath();
    }

    private static File getProjectBasePathFile() {
        String srcPath = System.getProperty("user.dir");
        srcPath = srcPath.replaceAll("\\\\", "/");
        return new File(srcPath);
    }

    public void init() {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        mpg.setGlobalConfig(globalConfig());

        // 数据源配置
        mpg.setDataSource(dataSourceConfig());

        // 包配置
        mpg.setPackageInfo(packageConfig());

        // 策略配置
        mpg.setStrategy(strategyConfig());

        // 模板配置
        mpg.setTemplate(templateConfig(true));

        // 自定义配置
        mpg.setCfg(injectionConfig());

        // 模板引擎
        mpg.setTemplateEngine(new MyFreemarkerTemplateEngine());

        mpg.execute();

        System.exit(0);
    }

    private GlobalConfig globalConfig() {
        GlobalConfig globalConfig = this.globalConfig;
        GlobalConfig gc = new GlobalConfig();
        // 是否覆盖已有文件
        gc.setFileOverride(globalConfig.isFileOverride());
        // 开启 activeRecord 模式
        gc.setActiveRecord(true);
        // 是否打开输出目录
        gc.setOpen(false);
        // 开启Swagger2
        gc.setSwagger2(globalConfig.isSwagger2());
        // xml 二级缓存
        gc.setEnableCache(false);
        // xml resultMap
        gc.setBaseResultMap(false);
        // xml columnList
        gc.setBaseColumnList(false);
        // 使用java8 LocalDate
        gc.setDateType(DateType.TIME_PACK);
        // 使用雪花Id
        gc.setIdType(IdType.ID_WORKER);
        // 作者
        gc.setAuthor("zxk175");

        // 自定义文件命名
        gc.setMapperName("%sDao");
        gc.setXmlName("%sMapper");
        gc.setServiceName("I%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");

        return gc;
    }

    private DataSourceConfig dataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);

        CopyOptions copyOptions = new CopyOptions();
        copyOptions.setIgnoreNullValue(true);
        copyOptions.setIgnoreError(true);

        BeanUtil.copyProperties(this.dataSourceConfig, dsc, copyOptions);

        return dsc;
    }

    private MyPackageConfig packageConfig() {
        MyPackageConfig pc = new MyPackageConfig();
        pc.setModuleName("module");
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("dao");
        pc.setXml("mappers");

        CopyOptions copyOptions = new CopyOptions();
        copyOptions.setIgnoreNullValue(true);
        copyOptions.setIgnoreError(true);

        BeanUtil.copyProperties(this.myPackageConfig, pc, copyOptions);

        return pc;
    }

    private StrategyConfig strategyConfig() {
        StrategyConfig strategy = new StrategyConfig();
        // 表前缀
        strategy.setTablePrefix("t_");
        // 驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 是否为lombok模型
        strategy.setEntityLombokModel(true);
        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("create_time", FieldFill.INSERT));
        tableFillList.add(new TableFill("update_time", FieldFill.INSERT_UPDATE));
        strategy.setTableFillList(tableFillList);

        strategy.setInclude(include);

        strategy.setSuperControllerClass(this.strategyConfig.getSuperControllerClass());

        return strategy;
    }

    private MyTemplateConfig templateConfig(boolean isNull) {
        String tmpBasePath = "templates/" + this.templateBasePath;

        MyTemplateConfig templateConfig = new MyTemplateConfig();
        templateConfig.setController(isNull ? null : tmpBasePath + "/controller.java.ftl");
        templateConfig.setEntity(isNull ? null : tmpBasePath + "/entity.java.ftl");
        templateConfig.setMapper(isNull ? null : tmpBasePath + "/mapper.java.ftl");
        templateConfig.setXml(isNull ? null : tmpBasePath + "/mapper.xml.ftl");
        templateConfig.setService(isNull ? null : tmpBasePath + "/service.java.ftl");
        templateConfig.setServiceImpl(isNull ? null : tmpBasePath + "/serviceImpl.java.ftl");

        return templateConfig;
    }

    private InjectionConfig injectionConfig() {
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>(8);
                map.put("since", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                this.setMap(map);
            }
        };

        cfg.setFileOutConfigList(fileOutConfigList(cfg));

        return cfg;
    }

    private List<FileOutConfig> fileOutConfigList(InjectionConfig cfg) {
        List<FileOutConfig> focList = new ArrayList<>();

        MyPackageConfig packageConfig = packageConfig();
        MyTemplateConfig templateConfig = templateConfig(false);

        String controllerPath = controllerBasePath + "/java/" + javaBasePath + packageConfig.getController();
        String entityPath = javaPath + packageConfig.getEntity();
        String daoPath = javaPath + packageConfig.getMapper();
        String servicePath = javaPath + packageConfig.getService();
        String serviceImplPath = javaPath + packageConfig.getServiceImpl();

        String packagePath = javaBasePath.replace("/", ".");

        focList.add(buildFileOutConfig(FileType.XML, cfg, xmlPath + packageConfig.getXml(), packagePath, templateConfig.getXml()));

        focList.add(buildFileOutConfig(FileType.CONTROLLER, cfg, controllerPath, packagePath, templateConfig.getController()));

        focList.add(buildFileOutConfig(FileType.ENTITY, cfg, entityPath, packagePath, templateConfig.getEntity(false)));

        focList.add(buildFileOutConfig(FileType.MAPPER, cfg, daoPath, packagePath, templateConfig.getMapper()));

        focList.add(buildFileOutConfig(FileType.SERVICE, cfg, servicePath, packagePath, templateConfig.getService()));

        focList.add(buildFileOutConfig(FileType.SERVICE_IMPL, cfg, serviceImplPath, packagePath, templateConfig.getServiceImpl()));

        return focList;
    }

    private FileOutConfig buildFileOutConfig(FileType fileType, InjectionConfig cfg, String javaPath, String packagePath, String templatePath) {
        return new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String[] tableNames = tableInfo.getName().split("_");
                String moduleName = tableNames[1];

                ConfigBuilder config = cfg.getConfig();
                Map<String, String> packageInfo = config.getPackageInfo();

                // 重新设置包名
                String modulePackName = "." + moduleName;
                MyPackageConfig packageConfig = packageConfig();
                packageInfo.put(ConstVal.CONTROLLER, packagePath + packageConfig.getController() + modulePackName);
                packageInfo.put(ConstVal.ENTITY, packagePath + packageConfig.getEntity() + modulePackName);
                packageInfo.put(ConstVal.SERVICE, packagePath + packageConfig.getService() + modulePackName);
                packageInfo.put(ConstVal.SERVICE_IMPL, packagePath + packageConfig.getServiceImpl() + modulePackName);
                packageInfo.put(ConstVal.MAPPER, packagePath + packageConfig.getMapper() + modulePackName);
                packageInfo.put("moduleName", moduleName);

                String basePath = javaPath + "/" + moduleName + "/";

                switch (fileType) {
                    case XML:
                        return basePath + tableInfo.getXmlName() + ConstVal.XML_SUFFIX;
                    case CONTROLLER:
                        return basePath + tableInfo.getControllerName() + suffixJavaOrKt();
                    case ENTITY:
                        return basePath + tableInfo.getEntityName() + suffixJavaOrKt();
                    case MAPPER:
                        return basePath + tableInfo.getMapperName() + suffixJavaOrKt();
                    case SERVICE:
                        return basePath + tableInfo.getServiceName() + suffixJavaOrKt();
                    case SERVICE_IMPL:
                        return basePath + tableInfo.getServiceImplName() + suffixJavaOrKt();
                    default:
                        throw new RuntimeException("FileType not found");
                }
            }

            String suffixJavaOrKt() {
                return cfg.getConfig().getGlobalConfig().isKotlin() ? ConstVal.KT_SUFFIX : ConstVal.JAVA_SUFFIX;
            }
        };
    }
}
