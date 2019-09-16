<%--
    Document   : footer
    Created on : Sep 3, 2019, 11:19:03 AM
    Author     : iahmadov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ua" uri="/WEB-INF/tld/UserAccess.tld"%>
<div class="kt-body kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor kt-grid--stretch" id="kt_body">
    <div class="kt-content kt-content--fit-top  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">

        <!-- begin:: Subheader -->
        <div class="kt-subheader   kt-grid__item" id="kt_subheader">
            <div class="kt-container ">
                <div class="kt-subheader__main">
                    <h3 class="kt-subheader__title">${sessionScope.module_description}</h3>


                </div>
                <div class="kt-subheader__toolbar">
                    <div class="kt-subheader__wrapper">
                        <c:set var="export" value="${ua:checkOperation(sessionScope.user.userModuleOperations, page, 'export')}"/>
                        <c:choose>
                            <c:when test="${export.status}">
                                <div class="dropdown dropdown-inline">
                                    <button type="button" style="color: white" class="btn btn-default btn-font-light btn-icon-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        <i class="la <c:out value="${export.object.icon.name}"/>"></i> <c:out value="${export.object.name}"/>
                                    </button>
                                    <div class="dropdown-menu dropdown-menu-right">
                                        <ul class="kt-nav">
                                            <li class="kt-nav__section kt-nav__section--first">
                                                <span class="kt-nav__section-text">Birini seçin</span>
                                            </li>
                                            <li class="kt-nav__item">
                                                <a href="#" class="kt-nav__link">
                                                    <i class="kt-nav__link-icon la la-print"></i>
                                                    <span class="kt-nav__link-text">Çap et</span>
                                                </a>
                                            </li>
                                            <li class="kt-nav__item">
                                                <a href="#" class="kt-nav__link">
                                                    <i class="kt-nav__link-icon la la-file-excel-o"></i>
                                                    <span class="kt-nav__link-text">Excel</span>
                                                </a>
                                            </li>
                                            <li class="kt-nav__item">
                                                <a href="#" class="kt-nav__link">
                                                    <i class="kt-nav__link-icon la la-file-text-o"></i>
                                                    <span class="kt-nav__link-text">CSV</span>
                                                </a>
                                            </li>
                                            <li class="kt-nav__item">
                                                <a href="#" class="kt-nav__link">
                                                    <i class="kt-nav__link-icon la la-file-pdf-o"></i>
                                                    <span class="kt-nav__link-text">PDF</span>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </c:when>
                        </c:choose>
                        <c:set var="create" value="${ua:checkOperation(sessionScope.user.userModuleOperations, page, 'create')}"/>
                        <c:choose>
                            <c:when test="${create.status}">
                                <a href="#" class="btn btn-danger" data-toggle="modal" data-target="#modal-operation">
                                    <i class="la <c:out value="${create.object.icon.name}"/>"></i>
                                    <c:out value="${create.object.name}"/>
                                </a>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="pages/${sessionScope.page}.jsp" ></jsp:include>
    </div>
</div>