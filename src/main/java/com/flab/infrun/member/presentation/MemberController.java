package com.flab.infrun.member.presentation;


import com.flab.infrun.common.response.Response;
import com.flab.infrun.member.application.MemberFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberFacade facade;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Response<Long> signup(@RequestBody @Valid final SignupRequest request) {
        var result = facade.signup(request.toCommand());

        return Response.success(result);
    }
}
