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
        <c:set var="view" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
        <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
        <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
        <table class="table table-striped- table-bordered table-hover table-checkable" id="datatable">
            <thead>
            <tr>
                <th>№</th>
                <th>ID</th>
                <th>Struktur</th>
                <th>Əməkdaş</th>
                <th>İstifadəçi</th>
                <th>Tarixdən</th>
                <th>Tarixədək</th>
                <th>İcazə yeniləndi</th>
                <th>Açıqlama</th>
                <th>Əməliyyat</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="t" items="${list}" varStatus="loop">
                <tr data="<c:out value="${utl:toJson(t)}" />">
                    <td>${loop.index + 1}</td>
                    <td><c:out value="${t.id}" /></td>
                    <td><c:out value="${t.user.employee.organization.name}" /></td>
                    <td><c:out value="${t.user.employee.person.fullName}" /></td>
                    <td><c:out value="${t.user.username}" /></td>
                    <td><fmt:formatDate value = "${t.permissionDateFrom}" pattern = "dd.MM.yyyy" /></td>
                    <td><fmt:formatDate value = "${t.permissionDateTo}" pattern = "dd.MM.yyyy" /></td>
                    <td><fmt:formatDate value = "${t.createdDate}" pattern = "dd.MM.yyyy HH:mm:ss" /></td>
                    <td><c:out value="${t.description}" /></td>
                    <td nowrap class="text-center">
                        <c:if test="${view.status}">
                            <a href="javascript:view($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${view.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                                <i class="<c:out value="${view.object.icon}"/>"></i>
                            </a>
                        </c:if>
                        <c:if test="${delete.status}">
                            <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.user.username}" /><br/><c:out value="${t.description}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
                                <i class="<c:out value="${delete.object.icon}"/>"></i>
                            </a>
                        </c:if>
                    </td>
                </tr>
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
                <form:form modelAttribute="form" id="form" method="post" action="/admin/approver-exception" cssClass="form-group">
                    <form:hidden path="id"/>
                    <div class="form-group">
                        <form:label path="user">İstifadəçi</form:label>
                        <form:select  path="user" cssClass="custom-select form-control" onchange="getUserModuleOperation($(this).val())">
                            <form:option value="" />
                            <form:options items="${users}" itemLabel="employee.person.fullName" itemValue="id"  />
                        </form:select>
                        <form:errors path="user" cssClass="alert-danger control-label"/>
                    </div>
                    <div class="row">
                        <div class="col-6">
                            <div class="form-group">
                                <form:label path="permissionDateFrom">Tarixdən</form:label>
                                <div class="input-group date">
                                    <form:input path="permissionDateFrom" autocomplete="off"
                                                cssClass="form-control datepicker-element" date_="date_"
                                                placeholder="dd.MM.yyyy"/>
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                    </div>
                                </div>
                                <form:errors path="permissionDateFrom" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="form-group">
                                <form:label path="permissionDateTo">Tarixədək</form:label>
                                <div class="input-group date">
                                    <form:input path="permissionDateTo" autocomplete="off"
                                                cssClass="form-control datepicker-element" date_="date_"
                                                placeholder="dd.MM.yyyy"/>
                                    <div class="input-group-append">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                                    </div>
                                </div>
                                <form:errors path="permissionDateTo" cssClass="control-label alert-danger"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:textarea path="description" cssClass="form-control" placeholder="Açıqlama daxil edin" />
                        <form:errors path="description" cssClass="alert alert-danger"/>
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
    $('#datatable tbody').on('dblclick', 'tr', function () {
        <c:if test="${view.status}">
            view($('#form'), $(this).attr('data'), 'modal-operation', '<c:out value="${view.object.name}" />');
        </c:if>
    });

    $("#datatable").DataTable({
        responsive: true,
        lengthMenu: [10, 25, 50, 75, 100, 200, 1000],
        pageLength: 100,
        order: [[1, 'desc']],
        columnDefs: [
            {
                targets: 0,
                width: '25px',
                className: 'dt-center',
                orderable: false
            },
        ],
    });

    $( "#form" ).validate({
        rules: {
            user: {
                required: true
            }
        },
        invalidHandler: function(event, validator) {
                    KTUtil.scrollTop();
            swal.close();
        },
    })
</script>


