package showcase.controller.matcher;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

/**
 * @author zfc827@gmail.com
 */
@Component(value = "pathMatcher")
public class PathMatcher {

    @Resource(type = RequestMappingHandlerMapping.class)
    private RequestMappingHandlerMapping handlerMapping;

    public Class<?> getValidBeanClass(String path) {
        Class parameterClass = null;

        org.springframework.util.PathMatcher pathMatcher = handlerMapping.getPathMatcher();
        Map<RequestMappingInfo,HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        for(Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo requestMappingInfo = entry.getKey();
            Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
            for(String pattern : patterns) {
                if(pathMatcher.match(pattern, path)) {
                    HandlerMethod handlerMethod = entry.getValue();
                    for(MethodParameter methodParameter : handlerMethod.getMethodParameters()) {
                        for(Annotation parameterAnnotation : methodParameter.getParameterAnnotations()) {
                            if(parameterAnnotation.annotationType().equals(Valid.class)) {
                                // 获取 actionPath 指定 handler method 标识了 @Valid 注解的 Class对象。
                                parameterClass = methodParameter.getParameterType();
                                break;
                            }
                        }
                    }
                }
            }
        }
        return parameterClass;
    }
}
