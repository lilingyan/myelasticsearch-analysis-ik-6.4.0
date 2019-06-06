package org.wltea.analyzer.dic;

import org.apache.logging.log4j.Logger;
import org.elasticsearch.common.logging.ESLoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lilingyan
 * @Date 2018/9/17 15:56
 */
public class MysqlRepo {

    private static final Logger logger = ESLoggerFactory.getLogger(MysqlRepo.class.getName());

    Connection connection;

    public MysqlRepo() {
        init();
    }

    private void init() {
        JDBCReloadProperties.getSingleton();
        initDataSource();
    }


    private void initDataSource() {
        logger.info("initDataSource()");
        try {
            connection = DriverManager.getConnection(JDBCReloadProperties.url,JDBCReloadProperties.username,JDBCReloadProperties.password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> selectMainWords(){
        logger.info("selectMainWords()");
        List<String> mainWordList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(JDBCReloadProperties.mainWordSql);
             ResultSet rs = preparedStatement.executeQuery()){
            while(rs.next()) {
                String theWord = rs.getString(JDBCReloadProperties.columnField);
                logger.info("[==========]main word from mysql: " + theWord);
                mainWordList.add(theWord);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mainWordList;
    }

    public List<String> selectStopWords(){
        logger.info("selectStopWords()");
        List<String> stopWordList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(JDBCReloadProperties.stopWordSql);
             ResultSet rs = preparedStatement.executeQuery()){
            while(rs.next()) {
                String theWord = rs.getString(JDBCReloadProperties.columnField);
                logger.info("[==========]stop word from mysql: " + theWord);
                stopWordList.add(theWord);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stopWordList;
    }

    public Integer selectNeedReload(){
        logger.info("needReLoad()");
        Integer isNeed = 0 ;
        try (PreparedStatement preparedStatement = connection.prepareStatement(JDBCReloadProperties.needReloadSql);
             ResultSet rs = preparedStatement.executeQuery()){
            while(rs.next()) {
                isNeed = rs.getInt(JDBCReloadProperties.columnReloadField);
                logger.info("[==========]reload need from mysql: " + isNeed);
            }
            return isNeed;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isNeed;
    }

    public Integer updateNeedReload(){
        logger.info("updateNeedReload()");
        try (PreparedStatement preparedStatement = connection.prepareStatement(JDBCReloadProperties.setReloadNeedSql)){
            int updateRowCnt = preparedStatement.executeUpdate();
            return updateRowCnt;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
