<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ include file="../layout/header.jsp" %>

        <div class="container my-3">
            <div class="d-flex my-board-box row">
                <table border="1">
                    <tr>
                        <th>id</th>
                        <th>comment</th>
                        <th>userId</th>
                        <th>boardId</th>
                        <th>createdAt</th>
                </table>
                </tr>
                <c:forEach items="${replyList}" var="reply">
                    <table border="1">
                        <tr>
                            <td>${reply.id}</td>
                            <td>${reply.comment}</td>
                            <td>${reply.userId}</td>
                            <td>${reply.boardId}</td>
                            <td>${reply.createdAt}</td>
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