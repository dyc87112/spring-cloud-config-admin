package com.didispace.scca.plugin.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by 程序猿DD/翟永超 on 2018/4/24.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
public interface PropertyRepo extends JpaRepository<Property, Long> {

    int deleteAllByProfile(String profile);

    int deleteAllByApplication(String project);

    int deleteAllByLabel(String label);

    int deleteAllByApplicationAndProfileAndLabel(String application, String profile, String label);

    int deleteAllByApplicationAndProfile(String application, String profile);

    List<Property> findAllByApplicationAndProfileAndLabel(String application, String profile, String label);

    @Modifying
    @Query("update Property u set u.profile = ?1 where u.profile = ?2")
    int updateProfileName(String newName, String oldName);

}
