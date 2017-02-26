package com.wicky.controller;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wicky.IOUtil;

@Controller
@RequestMapping("/")
@EnableAutoConfiguration
public class IndexController {

//	@Autowired
//	private AccountService service;

	private Set<String> words = new LinkedHashSet<>();
	
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
						word = word.substring(0, 4);
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
		String name = (String) words.toArray()[id];
		int i = 1;
		File file = findFile(name);
		while(file.exists()){
			file = findFile(name + i++);
		}
		name = file.getName();
		return "redirect:/" + name;
	}
	
	@PostMapping("{name}")
	@ResponseBody
	String save(@PathVariable(value="name", required=true) String name, @RequestParam String data) {
		File file = loadFile(name);
		try {
			IOUtil.saveFileNio(file, data);
			System.out.println("saved! " + data);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "success";
	}
	
    @GetMapping("{name}")
    String index(@PathVariable(value="name", required=false) String name, Model model) {
        return "index";
    }

    @GetMapping(value="{name}", headers="content-type=text/plain")
    @ResponseBody
	String load(@PathVariable(value="name") String name) {
		File file = loadFile(name);
		String data = "";
		try {
			data = IOUtil.readFileNio(file);
			System.out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}

	private File loadFile(String name) {
		File file = findFile(name);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
		return file;
	}

	private File findFile(String name) {
		String path = System.getProperty("store") + "/" + name;
    	File file = new File(path);
		return file;
	}

}
