package com.spring.board.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.board.service.BoardService;
import com.spring.board.vo.BoardVo;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/")
	public String home()	{
		return "home";
	}
	
//	@RequestMapping("/a")		// html 바로 보내기
//	public String html()	{
//		return "redirect:/static/html/test01.html";
//	}
//	
//	@RequestMapping("/b")		// html 바로 보내기
//	public String html2()	{
//		return "redirect:/static/html/test02.html";
//	}
	
	@RequestMapping("/{value}")
	public String html( @PathVariable("value") String val )	{
		String link = "";
		switch(val)	{
		case "a" :
			link = "redirect:/static/html/test01.html";
			break;
		case "b" :
			link = "redirect:/static/html/test02.html";
			break;
		case "c" :
			link = "redirect:/static/html/test03.html";
			break;
		case "d" :
			link = "redirect:/static/html/test04.html";
			break;
		case "e" :
			link = "redirect:/static/html/test05.html";
			break;
		case "f" :
			link = "redirect:/static/html/test06.html";
			break;
		}
		
		return link;
	}
	
	@RequestMapping("/ajax")
	public void ajax(String v, HttpServletResponse response)	{
		String json = "{ \"id\":\"aaaa\", \"pass\":\"1234\", \"v\":\"" + v + "\" }";
		
		try {
			response.setContentType("application/json; charset=utf-8");
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// {"pass":"blue","id":"sky"}
	@RequestMapping(value="/test1")
	@ResponseBody
	public Map<String, Object> test1(String id, String pass, String email)	{
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("pass", pass);
		map.put("email", email);
		
		return map;
	}
	
	@RequestMapping("/ajax2")
	@ResponseBody
	public String test2() throws IOException	{
		
		List<BoardVo> list = boardService.getList();
		
		// Jackson Library
		// java 객체 -> json
		ObjectMapper mapper = new ObjectMapper();
		String strJSON = mapper.writeValueAsString(list);
		System.out.println(strJSON);
		
		// json -> java 객체 [list<>]
		// String strJSON2 = "{"num":"aaa", "name":"1234", "email":"a@b.c" }";
		// BoardVo board = mapper.readValue(strJSON2, BoardVo.class);
		String strJSON2 = "[" + 
	            		  " {\"num\":1, \"name\":\"ab\", \"email\":\"a@b.c\"}," + 
	            		  " {\"num\":2, \"name\":\"bc\", \"email\":\"b@c.f\"}" + 
	            		  "]";
		List<BoardVo> list2 = mapper.readValue(strJSON2, new TypeReference<List<BoardVo>>() {});
		System.out.println(list2.get(0));
		System.out.println(list2.get(1));
		
		return strJSON;
		//return strJSON2;
	}
	
	@RequestMapping(value="/getMovieJSON")
	@ResponseBody
	public String getMovieJSON() throws IOException	{
		BufferedInputStream reader = null;
		
		try {
			URL url = new URL("http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=a1f86bc4b1ae63ffced7ca669992a8f9&targetDt=20200729");
			reader = new BufferedInputStream(url.openStream());
		
			StringBuffer buffer = new StringBuffer();
			int i;
			byte [] b = new byte[4096];
			while( (i = reader.read(b)) != -1 )	{
				buffer.append( new String(b, 0, i) );
			}
		
			return buffer.toString();
		} finally {
			if(reader != null)	reader.close();
		}
	}
	
	@RequestMapping(value="/getMovieXML", produces="application/xml; charset=utf-8")
	@ResponseBody
	public String getMovieXML() throws IOException	{
		BufferedInputStream reader = null;
		
		try {
			URL url = new URL("http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.xml?key=a1f86bc4b1ae63ffced7ca669992a8f9&targetDt=20200729");
			reader = new BufferedInputStream(url.openStream());
		
			StringBuffer buffer = new StringBuffer();
			int i;
			byte [] b = new byte[4096];
			while( (i = reader.read(b)) != -1 )	{
				buffer.append( new String(b, 0, i) );
			}
		
			return buffer.toString();
		} finally {
			if(reader != null)	reader.close();
		}
	}
}
