package com.sparta.queryfilemapper.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sparta.queryfilemapper.domain.User;

@Mapper
public interface UserMapper {
	User selectUserById(@Param("id") Long id);
}
