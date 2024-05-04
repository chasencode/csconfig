package io.github.chasencode.csconfigserver.mapper;

import io.github.chasencode.csconfigserver.model.Configs;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Program: csconfig-server
 * @Description: ConfigMapper
 * @Author: Chasen
 * @Create: 2024-04-27 20:21
 **/
@Repository
@Mapper
public interface ConfigsMapper {
    @Select("SELECT * FROM configs WHERE app = #{app} AND env = #{env} AND ns = #{ns}")
    List<Configs> list(String app, String env, String ns);

    @Select("SELECT * FROM configs WHERE app = #{app} AND env = #{env} AND ns = #{ns} AND pkey = #{pkey}")
    Configs select(String app, String env, String ns, String pkey);

    @Insert("INSERT INTO configs (app, env, ns, pkey, pval) VALUES (#{app}, #{env}, #{ns}, #{pkey}, #{pval})")
    void insert(Configs config);

    @Update("Update configs set pval = #{pval} where app = #{app} and env = #{env} and ns = #{ns} and pkey = #{pkey}")
    void update(Configs config);
}
