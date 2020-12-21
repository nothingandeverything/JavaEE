package cn.hxh.files.service;


import cn.hxh.files.pojo.Files;

import java.util.List;

/**
 * (Files)表服务接口
 *
 * @author makejava
 * @since 2020年12月13日22:29:26
 */
public interface FilesService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Files queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Files> queryAllByLimit(int offset, int limit,int id);

    /**
     * 新增数据
     *
     * @param files 实例对象
     * @return 实例对象
     */
    Files insert(Files files);

    /**
     * 修改数据
     *
     * @param files 实例对象
     * @return 实例对象
     */
    Files update(Files files);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}