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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <h3>Контакты</h3>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.Resume.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.Section>"/>
    <dl>
        <dt><b>${sectionEntry.key.sectionName}</b></dt>
        <dd>
            <c:choose>
                <c:when test="${sectionEntry.value.getClass().simpleName == 'TextSection'}">
                    ${sectionEntry.value.content}
                </c:when>
                <c:when test="${sectionEntry.value.getClass().simpleName == 'ListSection'}">
                    <ul><c:forEach var="item" items="${sectionEntry.value.items}">
                        <li>${item}</li>
                    </c:forEach>
                    </ul>
                </c:when>
                <c:when test="${sectionEntry.value.getClass().simpleName == 'OrganizationSection'}">
                    <c:forEach var="org" items="${sectionEntry.value.organizations}">
                        <div class="organization">
                            <c:choose>
                                <c:when test="${empty org.homePage.url}">
                                    <h4>${org.homePage.name}</h4>
                                </c:when>
                                <c:otherwise>
                                    <h4><a href="${org.homePage.url}">${org.homePage.name}</a></h4>
                                </c:otherwise>
                            </c:choose>
                            <c:forEach var="period" items="${org.periods}">
                                <div class="period">
                                    <p><strong>${period.title}</strong></p>
                                    <p>с ${period.startDate} по ${period.endDate}</p>
                                    <c:if test="${not empty period.description}">
                                        <p>${period.description}</p>
                                    </c:if>
                                </div>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
        </dd>

    </dl>

    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
