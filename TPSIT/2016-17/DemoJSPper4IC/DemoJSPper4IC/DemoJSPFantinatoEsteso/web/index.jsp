<%-- 
    Document   : index
    Created on : 24-mag-2017, 8.32.02
   --%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Problema incrementa e decrementa </title>
    </head>
    <body>
        <h1> Problema icrementa e decrementa </h1>
        <%
            String number=request.getParameter("txtNumber");
            int num;
            if(number!=null)
            {   int incremento=0;
                if (request.getParameter("btnIncr")!=null)incremento=1;
                if (request.getParameter("btnDecr")!=null)incremento=-1;
                num = Integer.parseInt(number) + incremento;
            }else{
                num=0;   
            }
        %>
        <form method="get" action='index.jsp'>
            <input type="submit"  value='Incrementa' name="btnIncr" />
             <input type="submit"  value='Decrementa' name="btnDecr" />
            <input type='text' name='txtNumber' value='<%= num%>'/>
        </form>
    </body>
</html>
