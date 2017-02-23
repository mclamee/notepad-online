package com.wicky.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.wicky.domain.EmployeeVO;
import com.wicky.repository.AccountDao;

@Service
public class DatabaseAccountServiceImpl implements AccountService {

    private final AccountDao accountDao;

    @Autowired
    public DatabaseAccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public List<EmployeeVO> getAllEmployees(){
        return accountDao.getAllEmployees();
    }

}