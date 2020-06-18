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
    <div class="kt-portlet kt-portlet--mobile">
        <div class="kt-portlet__body">
<c:choose>
    <c:when test="${not empty list}">
        <c:set var="view" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'view')}"/>
        <c:set var="export" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'export')}"/>
        <c:set var="edit" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'edit')}"/>
        <c:set var="delete" value="${utl:checkOperation(sessionScope.user.userModuleOperations, page, 'delete')}"/>
<table class="table table-striped- table-bordered table-hover table-checkable" id="datatable">
    <thead>
    <tr>
        <th>№</th>
        <th>ID</th>
        <th>Ad</th>
        <th>URL path</th>
        <th>İkon</th>
        <th>Əməliyyat</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="t" items="${list}" varStatus="loop">
        <tr data="<c:out value="${utl:toJson(t)}" />">
            <td>${loop.index + 1}</td>
            <td><c:out value="${t.id}" /></td>
            <td><c:out value="${t.name}" /></td>
            <td><c:out value="${t.path}" /></td>
            <td><i class="<c:out value="${t.icon}"/>"></i> <c:out value="${t.icon}" /></td>
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
                    <a href="javascript:deleteData('<c:out value="${t.id}" />', '<c:out value="${t.name}" />');" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="<c:out value="${delete.object.name}"/>">
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

<div class="modal fade" id="modal-operation" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Yeni əməliyyat yarat</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form modelAttribute="form" method="post" id="form" action="/admin/operation" cssClass="form-group">
                    <form:hidden path="id"/>
                    <div class="form-group">
                        <form:label path="name">Ad</form:label>
                        <form:input path="name" cssClass="form-control" placeholder="Modulun adını daxil edin" />
                    </div>
                    <div class="form-group">
                        <form:label path="path">URL Path</form:label>
                        <form:input path="path" cssClass="form-control" placeholder="Modul path daxil edin"/>
                    </div>
                    <div class="form-group">
                        <form:label path="icon">İkon</form:label>
                        <form:input path="icon" cssClass="form-control" placeholder="İkon adını daxil edin" />
                        <form:errors path="icon" cssClass="alert-danger control-label"/>
                        <div class="text-right" style="width: 100%">
                            <a href="/route/sub/admin/flat-icon" target="_blank" class="kt-link kt-font-sm kt-font-bold kt-margin-t-5">Flat ikonlardan ikon seçin</a>
                        </div>
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
        <c:if test="${edit.status}">
        edit($('#form'), $(this).attr('data'), 'modal-operation', '<c:out value="${edit.object.name}" />');
        </c:if>
        <c:if test="${!edit.status and view.status}">
        view($('#form'), $(this).attr('data'), 'modal-operation', '<c:out value="${view.object.name}" />');
        </c:if>
    });

    $("#datatable").DataTable({
        <c:if test="${export.status}">
        dom: 'Bfrtip',
        buttons: [
            'copy', 'csv', 'excel', 'pdf', 'print'
        ],
        </c:if>
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
            name: {
                required: true
            },
            path: {
                required: true
            }
        },
        invalidHandler: function(event, validator) {
                    KTUtil.scrollTop();
            swal.close();
        },
    })
</script>


