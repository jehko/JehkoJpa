package com.jehko.jpa.board.service;

import com.jehko.jpa.board.entity.BoardType;
import com.jehko.jpa.board.model.BoardTypeCount;
import com.jehko.jpa.board.model.BoardTypeInput;
import com.jehko.jpa.board.model.BoardTypeUsing;
import com.jehko.jpa.board.model.ServiceResult;
import com.jehko.jpa.board.repository.BoardCustomRepository;
import com.jehko.jpa.board.repository.BoardRepository;
import com.jehko.jpa.board.repository.BoardTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardTypeRepository boardTypeRepository;
    private final BoardRepository boardRepository;
    private final BoardCustomRepository boardCustomRepository;

    BoardType getByName(String boardName) {
        return null;
    }

    public ServiceResult addBoard(BoardTypeInput boardTypeInput) {
        BoardType boardType = boardTypeRepository.findByBoardName(boardTypeInput.getName());

        if(boardType != null && boardTypeInput.getName().equals(boardType.getBoardName())) {
            return ServiceResult.fail("이미 동일한 게시판이 존재합니다.");
        }

        BoardType addedBoardType = BoardType.builder()
                .boardName(boardTypeInput.getName())
                .regDate(LocalDateTime.now())
                .build();

        boardTypeRepository.save(addedBoardType);
        return ServiceResult.success();
    }

    public ServiceResult updateBoard(long id, BoardTypeInput boardTypeInput) {
        Optional<BoardType> optionalBoardType = boardTypeRepository.findById(id);

        if(!optionalBoardType.isPresent()) {
            return ServiceResult.fail("존재하지 않는 게시판입니다.");
        }

        BoardType sameBoardType = boardTypeRepository.findByBoardName(boardTypeInput.getName());
        BoardType boardType = optionalBoardType.get();

        if(sameBoardType != null) {
            String message = "이미 동일한 게시판이 존재합니다.";
            if(sameBoardType.getBoardName().equals(boardType.getBoardName())) {
                message = "게시판 이름이 동일합니다.";
            }
            return ServiceResult.fail(message);
        }

        boardType.setBoardName(boardTypeInput.getName());
        boardType.setUpdateDate(LocalDateTime.now());
        boardTypeRepository.save(boardType);

        return ServiceResult.success();
    }

    public ServiceResult deleteBoard(Long id) {
        Optional<BoardType> optionalBoardType = boardTypeRepository.findById(id);

        if(!optionalBoardType.isPresent()) {
            return ServiceResult.fail("존재하지 않는 게시판입니다.");
        }

        BoardType boardType = optionalBoardType.get();

        if(boardRepository.countByBoardType(boardType) > 0) {
            return ServiceResult.fail("삭제할 게시판 타입의 게시글이 존재합니다.");
        }

        boardTypeRepository.delete(boardType);

        return ServiceResult.success();
    }

    public List<BoardType> getAllBoardType() {
        return boardTypeRepository.findAll();
    }

    public ServiceResult setBoardTypeUsing(Long id, BoardTypeUsing boardTypeUsing) {
        Optional<BoardType> optionalBoardType = boardTypeRepository.findById(id);

        if(!optionalBoardType.isPresent()) {
            return ServiceResult.fail("존재하지 않는 게시판입니다.");
        }

        BoardType boardType = optionalBoardType.get();

        boardType.setUsingYn(boardTypeUsing.isUsingYn());
        boardTypeRepository.save(boardType);

        return ServiceResult.success();
    }

    public List<BoardTypeCount> getBoardTypeCount() {
        return boardCustomRepository.findBoardTypeCount();
    }
}
