package com.wallimn.iteye.sp.asset.common.base;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import java.io.Serializable;
import java.util.List;

/**
 * 基础service接口
 * @author wallimn
 *
 * @param <T>
 * @param <PK>
 */
public interface BaseService<T extends Serializable, PK extends Serializable> {
	
	/**
	 * 插入实体
	 * @param entity
	 * @return
	 */
	int insertEntity(T entity);
	
	/**
	 * 检查id是否重复，如果不重复则插入
	 * 使用selectEntityCount来检查重复，用户需要自己定义自己的检查逻辑。
	 * @param entity
	 * @return
	 *<br>作者：wallimn，时间：2017年12月21日 下午5:39:22
	 */
	int insertEntitySafe(T entity);
	
	/**
	 * 检查id是否重复，如果不重复则插入
	 * 使用selectEntityCount来检查重复，用户需要自己定义自己的检查逻辑。
	 * @param entity
	 * @param message 重复字段的提示信息
	 * @return
	 *<br>作者：陈京深，时间：2018年5月13日
	 */
	int insertEntitySafe(T entity,String message);
	
	/**
	 * 通过id删除实体
	 * @param id
	 * @return
	 */
	int deleteEntityById(PK id);
	/**
	 * 指定多个ID，同时删除
	 * @param id
	 * @return
	 */
	int deleteEntityByIds(PK[] id);
	
	/**
	 * 更新实体
	 * @param id
	 * @param entity
	 * @return
	 */
	int updateEntity(T entity);

	/**
	 * 通过主键Id查询
	 * @param id
	 * @return
	 */
	T selectEntityById(PK id);
	
	/**
	 * 查询
	 * @param entity
	 * @return
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
	 * 根据条件统计实体数量
	 * @param entity
	 * @return
	 *<br>作者：wallimn，时间：2017年12月21日 下午5:45:21
	 */
	int selectEntityCount(T entity);
	
	/**
	 * 程序中返回异常
	 * @param message
	 * @return
	 *<br>作者：wallimn，时间：2017年12月21日 下午9:43:15
	 */
	RuntimeException buildException(String message);
}
