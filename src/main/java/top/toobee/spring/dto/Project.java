package top.toobee.spring.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Description Project
 * @Author Anom
 * @Date 2025-07-21
 */
public class Project {
    private int id;
    private String identifier; //唯一标识符，仅允许小写英文、数字、下划线和横线
    private String name;

    private ProjectType type; //项目类型
    private int creatorId; // 页面创建者
    private LocalDateTime webpageCreateTime; //项目页面的创建时间
    private LocalDateTime webpageUpdateTime; //项目在页面中的最后更新时间
    private LocalDateTime projectCreateTime; //项目在游戏中的创建时间
    private LocalDateTime projectUpdateTime; //项目在游戏中的最后更新时间
    private String introduction; //文字介绍
    private byte[] thumbnail; //直接存储缩略图二进制数据
    private World world; //项目工程所在维度，包括以下三个坐标，标识项目在游戏中的位置
    private int x;
    private int y;
    private int z;

    public enum World {
        overworld,
        the_nether,
        the_end
    }

    public enum ProjectType {
        machine,
        building,
        settlement,
        recreation,
        landscape,
        picture,
        sculpture,
        unknown
    }

    public Project(int id, String identifier, ProjectType type, int creatorId, LocalDateTime webpageCreateTime, LocalDateTime webpageUpdateTime, LocalDateTime projectCreateTime, LocalDateTime projectUpdateTime, String introduction, byte[] thumbnail, World world, int x, int y, int z) {
        this.id = id;
        this.identifier = identifier;
        this.type = type;
        this.creatorId = creatorId;
        this.webpageCreateTime = webpageCreateTime;
        this.webpageUpdateTime = webpageUpdateTime;
        this.projectCreateTime = projectCreateTime;
        this.projectUpdateTime = projectUpdateTime;
        this.introduction = introduction;
        this.thumbnail = thumbnail;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Project() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public ProjectType getType() {
        return type;
    }

    public void setType(ProjectType type) {
        this.type = type;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public LocalDateTime getWebpageCreateTime() {
        return webpageCreateTime;
    }

    public void setWebpageCreateTime(LocalDateTime webpageCreateTime) {
        this.webpageCreateTime = webpageCreateTime;
    }

    public LocalDateTime getWebpageUpdateTime() {
        return webpageUpdateTime;
    }

    public void setWebpageUpdateTime(LocalDateTime webpageUpdateTime) {
        this.webpageUpdateTime = webpageUpdateTime;
    }

    public LocalDateTime getProjectCreateTime() {
        return projectCreateTime;
    }

    public void setProjectCreateTime(LocalDateTime projectCreateTime) {
        this.projectCreateTime = projectCreateTime;
    }

    public LocalDateTime getProjectUpdateTime() {
        return projectUpdateTime;
    }

    public void setProjectUpdateTime(LocalDateTime projectUpdateTime) {
        this.projectUpdateTime = projectUpdateTime;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
