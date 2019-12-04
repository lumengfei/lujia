package com.lumengjun.senior1.week1.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lumengjun.senior1.week1.entity.Brand;
import com.lumengjun.senior1.week1.entity.Goods;
import com.lumengjun.senior1.week1.entity.Goodskind;
import com.lumengjun.senior1.week1.service.MyService;

//控制层
@Controller
public class MyController {

	@Autowired
	public MyService ser;
	//列表方法
	@RequestMapping("list")
	public String list(@RequestParam(defaultValue="1")Integer pageNum,String name,HttpServletRequest request){
		//分页
		PageHelper.startPage(pageNum, 3);
		//模糊
		List<Goods> list=ser.getList(name);
		PageInfo<Goods> pageInfo = new PageInfo<Goods>(list);
		request.setAttribute("list", pageInfo);
		//返回list页面
		return "list";
	}
	//动态加载品牌
	@ResponseBody
	@RequestMapping("getB")
	public Object getB(){
		List<Brand> list =ser.getBrand();
		return list;
	}
	//动态加载类型
	@ResponseBody
	@RequestMapping("getK")
	public Object getK(){
		List<Goodskind> list =ser.getGoodskind();
		return list;
	}
	//跳转添加页面
	@RequestMapping("toadd")
	public String toadd(){
		return "add";
	}
	//进行添加的方法
	@ResponseBody
	@RequestMapping("add")
	public Object add(String gname,Integer datea,Integer  type_id,Integer price){
		Goods goods = new Goods();
		goods.setGname(gname);
		goods.setType_id(type_id);
		goods.setPrice(price);
		goods.setDatea(datea);
		int i= ser.add(goods);
		if(i>0){
			return true;
		}else{
			return false;
		}
	}
	//跳转修改页面
	@RequestMapping("toupd")
	public String toupd(Integer gid,HttpServletRequest request){
		Goods goods=ser.getGoods(gid);
		request.setAttribute("g", goods);
		return "update";
	}
	
	//进行修改的方法
	@ResponseBody
	@RequestMapping("update")
	public Object add(Integer gid,String gname,Integer datea,Integer  type_id,Integer price){
		Goods goods = new Goods();
		goods.setGid(gid);
		goods.setGname(gname);
		goods.setType_id(type_id);
		goods.setPrice(price);
		goods.setDatea(datea);
		int i= ser.update(goods);
		if(i>0){
			return true;
		}else{
			return false;
		}
	}
	//详情页面
	@RequestMapping("get")
	public String get(Integer gid,HttpServletRequest request){
		Goods goods=ser.getGoods(gid);
		request.setAttribute("g", goods);
		return "select";
	}
	
	//批量删除的方法
	@ResponseBody
	@RequestMapping("delAll")
	public Object delAll(String j){
		//获取批量删除的id
		int i=ser.delAll(j);
		if(i>0){
			return true;
		}else{
			return false;
		}
	}
	@Test
	public void del1(){
		int i=ser.delAll("2");
		System.out.println(i);
	}
	
	@Test
	public void add1(){
		Goods goods = new Goods();
		goods.setGname("师父父");
		goods.setType_id(1);
		goods.setPrice(200);
		goods.setDatea(1);
		int i= ser.add(goods);
		System.out.println(i);
	}
	@Test
	public void upd1(){
		Goods goods = new Goods();
		goods.setGid(2);
		goods.setGname("asd");
		goods.setType_id(1);
		goods.setPrice(200);
		goods.setDatea(1);
		int i= ser.update(goods);
		System.out.println(i);
	}
	
}
