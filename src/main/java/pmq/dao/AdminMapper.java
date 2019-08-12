package pmq.dao;

import java.util.List;
import pmq.pojo.Admin;

public interface AdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    Admin selectByPrimaryKey(Integer id);

    List<Admin> selectAll();

    int updateByPrimaryKey(Admin record);
    
    List<Admin> selectByRank(String rank);//等级模糊查询
    
    Admin selectByUP(Admin admin);//通过用户名和密码精准查询
    
    List<Admin> selectByName(String name);//通过用户名进行模糊查询
    
    int selectByUserId(String userId);//通过用户Id进行查询
    
    int updateSelfPass(Admin record);
    
    List<Admin> selectAllBy(Admin admin);//通过条件进行模糊查询
}