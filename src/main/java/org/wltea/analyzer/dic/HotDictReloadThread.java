package org.wltea.analyzer.dic;

import org.apache.logging.log4j.Logger;
import org.elasticsearch.common.logging.ESLoggerFactory;
import org.wltea.analyzer.cfg.Configuration;

/**
 * @Author: lilingyan
 * @Date 2018/9/17 14:43
 */
public class HotDictReloadThread implements Runnable {

    private static final Logger logger = ESLoggerFactory.getLogger(HotDictReloadThread.class.getName());

    @Override
    public void run() {
        while(true) {
            logger.info("[==========]reload hot dict from mysql......");
            try {
                if(Dictionary.getSingleton().needReLoad()){
                    Dictionary.getSingleton().reLoadMainDict();
                    Dictionary.getSingleton().updateNeedReload();
                }

                Thread.sleep(Integer.valueOf(String.valueOf(JDBCReloadProperties.interval)));
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
    }

}
