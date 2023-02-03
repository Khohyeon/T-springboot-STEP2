<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <%@ include file="../layout/header.jsp" %>

        <div class="container my-3">
            <div class="container">
                <form action="/join" method="post" onsubmit="return valid()">
                    <div class="d-flex form-group mb-2">
                        <input type="text" name="username" class="form-control" placeholder="Enter username"
                            id="username">
                        <button type="button" onclick="sameCheck()" class="badge bg-secondary ms-2">중복확인</button>
                    </div>

                    <div class="form-group mb-2">
                        <input type="password" name="password" class="form-control" placeholder="Enter password"
                            id="password">
                    </div>

                    <div class="form-group mb-2">
                        <input type="password" class="form-control" placeholder="Enter passwordCheck"
                            id="passwordCheck">
                    </div>

                    <div class="form-group mb-2">
                        <input type="email" name="email" class="form-control" placeholder="Enter email" id="email">
                    </div>

                    <button type="submit" class="btn btn-primary">회원가입</button>
                </form>

            </div>
        </div>

        <script>
            let submitCheck = false;

                function valid() {
                    if (submitCheck) {
                        return true;
                    } else {
                        alert("유저네임 중복체크를 해주세요");
                        return false;
                    }
                }
            function sameCheck() {
                    let username = $("#username").val();

                    $.ajax({
                        type: "get",
                        url: "/user/usernameSameCheck?username=" + username
                    }).done((res) => {
                        //console.log(res);
                        if (res.data === true) {
                            alert(res.msg);
                            submitCheck = true;
                        } else {
                            alert(res.msg);
                            submitCheck = false;
                        }
                    }).fail((err) => {
                    });
                }
        </script>

        <%@ include file="../layout/footer.jsp" %>