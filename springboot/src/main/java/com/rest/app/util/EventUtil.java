/**
 * 
 */
package com.rest.app.util;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.app.repo.AudittrailRepository;
import com.rest.app.table.Audittrail;

/**
 * @author danielf
 *
 */
@Service
public class EventUtil {

	@Autowired
	private AudittrailRepository auditRepository;

	public void LOG_EVENT(String username, Map<String, Object> res) {

		LocalTime now = LocalTime.now();
		Time time = Time.valueOf(now);

		Audittrail audit = new Audittrail();
		if (res.get("flag") != null && res.get("flag").equals("success")) {
			audit = new Audittrail();
			audit.setUsername(username != null && !username.isEmpty() ? username : (String) res.get("username"));
			audit.setEventdate(new Date());
			audit.setEventtime(time);
			audit.setEventdesc((String) res.get("event"));
			auditRepository.save(audit);
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> write(String responsebody) {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

		try {
			if (!responsebody.startsWith("[")) {
				responsebody = responsebody.replace("\"\"", "\"-\"");
				Map<String, Object> map = mapper.readValue(responsebody, Map.class);
				return map;
			}

		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new HashMap<String, Object>();
	}

}
