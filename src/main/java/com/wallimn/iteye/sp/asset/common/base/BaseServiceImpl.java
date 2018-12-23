package com.wallimn.iteye.sp.asset.common.base;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import java.io.Serializable;
import java.util.List;

/**
 * 基础service实现基类
 *
 * @param <T>  实体class
 * @param <PK> 主键类型
 * @author luhao
 */
public abstract class BaseServiceImpl<T extends Serializable, PK extends Serializable> implements BaseService<T, PK> {

    protected abstract BaseDao<T, PK> getBaseDao();

    
    /**
     * 通过主键Id查询
     *
     * @param id
     * @return
     */
    @Override
    public T selectEntityById(PK id) {
        return this.getBaseDao().selectEntityById(id);
    }
    
    /**
     * 分页查询
     *
     * @param entity
     * @param pageBounds
     * @return
     */
    @Override
    public PageList<T> selectEntityList(T entity, PageBounds pageBounds) {
        return this.getBaseDao().selectEntityList(entity, pageBounds);
    }

    /**
     * 查询
     *
     * @param entity
     * @return
     */
    @Override
    public List<T> selectEntityList(T entity) {
    	return this.getBaseDao().selectEntityList(entity);
    }

    /**
     * 插入实体
     *
     * @param entity
     * @return
     */
    @Override
    public int insertEntity(T entity) {
        return this.getBaseDao().insertEntity(entity);
    }

    /**
     * 插入实体，并指定是否检查重复
     * @param entity
     * @param bCheck
     * @return
     *<br>作者：wallimn，时间：2017年12月21日 下午5:54:16
     */
    @Override
    public int insertEntitySafe(T entity) {
    	if(this.getBaseDao().selectEntityCount(entity)==0){
    		return this.getBaseDao().insertEntity(entity);
    	}
    	else{
    		throw this.buildException("未通过唯一性检查，插入失败！");
    	}
    }
    
    /**
     * 插入实体，并指定是否检查重复
     * @param entity
     * @param message 重复字段的提示信息
     * @return
     *<br>作者：wallimn，时间：2017年12月21日 下午5:54:16
     */
    @Override
    public int insertEntitySafe(T entity,String message) {
    	if(this.getBaseDao().selectEntityCount(entity)==0){
    		return this.getBaseDao().insertEntity(entity);
    	}
    	else{
    		throw this.buildException(message);
    	}
    }
    
    
    /**
     * 更新实体
     *
     * @param entity
     * @return
     */
    @Override
    public int updateEntity( T entity) {
        return this.getBaseDao().updateEntity(entity);
    }

    /**
     * 通过id删除实体
     *
     * @param id
     * @return
     */
    @Override
    public int deleteEntityById(PK id) {
        return this.getBaseDao().deleteEntityById(id);
    }
    @Override
    public int deleteEntityByIds(PK[] id) {
    	if(id!=null && id.length>0){
    		return this.getBaseDao().deleteEntityByIds(id);
    	}
    	else{
    		return 0;
    	}
    }
	/**
	 * 根据条件统计实体数量
	 * @param entity
	 * @return
	 *<br>作者：wallimn，时间：2017年12月21日 下午5:45:21
	 */
    @Override
	public int selectEntityCount(T entity){
		return this.getBaseDao().selectEntityCount(entity);
	}
    /**
     * 生成运行时异常
     */
    public RuntimeException buildException(String message){
    	return new RuntimeException(message);
    }
}
