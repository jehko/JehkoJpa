package com.jehko.jpa.board.service;

import com.jehko.jpa.board.entity.*;
import com.jehko.jpa.board.model.*;
import com.jehko.jpa.board.repository.*;
import com.jehko.jpa.user.entity.User;
import com.jehko.jpa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardTypeRepository boardTypeRepository;
    private final BoardRepository boardRepository;
    private final BoardCustomRepository boardCustomRepository;
    private final BoardHitsRepository boardHitsRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final BoardBadReportRepository boardBadReportRepository;
    private final UserRepository userRepository;

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

    public ServiceResult setBoardTop(Long id, BoardTopInput boardTopInput) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(!optionalBoard.isPresent()) {
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }

        Board board = optionalBoard.get();
        if(board.isTopYn() == boardTopInput.isTopYn()) {
            if(boardTopInput.isTopYn()) {
                return ServiceResult.fail("이미 게시글이 최상단에 배치되어 있습니다.");
            } else {
                return ServiceResult.fail("이미 게시글이 최상단 배치가 해제되어 있습니다.");
            }
        }

        board.setTopYn(boardTopInput.isTopYn());
        boardRepository.save(board);

        return ServiceResult.success();
    }

    public ServiceResult setBoardPeriod(Long id, BoardPeriod boardPeriod) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(!optionalBoard.isPresent()) {
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }

        Board board = optionalBoard.get();
        board.setPublishStartDate(boardPeriod.getStartDate());
        board.setPublishEndDate(boardPeriod.getEndDate());

        boardRepository.save(board);

        return ServiceResult.success();
    }

    public ServiceResult setBoardHits(Long id, String email) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(!optionalBoard.isPresent()) {
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(!optionalUser.isPresent()) {
            return ServiceResult.fail("사용자 정보가 존재하지 않습니다.");
        }

        Board board = optionalBoard.get();
        User user = optionalUser.get();

        if(boardHitsRepository.countByBoardAndUser(board, user) > 0) {
            return ServiceResult.fail("이미 조회수가 있습니다.");
        }

        boardHitsRepository.save(BoardHits.builder()
                .board(board)
                .user(user)
                .regDate(LocalDateTime.now())
                .build());

        return ServiceResult.success();
    }

    public ServiceResult setBoardLike(Long id, String email) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(!optionalBoard.isPresent()) {
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(!optionalUser.isPresent()) {
            return ServiceResult.fail("사용자 정보가 존재하지 않습니다.");
        }

        Board board = optionalBoard.get();
        User user = optionalUser.get();

        if(boardLikeRepository.countByBoardAndUser(board, user) > 0) {
            return ServiceResult.fail("이미 조회수가 있습니다.");
        }

        boardLikeRepository.save(BoardLike.builder()
                .board(board)
                .user(user)
                .regDate(LocalDateTime.now())
                .build());

        return ServiceResult.success();
    }

    public ServiceResult setBoardUnlike(Long id, String email) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(!optionalBoard.isPresent()) {
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(!optionalUser.isPresent()) {
            return ServiceResult.fail("사용자 정보가 존재하지 않습니다.");
        }

        Board board = optionalBoard.get();
        User user = optionalUser.get();

        Optional<BoardLike> boardLike = boardLikeRepository.findByBoardAndUser(board, user);
        if(!boardLike.isPresent()) {
            return ServiceResult.fail("좋아요한 내용이 없습니다.");
        }

        boardLikeRepository.delete(boardLike.get());
        return ServiceResult.success();
    }

    public ServiceResult addBadReport(Long id, String email, BoardBadReportInput boardBadReportInput) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(!optionalBoard.isPresent()) {
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(!optionalUser.isPresent()) {
            return ServiceResult.fail("사용자 정보가 존재하지 않습니다.");
        }

        Board board = optionalBoard.get();
        User user = optionalUser.get();

        if(boardBadReportRepository.countByUserAndBoardId(user, board.getId()) > 0) {
            return ServiceResult.fail("이미 신고한 게시글입니다.");
        }

        BoardBadReport boardBadReport = BoardBadReport.builder()
                .user(user)
                .boardId(board.getId())
                .boardUserId(board.getUser().getId())
                .boardTitle(board.getTitle())
                .boardContents(board.getContents())
                .boardRegDate(board.getRegDate())
                .comments(boardBadReportInput.getComments())
                .regDate(LocalDateTime.now())
                .build();

        boardBadReportRepository.save(boardBadReport);

        return ServiceResult.success();
    }

    public List<BoardBadReportResponse> badReportList() {
        List<BoardBadReport> badReportList = boardBadReportRepository.findAll();
        List<BoardBadReportResponse> resultList = new ArrayList<BoardBadReportResponse>();
        badReportList.stream().forEach(e -> {
            resultList.add(BoardBadReportResponse.of(e));
        });
        return resultList;
    }
}
