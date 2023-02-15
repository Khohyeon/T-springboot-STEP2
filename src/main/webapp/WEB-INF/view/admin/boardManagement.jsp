<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ include file="../layout/header.jsp" %>

        <div class="container my-3">
            <div class="d-flex my-board-box row">
                <table border="1">
                    <tr>
                        <th>id</th>
                        <th>title</th>
                        <th>content</th>
                        <th>thumbnail</th>
                        <th>userId</th>
                        <th>createdAt</th>
                </table>
                </tr>
                <c:forEach items="${boardList}" var="board">
                    <table border="1">
                        <tr>
                            <td>${board.id}</td>
                            <td>${board.title}</td>
                            <td>${board.content}</td>
                            <td>${board.thumbnail}</td>
                            <td>${board.userId}</td>
                            <td>${board.createdAt}</td>
                        </tr>
                    </table>
                </c:forEach>
            </div>
            <ul class="pagination mt-3 d-flex justify-content-center">
                <li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>
                <li class="page-item"><a class="page-link" href="#">Next</a></li>
            </ul>
        </div>

        <%@ include file="../layout/footer.jsp" %>