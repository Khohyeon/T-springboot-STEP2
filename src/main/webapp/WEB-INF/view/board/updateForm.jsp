<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ include file="../layout/header.jsp" %>

        <div class="container my-3">
            <form>
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Enter title" name="title" id="title"
                        value="${board.title}">
                </div>

                <div class="form-group">
                    <textarea class="form-control summernote" rows="5" id="content" name="content" value="">${board.content}
                    
                </textarea>
                </div>
            </form>
            <button onclick="updateById(${board.id})" type="button" class="btn btn-primary">글수정완료</button>

        </div>

    <script>
         function updateById(id) {
            let data = {
                title: $("#title").val(),
                content: $("#content").val()
            };
            // console.log(data.title);
            $.ajax({
                type:"put",
                url:"/board/"+id+"/update",
                data:JSON.stringify(data),
                contentType:'application/json;charset=UTF-8',
            }).done((res)=>{    // 20x 일때
                console.log(res);
                alert(res.msg);
                location.href="/";
            }).fail((err)=>{    // 40x , 50x 일때
                // console.log(err);
                alert(err.responseJSON.msg);
            });
        }
            $('.summernote').summernote({
                tabsize: 2,
                height: 400
            });
    </script>

        <%@ include file="../layout/footer.jsp" %>