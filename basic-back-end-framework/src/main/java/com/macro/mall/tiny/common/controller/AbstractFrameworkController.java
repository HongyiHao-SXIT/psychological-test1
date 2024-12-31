package com.macro.mall.tiny.common.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.macro.mall.tiny.common.aop.EnumOrDictTrans;
import com.macro.mall.tiny.common.api.CommonPage;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.common.dto.RequestDto;
import com.macro.mall.tiny.common.model.BaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 抽象控制器
 *
 * @author ztx
 * @version 1
 */
@Controller
public abstract class AbstractFrameworkController<E extends BaseModel, S extends IService<E>> {

    /**
     * service层
     */
    @Autowired
    protected S service;


    /**
     * 获取列表
     *
     * @param requestDto 入参
     * @return 出参
     */
    @GetMapping("/list")
    @EnumOrDictTrans
    public CommonResult<CommonPage<E>> list(RequestDto requestDto) {
        return CommonResult.success(CommonPage.restPage(this.afterGet(this.service.page(new Page<>(requestDto.getPageNum(), requestDto.getPageSize()), this.wrappersBuilder(requestDto)))));
    }

    /**
     * 后置数据处理
     *
     * @param list 列表
     * @return 结果
     */
    protected Page<E> afterGet(Page<E> list) {
        return list;
    }

    /**
     * 后置数据处理
     *
     * @param list 列表
     * @return 结果
     */
    protected List<E> afterGet(List<E> list) {
        return list;
    }


    /**
     * 更新之前操作
     *
     * @param model 入参
     * @return 结果
     */
    protected E beforeSaveOrUpdate(E model) {
        return model;
    }

    /**
     * 更新之后操作
     *
     * @param model 入参
     */
    protected void afterSaveOrUpdate(E model) {

    }

    /**
     * 查询条件构造器
     *
     * @return 条件列表
     */
    protected Wrapper<E> wrappersBuilder(RequestDto requestDto) {
        return null;
    }

    /**
     * 新增或修改
     *
     * @param model 入参
     * @return 出参
     */
    @PostMapping("/saveOrUpdate")
    @Transactional
    public CommonResult<Void> saveOrUpdate(@RequestBody E model) {
        model = this.beforeSaveOrUpdate(model);
        this.service.saveOrUpdate(model);
        this.afterSaveOrUpdate(model);
        return CommonResult.success(null);
    }

    /**
     * 删除
     *
     * @param model 入参
     * @return 出参
     */
    @DeleteMapping("/deleteById")
    @Transactional
    public CommonResult<Void> deleteById(@RequestBody E model) {
        CommonResult<E> beforeDeleteResult = this.beforeDelete(model);
        if (!beforeDeleteResult.isSuccess()) {
            return CommonResult.failed(beforeDeleteResult.getMessage());
        }
        this.service.removeById(model.getId());
        this.afterDelete(model);
        return CommonResult.success(null);
    }

    /**
     * 获取指定数据
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/{id}")
    public CommonResult<E> getOne(@PathVariable String id) {
        return CommonResult.success(this.service.getById(id));
    }

    /**
     * 删除之前操作
     *
     * @param model 入参
     * @return 结果
     */
    protected CommonResult<E> beforeDelete(E model) {
        return CommonResult.success(model);
    }

    /**
     * 删除之后操作
     *
     * @param model 入参
     */
    protected void afterDelete(E model) {
    }

    /**
     * 获取所有数据
     *
     * @param requestDto 入参
     * @return 结果
     */
    @GetMapping("/allList")
    public CommonResult<List<E>> getAllList(RequestDto requestDto) {
        return CommonResult.success(this.afterGet(this.service.list(this.wrappersBuilder(requestDto))));
    }
}
