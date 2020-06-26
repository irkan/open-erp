<%@ page import="java.util.Date" %><%--
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
        <c:set var="export" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'export')}"/>
        <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
        <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
        <c:set var="today" value="<%=new Date()%>"/>
        <table class="table table-striped- table-bordered table-hover table-checkable" id="group_table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Əməkdaş</th>
                <th>Struktur</th>
                <th>İstifadəçi</th>
                <th>Tarixdən</th>
                <th>Tarixədək</th>
                <th>İcazə yeniləndi</th>
                <th style="max-width: 100px;">Geriyə əməliyyat günlərinin sayı</th>
                <th>Açıqlama</th>
                <th>Əməliyyat</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="t" items="${list}" varStatus="loop">
                <tr data="<c:out value="${utl:toJson(t)}" />" class="<c:out value="${t.permissionDateTo.getTime()<today.getTime()?'kt-bg-danger':(t.permissionDateFrom.getTime()>today.getTime()?'kt-bg-warning':'')}" />">
                    <td><c:out value="${t.id}" /></td>
                    <td><c:out value="${t.user.employee.person.fullName}" /></td>
                    <td><c:out value="${t.user.employee.organization.name}" /></td>
                    <td><c:out value="${t.user.username}" /></td>
                    <td><fmt:formatDate value = "${t.permissionDateFrom}" pattern = "dd.MM.yyyy" /></td>
                    <td><fmt:formatDate value = "${t.permissionDateTo}" pattern = "dd.MM.yyyy" /></td>
                    <td><fmt:formatDate value = "${t.createdDate}" pattern = "dd.MM.yyyy HH:mm:ss" /></td>
                    <td><c:out value="${t.backOperationDays}" /></td>
                    <td><c:out value="${t.permissionDateTo.getTime()<today.getTime()?'İcazə qüvvədən düşəcək':(t.permissionDateFrom.getTime()>today.getTime()?'İcazə qüvvəyə minəcək':'')}" /> <c:out value="${t.description}" /></td>
                    <td nowrap class="text-center">
                        <c:if test="${view.status}">
                            <a href="javascript:view($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${view.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${view.object.name}"/>">
                                <i class="<c:out value="${view.object.icon}"/>"></i>
                            </a>
                        </c:if>
                        <c:if test="${edit.status}">
                            <a href="javascript:edit($('#form'), '<c:out value="${utl:toJson(t)}" />', 'modal-operation', '<c:out value="${edit.object.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${edit.object.name}"/>">
                                <i class="<c:out value="${edit.object.icon}"/>"></i>
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
    <div class="modal-dialog modal-sm" role="document">
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
                    <div class="form-group">
                        <form:label path="permissionDateFrom">Tarixdən</form:label>
                        <div class="input-group date">
                            <div class="input-group-prepend">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                            </div>
                            <form:input path="permissionDateFrom" autocomplete="off"
                                        cssClass="form-control datepicker-element" date_="date_"
                                        placeholder="dd.MM.yyyy"/>
                        </div>
                        <form:errors path="permissionDateFrom" cssClass="control-label alert-danger"/>
                    </div>
                    <div class="form-group">
                        <form:label path="permissionDateTo">Tarixədək</form:label>
                        <div class="input-group date">
                            <div class="input-group-prepend">
                                        <span class="input-group-text">
                                            <i class="la la-calendar"></i>
                                        </span>
                            </div>
                            <form:input path="permissionDateTo" autocomplete="off"
                                        cssClass="form-control datepicker-element" date_="date_"
                                        placeholder="dd.MM.yyyy"/>
                        </div>
                        <form:errors path="permissionDateTo" cssClass="control-label alert-danger"/>
                    </div>
                    <div class="form-group">
                        <form:label path="backOperationDays">Geriyə əməliyyat günlərinin sayı</form:label>
                        <div class="input-group date">
                            <div class="input-group-prepend">
                                        <span class="input-group-text">
                                            <i class="la la-calculator"></i>
                                        </span>
                            </div>
                            <form:input path="backOperationDays" cssClass="form-control" placeholder="Daxil edin"/>
                        </div>
                        <form:errors path="backOperationDays" cssClass="control-label alert-danger"/>
                    </div>
                    <div class="form-group">
                        <form:label path="description">Açıqlama</form:label>
                        <form:textarea path="description" cssClass="form-control" placeholder="Açıqlama daxil edin" rows="4"/>
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
    $('#group_table tbody').on('dblclick', 'tr', function () {
        <c:if test="${view.status}">
            view($('#form'), $(this).attr('data'), 'modal-operation', '<c:out value="${view.object.name}" />');
        </c:if>
    });

    $('#group_table').DataTable({
        <c:if test="${export.status}">
        dom: 'B<"clear">lfrtip',
        buttons: [
            'copy', 'csv', 'excel', 'pdf', 'print'
        ],
        </c:if>
        responsive: true,
        pageLength: 100,
        order: [[2, 'asc']],
        drawCallback: function(settings) {
            var api = this.api();
            var rows = api.rows({page: 'current'}).nodes();
            var last = null;

            api.column(2, {page: 'current'}).data().each(function(group, i) {
                if (last !== group) {
                    $(rows).eq(i).before(
                        '<tr class="group"><td colspan="30">' + group + '</td></tr>'
                    );
                    last = group;
                }
            });
        },
        columnDefs: [
            {
                targets: [2],
                visible: false
            }
        ]
    });

    $( "#form" ).validate({
        rules: {
            user: {
                required: true
            },
            description: {
                maxlength: 80
            },
            backOperationDays: {
                required: true,
                number: true,
                min: 1,
                max: 1824
            },
            permissionDateFrom: {
                required: true
            },
            permissionDateTo: {
                required: true
            }
        },
        invalidHandler: function(event, validator) {
                    KTUtil.scrollTop();
            swal.close();
        },
    })
</script>


