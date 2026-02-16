<%-- PASSO 1: A PRIMEIRA LINHA DEVE SER SEMPRE ESTA --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <%-- PASSO 2: ADICIONE ESTA META TAG DENTRO DO HEAD --%>
    <meta charset="UTF-8">
    <title>Relatório de Sincronização TQI</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background: #f4f4f4; padding: 20px; }
        table { width: 100%; border-collapse: collapse; background: white; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        th, td { padding: 10px; border: 1px solid #ddd; text-align: left; }
        th { background: #007bff; color: white; }
        tr:hover { background-color: #f9f9f9; }
    </style>
</head>
<body>
    <h2>Relatório de Transações - Legacy Sync Cloud</h2>
    <table>
        <tr>
            <th>ID</th>
            <th>Conta</th>
            <th>Valor</th>
            <th>Status</th>
            <th>Data Processamento</th>
        </tr>
        <c:forEach items="${transacoes}" var="t">
            <tr>
                <td style="font-size: 0.8em; color: #777;">${t.id}</td>
                <td>${t.accountNumber}</td>
                <td>R$ ${t.amount}</td>
                <td>${t.status}</td>
                <td>${t.createdAt}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>