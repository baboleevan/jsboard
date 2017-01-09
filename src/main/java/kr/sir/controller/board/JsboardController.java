package kr.sir.controller.board;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.sir.domain.Write;
import kr.sir.service.board.BoardService;

@Controller
@RequestMapping(value="/board")
public class JsboardController {
	
	private BoardService boardService;
	
	@Autowired
	public void setBoardService(BoardService boardService) {
		this.boardService = boardService;
	}

	@RequestMapping(value="/list")
	public String index(Model model, Write write) {
		
		int paramCurrentPage = 0;
		int paramPagePerPosts = 10;
		int paramBoardId = 1;
		
		PageRequest pageRequest = new PageRequest(paramCurrentPage, paramPagePerPosts, new Sort(Direction.ASC, "id"));
		
		Page<Write> result = boardService.findByBoardId(paramBoardId, pageRequest);
		List<Write> writeList = result.getContent();
		int totalPages = result.getTotalPages();			// 전체 페이지 수
		long totalCount = result.getTotalElements();		// 전체 게시물 수
		int currentPage = result.getNumber();				// 현재 페이지
		boolean hasNextPage = result.hasNext();				// 다음 페이지 여부
		
		model.addAttribute("currentPage", currentPage + 1);	// 0부터 시작.
		model.addAttribute("totalPages", totalPages);
//		model.addAttribute("hasNextPage", hasNextPage);
		model.addAttribute("writeList", writeList);
		model.addAttribute("totalCount", totalCount); 
		
		return "/board/list";
	}
}