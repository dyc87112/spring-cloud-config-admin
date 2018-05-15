package com.didispace.scca.service.persistence.git;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by 程序猿DD/翟永超 on 2018/5/15.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({SccaGitConfiguration.class})
public @interface EnableSccaPersistenceGit {
}
