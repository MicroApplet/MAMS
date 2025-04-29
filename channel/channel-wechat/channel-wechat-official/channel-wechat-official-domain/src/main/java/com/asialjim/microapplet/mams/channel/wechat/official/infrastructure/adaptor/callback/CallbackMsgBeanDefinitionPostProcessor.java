package com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.callback;

import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.qrcode.QrCodeScene;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.qrcode.SceneQrCodeHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 回调消息处理器准备
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class CallbackMsgBeanDefinitionPostProcessor implements BeanFactoryPostProcessor, Ordered {
    @Override
    public void postProcessBeanFactory(@SuppressWarnings("NullableProblems") ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (!(beanFactory instanceof BeanDefinitionRegistry))
            return;
        // 获取 BeanFactory 的注册表
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;

        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            String className = beanDefinition.getBeanClassName();
            if (StringUtils.isBlank(className))
                continue;

            // 回调消息/事件处理器准备
            processCallMsgHandler(beanDefinitionName, className, registry);
            // 二维码扫码事件处理器准备
            processQrCodeScene(beanDefinitionName,className,registry);
        }
    }


    private void processCallMsgHandler(String beanDefinitionName, String className, BeanDefinitionRegistry registry) {
        CallMsgHandler callMsgHandler = callMsgHandler(className);
        if (Objects.isNull(callMsgHandler))
            return;

        String msgType = callMsgHandler.msgType().getCode();
        String eventType = callMsgHandler.eventType().getCode();

        String beanName = CallbackMsgProcessor.nameFormat(eventType, msgType);
        AbstractBeanDefinition target = BeanDefinitionBuilder.genericBeanDefinition(className).getBeanDefinition();
        registry.removeBeanDefinition(beanDefinitionName);
        registry.registerBeanDefinition(beanName, target);
    }

    private void processQrCodeScene(String beanDefinitionName, String className, BeanDefinitionRegistry registry) {
        QrCodeScene qrCodeScene = qrCodeScene(className);
        if (Objects.isNull(qrCodeScene))
            return;

        String beanName = SceneQrCodeHandler.nameFormat(qrCodeScene.value());
        AbstractBeanDefinition target = BeanDefinitionBuilder.genericBeanDefinition(className).getBeanDefinition();
        registry.removeBeanDefinition(beanDefinitionName);
        registry.registerBeanDefinition(beanName, target);
    }

    private CallMsgHandler callMsgHandler(String beanClassName) {
        try {
            Class<?> aClass = Class.forName(beanClassName);
            if (!CallbackMsgProcessor.class.isAssignableFrom(aClass))
                return null;

            CallMsgHandler annotation = aClass.getAnnotation(CallMsgHandler.class);
            if (Objects.isNull(annotation))
                throw new IllegalStateException(beanClassName + " 必须被注解：" + CallMsgHandler.class.getName() + "标注");

            return annotation;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private QrCodeScene qrCodeScene(String beanClassName) {
        try {
            Class<?> aClass = Class.forName(beanClassName);
            if (!SceneQrCodeHandler.class.isAssignableFrom(aClass))
                return null;

            QrCodeScene annotation = aClass.getAnnotation(QrCodeScene.class);
            if (Objects.isNull(annotation))
                throw new IllegalStateException(beanClassName + " 必须被注解：" + QrCodeScene.class.getName() + "标注");

            return annotation;
        } catch (ClassNotFoundException e) {
            return null;
        }

    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}