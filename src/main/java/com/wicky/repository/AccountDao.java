package com.wicky.repository;

import java.util.List;

import com.wicky.domain.EmployeeVO;

public interface AccountDao {

	public List<EmployeeVO> getAllEmployees();

}
