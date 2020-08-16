package com.article;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

@SpringBootApplication
@org.springframework.cache.annotation.EnableCaching
public class ArticleStore {

	public static void main(String[] args) {
		SpringApplication.run( ArticleStore.class, args);
	}

}
