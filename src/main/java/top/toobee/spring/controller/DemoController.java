package top.toobee.spring.controller;

import java.security.Principal;
import java.sql.SQLDataException;
import java.util.Optional;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Profile("dev")
@RestController
@RequestMapping("/api/demo")
public class DemoController {
    @GetMapping("/me")
    public String me(Principal principal) {
        return "当前用户:" + principal.getName();
    }

    public record TestResult(String name, int age) {}

    @PostMapping("test")
    public ResponseEntity<?> test(@RequestBody TestResult result) {
        return switch (result.name()) {
            case "admin" -> ResponseEntity.ok(new TestResult("admin", 18));
            case "boss" -> ResponseEntity.ofNullable(null);
            case "user" -> ResponseEntity.notFound().build();
            case "guest" -> ResponseEntity.noContent().build();
            case "test" -> ResponseEntity.internalServerError().body("test");
            case "warn" ->
                    throw new ConstraintViolationException(
                            "warn", new SQLDataException("no"), "warn");
            case "error" -> throw new IllegalArgumentException("error");
            case null -> throw new NullPointerException("null value");
            default -> ResponseEntity.status(HttpStatus.FORBIDDEN).body("forbidden");
        };
    }

    @GetMapping("null")
    public Optional<TestResult> testNull() {
        return Optional.empty();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
