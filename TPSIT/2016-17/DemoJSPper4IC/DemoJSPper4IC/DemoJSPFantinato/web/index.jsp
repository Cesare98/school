<%-- 
    Document   : index
    Created on : 24-mag-2017, 8.32.02
   --%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World! Demo JSP </h1>
        <%
            String number=request.getParameter("txtNumber");
            int num;
            if(number!=null)
            {
                num=Integer.parseInt(number)+1;
            }else{
                num=0;   
            }
        %>
        <form method="get" action='index.jsp'>
            <input type="submit"  value='Incrementa' />
            <input type='text' name='txtNumber' value='<%= num%>'/>
        </form>
    </body>
</html>
