package top.toobee.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultInfo {
    private boolean success; // 是否成功
    private String message; // 提示信息
    private Object data; // 返回数据

    // 添加只接收success参数的构造器
    public ResultInfo(boolean success) {
        this.success = success;
    }

    // 添加接收success和message参数的构造器
    public ResultInfo(boolean success, String message) {
        this.success = success;
        this.message = message;
    }


} 