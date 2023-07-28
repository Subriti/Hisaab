package com.example.project.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonPropertyOrder({"category_id, category_name"})

@Entity
@Table(name = "Category")
public class Category implements Serializable{
	
	private static final long serialVersionUID = -1244383128993878686L;

	@Id
	@SequenceGenerator(name = "category_sequence", sequenceName = "category_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence")
	
	@Column(name = "category_id")
	private int categoryId;

	@Column(name = "category_name")
	private String categoryName;
    
	public Category() {

	}

	public Category(int categoryId, String categoryName) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}

	public Category(String categoryName) {
        super();
        this.categoryName = categoryName;
    }

    @JsonGetter("category_id")
	public int getCategoryId() {
		return categoryId;
	}

	@JsonSetter("category_id")
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	@JsonGetter("category_name")
	public String getCategoryName() {
		return categoryName;
	}

	@JsonSetter("category_name")
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
