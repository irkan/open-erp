<%--
  Created by IntelliJ IDEA.
  User: irkan.ahmadov
  Date: 01.09.2019
  Time: 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="uj" uri="/WEB-INF/tld/UtilJson.tld"%>
<%@ taglib prefix="ua" uri="/WEB-INF/tld/UserAccess.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="kt-container  kt-grid__item kt-grid__item--fluid">
    <div class="kt-portlet kt-portlet--mobile">
        <div class="kt-portlet__body">
<c:choose>
    <c:when test="${not empty list}">
<table class="table table-striped- table-bordered table-hover table-checkable" id="kt_table_1">
    <thead>
    <tr>
        <th>№</th>
        <th>ID</th>
        <th>Ad</th>
        <th>Açıqlama</th>
        <th>URL path</th>
        <th>İkon</th>
        <th>Üst modul</th>
        <th>Əməliyyat</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="t" items="${list}" varStatus="loop">
        <tr>
            <td>${loop.index + 1}</td>
            <td><c:out value="${t.id}" /></td>
            <td><c:out value="${t.name}" /></td>
            <td><c:out value="${t.description}" /></td>
            <td><c:out value="${t.path}" /></td>
            <c:choose>
                <c:when test="${not empty t.icon}">
                    <td><i class="<c:out value="${t.icon.name}"/>"></i> <c:out value="${t.icon.name}" /></td>
                </c:when>
                <c:otherwise>
                    <td></td>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${not empty t.module}">
                    <td>
                        <span class="kt-badge kt-badge--danger kt-badge--inline kt-badge--pill"><c:out value="${t.module.name}" /></span>
                    </td>
                </c:when>
                <c:otherwise>
                    <td></td>
                </c:otherwise>
            </c:choose>
            <td nowrap class="text-center">
                <c:set var="view" value="${ua:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
                <c:choose>
                    <c:when test="${view.status}">
                        <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                            <i class="la <c:out value="${view.object.icon.name}"/>"></i>
                        </a>
                    </c:when>
                </c:choose>
                <c:set var="edit" value="${ua:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                <c:choose>
                    <c:when test="${edit.status}">
                        <a href="javascript:edit($('#form'), '<c:out value="${uj:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${edit.object.name}"/>">
                            <i class="<c:out value="${edit.object.icon}"/>"></i>
                        </a>
                    </c:when>
                </c:choose>
                <c:set var="delete" value="${ua:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
                <c:choose>
                    <c:when test="${delete.status}">
                        <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
                            <i class="<c:out value="${delete.object.icon}"/>"></i>
                        </a>
                    </c:when>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
    </c:when>
    <c:otherwise>
        Məlumat yoxdur
    </c:otherwise>
</c:choose>

        </div>
    </div>
</div>

<div class="modal fade" id="modal-operation" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" id="form" method="post" action="/admin/module" cssClass="form-group">
                    <div class="form-group">
                        <form:label path="module">Üst modul</form:label>
                        <form:select  path="module" cssClass="custom-select form-control">
                            <form:options items="${list}" itemLabel="name" itemValue="id" />
                        </form:select>
                    </div>
                    <div class="form-group">
                        <form:label path="name">Ad</form:label>
                        <form:input path="name" type="text" cssClass="form-control" placeholder="Modulun adını daxil edin" />
                        <form:errors path="name" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:input path="description" type="text" cssClass="form-control" placeholder="Modul açıqlamasını daxil edin" />
                    </div>
                    <div class="form-group">
                        <form:label path="path">URL Path</form:label>
                        <form:input path="path" type="text" cssClass="form-control" placeholder="Modul path daxil edin" />
                    </div>
                    <div class="form-group">
                        <form:label path="icon">İkon</form:label>
                        <form:select  path="icon" cssClass="custom-select form-control">
                            <form:options items="${icons}" itemLabel="name" itemValue="id" />
                        </form:select>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="$('#form').submit()">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>


