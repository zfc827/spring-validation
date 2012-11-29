package showcase.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import showcase.controller.matcher.PathMatcher;

import javax.annotation.Resource;

/**
 * @author zfc827@gmail.com
 */
@Controller
public class ValidationController {

    private final Logger logger = LoggerFactory.getLogger(ValidationController.class);

    @Resource(name = "pathMatcher")
    private PathMatcher pathMatcher;

    @RequestMapping(value = "/rules", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    public @ResponseBody String getRules(@RequestParam String path) {
        logger.debug("path = {}", path);
        Class<?> validBeanClass = pathMatcher.getValidBeanClass(path);
        logger.debug("validBeanClass = {}", validBeanClass.getName());

        return validBeanClass.getName();
    }
}
