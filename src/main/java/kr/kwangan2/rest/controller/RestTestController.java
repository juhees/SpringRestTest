package kr.kwangan2.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.kwangan2.rest.domain.Person;
import kr.kwangan2.rest.domain.Ticket;
import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/test")
@Log4j
public class RestTestController {
	
	//1
	@GetMapping(value="/plainText", 
			produces ="text/plain; charset=UTF-8")//produces 속성값으로 지정된 text/plain 결과가 나옴
	public String getText() {
		log.info("MIME TYPE:" + MediaType.TEXT_PLAIN_VALUE);
		return "안녕 REST";
	}
	
	//2
	@RequestMapping(value="/object", 
			produces = {
					MediaType.APPLICATION_JSON_UTF8_VALUE,
					MediaType.APPLICATION_XML_VALUE
			}
	)
	public Person getObject() {
		return new Person("홍길동", 30);
	}
	
	
	//3
	@RequestMapping("/getList")
	public List<Person> getList(){
		return IntStream.range(1, 10)
				.mapToObj(i->new Person("이름"+i,i))
				.collect(Collectors.toList());
	}
	
	//4
	@RequestMapping("/getMap")
	public Map<String, Person> getMap(){
		Map<String, Person> map = new HashMap<String, Person>();
		
		map.put("hong", new Person("홍길동",30));
		map.put("shin", new Person("신주희",26));
		map.put("lee", new Person("이지은",23));
		
		return map;
	}
	
	//5
	@GetMapping(value="/responseEntity", params = {"height", "weight"})
	public ResponseEntity<Person> responseEntity(double height, double weight){
		
		Person person = new Person("신주희",26); //body에 보낼 객체를 만들어줌
		
		ResponseEntity<Person> result = null;
		
		if(height < 150) {
			result=ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(person);
		}else {
			result=ResponseEntity.status(HttpStatus.OK).body(person);
		}
		return result;
	}
	
	//6
	@RequestMapping(value="/getPath/{cat}/{pid}")
	public String[] getPath(
		@PathVariable("cat") String cat,
		@PathVariable("pid") String pid) {
		return new String[] {"category: "+ cat,"pid: "+pid};
	}
	
	//7
	@PostMapping("/requestBody") //왜 post? get은 request header만 보내기때문에 body를 보내려면 postmapping을 해야함
	public Ticket requestBody(@RequestBody Ticket ticket) {
		return ticket;
	}
	
	
	
}//class
