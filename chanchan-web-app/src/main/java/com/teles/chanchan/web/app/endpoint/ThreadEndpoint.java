package com.teles.chanchan.web.app.endpoint;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teles.chanchan.web.app.document.mongo.ChanThread;
import com.teles.chanchan.web.app.dto.ThreadResponse;
import com.teles.chanchan.web.app.service.ThreadService;

import io.swagger.annotations.Api;

@Api
@RestController
@RequestMapping("/thread")
public class ThreadEndpoint {

	private final ThreadService threadService;

	public ThreadEndpoint(ThreadService threadService) {
		this.threadService = threadService;
	}

	@GetMapping
	public ResponseEntity<?> getThreads() {
		ResponseEntity<?> responseEntity = null;

		List<ChanThread> chanThreads = this.threadService.findAll();

		if (chanThreads.isEmpty()) {
			responseEntity = ResponseEntity.noContent().build();
		} else {
			responseEntity = ResponseEntity.ok(chanThreads.stream().map(ThreadResponse::new).collect(Collectors.toList()));
		}

		return responseEntity;
	}
	
}
