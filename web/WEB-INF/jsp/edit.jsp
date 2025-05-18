<%@ page import="com.urise.webapp.model.Resume" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.Resume.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <hr>
        <h3>Контакты</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.contactType}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <hr>
        <h3>Резюме:</h3>
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <dl>
                <dt>${sectionType.sectionName}:</dt>
                <c:choose>
                    <c:when test="${sectionType eq 'PERSONAL' or sectionType eq 'OBJECTIVE'}">
                        <dd><input type="text" name="${sectionType.name()}" size=50
                                   value="${resume.getSection(sectionType).content}"></dd>
                    </c:when>
                    <c:when test="${sectionType eq SectionType.ACHIEVEMENT or sectionType eq SectionType.QUALIFICATIONS}">
                        <dd>
                            <textarea name="${sectionType.name()}" rows="5" cols="50"
                             placeholder="Каждый пункт с новой строки"
                             style="white-space: pre;"><c:forEach items="${resume.getSection(sectionType).items}" var="item">${item}&#10;</c:forEach></textarea>
                        </dd>
                    </c:when>
                    <c:when test="${sectionType eq SectionType.EXPERIENCE or sectionType eq SectionType.EDUCATION}">
                        <dd>обработка organization section</dd>
                    </c:when>
                </c:choose>


            </dl>
        </c:forEach>

        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>


    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
