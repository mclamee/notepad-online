package com.wicky.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import com.wicky.domain.EmployeeVO;

@ComponentScan
@Repository
public class AccountDaoImpl implements AccountDao{
	
	@Override
    public List<EmployeeVO> getAllEmployees() {
        List<EmployeeVO> employees = new ArrayList<EmployeeVO>();
         
        EmployeeVO vo1 = new EmployeeVO();
        vo1.setId(1);
        vo1.setFirstName("Lokesh");
        vo1.setLastName("Gupta");
        employees.add(vo1);
         
        EmployeeVO vo2 = new EmployeeVO();
        vo2.setId(2);
        vo2.setFirstName("Raj");
        vo2.setLastName("Kishore");
        employees.add(vo2);
         
        return employees;
    }
}
