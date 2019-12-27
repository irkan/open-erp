<%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 01.09.2019
  Time: 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="utl" uri="/WEB-INF/tld/Util.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="kt-portlet kt-portlet--height-fluid- pt-5">
    <div class="kt-portlet__body kt-portlet__body--fit-y">
        <!--begin::Widget -->
        <div class="kt-widget kt-widget--user-profile-1">
            <div class="kt-widget__head">
                <div class="kt-widget__media">
                    <img src="<c:url value="/assets/media/users/default.jpg" />" alt="image">
                </div>
                <div class="kt-widget__content">
                    <div class="kt-widget__section">
                        <a href="#" class="kt-widget__username">
                            <c:out value="${sessionScope.user.employee.person.firstName}"/> <c:out value="${sessionScope.user.employee.person.lastName}"/>
                            <i class="flaticon2-correct kt-font-success"></i>
                        </a>
                        <span class="kt-widget__subtitle">
                            <c:out value="${sessionScope.user.employee.position.name}"/>
                        </span>
                    </div>
                </div>
            </div>
            <div class="kt-widget__body">
                <div class="kt-widget__content">
                    <div class="kt-widget__info">
                        <span class="kt-widget__label">Email:</span>
                        <a href="#" class="kt-widget__data"><c:out value="${sessionScope.user.employee.person.contact.email}"/></a>
                    </div>
                    <div class="kt-widget__info">
                        <span class="kt-widget__label">Telefon:</span>
                        <a href="#" class="kt-widget__data"><c:out value="${sessionScope.user.employee.person.contact.mobilePhone}"/></a>
                    </div>
                    <div class="kt-widget__info">
                        <span class="kt-widget__label">İş yeri:</span>
                        <span class="kt-widget__data"><c:out value="${sessionScope.user.employee.organization.name}"/></span>
                    </div>
                </div>
                <div class="kt-separator kt-separator--border-dashed"></div>
                <div class="kt-widget__items">
                    <c:forEach var="t" items="${sessionScope.modules}" varStatus="loop">
                        <c:if test="${t.module.path eq 'profile'}">
                            <c:set var="active" value="${page eq t.path ? 'kt-widget__item--active' : ''}"/>
                            <a href="/route/sub/<c:out value="${t.module.path}"/>/<c:out value="${t.path}"/>" class="kt-widget__item <c:out value="${active}"/>">
                                <span class="kt-widget__section">
                                    <span class="kt-widget__icon">
                                        <i class="<c:out value="${t.icon}"/>"></i>
                                    </span>
                                    <span  class="kt-widget__desc">
                                        <c:out value="${t.name}"/>
                                    </span>
                                </span>
                            </a>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>