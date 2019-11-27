package com.zxk175.generator;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.zxk175.generator.custom.MyCodeGenerator;
import com.zxk175.generator.custom.MyPackageConfig;
import com.zxk175.generator.enums.PathType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zxk175
 * @since 2019-09-05 14:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class CodeGeneratorTest {

    @Test
    public void generatorProd() {
        String projectBasePath = MyCodeGenerator.getProjectPathByLevel(PathType.CURRENT, true);
        String basePath = projectBasePath + "/well-notify/src/main";
        String javaBasePath = "";

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://127.0.0.1:23306/notify_data?useUnicode=true&serverTimezone=Asia/Shanghai");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("123456");

        MyPackageConfig myPackageConfig = new MyPackageConfig();
        myPackageConfig.setMapper(null);
        myPackageConfig.setXml(null);

        StrategyConfig strategyConfig = new StrategyConfig();

        GlobalConfig globalConfig = new GlobalConfig();

        List<String> include = new ArrayList<>();

        new MyCodeGenerator(basePath, javaBasePath, basePath, "normal", include, dataSourceConfig, myPackageConfig, strategyConfig, globalConfig).init();
    }
}