package cn.hxh.files.service.impl;

import cn.hxh.files.mapper.FilesDao;
import cn.hxh.files.pojo.Files;
import cn.hxh.files.service.FilesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Files)表服务实现类
 *
 * @author makejava
 * @since 2020年12月13日22:29:26
 */
@Service
public class FilesServiceImpl implements FilesService {
    @Resource
    private FilesDao filesDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Files queryById(Integer id) {
        return this.filesDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Files> queryAllByLimit(int offset, int limit,int id) {
        return this.filesDao.queryAllByLimit(id,offset,limit);
    }

    /**
     * 新增数据
     *
     * @param files 实例对象
     * @return 实例对象
     */
    @Override
    public Files insert(Files files) {
        this.filesDao.insert(files);
        return files;
    }

    /**
     * 修改数据
     *
     * @param files 实例对象
     * @return 实例对象
     */
    @Override
    public Files update(Files files) {
        this.filesDao.update(files);
        return this.queryById(files.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.filesDao.deleteById(id) > 0;
    }
}