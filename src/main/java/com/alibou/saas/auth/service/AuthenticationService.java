package com.alibou.saas.auth.service;

import com.alibou.saas.auth.requests.LoginRequest;
import com.alibou.saas.auth.responses.LoginResponse;

public interface AuthenticationService {

    LoginResponse login(final LoginRequest request);
}
