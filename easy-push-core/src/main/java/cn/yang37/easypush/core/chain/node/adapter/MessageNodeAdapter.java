package cn.yang37.easypush.core.chain.node.adapter;


import cn.yang37.easypush.core.chain.node.MessageNode;
import cn.yang37.easypush.core.factory.ConfigFactory;
import cn.yang37.easypush.core.util.MessageProvider;
import cn.yang37.easypush.entity.config.SceneConfigProperties;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: 节点
 * @class: MessageHandler
 * @author: yang37z@qq.com
 * @date: 2023/1/11 11:34
 * @version: 1.0
 */
@Slf4j
public abstract class MessageNodeAdapter extends MessageProvider implements MessageNode {

    /**
     * 全局配置前缀
     */
    private static final String PREFIX_CONFIG = "cn.yang37.easy-push";

    /**
     * 全局配置
     */
    protected SceneConfigProperties globalConfig() {
        return ConfigFactory.instance().getConfigProperties(SceneConfigProperties.class, PREFIX_CONFIG);
    }

    /**
     * 场景配置
     */
    protected SceneConfigProperties sceneConfig() {
        return globalConfig();
    }

    protected void logConfig() {
        log.info("[Config][{}] -> {}", sceneConfig().getPrefix(), JSON.toJSONString(sceneConfig()));
    }
}