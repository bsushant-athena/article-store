package com.article.entity;

import com.fasterxml.jackson.annotation.*;
import java.time.*;
import lombok.*;

import javax.persistence.*;

@JsonPropertyOrder({
    "id",
    "slug",
    "title",
    "description",
    "body",
    "createdAt",
    "updatedAt",
	"wordcount"
})

@Data
@Entity
public class Article implements java.io.Serializable {

	public static final long serialVersionUID = 2L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty(value="id")
	private Long id;

    private String slug;

    //@lombok.NonNull
    private String title;

	//@lombok.NonNull
    private String description;

	//@lombok.NonNull
    private String body;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

	private Long wordcount;


	public Long getSlug_id ( ) {
		return id;
	}

	public void setSlug_id ( Long id ) {
		this.id = id;
	}

	public String getSlug ( ) {
		return slug;
	}

	public void setSlug ( String slug ) {
		this.slug = slug;
	}

	public String getTitle ( ) {
		return title;
	}

	public void setTitle ( String title ) {
		this.title = title;
	}

	public String getDescription ( ) {
		return description;
	}

	public void setDescription ( String description ) {
		this.description = description;
	}

	public String getBody ( ) {
		return body;
	}

	public void setBody ( String body ) {
		this.body = body;
	}

	public ZonedDateTime getCreatedAt ( ) {
		return createdAt;
	}

	public void setCreatedAt ( ZonedDateTime createdAt ) {
		this.createdAt = createdAt;
	}

	public ZonedDateTime getUpdatedAt ( ) {
		return updatedAt;
	}

	public void setUpdatedAt ( ZonedDateTime updatedAt ) {
		this.updatedAt = updatedAt;
	}

	public Long getWordcount ( ) {
		return wordcount;
	}

	public void setWordcount ( Long wordcount ) {
		this.wordcount = wordcount;
	}

}
