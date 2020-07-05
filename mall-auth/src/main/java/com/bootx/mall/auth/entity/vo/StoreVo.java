package com.bootx.mall.auth.entity.vo;

import com.bootx.mall.auth.entity.Store;

/**
 * StoreVo
 *
 * @author 夏黎
 * @version 1.0
 * @date 2020-07-03 17:28:08
 */
public class StoreVo {

    private Long id;

    private String name;

    private Store.Type type;

    private String path;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Store.Type getType() {
        return type;
    }

    public void setType(Store.Type type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
