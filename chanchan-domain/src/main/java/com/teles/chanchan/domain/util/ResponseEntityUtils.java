package com.teles.chanchan.domain.util;

import java.util.Collection;

import org.springframework.http.ResponseEntity;

public final class ResponseEntityUtils {

	private ResponseEntityUtils() {
	}

	public static <T> ResponseEntity<?> buildDataResponseEntity(T e) {
		return build(e);
	}

	public static <T> ResponseEntity<?> buildDataResponseEntity(Collection<? extends T> e) {
		return build(e);
	}

	private static <T> ResponseEntity<?> build(T e) {
		ResponseEntity<?> r;

		if (hasContent(e)) {
			r = ResponseEntity.ok(e);
		} else {
			r = ResponseEntity.notFound().build();
		}

		return r;
	}

	private static <T> boolean hasContent(T t) {
		boolean b = true;

		if (t == null) {
			b = false;
		} else if (t instanceof Collection) {
			b = !((Collection<?>) t).isEmpty();
		}

		return b;
	}

}