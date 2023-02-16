<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ include file="../layout/header.jsp" %>
        <form action="/board/main" method="GET" class="form-inline p-2 bd-highlight" role="search">
            <input type="text" name="title" class="form-control" id="search" placeholder="검색">
            <button class="btn btn-success bi bi-search"></button>
        </form>

        <div id="posts_list">
            <table id="table" class="table table-horizontal">
                <thead id="thead">
                    <tr>
                        <th>번호</th>
                        <th>제목</th>
                        <th>내용</th>
                        <th>등록일</th>
                        <th>조회수</th>
                    </tr>
                </thead>
                    <c:forEach items="${boardList}" var="board">
                <tbody id="tbody">
                    <tr>
                        <td>${board.id}</td>
                        <td><a href="/board/#/${board.id}">${board.title}</a></td>
                        <td>${board.content}</td>
                        <td>${board.createdAt}</td>
                        <td>1</td>
                    </tr>
                </tbody>
                    </c:forEach>
            </table>
            
        </div>
        <%@ include file="../layout/footer.jsp" %>