package cn.miaow.framework.config.security.impl;

import cn.miaow.framework.constant.enums.UserStatus;
import cn.miaow.framework.entity.system.SysUser;
import cn.miaow.framework.exception.ServiceException;
import cn.miaow.framework.model.LoginUser;
import cn.miaow.framework.service.system.impl.SysUserServiceImpl;
import cn.miaow.framework.util.MessageUtils;
import cn.miaow.framework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户验证处理
 *
 * @author miaow
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserServiceImpl userService;

    private final SysPasswordService passwordService;

    private final SysPermissionService permissionService;

    public UserDetailsServiceImpl(SysUserServiceImpl userService, SysPasswordService passwordService, SysPermissionService permissionService) {
        this.userService = userService;
        this.passwordService = passwordService;
        this.permissionService = permissionService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userService.selectUserByUserName(username);
        if (StringUtils.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException(MessageUtils.message("user.not.exists"));
        } else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            log.info("登录用户：{} 已被删除.", username);
            throw new ServiceException(MessageUtils.message("user.password.delete"));
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new ServiceException(MessageUtils.message("user.blocked"));
        }

        passwordService.validate(user);

        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user) {
        return new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
    }
}
