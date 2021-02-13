/**
 * 
 */
package com.rest.app.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.rest.app.repo.AudittrailRepository;
import com.rest.app.repo.UseraccountRepository;
import com.rest.app.table.Audittrail;
import com.rest.app.table.Useraccount;
import com.rest.app.util.PaginateValues;

/**
 * @author danielf
 *
 */
@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UseraccountRepository UserRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AudittrailRepository AuditRepository;

	public List<Useraccount> getUsers() {

		List<Useraccount> list = new ArrayList<Useraccount>();
		try {

			return UserRepository.findAll();

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return list;
	}

	public ResponseEntity<Map<String, Object>> saveUser(Useraccount user) {

		Map<String, Object> response = new HashMap<String, Object>();

		try {
			response.put("event", "Updated " + user.getName() + "'s user account details");
			if (user.getUserid() == null || user.getUserid() == 0) {
				response.put("event", "Saved new user account");
				user.setDisabled(true);
				user.setPassword(passwordEncoder.encode(user.getPassword()));
			}
			UserRepository.save(user);
			response.put("flag", "success");

		} catch (Exception e) {
			e.printStackTrace();
			response.put("flag", "failed");
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseEntity<Map<String, Object>> deteleUser(Long userid) {

		Map<String, Object> response = new HashMap<String, Object>();

		try {
			Useraccount user = UserRepository.findById(userid).get();
			if (user != null) {
				UserRepository.delete(user);
				response.put("event", "Deleted " + user.getName() + "'s user account");
				response.put("flag", "success");
			} else {
				response.put("flag", "failed");
				return new ResponseEntity(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.put("flag", "failed");
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

	@Override
	public ResponseEntity<Map<String, Object>> validateReset(Useraccount user) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
			response.put("flag", "failed");
			response.put("event", "User Account Recovery validation failed");
			if (user.getUsername() != null && user.getBday() != null) {
				Useraccount u = UserRepository.findByUsername(user.getUsername());
				if (dateformatter.format(u.getBday()).equals(dateformatter.format(user.getBday()))) {
					response.put("flag", "success");
					response.put("event", "User Account Recovery validated");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.put("flag", "failed");
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

	@Override
	public ResponseEntity<Map<String, Object>> resetPassword(Useraccount user) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			response.put("flag", "failed");
			response.put("event", "User Account Reset");
			Useraccount u = UserRepository.findByUsername(user.getUsername());
			u.setPassword(passwordEncoder.encode(user.getPassword()));
			UserRepository.save(u);
			response.put("flag", "success");
			// event.LOG_EVENT(u.getUsername(), response);

		} catch (Exception e) {
			e.printStackTrace();
			response.put("flag", "failed");
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

	@Override
	public List<Audittrail> getActivity(Integer row, Integer page) {
		// TODO Auto-generated method stub
		try {

			Pageable pagination = PageRequest.of(page, row, Sort.by(Sort.Direction.DESC, "id"));
			return AuditRepository.findAll(pagination).getContent();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return new ArrayList<Audittrail>();
	}

	@Override
	public ResponseEntity<PaginateValues> getPageValues(Integer row) {
		try {
			List<Integer> options = new ArrayList<Integer>();
			PaginateValues values = new PaginateValues();
			values.setCount(AuditRepository.count());
			values.setRow(row);
			if (values.getCount() >= 20) {
				options = new ArrayList<Integer>();
				options.add(10);
				options.add(15);
				options.add(20);
			} else if (values.getCount() >= 15 && values.getCount() < 20) {
				options = new ArrayList<Integer>();
				options.add(10);
				options.add(15);
			} else if (values.getCount() >= 10 && values.getCount() < 15) {
				options = new ArrayList<Integer>();
				options.add(10);
			} else {
				options = new ArrayList<Integer>();
				options.add((int) values.getCount());
			}
			values.setRowoptions(options);
			return ResponseEntity.ok(values);

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return ResponseEntity.ok(new PaginateValues());
	}

	@Override
	public ResponseEntity<Map<String, Object>> signOut(String user) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {

			response.put("flag", "success");
			response.put("event", "System logged out");

		} catch (Exception e) {
			e.printStackTrace();
			response.put("flag", "failed");
			return ResponseEntity.badRequest().body(response);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(response);
	}

}