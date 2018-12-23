package com.wallimn.iteye.sp.asset.bus.example.dao;

import org.apache.ibatis.annotations.Mapper;

import com.wallimn.iteye.sp.asset.bus.example.model.Example;
import com.wallimn.iteye.sp.asset.common.base.BaseDao;


@Mapper
public interface ExampleDao extends BaseDao<Example,String> {


}
