package top.toobee.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import top.toobee.spring.dto.LoginUser;
import top.toobee.spring.dto.Project;
import top.toobee.spring.dto.ResultInfo;
import top.toobee.spring.service.ProjectService;

/**
 * @Description ProjectController
 * @Author Anom
 * @Date 2025-07-21
 */
@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @PostMapping("/create")
    public ResultInfo createProject(@RequestBody Project project) {
        ResultInfo resultInfo =projectService.createProject(project);
        if(resultInfo.isSuccess()) {
            return ResultInfo.builder()
                    .success(true)
                    .data(project.getIdentifier())
                    .message(resultInfo.getMessage())
                    .build();
        }
        return ResultInfo.builder()
                .success(false)
                .data(project.getIdentifier())
                .message(resultInfo.getMessage())
                .build();
    }

    @PutMapping("/update")
    public ResultInfo updateProject(@RequestBody Project project) {
        ResultInfo resultInfo = projectService.updateProject(project);
        if(resultInfo.isSuccess()) {
            return ResultInfo.builder()
                    .success(true)
                    .data(project.getIdentifier())
                    .message(resultInfo.getMessage())
                    .build();
        }
        return ResultInfo.builder()
                .success(false)
                .data(project.getIdentifier())
                .message(resultInfo.getMessage())
                .build();
    }

    @GetMapping("/myProject")
    public ResultInfo getMyProject(Authentication authentication) {
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        ResultInfo resultInfo = projectService.findMyProject(loginUser.getUser().getId());
        if(resultInfo.isSuccess()) {
            return ResultInfo.builder()
                    .success(true)
                    .data(resultInfo.getData())
                    .message(resultInfo.getMessage())
                    .build();
        }
        return ResultInfo.builder()
                .success(false)
                .data(resultInfo.getData())
                .message(resultInfo.getMessage())
                .build();
    }
}
