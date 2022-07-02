package com.cs.lapc.report.object;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Book {

	private String author;
	private String title;
	private String genre;
	private float price;
	private Date publish_date;
	private UserRating user_rating;
}
