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

package org.casbin;

import org.casbin.adapter.JDBCAdapter;
import org.casbin.jcasbin.main.Enforcer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
public class JdbcTest {

    private String url="jdbc:mysql://localhost:3306/casbin?useSSL=false&allowPublicKeyRetrieval=true";
    private String username="root";
    private String password="casbin_test";
    private String driver="com.mysql.jdbc.Driver";
    @Test
    public void TestJdbc() throws Exception {
        //save policy to database
        Enforcer e = new Enforcer("examples/rbac_model.conf", "examples/rbac_policy.csv");
        JDBCAdapter adapter = new JDBCAdapter(driver, url, username, password);
        adapter.savePolicy(e.getModel());

        //read policy to database
        e.clearPolicy();
        adapter.loadPolicy(e.getModel());

        Enforcer newOne=new Enforcer("examples/rbac_model.conf",e.getAdapter());
        System.out.println(newOne.getPolicy());

    }

    @Test
    public void TestModelAuth(){
        Enforcer e = new Enforcer("examples/rbac_model.conf", "examples/rbac_policy.csv");
        boolean enforce = e.enforce("alice", "data1", "read");
        System.out.println(enforce);
    }
}
