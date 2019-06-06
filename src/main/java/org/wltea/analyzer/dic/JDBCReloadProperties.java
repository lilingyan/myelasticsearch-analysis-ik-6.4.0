package org.wltea.analyzer.dic;

import org.apache.logging.log4j.Logger;
import org.elasticsearch.common.io.PathUtils;
import org.elasticsearch.common.logging.ESLoggerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

/**
 * @Author: lilingyan
 * @Date 2018/9/17 16:39
 */
public class JDBCReloadProperties {

    private static final Logger logger = ESLoggerFactory.getLogger(JDBCReloadProperties.class.getName());

    private static JDBCReloadProperties singleton;


    //jdbc
    public static String url;
    public static String username;
    public static String password;
    public static String driverClassName;

    //dataSource
    public static int initialSize;
    public static int minIdle;
    public static int maxActive;

    //sql
    public static String mainWordSql;
    public static String stopWordSql;
    public static String needReloadSql;
    public static String setReloadNeedSql;

    public static int interval;

    public static String columnField = "word";
    public static String columnReloadField = "need";

    public static Path conf_dir;

    public static JDBCReloadProperties getSingleton() {
        logger.info("JDBCReloadPropertiesgetSingleton()");
        if(singleton == null){
            try {
                init();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return singleton;
    }

    private static void init() throws IOException {
        logger.info("JDBCReloadPropertiesInit()");
        Path file = PathUtils.get(conf_dir.toAbsolutePath().toString() , "jdbc-reload.properties");
        Properties prop = new Properties();
        prop.load(new FileInputStream(file.toFile()));
        url = prop.getProperty("jdbc.url");
        username = prop.getProperty("jdbc.username");
        password = prop.getProperty("jdbc.password");
        driverClassName = prop.getProperty("jdbc.driverClassName");
        initialSize = Integer.valueOf(prop.getProperty("jdbc.datasource.initialSize").trim());
        minIdle = Integer.valueOf(prop.getProperty("jdbc.datasource.minIdle").trim());
        maxActive = Integer.valueOf(prop.getProperty("jdbc.datasource.maxActive").trim());
        mainWordSql = prop.getProperty("jdbc.reload.mainWordSql");
        stopWordSql = prop.getProperty("jdbc.reload.stopWordSql");
        needReloadSql = prop.getProperty("jdbc.reload.reloadNeedSql");
        setReloadNeedSql = prop.getProperty("jdbc.reload.setReloadNeedSql");
        interval = Integer.valueOf(prop.getProperty("jdbc.reload.interval").trim());
    }

}
