package com.example.study.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.study.service.IEmpService;
import com.example.study.service.pojo.Emp;

import static java.util.Arrays.asList;

/**
 * @Author: wangxh
 * @Description:
 * @Date: Created in 10:20 2020/8/8
 * @Modified By:
 */
public class EmpServiceImpl implements IEmpService {

	private static Map<Integer,Emp> emps = new HashMap<>();


	@Override
	public void add(Emp emp) {
		emps.put(emp.getId(), emp);
	}

	@Override
	public void delete(Integer id) {
		emps.remove(id);
	}

	@Override
	public void update(Emp emp) {
		emps.remove(emp);
		emps.put(emp.getId(), emp);
	}

	@Override
	public Emp find(Emp emp) {
		return emps.get(emp.getId());
	}

	@Override
	public Emp findById(Integer id) {
		return emps.get(id);
	}

	static{
		Emp emp1 = new Emp(1, "emp1", 12, "emp1_addr");
		Emp emp2 = new Emp(2, "emp2", 34, "emp2_addr");
		List<Emp> empList = Arrays.asList(emp1, emp2);
		Map<Integer, Emp> empMap = empList.stream().collect(
				Collectors.toMap(
						emp -> emp.getId(),
						emp -> emp,
						(e1, e2) -> e2));
		emps.putAll(empMap);
	}
}
