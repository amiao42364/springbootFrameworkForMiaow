package cn.miaow.framework.controller.system;

import cn.miaow.framework.config.security.impl.SysRegisterService;
import cn.miaow.framework.model.AjaxResult;
import cn.miaow.framework.model.BaseController;
import cn.miaow.framework.model.RegisterBody;
import cn.miaow.framework.service.system.ISysConfigService;
import cn.miaow.framework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册验证
 */
@RestController
public class SysRegisterController extends BaseController {
    private final SysRegisterService registerService;

    private final ISysConfigService configService;

    public SysRegisterController(SysRegisterService registerService, ISysConfigService configService) {
        this.registerService = registerService;
        this.configService = configService;
    }

    @PostMapping("/register" )
    public AjaxResult register(@RequestBody RegisterBody user) {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser" )))) {
            return error("当前系统没有开启注册功能！" );
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}
