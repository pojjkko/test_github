package com.spring.board.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.spring.board.dao.BoardDao;
import com.spring.board.vo.BoardVo;

@Repository("boardDao")
public class BoardDaoImpl implements BoardDao {

	@Override
	public List<BoardVo> getList() {
		// data 입력
		List<BoardVo> list = new ArrayList<BoardVo>();
		list.add( new BoardVo(1, "제목1", "bada@naver.com") );
		list.add( new BoardVo(2, "제목2", "bada@nate.com") );	
		
		return list;
	}

}
