package org.copy.system.service;

import org.copy.common.domain.Tree;
import org.copy.common.utils.BuildTree;
import org.copy.system.dao.DeptDao;
import org.copy.system.domain.DeptDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeptService {
    @Autowired
    private DeptDao sysDeptMapper;

    public DeptDO get(Long deptId){
        return sysDeptMapper.get(deptId);
    }

    public List<DeptDO> list(Map<String, Object> map){
        return sysDeptMapper.list(map);
    }

    public int count(Map<String, Object> map){
        return sysDeptMapper.count(map);
    }

    public int save(DeptDO sysDept){
        return sysDeptMapper.save(sysDept);
    }

    public int update(DeptDO sysDept){
        return sysDeptMapper.update(sysDept);
    }

    public int remove(Long deptId){
        return sysDeptMapper.remove(deptId);
    }

    public int batchRemove(Long[] deptIds){
        return sysDeptMapper.batchRemove(deptIds);
    }

    public Tree<DeptDO> getTree() {
        List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
        List<DeptDO> sysDepts = sysDeptMapper.list(new HashMap<String,Object>(16));
        for (DeptDO sysDept : sysDepts) {
            Tree<DeptDO> tree = new Tree<DeptDO>();
            tree.setId(sysDept.getDeptId().toString());
            tree.setParentId(sysDept.getParentId().toString());
            tree.setText(sysDept.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<DeptDO> t = BuildTree.build(trees);
        return t;
    }

    public boolean checkDeptHasUser(Long deptId) {
        // TODO Auto-generated method stub
        //查询部门以及此部门的下级部门
        int result = sysDeptMapper.getDeptUserNumber(deptId);
        return result==0?true:false;
    }
}
