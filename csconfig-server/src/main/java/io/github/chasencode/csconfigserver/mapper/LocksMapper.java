package io.github.chasencode.csconfigserver.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * LocksMapper
 */
public interface LocksMapper {
    @Select("SELECT app FROM locks WHERE id = 1 FOR UPDATE")
    String selectForUpdate();
}
