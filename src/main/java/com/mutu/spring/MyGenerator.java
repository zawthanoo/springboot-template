package com.mutu.spring;


import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * @author Zaw Than Oo
 * @since 01-DEC-2018
 */

public class MyGenerator {

	public static void main(String[] args) throws Exception {
		/*
		Run Following commend to initialize the DB before generation.
		java -cp h2-2.1.214.jar org.h2.tools.RunScript -url "jdbc:h2:file:~/testdb" -user sa -script schema.sql
		*/
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(new FileInputStream("src/main/resources/mybatic-generator.xml"));
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
		System.out.println("Generation Completed");
	}
}
