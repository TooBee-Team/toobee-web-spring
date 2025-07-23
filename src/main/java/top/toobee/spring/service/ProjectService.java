package top.toobee.spring.service;

import top.toobee.spring.dto.Project;
import top.toobee.spring.dto.ResultInfo;

/**
 * @Description ProjectService
 * @Author Anom
 * @Date 2025-07-21
 */
public interface ProjectService {
    ResultInfo createProject(Project project);

    ResultInfo updateProject(Project project);

    ResultInfo findMyProject(int creatorId);
}
