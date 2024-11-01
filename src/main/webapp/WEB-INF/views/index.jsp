<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>同步用户</title>
    <style type="text/css">
        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
        }

        th {
            background-color: #f2f2f2;
            text-align: left;
        }

        input[type="text"], input[type="email"] {
            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        button {
            background-color: #4CAF50;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
            width: 100%;
        }

        button:hover {
            opacity: 0.8;
        }
    </style>
</head>
<body>
    <h2>同步用户</h2>
    <form id="userForm">
        <label for="oname">原始用户名:</label>
        <input type="text" id="oname" name="oname" required="required">
        <label for="oemail">原始邮箱:</label>
        <input type="text" id="oemail" name="oemail" required="required">

        <label for="name">用户名:</label>
        <input type="text" id="name" name="name" required="required">
        <label for="email">邮箱:</label>
        <input type="email" id="email" name="email" required="required">

        <button type="submit">开始同步</button>
    </form>
</body>
    <script src="./jquery-3.7.1.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#userForm').on('submit', function(event) {
                event.preventDefault(); // 阻止表单默认提交行为

                // 获取表单数据
                const oname = $('#name').val();
                const oemail = $('#email').val();
                // 获取表单数据
                const name = $('#name').val();
                const email = $('#email').val();

                // 准备发送的数据
                const userData = {
                    oname: oname,
                    oemail: oemail,
                    name: name,
                    email: email
                };

                // 发送AJAX请求
                $.ajax({
                    url: '/api/users',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(userData),
                    success: function(response) {
                        // 清空表单
                        $('#oname').val('');
                        $('#oemail').val('');

                        // 清空表单
                        $('#name').val('');
                        $('#email').val('');
                    },
                    error: function(xhr, status, error) {
                        alert('添加用户失败，请稍后再试。');
                        console.error('Error:', error);
                    }
                });
            });
        });
    </script>
</html>