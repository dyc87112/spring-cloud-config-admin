package com.didispace.scca.service.persistence.db;

import com.didispace.scca.core.config.SccaConfigServerProperties;
import com.didispace.scca.core.domain.Env;
import com.didispace.scca.core.domain.EnvRepo;
import com.didispace.scca.core.domain.Label;
import com.didispace.scca.core.service.PersistenceService;
import com.didispace.scca.core.service.UrlMakerService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Credentials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

/**
 * Created by 程序猿DD/翟永超 on 2018/4/24.
 * <p>
 * Blog: http://blog.didispace.com/ Github: https://github.com/dyc87112/
 */
@Slf4j
public class DbPersistenceService implements PersistenceService {

	@Autowired
	private UrlMakerService urlMakerService;

	private RestTemplate restTemplate = new RestTemplate();

	@Autowired
	private EnvRepo envRepo;

	@Autowired
	protected SccaConfigServerProperties sccaConfigServerProperties;

	@Override
	public Properties readProperties(String application, String profile, String label) {
		String url = urlMakerService.configServerBaseUrl(profile)
				+ "/readProperties?application={1}&profile={2}&label={3}";

		return getForObject(url, Properties.class, application, profile, label);
	}

	@Override
	public void deletePropertiesByEnv(String profile) {
		// 删除某个环境下的所有配置
		String url = urlMakerService.configServerBaseUrl(profile) + "/deletePropertiesByEnv?profile={1}";
		Integer rows = getForObject(url, Integer.class, profile);
		log.info("delete env [{}] property rows {}", profile, rows);
	}

	@Override
	public void deletePropertiesByProject(String application) {
		// 删除某个项目所有环境下的配置
		for (Env env : envRepo.findAll()) {
			String url = urlMakerService.configServerBaseUrl(env.getName())
					+ "/deletePropertiesByProject?application={1}";
			Integer rows = getForObject(url, Integer.class, application);
			log.info("delete project [{}] in env [{}] property rows {}", application, rows);
		}
	}

	@Override
	public void deletePropertiesByProjectAndEnv(String application, String profile) {
		String url = urlMakerService.configServerBaseUrl(profile)
				+ "/deletePropertiesByProjectAndEnv?application={1}&profile={2}";
		Integer rows = getForObject(url, Integer.class, profile);
		log.info("delete project [{}] in env [{}] property rows {}", application, profile, rows);
	}

	@Override
	public void deletePropertiesByLabel(Label label) {
		// TODO
		// int rows = propertyRepo.deleteAllByLabel(label);
		// log.info("delete project [{}] label [{}] property rows {}",
		// label.getProject().getName(), label.getName(), rows);
	}

	@Override
	public void deletePropertiesByProjectAndEnvAndLabel(String application, String profile, String label) {
		String url = urlMakerService.configServerBaseUrl(profile)
				+ "/deletePropertiesByProjectAndEnvAndLabel?application={1}&profile={2}&label={3}";
		Integer rows = getForObject(url, Integer.class, application, profile, label);
		log.info("delete {}-{}-{} property rows {}", application, profile, label, rows);
	}

	@Override
	public void saveProperties(String application, String profile, String label, Properties update) {
		String url = urlMakerService.configServerBaseUrl(profile)
				+ "/saveProperties?application={1}&profile={2}&label={3}";
		Integer rows = postForObject(url, update, Integer.class, application, profile, label);
		log.info("add {}-{}-{} rows {}", application, profile, label, rows);
	}

	@Override
	public void updateProfileName(String oldName, String newName) {
		String url = urlMakerService.configServerBaseUrl(oldName) + "/updateProfileName?oldName={1}&newName={2}";
		Integer rows = postForObject(url, null, Integer.class, oldName, newName);
		log.info("update profile name {} -> {}, change property rows {}", oldName, newName, rows);
	}

	private <T> T getForObject(String url, Class<T> cls, Object... param) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization",
				Credentials.basic(sccaConfigServerProperties.getUsername(), sccaConfigServerProperties.getPassword()));
		HttpEntity<T> entity = new HttpEntity<>(headers);
		ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, cls, param);
		return response.getBody();
	}

	private <T> T postForObject(String url, Object request, Class<T> cls, Object... param) {
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.add("Authorization",
				Credentials.basic(sccaConfigServerProperties.getUsername(), sccaConfigServerProperties.getPassword()));
		HttpEntity<Object> entity = new HttpEntity<>(request, headers);
		ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, entity, cls, param);
		return response.getBody();
	}

}
