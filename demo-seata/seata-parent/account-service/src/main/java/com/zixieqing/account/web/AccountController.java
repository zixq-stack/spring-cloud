package com.zixieqing.account.web;

import com.zixieqing.account.service.AccountService;
import com.zixieqing.account.service.AccountTccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : ZiXieqing
 */
@RestController
@RequestMapping("account")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountTccService tccService;

    @PutMapping("/{userId}/{money}")
    public ResponseEntity<Void> deduct(@PathVariable("userId") String userId, @PathVariable("money") Integer money){
        tccService.deduct(userId, money);
        return ResponseEntity.noContent().build();
    }
}
