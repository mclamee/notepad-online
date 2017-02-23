package com.wicky.controller;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wicky.IOUtil;
import com.wicky.domain.EmployeeVO;
import com.wicky.service.AccountService;

@Controller
@RequestMapping("/")
@EnableAutoConfiguration
public class IndexController {
	
	@Autowired
	private AccountService service;
	
	private List<String> words = new ArrayList<>();
	
	@PostConstruct
	public void init(){
		// setup environments
		System.setProperty("store", "D:/_tmp");
		
		// setup random name list
    	try {
			@SuppressWarnings("resource")
			RandomAccessFile file = new RandomAccessFile(this.getClass().getResource("/wordsEn.txt").getFile(), "r");
			FileChannel channel = file.getChannel();
			
			MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
			buffer.load();
			
			boolean fr = false;
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < buffer.limit(); i++) {
				char c = (char)buffer.get();
				if(c == '\r'){
					fr = true;
				}
				if(c != '\r' && c != '\n'){
					sb.append(c);
				}
				if(fr == true && c == '\n'){
					// line break;
					String word = sb.toString();
//					System.out.println(word);
					if(word.length() > 3){
						words.add(word);	
					}
					sb = new StringBuilder();
					fr = false;
				}
			}
			System.out.println(words);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping
	String redirect(HttpServletRequest request){
		int id = ThreadLocalRandom.current().nextInt(0, words.size());
		String name = words.get(id);
		return "redirect:/" + name;
	}
	
	@PostMapping("{name}")
	@ResponseBody
	String save(@PathVariable(value="name", required=true) String name, String data, HttpServletRequest request) {
		
		return "success";
	}
	
    @GetMapping("{name}")
    String index(@PathVariable(value="name", required=false) String name, HttpServletRequest request) {
    	String path = System.getProperty("store") + "/" + name;

    	File file = new File(path);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	
		List<EmployeeVO> employees = service.getAllEmployees();
		System.out.println(employees);
		
		try {
			String data = IOUtil.readFileNio(file);
			System.out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        return "index";
    }
    
}
