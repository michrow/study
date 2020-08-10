package com.example.study.service.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wangxh
 * @Description:
 * @Date: Created in 10:10 2020/8/8
 * @Modified By:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Emp implements Serializable {
	private Integer id;
	private String name;
	private Integer age;
	private String addr;
}
