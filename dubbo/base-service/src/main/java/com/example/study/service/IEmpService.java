package com.example.study.service;

import com.example.study.service.pojo.Emp;

/**
 * @Author: wangxh
 * @Description:
 * @Date: Created in 10:10 2020/8/8
 * @Modified By:
 */
public interface IEmpService {

	void add(Emp emp);
	void delete(Integer id);
	void update(Emp emp);
	Emp find(Emp emp);
	Emp findById(Integer id);
}
