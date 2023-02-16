<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp" %>

<div class="container-fluid mt-5">
    <div class="row">
        <!-- 왼쪽 카테고리 -->
        <div class="col-md-3">
            <div class="list-group">
                <a href="/admin/userManagement" class="list-group-item list-group-item-action">회원 관리</a>
                <a href="/admin/boardManagement" class="list-group-item list-group-item-action">게시글 관리</a>
                <a href="/admin/replyManagement" class="list-group-item list-group-item-action active">댓글 관리</a>
            </div>
        </div>
        <!-- 오른쪽 테이블 -->
    <div class="col-md-9">
        <table class="table table-bordered">
          <thead>
            <tr class="text-center">
              <th>번호</th>
              <th>내용</th>
              <th>작성자</th>
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
                <td>${reply.username}</td>
                <td>${reply.boardId}</td>
                <td>${reply.createdAt}</td>
                <td><button type="button" class="btn btn-danger btn-sm" onclick="deleteByReplyId(${reply.id})">삭제</button></td> <!-- 삭제 버튼 추가 -->
            </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
</div>

<script>
    function deleteByReplyId(id) {
            $.ajax({
                type: "delete",
                url: "/reply/" + id,
                dataType: "json"
            })
            .done((res)=>{ // 20X 일 때
                alert(res.msg);
                $('#list-' + id).remove();
            })
            .fail((err)=>{ // 40X, 50X 일 때
                alert(err.responseJSON.msg);
            })
        }
</script>


<%@ include file="../layout/footer.jsp" %>