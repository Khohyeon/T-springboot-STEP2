<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ include file="../layout/header.jsp" %>

        <div class="container my-3">
            <c:if test="${boardDto.userId == principal.id}">
                <div class="mb-3">
                    <a href="/board/${boardDto.id}/updateForm" class="btn btn-warning">수정</a>
                    <button onclick="deleteById(${boardDto.id})" class="btn btn-danger">삭제</button>
                </div>
            </c:if>
            <div class="mb-2">
                글 번호 : <span id="id"><i>${boardDto.id} </i></span> 
                작성자 : <span class="me-3"><i>${boardDto.username} </i></span>
                <%-- heart 현재 false --%>
                   <%-- <c:if test="${boardDto.likeNum != 1}">
                   <i id="heart" onclick="likeInsertClick(${boardDto.id},${principal.id},${boardDto.likeNum})" class="fa-regular fa-heart my-xl my-cursor"></i>
                   </c:if>
                    <c:if test="${boardDto.likeNum == 1}">
                   <i id="heart" onclick="likeDeleteClick(${boardDto.id},${principal.id},${boardDto.likeNum})" class="fa-solid fa-heart my-xl my-cursor"></i>
                   </c:if> --%>
                   <c:choose>
                      <c:when test="${loveDto == null}">
                      <i id="heart" class="fa-regular fa-heart my-xl my-cursor" value="no"></i>
                      </c:when>
                   
                      <c:otherwise>
                      <i id="heart-${loveDto.id}" class="fa-solid fa-heart my-xl my-cursor"></i>
                      </c:otherwise>
                   </c:choose>
                    
            </div>

            <div>
                <h3>${boardDto.title}</h3>
            </div>
            <hr />
            <div>
                <div>${boardDto.content}</div>
            </div>
            <hr />

            <div class="card">
                <form action="/reply" method="post">
                    <input type="hidden" name="boardId" value="${boardDto.id}">
                    <div class="card-body">
                        <textarea name="comment" id="reply-comment" class="form-control" rows="1"></textarea>
                    </div>
                    <div class="card-footer">
                        <button type="submit" id="btn-reply-save" class="btn btn-primary">등록</button>
                    </div>
                </form>
            </div>
            <br />
            <div class="card">
                <div class="card-header">댓글 리스트</div>
                <ul id="reply-box" class="list-group">
                <c:forEach items="${replyDtos}" var="reply">
                    <li id="reply-${reply.id}" class="list-group-item d-flex justify-content-between">
                        <div>${reply.comment}</div>
                        <div class="d-flex">
                            <div class="font-italic">작성자 : ${reply.username} &nbsp;</div>
                            <button onclick="deleteByReplyId(${reply.id})" class="badge bg-secondary">삭제</button>
                        </div>
                    </li>
                </c:forEach>
                </ul>
            </div>
        </div>
       

    <script>
        $("#heart").click(() => {
            let value = $("#heart").val();
            if (value == "ok") {
        $("#heart").removeClass("fa-solid");
        $("#heart").val("no");
             } else {
        $("#heart").addClass("fa-solid");
        $("#heart").val("ok");
        }

        });
    </script>
    <script>
            // function likeInsertClick(id,userId,likeNum) {
            //     let data = {
            //         boardId: id,
            //         userId: userId,
            //         likeNum: likeNum
            //     };
            //     console.log(data.boardId);
            //     console.log(data.userId);
            //     console.log(data.likeNum);
            //     $.ajax({
            //         type: "post",
            //         url: "/like/"+id,
            //         data: JSON.stringify(data),
            //         contentType: 'application/json;charset=UTF-8',
            //         dataType: "json"  // default : 응답의 mime 타입으로 유추함
            //     }).done((res) => {    // 20x 일때
            //         // console.log(res);
            //         document.getElementById('heart').className = 'fa-solid fa-heart my-xl my-cursor';
            //     }).fail((err) => {    // 40x , 50x 일때
            //         console.log(err);
            //         alert(err.responseJSON.msg);
            //     });
            // }
            // function likeDeleteClick(id,userId) {
            //     let data = {
            //         boardId: id,
            //         userId: userId
            //     };
            //     console.log(data.boardId);
            //     console.log(data.userId);
            //     $.ajax({
            //         type: "delete",
            //         url: "/like/"+id,
            //         data: JSON.stringify(data),
            //         contentType: 'application/json;charset=UTF-8',
            //         dataType: "json"  // default : 응답의 mime 타입으로 유추함
            //     }).done((res) => {    // 20x 일때
            //         // console.log(res);
            //         document.getElementById('heart').className = 'fa-regular fa-heart my-xl my-cursor';
            //     }).fail((err) => {    // 40x , 50x 일때
            //         console.log(err);
            //         alert(err.responseJSON.msg);
            //     });
            // }
            function deleteByReplyId(id) {
                $.ajax({
                    type: "delete",
                    url: "/reply/" + id,
                    dataType: "json"
                }).done((res) => {
                    alert(res.msg);
                    location.href = "/";
                    $("reply-"+id).remove();
                }).fail((err) => {
                    
                });
            }

            function deleteById(id) {
                $.ajax({
                    type: "delete",
                    url: "/board/" + id,
                    dataType: "json"
                }).done((res) => {    // 20x 일때
                    alert(res.msg);
                    location.href = "/";
                }).fail((err) => {    // 40x , 50x 일때
                    // console.log(err);
                    alert(err.responseJSON.msg);
                });
            }
        </script>

        <%@ include file="../layout/footer.jsp" %>