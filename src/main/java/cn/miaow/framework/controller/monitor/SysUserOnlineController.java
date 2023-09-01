package cn.miaow.framework.controller.monitor;

import cn.miaow.framework.aspectj.annotation.Log;
import cn.miaow.framework.constant.CacheConstants;
import cn.miaow.framework.constant.enums.BusinessType;
import cn.miaow.framework.entity.system.SysUserOnline;
import cn.miaow.framework.model.AjaxResult;
import cn.miaow.framework.model.BaseController;
import cn.miaow.framework.model.LoginUser;
import cn.miaow.framework.model.TableDataInfo;
import cn.miaow.framework.service.system.ISysUserOnlineService;
import cn.miaow.framework.util.RedisCache;
import cn.miaow.framework.util.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 在线用户监控
 *
 * @author miaow
 */
@RestController
@RequestMapping("/monitor/online" )
public class SysUserOnlineController extends BaseController {
    private final ISysUserOnlineService userOnlineService;

    private final RedisCache redisCache;

    public SysUserOnlineController(ISysUserOnlineService userOnlineService, RedisCache redisCache) {
        this.userOnlineService = userOnlineService;
        this.redisCache = redisCache;
    }

    @PreAuthorize("@ss.hasPermission('monitor:online:list')" )
    @GetMapping("/list" )
    public TableDataInfo list(String ipaddr, String userName) {
        Collection<String> keys = redisCache.keys(CacheConstants.LOGIN_TOKEN_KEY + "*" );
        List<SysUserOnline> userOnlineList = new ArrayList<>();
        for (String key : keys) {
            LoginUser user = redisCache.getCacheObject(key);
            if (StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(userName)) {
                userOnlineList.add(userOnlineService.selectOnlineByInfo(ipaddr, userName, user));
            } else if (StringUtils.isNotEmpty(ipaddr)) {
                userOnlineList.add(userOnlineService.selectOnlineByIpaddr(ipaddr, user));
            } else if (StringUtils.isNotEmpty(userName) && StringUtils.isNotNull(user.getUser())) {
                userOnlineList.add(userOnlineService.selectOnlineByUserName(userName, user));
            } else {
                userOnlineList.add(userOnlineService.loginUserToUserOnline(user));
            }
        }
        Collections.reverse(userOnlineList);
        userOnlineList.removeAll(Collections.singleton(null));
        return getDataTable(userOnlineList);
    }

    /**
     * 强退用户
     */
    @PreAuthorize("@ss.hasPermission('monitor:online:forceLogout')" )
    @Log(title = "在线用户" , businessType = BusinessType.FORCE)
    @DeleteMapping("/{tokenId}" )
    public AjaxResult forceLogout(@PathVariable String tokenId) {
        redisCache.deleteObject(CacheConstants.LOGIN_TOKEN_KEY + tokenId);
        return success();
    }
}
