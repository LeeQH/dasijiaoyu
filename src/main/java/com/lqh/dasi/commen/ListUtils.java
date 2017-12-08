package com.lqh.dasi.commen;

import java.util.List;

/**
 * List操作工具
 * @author LiQuanhui
 * @date 2017年11月24日 下午5:22:23
 */
public class ListUtils {
	/**
	 * 打印嵌套任意层数的arraylist
	 * @author LiQuanhui
	 * @date 2017年11月21日 下午3:42:18
	 * @param list
	 */
	@SuppressWarnings("rawtypes")
	public static <T extends List> void printArrayList(T list){
		if(list!=null&&list.size()!=0){
			if(list.get(0) instanceof List){
				for(int i=0,size=list.size();i<size;i++){
					printArrayList((List)list.get(i));
				}
			}else{
				for(int i=0,size=list.size();i<size;i++){
					System.out.println(list.get(i).toString());
				}
			}
		}else{
			System.out.println("无数据");
		}
	}
}
