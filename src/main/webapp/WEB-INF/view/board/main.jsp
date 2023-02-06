<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ include file="../layout/header.jsp" %>

        <div class="container my-3">
            <div class="d-flex my-board-box row">
                <c:forEach items="${boardMainRespDtos}" var="boardMainRespDto">
                    <div class="card col-lg-3">
                        <img class="card-img-top" style="height: 250px;" src="images/profile.png" alt="Card image">
                        <div class="card-body">
                            <div>작성자 : ${boardMainRespDto.username}</div>
                            <h4 class="card-title my-text-ellipsis">${boardMainRespDto.title}</h4>
                            <a href="/board/${boardMainRespDto.id}" class="btn btn-primary">상세보기</a>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <ul class="pagination mt-3 d-flex justify-content-center">
                <li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>
                <li class="page-item"><a class="page-link" href="#">Next</a></li>
            </ul>
        </div>

        <%@ include file="../layout/footer.jsp" %>