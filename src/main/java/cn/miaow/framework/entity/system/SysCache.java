package cn.miaow.framework.entity.system;

import cn.miaow.framework.util.StringUtils;
import lombok.Data;

/**
 * 缓存信息
 */
@Data
public class SysCache {
    /**
     * 缓存名称
     */
    private String cacheName;

    /**
     * 缓存键名
     */
    private String cacheKey = "";

    /**
     * 缓存内容
     */
    private String cacheValue = "";

    /**
     * 备注
     */
    private String remark = "";

    public SysCache(String cacheName, String remark) {
        this.cacheName = cacheName;
        this.remark = remark;
    }

    public SysCache(String cacheName, String cacheKey, String cacheValue) {
        this.cacheName = StringUtils.replace(cacheName, ":" , "" );
        this.cacheKey = StringUtils.replace(cacheKey, cacheName, "" );
        this.cacheValue = cacheValue;
    }
}
