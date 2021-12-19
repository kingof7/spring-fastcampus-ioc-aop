package com.example.aop;

import com.example.aop.dto.Car;
import com.example.aop.dto.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Main {

	public static void main(String[] args) throws JsonProcessingException {
		System.out.println("main");

		ObjectMapper objectMapper = new ObjectMapper();

		User user = new User();
		user.setName("홍길동");
		user.setAge(10);

		Car car1 = new Car();
		car1.setName("K5");
		car1.setCarNumber("11가 1111");
		car1.setType("sedan");

		Car car2 = new Car();
		car2.setName("Q5");
		car2.setCarNumber("11가 1112");
		car2.setType("suv");

		List<Car> carList = Arrays.asList(car1, car2);
		user.setCars(carList);
		System.out.println(carList);

		System.out.println(user);

		String json = objectMapper.writeValueAsString(user);
		System.out.println(json);

		JsonNode jsonNode = objectMapper.readTree(json);
		String _name = jsonNode.get("name").asText();
		int _age = jsonNode.get("age").asInt();
		System.out.println("name: " + _name);
		System.out.println("age: " + _age);

//		String _list = jsonNode.get("cars").asText();
//		System.out.println(_list);

		JsonNode cars = jsonNode.get("cars");
		ArrayNode arrayNode = (ArrayNode)cars;
		List<Car> _cars = objectMapper.convertValue(arrayNode, new TypeReference<List<Car>>(){});
		System.out.println(_cars);
		System.out.println("##############################");
		ObjectNode objectNode = (ObjectNode) jsonNode;

		//값 바꿔주는 메서드
		objectNode.put("name", "steve");
		objectNode.put("age", 20);
		System.out.println(objectNode.toPrettyString()); // fomatter


	}

}
