// Copyright 2024 The Casdoor Authors. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.casbin.controller;

import org.casbin.adapter.JDBCAdapter;
import org.casbin.api.CommonResult;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;


@RestController
@RequestMapping("/casbin")
public class AdapterController {
    @Value(value = "${mysql.url}")
    private String url;
    @Value(value = "${mysql.username}")
    private String username;
    @Value(value = "${mysql.password}")
    private String password;
    @Value(value = "${mysql.driver}")
    private String driver;

    @RequestMapping("/InitDataAndLogin")
    public CommonResult InitDataAndLogin(Authentication authentication, HttpSession session) throws Exception {

        init();
        SecurityContext context = SecurityContextHolder.getContext();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("readData1");
        ArrayList<SimpleGrantedAuthority> arrayList = new ArrayList<>();
        arrayList.add(authority);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("alice", "pass", arrayList);

        context.setAuthentication(token);
        return CommonResult.success("login success, init data");
    }

    @RequestMapping("/canReadData1")
    @PreAuthorize("hasAuthority('readData1')")
    public CommonResult canReadData1() {
        return CommonResult.success("you can read data1");
    }

    @RequestMapping("/canNotReadData2")
    @PreAuthorize("hasAuthority('readData2')")
    public CommonResult canNotReadData() {
        return CommonResult.success("you can read data2");
    }

    /**
     * use init once to load model data into mysql database
     * then you can dismiss it
     *
     * @throws Exception
     */
    public void init() throws Exception {
        Enforcer e = new Enforcer("examples/rbac_model.conf", "examples/rbac_policy.csv");
        JDBCAdapter adapter = new JDBCAdapter(driver, url, username, password);
        adapter.savePolicy(e.getModel());
    }

}
