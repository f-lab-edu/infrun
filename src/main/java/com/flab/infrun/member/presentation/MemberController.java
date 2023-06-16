package com.flab.infrun.member.presentation;


import com.flab.infrun.common.response.Response;
import com.flab.infrun.member.application.MemberFacade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberFacade facade;

    public MemberController(final MemberFacade facade) {
        this.facade = facade;
    }

    @PostMapping("/signup")
    public ResponseEntity<Response<Long>> signUp(@RequestBody @Valid final SignupRequest request) {
        var result = facade.signup(request.toCommand());

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(Response.success(result));
    }
}
