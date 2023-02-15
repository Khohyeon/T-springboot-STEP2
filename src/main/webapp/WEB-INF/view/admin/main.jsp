<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


    <%@ include file="../layout/header.jsp" %>


        <div class="container">
      <div class="row">
        <div class="col">
          <h1>관리자 페이지</h1>
          <hr/>
            <ul class="nav nav-tabs">
              <li class="nav-item">
                <a class="nav-link active" data-toggle="tab" href="#userCare">회원관리</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" data-toggle="tab" href="#boardCare">게시글관리</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" data-toggle="tab" href="#replyCare">댓글관리</a>
              </li>
            </ul>
            <div class="tab-content">
              <div class="tab-pane fade show active" id="userCare">
              <c:forEach items="${userList}" var="user">
            <ul class="list-group">
                <li class="list-group-item">${user.id}</li>
                <li class="list-group-item">${user.username}</li>
            </ul>
              </c:forEach>
              </div>
              <div class="tab-pane fade" id="boardCare">
                <c:forEach items="${boardList}" var="board">
            <ul class="list-group">
                <li class="list-group-item">${board.id}</li>
                <li class="list-group-item">${board.title}</li>
                <li class="list-group-item">${board.content}</li>
            </ul>
              </c:forEach>
              </div>
              <div class="tab-pane fade" id="replyCare">
               <c:forEach items="${replyList}" var="reply">
            <ul class="list-group">
                <li class="list-group-item">${reply.id}</li>
                <li class="list-group-item">${reply.comment}</li>
                <li class="list-group-item">${reply.createdAt}</li>
            </ul>
              </c:forEach>
              </div>
            </div>
        </div>
      </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>

        <script>
            function userManagement() 
        </script>



        <%@ include file="../layout/footer.jsp" %>