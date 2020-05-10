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
<style>
    .help { opacity: 0; margin-top: -24px;}

    a:hover + .help, .help:hover { opacity: 1; cursor: pointer; }

    .tooltip { border: 1px solid black; display: none; padding: 0.75em; width: 50%; text-align: center; font-family: sans-serif; font-size:0.8em; }
</style>
<div class="kt-container  kt-grid__item kt-grid__item--fluid">
    <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
    <c:set var="filter" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'filter')}"/>
    <c:if test="${filter.status}">
        <div class="accordion  accordion-toggle-arrow mb-2" id="accordionFilter">
            <div class="card" style="border-radius: 4px;">
                <div class="card-header">
                    <div class="card-title w-100" data-toggle="collapse" data-target="#filterContent" aria-expanded="true" aria-controls="collapseOne4">
                        <div class="row w-100">
                            <div class="col-3">
                                <i class="<c:out value="${filter.object.icon}"/>"></i>
                                <c:out value="${not empty contents?contents.size():0} sətr"/>
                            </div>
                            <div class="col-6 text-center" style="letter-spacing: 10px;">
                                <c:out value="${filter.object.name}"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="filterContent" class="collapse show" aria-labelledby="headingOne" data-parent="#accordionFilter">
                    <div class="card-body">
                        <form:form modelAttribute="filter" id="filter" method="post" action="/admin/log-file/filter">
                        <div class="row">
                                <div class="col-md-11">
                                    <div class="row">
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="count">Sətr sayı</form:label>
                                                <form:input path="count" cssClass="form-control" placeholder="######"/>
                                                <form:errors path="count" cssClass="control-label alert-danger"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-1 text-right">
                                    <div class="form-group">
                                        <a href="#" onclick="location.reload();" class="btn btn-danger btn-elevate btn-icon-sm btn-block mb-2" style="padding: 0.35rem 0.6rem;">
                                            <i class="la la-trash"></i> Sil
                                        </a>
                                        <a href="#" onclick="submit($('#filter'))" class="btn btn-warning btn-elevate btn-icon-sm btn-block mt-2" style="padding: 0.35rem 0.6rem">
                                            <i class="la la-search"></i> Axtar
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <div class="row">
        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
                <div class="kt-portlet__body">

    <c:if test="${not empty files}">
        <div class="row">
            <c:forEach var="t" items="${files}" varStatus="loop">
                <div class="col-md-2 text-center">
                    <c:choose>
                        <c:when test="${utl:checkDirectory(t)}">
                        <a href="/route/sub/admin/log-file/<c:out value="${fn:replace(t.path, '\\\\', '---')}"/>" class="m-2 item">
                            <i class="flaticon-folder-1 font-weight-bolder" style="font-size: 100px; color: #cfbb13"></i>
                            <div><c:out value="${t.name}"/></div>
                        </a>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${utl:checkTXTFile(t)}">
                                    <a href="#">
                                        <i class="flaticon-file-2" style="font-size: 100px; color: #cf4b38"></i>
                                        <div><c:out value="${t.name}"/></div>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="/route/sub/admin/log-file/<c:out value="${fn:replace(fn:replace(t.path, '\\\\', '---'), '/', '---')}"/>" class="m-2 item">
                                        <i class="flaticon-file-1 font-weight-bolder" style="font-size: 100px;"></i>
                                        <div><c:out value="${t.name}"/></div>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                    <div class="help">
                        <a href="javascript:deleteData('<c:out value="${path}"/><c:out value="${t.name}"/>', '<c:out value="${t.name}"/>')"><i class="la la-trash-o text-danger">Sil</i></a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
    <c:if test="${not empty contents}">
        <div class="row p-4" style="background: #2f2f2f">
            <div class="col-md-12" style="color: #e4e6da;">
                <c:forEach var="t" items="${contents}" varStatus="loop">
                    <c:out value="${t}"/><br/>
                </c:forEach>
            </div>
        </div>
    </c:if>
                </div>
            </div>
        </div>

    </div>
</div>