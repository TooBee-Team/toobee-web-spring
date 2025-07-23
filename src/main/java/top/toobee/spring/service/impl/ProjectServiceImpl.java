package top.toobee.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import top.toobee.spring.dto.Project;
import top.toobee.spring.dto.ResultInfo;
import top.toobee.spring.dto.TBUser;
import top.toobee.spring.mapper.ProjectMapper;
import top.toobee.spring.mapper.UserMapper;
import top.toobee.spring.service.ProjectService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description ProjectServiceImpl
 * @Author Anom
 * @Date 2025-07-21
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    UserMapper userMapper;
    @Override
    public ResultInfo createProject(Project project) {
        Project newProject = new Project();
        String str = "^[a-zA-Z0-9_.]{1,100}";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        TBUser user = userMapper.findByUsername(username);
        if (!project.getIdentifier().matches(str)) {
            return new ResultInfo(false,"标识符仅允许小写英文、数字、下划线和横线");
        }
        if (projectMapper.findByIdentifier(project.getIdentifier())!=null) {
            return new ResultInfo(false,"该标识符已存在，请重新输入");
        }
        newProject.setIdentifier(project.getIdentifier());
        newProject.setName(project.getName());
        newProject.setType(project.getType());
        newProject.setCreatorId(user.getId());
        newProject.setWebpageCreateTime(LocalDateTime.now());
        newProject.setWebpageUpdateTime(LocalDateTime.now());
        newProject.setProjectCreateTime(project.getProjectCreateTime());
        newProject.setProjectUpdateTime(project.getProjectUpdateTime());
        newProject.setIntroduction(project.getIntroduction());
        newProject.setThumbnail(project.getThumbnail());
        newProject.setWorld(project.getWorld());
        newProject.setX(project.getX());
        newProject.setY(project.getY());
        newProject.setZ(project.getZ());
        projectMapper.insert(newProject);
        return new ResultInfo(true,"创建成功！",newProject);
    }

    @Override
    public ResultInfo updateProject(Project project) {
        Project newProject = new Project();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        TBUser user = userMapper.findByUsername(username);
        if (project.getCreatorId()!= user.getId()) {
            return new ResultInfo(false,"更新失败，权限不足");
        }
        project.setWebpageUpdateTime(LocalDateTime.now());
        projectMapper.update(project);
        return new ResultInfo(true,"更新成功");
    }

    @Override
    public ResultInfo findMyProject(int creatorId) {
        List<Project> myProject = projectMapper.findMyProjectByCreatorId(creatorId);
        if (myProject==null) {
            return new ResultInfo(false,"未查询到已创建工程");
        }

        return new ResultInfo(true,"查询成功!",myProject);
    }


}
