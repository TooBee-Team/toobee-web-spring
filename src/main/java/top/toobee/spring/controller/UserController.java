package top.toobee.spring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import top.toobee.spring.dto.LoginUser;
=======
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
>>>>>>> 12c385e32a27627a2527439f29ef196b8dd842f5
import top.toobee.spring.dto.ResultInfo;
import top.toobee.spring.dto.TBUser;
import top.toobee.spring.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/register")
    public ResultInfo register(@RequestBody TBUser user) {
        ResultInfo resultInfo = userService.register(user);
        if(resultInfo.isSuccess()) {
<<<<<<< HEAD
=======
            userService.register(user);
>>>>>>> 12c385e32a27627a2527439f29ef196b8dd842f5
            return ResultInfo.builder()
                    .success(true)
                    .data(user.getUsername())
                    .message(resultInfo.getMessage())
                    .build();
        }
        return ResultInfo.builder()
                .success(false)
                .data(user.getUsername())
                .message(resultInfo.getMessage())
                .build();
    }

    /*@PostMapping("/login")
    public ResultInfo login(@RequestBody TBUser user) {
        ResultInfo resultInfo = userService.login(user.getUsername(), user.getPassword());
        if (resultInfo.isSuccess()) {
            return ResultInfo.builder()
                    .success(true)
                    .data(user.getUsername())
                    .message(resultInfo.getMessage())
                    .build();
        }
        return ResultInfo.builder()
               .success(false)
               .data(user.getUsername())
               .message(resultInfo.getMessage())
               .build();
    }*/

    @PutMapping("/updateQQ")
    public ResultInfo updateQQ(@RequestBody TBUser user) {
        ResultInfo resultInfo = userService.updateQQ(user);
        if (resultInfo.isSuccess()) {
            return ResultInfo.builder()
                   .success(true)
                   .data(user.getUsername())
                   .message(resultInfo.getMessage())
                   .build();
        }
        return ResultInfo.builder()
              .success(false)
              .data(user.getUsername())
              .message(resultInfo.getMessage())
              .build();
    }

    @PutMapping("/updateWechat")
    public ResultInfo updateWechat(@RequestBody TBUser user) {
        ResultInfo resultInfo = userService.updateWechat(user);
        if (resultInfo.isSuccess()) {
            return ResultInfo.builder()
                  .success(true)
                  .data(user.getUsername())
                  .message(resultInfo.getMessage())
                  .build();
        }
        return ResultInfo.builder()
              .success(false)
              .data(user.getUsername())
              .message(resultInfo.getMessage())
              .build();
    }

    @PutMapping("/updateTelegram")
    public ResultInfo updateTelegram(@RequestBody TBUser user) {
        ResultInfo resultInfo = userService.updateTelegram(user);
        if (resultInfo.isSuccess()) {
            return ResultInfo.builder()
                 .success(true)
                 .data(user.getUsername())
                 .message(resultInfo.getMessage())
                 .build();
        }
        return ResultInfo.builder()
             .success(false)
             .data(user.getUsername())
             .message(resultInfo.getMessage())
             .build();
    }

    @PutMapping("/updateEmail")
    public ResultInfo updateEmail(@RequestBody TBUser user) {
        ResultInfo resultInfo = userService.updateEmail(user);
        if (resultInfo.isSuccess()) {
            return ResultInfo.builder()
                .success(true)
                .data(user.getUsername())
                .message(resultInfo.getMessage())
                .build();
        }
        return ResultInfo.builder()
            .success(false)
            .data(user.getUsername())
            .message(resultInfo.getMessage())
            .build();
    }

    @PutMapping("/updatePassword")
    public ResultInfo updatePassword(@RequestBody TBUser user) {
        ResultInfo resultInfo = userService.updatePassword(user);
        if (resultInfo.isSuccess()) {
            return ResultInfo.builder()
                    .success(true)
                    .data(user.getUsername())
                    .message(resultInfo.getMessage())
                    .build();
        }
        return ResultInfo.builder()
                .success(false)
                .data(user.getUsername())
                .message(resultInfo.getMessage())
                .build();
    }

    @GetMapping("/getUserInfo")
<<<<<<< HEAD
    public ResultInfo getUserInfo(Authentication authentication) {
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        ResultInfo resultInfo = userService.getUserInfo(loginUser.getUser().getId());
=======
    public ResultInfo getUserInfo(Principal principal) {
        ResultInfo resultInfo = userService.getUserInfo(principal.getName());
>>>>>>> 12c385e32a27627a2527439f29ef196b8dd842f5
        if (resultInfo.isSuccess()) {
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

    @PutMapping ("/updateUserInfo")
    public ResultInfo updateUserInfo(@RequestBody TBUser tbUser) {
        ResultInfo resultInfo = userService.updateUserInfo(tbUser);
        if (resultInfo.isSuccess()) {
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
<<<<<<< HEAD
    }

    @GetMapping("/findByQq")
    public ResultInfo findByQq(@RequestParam String qq) {
        ResultInfo resultInfo = userService.findByQq(qq);
        if (resultInfo.isSuccess()) {
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

    @GetMapping("/findByEmail")
    public ResultInfo findByEmail(@RequestParam String email) {
        ResultInfo resultInfo = userService.findByEmail(email);
        if (resultInfo.isSuccess()) {
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

    @GetMapping("/findByWechat")
    public ResultInfo findByWechat(@RequestParam String wechat) {
        ResultInfo resultInfo = userService.findByWechat(wechat);
        if (resultInfo.isSuccess()) {
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

    @GetMapping("/findByTelegram")
    public ResultInfo findByTelegram(@RequestParam String telegram) {
        ResultInfo resultInfo = userService.findByTelegram(telegram);
        if (resultInfo.isSuccess()) {
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
=======
>>>>>>> 12c385e32a27627a2527439f29ef196b8dd842f5
    }
}
