<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib  uri="http://www.springframework.org/security/tags" prefix="sec"%>
<body>
	<nav>
            <ul id="mainMenu">
                <li onclick="menuOn()"><img class="menuIcon" src="./img/menuBar.png" alt=""></li>
                <sec:authorize access="hasRole('ROLE_USER')">
                <li>
                    <span>전자결재</span><img class="menuIcon" src="./img/pencil_32.png" alt="">
                    <ul class="subMenu">
                        <li><span onclick="location.href='./docWriteForm.do;'">문서작성</span></li>
                        <li><span onclick="location.href='./approMain.do;'">문서함</span></li>
                        <li><span onclick="location.href='./signMain.do;'">전자서명</span></li>
                    </ul>
                </li>
                <li onclick="location.href='./moveCalendar.do'"><span>캘린더</span><img class="menuIcon" src="./img/calendar_32.png" alt=""></li>
                <li><span>회의실</span><img class="menuIcon" src="./img/meet_32.png" alt=""></li>
                <li>
                    <span>게시판</span><img class="menuIcon" src="./img/board_32.png" alt="">
                    <ul class="subMenu">
                        <li onclick="location.href='./entrBoard.do'"><span>공지게시판</span></li>
                        <li onclick="location.href='./jaryoBoard.do'"><span>자료게시판</span></li>
                    </ul>
                </li>
                <li><span>연차현황</span><img class="menuIcon" src="./img/apro_32.png" alt=""></li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_ADMIN_BOARD','ROLE_ADMIN_INSA')">
                <li onclick="location.href='./selAllEmp.do'"><span>회원조회</span><img class="menuIcon" src="./img/user_set32.png" alt=""></li>
                <li onclick="location.href='./annualAdmin.do'"><span>연차관리</span><img class="menuIcon" src="./img/calendar_32.png" alt=""></li>
                <li onclick="location.href='./entrBoardAdmin.do'"><span>게시판관리</span><img class="menuIcon" src="./img/board_set32.png" alt=""></li>
                </sec:authorize>
            </ul>
        </nav>
</body>
</html>