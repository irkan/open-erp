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
<div class="kt-container  kt-grid__item kt-grid__item--fluid">
    <div class="row">
        <div class="col-lg-12">
            <div class="kt-portlet kt-portlet--mobile">
                <div class="kt-portlet__body">

<c:choose>
    <c:when test="${not empty list}">
<table class="table table-striped- table-bordered table-hover table-checkable" id="group_table">
    <thead>
    <tr>
        <th>№</th>
        <th>ID</th>
        <th>Üst modul</th>
        <th>Modul</th>
        <th>Əməliyyat</th>
        <th>Əməliyyat</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="t" items="${list}" varStatus="loop">
        <c:choose>
            <c:when test="${not empty t.module.module}">
                <tr data="<c:out value="${utl:toJson(t)}" />">
                    <td>${loop.index + 1}</td>
                    <td><c:out value="${t.id}" /></td>
                    <td><c:out value="${t.module.module.name}" /></td>
                    <td><c:out value="${t.module.name}" /></td>
                    <td><c:out value="${t.operation.name}" /></td>>
                    <td nowrap class="text-center">
                        <c:set var="view" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
                        <c:choose>
                            <c:when test="${view.status}">
                                <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                                    <i class="la <c:out value="${view.object.icon.name}"/>"></i>
                                </a>
                            </c:when>
                        </c:choose>
                        <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
                        <c:choose>
                            <c:when test="${edit.status}">
                                <a href="javascript:edit($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${edit.object.name}"/>">
                                    <i class="<c:out value="${edit.object.icon}"/>"></i>
                                </a>
                            </c:when>
                        </c:choose>
                        <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
                        <c:choose>
                            <c:when test="${delete.status}">
                                <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.module.name}" /> - <c:out value="${t.operation.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
                                    <i class="<c:out value="${delete.object.icon}"/>"></i>
                                </a>
                            </c:when>
                        </c:choose>
                    </td>
                </tr>
            </c:when>
        </c:choose>
    </c:forEach>
    </tbody>
</table>

    </c:when>
    <c:otherwise>
        <div class="row">
                                <div class="col-md-12 text-center" style="letter-spacing: 10px;">
                                    Məlumat yoxdur
                                </div>
                            </div>
    </c:otherwise>
</c:choose>
                </div>
            </div>
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
                <form:form modelAttribute="form" id="form" method="post" action="/admin/module-operation" cssClass="form-group">
                    <form:input type="hidden" name="id" path="id"/>
                    <div class="form-group">
                        <form:label path="module">Modul</form:label>
                        <form:select  path="module" cssClass="custom-select form-control">
                            <form:options items="${modules}" itemLabel="name" itemValue="id" />
                        </form:select>
                    </div>
                    <div class="form-group">
                        <form:label path="operation">Əməliyyat</form:label>
                        <form:select  path="operation" cssClass="custom-select form-control">
                            <form:options items="${operations}" itemLabel="name" itemValue="id" />
                        </form:select>
                    </div>
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="submit($('#form'));">Yadda saxla</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Bağla</button>
            </div>
        </div>
    </div>
</div>

<script>
    <c:if test="${edit.status}">
    $('#group_table tbody').on('dblclick', 'tr', function () {
        edit($('#form'), $(this).attr('data'), 'modal-operation', 'Redaktə');
    });
    </c:if>
</script>

<script src="<c:url value="/assets/js/demo4/pages/crud/datatables/advanced/row-grouping.js" />" type="text/javascript"></script>




