package top.toobee.spring.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.toobee.spring.dto.Project;

import java.util.List;

/**
 * @Description ProjectMapper
 * @Author Anom
 * @Date 2025-07-21
 */
@Mapper
public interface ProjectMapper {

    @Select("SELECT * from project where identifier=#{identifier}")
    Project findByIdentifier(String identifier);

    //@Insert("insert into project (identifier,name,type,creator_id,webpage_create_time,webpage_update_time,project_create_time,project_update_time,introduction,thumbnail,world,x,y,z) values (#{identifier},#{name},#{type}::type,#{creatorId},#{webpageCreateTime},#{webpageUpdateTime},#{projectCreateTime},#{projectUpdateTime},#{introduction},#{thumbnail},#{world}::world,#{x},#{y},#{z})")
    void insert(Project project);

    void update(Project project);

    @Select("select * from project where creator_id=#{creatorId}")
    List<Project> findMyProjectByCreatorId(int creatorId);
}
