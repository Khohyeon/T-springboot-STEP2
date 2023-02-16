<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


    <%@ include file="../layout/header.jsp" %>


        <div class="container container-fluid mt-5">
      <div class="row">
        <div class="col">
          <h1>관리자 페이지</h1>
          <hr/>
           <div class="col-md-3">
            <div class="list-group">
            <ul class="nav nav-tabs">
              <li class="nav-item">
                <a class="nav-link list-group-item list-group-item-action active" data-toggle="tab" href="#userCare">회원관리</a>
              </li>
              <li class="nav-item">
                <a class="nav-link list-group-item list-group-item-action" data-toggle="tab" href="#boardCare">게시글관리</a>
              </li>
              <li class="nav-item">
                <a class="nav-link list-group-item list-group-item-action" data-toggle="tab" href="#replyCare">댓글관리</a>
              </li>
            </ul>
            </div>
            </div>

            <div class="tab-content">
              <div class="tab-pane fade show active" id="userCare">
              <table class="table table-bordered">
          <thead>
            <tr class="text-center">
              <th>번호</th>
              <th>아이디</th>
              <th>패스워드</th>
              <th>이메일</th>
              <th>권한</th>
              <th>가입일</th>
              <th>비고</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${userList}" var="user">
              <tr id="list-${user.id}" class="text-center">
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.password}</td>
                <td>${user.email}</td>
                <td>${user.role}</td>
                <td>${user.createdAt}</td>
                <td><button class="btn btn-danger btn-sm" onclick="deleteByUserId(${user.id})">삭제</button>
                </td> 
              </tr>
            </c:forEach>
          </tbody>
        </table>
              </div>
              <div class="tab-pane fade" id="boardCare">
                <table class="table table-bordered">
          <thead>
            <tr class="text-center">
              <th>번호</th>
              <th>제목</th>
              <th>내용</th>
              <th>작성일</th>
              <th>비고</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${boardList}" var="board">
              <tr id="list-${board.id}" class="text-center">
                  <td>${board.id}</td>
                  <td>${board.title}</td>
                  <td>${board.content}</td>
                  <td>${board.createdAt}</td>
                  <td><button class="btn btn-danger btn-sm" onclick="deleteByBoardId(${board.id})">삭제</button></td> <!-- 삭제 버튼 추가 -->
              </tr>
            </c:forEach>
          </tbody>
        </table>
        </div>
              <div class="tab-pane fade" id="replyCare">
                <table class="table table-bordered">
            <table class="table table-bordered">
          <thead>
            <tr class="text-center">
              <th>번호</th>
              <th>내용</th>
              <th>게시물 번호</th>
              <th>작성일</th>
              <th>비고</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${replyList}" var="reply">
            <tr id="list-${reply.id}" class="text-center">
                <td>${reply.id}</td>
                <td>${reply.comment}</td>
                <td>${reply.boardId}</td>
                <td>${reply.createdAt}</td>
                <td><button class="btn btn-danger btn-sm" onclick="deleteByReplyId(${reply.id})">삭제</button></td> <!-- 삭제 버튼 추가 -->
            </tr>
            </c:forEach>
          </tbody>
            </table>
              </div>
            </div>
        </div>
      </div>
    </div>

    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>

        <script>
            function deleteByUserId(id) {
                $.ajax({
                    type: "delete",
                    url: "/user/"+id ,
                    dataType: "json"
                }).done((res) => {    // 20x 일때
                    alert(res.msg);
                    location.href = "/admin";
                    $('#list-'+id).remove();
                }).fail((err) => {    // 40x , 50x 일때
                    console.log(err);
                    alert(err.responseJSON.msg);
                });
            }
            function deleteByBoardId(id) {
                $.ajax({
                    type: "delete",
                    url: "/board/"+id ,
                    dataType: "json"
                }).done((res) => {    // 20x 일때
                    alert(res.msg);
                    location.href = "/admin";
                    $('#list-'+id).remove();
                }).fail((err) => {    // 40x , 50x 일때
                    // console.log(err);
                    alert(err.responseJSON.msg);
                });
            }
            function deleteByReplyId(id) {
                $.ajax({
                    type: "delete",
                    url: "/reply/"+id ,
                    dataType: "json"
                }).done((res) => {    // 20x 일때
                    alert(res.msg);
                    location.href = "/admin";
                    $('#list-'+id).remove();
                }).fail((err) => {    // 40x , 50x 일때
                    // console.log(err);
                    alert(err.responseJSON.msg);
                });
            }
        </script>



        <%@ include file="../layout/footer.jsp" %>