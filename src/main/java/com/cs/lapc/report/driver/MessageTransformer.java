package com.cs.lapc.report.driver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.function.Consumer;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class MessageTransformer {

	private static final String RESOURCE_PATH = "\\resources\\com\\cs\\lapc\\report\\";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("I am in spark message transformer");

		new MessageTransformer().transform();
	}

	private void transform() {
		// TODO Auto-generated method stub
		SparkConf sparkConf = getSparkConfig();
		// SparkSession spark = SparkSession.builder().getOrCreate();
		JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);
		JavaRDD<String> input = sparkContext.textFile(getAbsolutePath("xml/sample.xml"));
		System.out.println(input.toDebugString());
	}

	private String getAbsolutePath(String resource) {
		// TODO Auto-generated method stub
		ClassLoader classLoader = getClass().getClassLoader();
		File file;
		try {
			URL res = classLoader.getResource(resource);
			file = Paths.get(res.toURI()).toFile();
			return file.getAbsolutePath();
		} catch (URISyntaxException e) {
			System.err.println("Error obtaining absolute path for file:" + resource);
			System.err.println("Exception:" + e.getMessage());
		}
		return null;
	}

	private SparkConf getSparkConfig() {
		// TODO Auto-generated method stub
		FileReader reader;
		SparkConf sparkConf = new SparkConf();
		try {
			reader = new FileReader(getAbsolutePath("properties/sparkcnf.properties"));

			Properties properties = new Properties();
			properties.load(reader);

			Consumer<? super Entry<Object, Object>> action = t -> {
				sparkConf.set(t.getKey().toString(), t.getValue().toString());
			};
			properties.entrySet().forEach(action);

		} catch (IOException e) {
			System.err.println("Exception in creating spark conf:" + e.getMessage());
			e.printStackTrace();
		}

		return sparkConf;
	}

}
