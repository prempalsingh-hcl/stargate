package com.hcl.stargate.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.hcl.stargate.bean.StargateUser;

public interface StargateUserRepository extends JpaRepository <StargateUser,String>{

}
