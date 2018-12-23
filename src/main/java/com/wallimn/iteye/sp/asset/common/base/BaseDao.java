package com.wallimn.iteye.sp.asset.common.base;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;


/**
 * 通用的基础DAO。 实现一般实体增删改查(ID)查(不分页)查(分页)，五个方法。
 * 如果不需要这五个方法，可不从这个类派生
 * 增、删、改，返回数值为影响数据条数
 * @author wallimn
 *
 * @param <T> 实体
 * @param <PK> 主键类型
 */

public interface BaseDao<T extends Serializable, PK extends Serializable>  {

    /**
     * 插入实体
     * @param entity
     * @return
     */
    int insertEntity(T entity);

     /**
     * 通过id删除实体
     * @param id
     * @return
     */
    int deleteEntityById(@Param("id") PK id);
    /**
     * 删除多行数据
     * @param ids
     * @return
     */
    int deleteEntityByIds(@Param("id") PK[] id);
    
   /**
     * 更新实体
     * @param entity
     * @return
     */
    int updateEntity(T entity);

    /**
     * 通过主键Id查询
     * @param id
     * @return
     */
    T selectEntityById(@Param("id") PK id);
    
    /**
     * 不分页查询
     * @param entity
     * @return
     *
     *<br>
     *作者：wallimn　时间：2017年12月16日<br>
     *联系：54871876@qq.com
     */
    List<T> selectEntityList(T entity);


    /**
     * 分页查询
     * @param entity
     * @param pageBounds
     * @return
     */
    PageList<T> selectEntityList(T entity, PageBounds pageBounds);
    
    /**
     * 统计实体数量，主要用于插入时判断重复
     * @param entity
     * @return
     *<br>作者：wallimn，时间：2017年12月21日 下午5:46:28
     */
	int selectEntityCount(T entity);

}
