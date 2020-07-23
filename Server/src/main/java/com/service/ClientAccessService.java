package com.service;

import com.dao.DAOUtil;
import com.dao.mapper.ClientAccessMapper;
import com.model.ClientAccess;
import org.apache.ibatis.session.SqlSession;

/**
 * @author IceCube
 * @date 2020/7/23 17:33
 */
public class ClientAccessService {
    /**
     * 保存客户访问记录
     */
    public void saveAccess(ClientAccess clientAccess) {
        ClientAccessMapper mapper;
        try (SqlSession sqlSession = DAOUtil.getSessionFactory().openSession()) {
            mapper = sqlSession.getMapper(ClientAccessMapper.class);
            boolean succeed = mapper.addClientAccess(clientAccess);
            sqlSession.commit();
            if(!succeed){
                System.out.println("保存客户信息失败: ");
                System.out.println(clientAccess);
            }
        }
    }
}
