package top.toobee.spring.EntityClassTestService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.jpa.hibernate.ddl-auto=validate",   // 关键：只验证不更新
        "spring.datasource.url=jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=PostgreSQL"
}) // 内存库，连不上真实库也能跑
class SchemaValidationTest {
    @Test
    void entityMatchesSchema() {
        // 如果列对不上，启动就会抛 SchemaManagementException
        // 测试能跑完 = 全部对齐
    }
}