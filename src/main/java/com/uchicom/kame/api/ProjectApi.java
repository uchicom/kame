// (C) 2025 uchicom
package com.uchicom.kame.api;

import com.uchicom.kame.annotation.Auth;
import com.uchicom.kame.annotation.Path;
import com.uchicom.kame.dto.request.project.ProjectIdentificationDto;
import com.uchicom.kame.dto.request.project.ProjectRegisterDto;
import com.uchicom.kame.dto.request.project.ProjectUpdateDto;
import com.uchicom.kame.dto.response.ListDto;
import com.uchicom.kame.dto.response.MessageDto;
import com.uchicom.kame.entity.Project;
import com.uchicom.kame.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@Path("/project")
public class ProjectApi extends AbstractApi {

  private final ProjectService projectService;

  public ProjectApi(ProjectService projectService) {
    this.projectService = projectService;
  }

  @Auth
  @Path("/get")
  public Object get(ProjectIdentificationDto dto, HttpServletRequest req, HttpServletResponse res) {
    return refer(
        () -> {
          return projectService.get(dto.projectId);
        });
  }

  @Auth
  @Path("/list")
  public Object list(HttpServletRequest req, HttpServletResponse res) {
    return refer(
        () -> {
          return new ListDto<Project>(projectService.getList(getAccountId(req)));
        });
  }

  @Auth
  @Path("/register")
  public Object register(ProjectRegisterDto dto, HttpServletRequest req, HttpServletResponse res) {
    return trans(
        req,
        () -> {
          projectService.register(getAccountId(req), dto);
          return new MessageDto("プロジェクトを登録しました。");
        });
  }

  @Auth
  @Path("/update")
  public Object update(
      List<ProjectUpdateDto> dtoList, HttpServletRequest req, HttpServletResponse res) {
    return trans(
        req,
        () -> {
          projectService.update(getAccountId(req), dtoList);
          return new MessageDto("プロジェクトを更新しました。");
        });
  }
}
